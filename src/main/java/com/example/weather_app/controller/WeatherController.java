package com.example.weather_app.controller;

import org.springframework.ui.Model;
import com.example.weather_app.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class WeatherController {
    @Value("${api.key}")
    private String apiKey;

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city, Model model){
        // Handle common city disambiguations
        String searchQuery = city;
        if (city.equalsIgnoreCase("melbourne") || city.equalsIgnoreCase("melbourne au") || city.equalsIgnoreCase("melbourne australia")) {
            searchQuery = "Melbourne,AU";
        } else if (city.equalsIgnoreCase("melbourne us") || city.equalsIgnoreCase("melbourne usa")) {
            searchQuery = "Melbourne,US";
        } else if (city.equalsIgnoreCase("sydney") || city.equalsIgnoreCase("sydney au") || city.equalsIgnoreCase("sydney australia")) {
            searchQuery = "Sydney,AU";
        } else if (city.equalsIgnoreCase("perth") || city.equalsIgnoreCase("perth au") || city.equalsIgnoreCase("perth australia")) {
            searchQuery = "Perth,AU";
        } else if (city.equalsIgnoreCase("adelaide") || city.equalsIgnoreCase("adelaide au") || city.equalsIgnoreCase("adelaide australia")) {
            searchQuery = "Adelaide,AU";
        } else if (city.equalsIgnoreCase("brisbane") || city.equalsIgnoreCase("brisbane au") || city.equalsIgnoreCase("brisbane australia")) {
            searchQuery = "Brisbane,AU";
        }
        
        String url ="https://api.openweathermap.org/data/2.5/weather?q=" + searchQuery + "&appId=" + apiKey + "&units=metric"; 
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
        } else {
            model.addAttribute("error", "City not found.");
        }

        return "weather";

    }
}
