# Weather App

A beautiful Spring Boot weather application with animated cloud backgrounds.

## Environment Variables

- `API_KEY`: Your OpenWeatherMap API key
- `PORT`: Server port (defaults to 8080)

## Local Development

1. Set your OpenWeatherMap API key:
   ```bash
   export API_KEY=your-api-key-here
   ```

2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Deployment

This app is ready to deploy on:
- Railway
- Render
- Heroku
- Azure App Service

Make sure to set the `API_KEY` environment variable in your deployment platform.
