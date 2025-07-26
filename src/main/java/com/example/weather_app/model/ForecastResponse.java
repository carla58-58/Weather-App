package com.example.weather_app.model;

import java.util.List;

public class ForecastResponse {
    private List<ForecastItem> list;
    
    public List<ForecastItem> getList() {
        return list;
    }
    
    public void setList(List<ForecastItem> list) {
        this.list = list;
    }
    
    public static class ForecastItem {
        private Main main;
        private List<Weather> weather;
        private Wind wind;
        private String dt_txt;
        
        public Main getMain() {
            return main;
        }
        
        public void setMain(Main main) {
            this.main = main;
        }
        
        public List<Weather> getWeather() {
            return weather;
        }
        
        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }
        
        public Wind getWind() {
            return wind;
        }
        
        public void setWind(Wind wind) {
            this.wind = wind;
        }
        
        public String getDt_txt() {
            return dt_txt;
        }
        
        public void setDt_txt(String dt_txt) {
            this.dt_txt = dt_txt;
        }
    }
    
    public static class Main {
        private double temp;
        private int humidity;
        
        public double getTemp() {
            return temp;
        }
        
        public void setTemp(double temp) {
            this.temp = temp;
        }
        
        public int getHumidity() {
            return humidity;
        }
        
        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }
    }
    
    public static class Weather {
        private int id;
        private String description;
        
        public int getId() {
            return id;
        }
        
        public void setId(int id) {
            this.id = id;
        }
        
        public String getDescription() {
            return description;
        }
        
        public void setDescription(String description) {
            this.description = description;
        }
    }
    
    public static class Wind {
        private double speed;
        
        public double getSpeed() {
            return speed;
        }
        
        public void setSpeed(double speed) {
            this.speed = speed;
        }
    }
}
