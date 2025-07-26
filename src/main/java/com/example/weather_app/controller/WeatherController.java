package com.example.weather_app.controller;

import org.springframework.ui.Model;
import com.example.weather_app.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            String url ="https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&appId=" + apiKey + "&units=metric"; 
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
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving weather data: " + e.getMessage());
        }

        return "weather";
    }
}
