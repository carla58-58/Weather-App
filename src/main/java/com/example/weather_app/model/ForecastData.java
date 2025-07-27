package com.example.weather_app.model;

public class ForecastData {
    private String date;
    private String dayName;
    private double temperature;
    private String description;
    private int humidity;
    private int rainProbability;
    private double windSpeed;
    private String weatherIcon;
    
    public ForecastData() {}
    
    public ForecastData(String date, String dayName, double temperature, String description, 
                       int humidity, int rainProbability, double windSpeed, String weatherIcon) {
        this.date = date;
        this.dayName = dayName;
        this.temperature = temperature;
        this.description = description;
        this.humidity = humidity;
        this.rainProbability = rainProbability;
        this.windSpeed = windSpeed;
        this.weatherIcon = weatherIcon;
    }
    
    // Getters and Setters
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getDayName() {
        return dayName;
    }
    
    public void setDayName(String dayName) {
        this.dayName = dayName;
    }
    
    public double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getHumidity() {
        return humidity;
    }
    
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
    
    public int getRainProbability() {
        return rainProbability;
    }
    
    public void setRainProbability(int rainProbability) {
        this.rainProbability = rainProbability;
    }
    
    public double getWindSpeed() {
        return windSpeed;
    }
    
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
    
    public String getWeatherIcon() {
        return weatherIcon;
    }
    
    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}
