package com.example.weather_app.controller;

import org.springframework.ui.Model;
import com.example.weather_app.model.WeatherResponse;
import com.example.weather_app.model.ForecastResponse;
import com.example.weather_app.model.ForecastData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class WeatherController {
    @Value("${api.key}")
    private String apiKey;

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city, 
                           @RequestParam(value = "selectedCity", required = false) String selectedCity, 
                           Model model){
        
        // Use selectedCity if available, otherwise use the typed city name
        String cityToSearch = (selectedCity != null && !selectedCity.isEmpty()) ? selectedCity : city;
        
        String url ="https://api.openweathermap.org/data/2.5/weather?q=" + cityToSearch + "&appId=" + apiKey + "&units=metric"; 
        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);

        if(weatherResponse != null){
            model.addAttribute("city",weatherResponse.getName());
            model.addAttribute("country",weatherResponse.getSys().getCountry());
            model.addAttribute("weatherDescription",weatherResponse.getWeather().get(0).getDescription());
            model.addAttribute("temperature",weatherResponse.getMain().getTemp());
            model.addAttribute("humidity",weatherResponse.getMain().getHumidity());
            model.addAttribute("windSpeed",weatherResponse.getWind().getSpeed());
            String weatherIcon = "wi wi-owm-" + weatherResponse.getWeather().get(0).getId();
            model.addAttribute("weatherIcon", weatherIcon);
            
            // Add current date and time
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
            
            model.addAttribute("currentDate", currentDateTime.format(dateFormatter));
            model.addAttribute("currentTime", currentDateTime.format(timeFormatter));
            
            // Fetch forecast data for tomorrow and day after tomorrow
            List<ForecastData> forecast = getWeeklyForecastData(cityToSearch);
            model.addAttribute("forecast", forecast);
        } else {
            model.addAttribute("error", "City not found.");
        }

        return "weather";

    }

    @GetMapping("/search-cities")
    @ResponseBody
    public List<Map<String, String>> searchCities(@RequestParam("query") String query) {
        List<Map<String, String>> cities = new ArrayList<>();
        
        if (query.length() < 2) {
            return cities; // Return empty list for short queries
        }
        
        try {
            String geoUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + query + "&limit=5&appid=" + apiKey;
            RestTemplate restTemplate = new RestTemplate();
            
            // The geocoding API returns an array of location objects
            List<Map<String, Object>> geoResponse = restTemplate.getForObject(geoUrl, List.class);
            
            if (geoResponse != null) {
                for (Map<String, Object> location : geoResponse) {
                    Map<String, String> city = new HashMap<>();
                    String name = (String) location.get("name");
                    String country = (String) location.get("country");
                    String state = (String) location.get("state");
                    
                    String displayName = name + ", " + country;
                    if (state != null && !state.isEmpty()) {
                        displayName = name + ", " + state + ", " + country;
                    }
                    
                    city.put("name", name);
                    city.put("country", country);
                    city.put("displayName", displayName);
                    city.put("fullName", name + "," + country); // For API call
                    
                    cities.add(city);
                }
            }
        } catch (Exception e) {
            // Log error and return empty list
            System.err.println("Error fetching cities: " + e.getMessage());
        }
        
        return cities;
    }
    
    private List<ForecastData> getWeeklyForecastData(String cityToSearch) {
        List<ForecastData> forecastList = new ArrayList<>();
        
        try {
            String forecastUrl = "https://api.openweathermap.org/data/2.5/forecast?q=" + cityToSearch + "&appid=" + apiKey + "&units=metric";
            RestTemplate restTemplate = new RestTemplate();
            ForecastResponse forecastResponse = restTemplate.getForObject(forecastUrl, ForecastResponse.class);
            
            if (forecastResponse != null && forecastResponse.getList() != null) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE"); // Short day name (Mon, Tue, etc.)
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd");
                
                // Group forecast data by day for up to 5 days (API provides 5-day forecast)
                Map<String, List<ForecastResponse.ForecastItem>> dailyData = new HashMap<>();
                
                for (ForecastResponse.ForecastItem item : forecastResponse.getList()) {
                    if (item.getDt_txt() != null) {
                        LocalDateTime itemDateTime = LocalDateTime.parse(item.getDt_txt().replace(" ", "T"));
                        String dayKey = itemDateTime.toLocalDate().toString();
                        
                        // Skip today, start from tomorrow
                        if (itemDateTime.toLocalDate().isAfter(now.toLocalDate())) {
                            dailyData.computeIfAbsent(dayKey, k -> new ArrayList<>()).add(item);
                        }
                    }
                }
                
                // Calculate averages for each day and sort by date
                dailyData.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        LocalDateTime date = LocalDateTime.parse(entry.getKey() + "T12:00:00"); // Use noon as reference
                        ForecastData dayAverage = calculateAverageForecast(entry.getValue(), date, dayFormatter, dateFormatter);
                        forecastList.add(dayAverage);
                    });
            }
        } catch (Exception e) {
            System.err.println("Error fetching forecast data: " + e.getMessage());
        }
        
        return forecastList;
    }
    
    private ForecastData calculateAverageForecast(List<ForecastResponse.ForecastItem> dayData, LocalDateTime date, 
                                                DateTimeFormatter dayFormatter, DateTimeFormatter dateFormatter) {
        double totalTemp = 0;
        double totalHumidity = 0;
        double totalWindSpeed = 0;
        Map<String, Integer> weatherDescriptions = new HashMap<>();
        Map<Integer, Integer> weatherIds = new HashMap<>();
        
        // Calculate averages and find most common weather condition
        for (ForecastResponse.ForecastItem item : dayData) {
            totalTemp += item.getMain().getTemp();
            totalHumidity += item.getMain().getHumidity();
            totalWindSpeed += item.getWind().getSpeed();
            
            // Count weather descriptions and IDs
            String description = item.getWeather().get(0).getDescription();
            int weatherId = item.getWeather().get(0).getId();
            
            weatherDescriptions.put(description, weatherDescriptions.getOrDefault(description, 0) + 1);
            weatherIds.put(weatherId, weatherIds.getOrDefault(weatherId, 0) + 1);
        }
        
        int dataCount = dayData.size();
        
        // Find most common weather condition
        String mostCommonDescription = weatherDescriptions.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("Unknown");
            
        int mostCommonWeatherId = weatherIds.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(800); // Default to clear sky
        
        // Create forecast data with averages
        ForecastData forecast = new ForecastData();
        forecast.setDayName(date.format(dayFormatter));
        forecast.setDate(date.format(dateFormatter));
        forecast.setTemperature(Math.round(totalTemp / dataCount * 10.0) / 10.0); // Round to 1 decimal place
        forecast.setDescription(mostCommonDescription + " (avg)");
        forecast.setHumidity((int) Math.round(totalHumidity / dataCount));
        forecast.setWindSpeed(Math.round(totalWindSpeed / dataCount * 10.0) / 10.0); // Round to 1 decimal place
        
        String weatherIcon = "wi wi-owm-" + mostCommonWeatherId;
        forecast.setWeatherIcon(weatherIcon);
        
        return forecast;
    }
}
