package org.sasha;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class for getting forecast data.
 */
public class ForecastGetter {
    /**
     * Takes weather data for needed state.
     *
     * @param state the name of the state
     * @return weather data as a string
     */
    public String fetchWeatherData(final String state) {
        String apiLink = "https://api.weatherapi.com/v1/forecast.json?key=addab5935ca14383b6e221323230411&q=" + state;

        try {
            URL link = new URL(apiLink);
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            connection.setRequestMethod("GET");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder content = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                return content.toString();
            } finally {
                connection.disconnect();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

}

