package org.sasha;

import java.io.IOException;

/**
 * Main class for the weather application.
 */
public class Main {

    /**
     * Main method to render the app.
     *
     * @param args the command line arguments
     * @throws IOException if an I/O error occurs
     */
    public static void main(final String[] args) throws IOException {
        ForecastGetter ar = new ForecastGetter();
        String res = ar.fetchWeatherData("IvanoFrankivsk");
        ReturnData.displayWeatherForecast(res);
    }
}
