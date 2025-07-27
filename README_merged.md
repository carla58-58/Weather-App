# ⛅ Weather App by Carla Tasinazzo

A beautiful Spring Boot weather application with animated cloud backgrounds that fetches real-time weather data and displays interactive weather charts.

## 📋 Table of Contents

1. [🤖 Introduction](#introduction)
2. [⚙️ Tech Stack](#tech-stack)
3. [🔋 Features](#features)
4. [🌍 Environment Variables](#environment-variables)
5. [🤸 Quick Start](#quick-start)
6. [🚀 Deployment](#deployment)

## 🤖 Introduction

Welcome to the Weather App! This dynamic web application is built with Spring Boot and fetches real-time weather data using the OpenWeatherMap API. The app enables users to search for current weather conditions in any city and view detailed information such as temperature, rain probability, wind speed, and 7-day weather trends with interactive charts.

Designed for both usability and learning, this project demonstrates how to integrate REST APIs, process JSON data, create interactive web interfaces with Java and Spring Boot, and deploy applications to various cloud platforms.

## ⚙️ Tech Stack

- **Spring Boot** – Provides the core backend framework for rapid application development and RESTful service integration
- **Java** – Main programming language powering the application logic
- **Thymeleaf** – Template engine for rendering dynamic HTML pages and displaying weather data
- **Bootstrap/CSS** – Ensures responsive and visually appealing UI with animated cloud backgrounds
- **Chart.js** – Interactive charts for weather data visualization
- **OpenWeatherMap API** – Supplies real-time weather data in JSON format
- **Jackson** – Handles JSON parsing and data binding
- **Lombok** – Reduces boilerplate code in Java models

## 🔋 Features

👉 **City Search with Autocomplete**: Enter any city name with intelligent autocomplete suggestions to instantly retrieve current weather conditions

👉 **Real-Time Data**: Fetches up-to-date weather information including temperature, rain probability (calculated from weather conditions), wind speed, and detailed weather descriptions

👉 **Interactive 7-Day Weather Charts**: Visual representation of weather trends with Chart.js, featuring:
   - Temperature trends
   - Rain probability forecasting
   - Wind speed variations
   - Clickable legend to toggle data visibility
   - Mobile-optimized responsive design

👉 **Accurate Local Time**: Displays the correct local time for the searched city using timezone data

👉 **Weather Icons**: Dynamic weather condition icons using Weather Icons library

👉 **Animated Background**: Beautiful animated cloud effects for enhanced user experience

👉 **Fully Responsive Design**: Optimized interface for all devices (desktop, tablet, mobile)

👉 **Error Handling**: User-friendly messages for invalid city names or network issues

## 🌍 Environment Variables

The application requires the following environment variables:

- `API_KEY`: Your OpenWeatherMap API key (required)
- `PORT`: Server port (defaults to 8080 if not specified)

## 🤸 Quick Start

Ready to run the app locally? Here's how you can get started:

### Prerequisites
- Java JDK (11 or later)
- Maven
- Git
- OpenWeatherMap API key (sign up at [openweathermap.org](https://openweathermap.org))

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/carla58-58/Weather-App.git
   cd Weather-App
   ```

2. **Configure your API key:**
   
   **Option A: Environment Variable (Recommended)**
   ```bash
   export API_KEY=your-api-key-here
   ```
   
   **Option B: Application Properties (Local Development Only)**
   ```properties
   # In src/main/resources/application.properties
   api.key=your-api-key-here
   ```

3. **Install dependencies and build the project:**
   ```bash
   mvn clean install
   ```

### Running the Application

**Start the application:**
```bash
mvn spring-boot:run
```

**Or using the Maven wrapper:**
```bash
./mvnw spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080) in your browser to view the app.

## 🚀 Deployment

This app is ready to deploy on multiple cloud platforms. Make sure to set the `API_KEY` environment variable in your deployment platform.

### Supported Platforms:
- **Render** ⭐ (Recommended)
- **Railway**
- **Heroku**
- **Azure App Service**

### Deployment Steps:

1. **Prepare for deployment:**
   - Ensure `application.properties` uses `api.key=${API_KEY}` (not hardcoded)
   - Commit all changes to your repository

2. **Set environment variables:**
   - In your deployment platform's dashboard, add:
     - `API_KEY`: Your OpenWeatherMap API key
     - `PORT`: (Optional, most platforms auto-configure this)

3. **Deploy:**
   - Connect your GitHub repository to your chosen platform
   - The platform will automatically build and deploy using the included `Dockerfile` or build configuration

### Platform-Specific Notes:

- **Render**: Uses `render.yaml` configuration
- **Heroku**: Uses `Procfile` for process configuration
- **General**: The app includes `system.properties` for Java version specification

## 🎯 Usage

1. **Search for a city**: Type a city name in the search box
2. **View current weather**: See real-time temperature, rain probability, and wind speed
3. **Explore trends**: Check the interactive 7-day weather chart
4. **Toggle data**: Click legend items to show/hide specific weather metrics
5. **Mobile experience**: Enjoy the fully responsive design on any device

## 🤝 Contributing

Thank you for checking out the Weather App! If you'd like to collaborate, report issues, or suggest improvements, please feel free to:

- Open an issue on GitHub
- Submit a pull request
- Reach out for questions or collaboration opportunities

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

**Made with ❤️ by [Carla Tasinazzo](https://github.com/carla58-58)**
