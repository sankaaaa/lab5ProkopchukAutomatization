package org.sasha;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for displaying forecasts in cities hourly.
 */
public class ReturnData {

    /**
     * Displays the weather forecast.
     *
     * @param weatherForecast the weather forecast as a string
     * @throws IOException if an I/O error occurs
     */
    public static void displayWeatherForecast(final String weatherForecast) throws IOException {
        ObjectMapper map = new ObjectMapper();
        JsonNode startNode = map.readTree(weatherForecast);


        if (startNode.has("location") && startNode.has("current") && startNode.has("forecast")) {
            JsonNode location = startNode.path("location");
            JsonNode current = startNode.path("current");
            JsonNode forecastNode = startNode.path("forecast").path("forecastday");

            if (forecastNode.isArray() && !forecastNode.isEmpty()) {
                String country = location.path("country").asText();
                String city = location.path("name").asText();
                String region = location.path("region").asText();
                String localtime = location.path("localtime").asText();
                String timeZone = location.path("tz_id").asText();
                JsonNode forecast = forecastNode.get(0).path("hour");

                LocalDateTime now = LocalDateTime.parse(localtime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                List<JsonNode> housCount = new ArrayList<>();
                for (JsonNode hourData : forecast) {
                    LocalDateTime hourTime = LocalDateTime.parse(hourData.path("time").asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    if (!hourTime.isBefore(now))
                        housCount.add(hourData);

                    if (housCount.size() >= 3)
                        break;
                }
                System.out.println("\nWeather in " + city + ", " + region + ", " + country + ":");
                System.out.println("Local Time: " + localtime);
                System.out.println("Timezone: " + timeZone);
                System.out.println("Temperature: " + current.path("temp_c").asText() + "°C");

                System.out.println("\n3 hours forecast:");

                for (JsonNode hourlyForecast : housCount) {
                    String time = hourlyForecast.path("time").asText();
                    String temp = hourlyForecast.path("temp_c").asText();
                    String tempF = hourlyForecast.path("temp_f").asText();
                    String feelsLikeC = hourlyForecast.path("feelslike_c").asText();
                    String windMPH = hourlyForecast.path("wind_mph").asText();
                    String windDir = hourlyForecast.path("wind_dir").asText();
                    JsonNode astro = forecastNode.get(0).path("astro");
                    String moonPhase = astro.path("moon_phase").asText();
                    boolean willItSnow = hourlyForecast.path("will_it_snow").asBoolean();
                    int chanceOfRain = hourlyForecast.path("chance_of_rain").asInt();
                    String condition = hourlyForecast.path("condition").path("text").asText();

                    System.out.println("1) Time: " + time);
                    System.out.println("2) Temperature: " + temp + "°C");
                    System.out.println("3) Temperature Fahrenheit: " + tempF + "°F");
                    System.out.println("4) Temperature feels like: " + feelsLikeC + "°C");
                    System.out.println("5) Wind MPH: " + windMPH);
                    System.out.println("6) Wind direction: " + windDir);
                    System.out.println("7) Condition: " + condition);
                    System.out.println("8) Will it snow: " + willItSnow);
                    System.out.println("9) Chance of rain: " + chanceOfRain + "%");
                    System.out.println("10) Moon phase: " + moonPhase);
                    String rainbowStars = "\033[31m*\033[0m\033[33m*\033[0m\033[32m*\033[0m\033[36m*\033[0m\033[34m*\033[0m\033[35m*\033[0m\033[31m*\033[0m\033[33m*\033[0m\033[32m*\033[0m\033[36m*\033[0m\033[34m*\033[0m\033[35m*\033[0m\033[31m*\033[0m\033[33m*\033[0m\033[32m*\033[0m\033[36m*\033[0m\033[34m*\033[0m\033[35m*\033[0m";
                    System.out.println("\n" + rainbowStars + "\n");
                }
            } else
                System.out.println("Forecast data is not available.");
        } else
            System.out.println("Weather data is not available.");
    }
}
