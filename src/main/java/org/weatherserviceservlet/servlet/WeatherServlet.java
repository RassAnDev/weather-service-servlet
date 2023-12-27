package org.weatherserviceservlet.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * When a GET request is made to "http://localhost:8000/", it makes a request
 * to the API of the resource "https://openweathermap.org/" and receives weather data.
 * Then it passes this data to the template for display on the site page.
 *
 */
public class WeatherServlet extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();

    //    private String getWeatherFromApi() throws Exception {
//        final URL url = new URL(
//                "https://api.openweathermap.org/data/2.5/weather?" +
//                        "lat=51.666389&lon=39.169998&appid=8f48b1a2a1f1d96d71192f3b70e31e58"
//        );
//
//        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestProperty("Content-Type", "application/json");
//        connection.setConnectTimeout(60000);
//        connection.setReadTimeout(60000);
//
//        try (final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//            String inputLine;
//            final StringBuilder content = new StringBuilder();
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            return content.toString();
//        } catch (final Exception ex) {
//            ex.printStackTrace();
//            return "";
//        }
//    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
//
//
//        String content;
//
//        try {
//            content = getWeatherFromApi();
//        } catch (Exception e) {
//            throw new IOException(e);
//        }

        Map<String, Object> weatherData = mapper.readValue(new URL(
                        "https://api.openweathermap.org/data/2.5/weather?"
                                + "lat=51.666389&lon=39.169998&appid=8f48b1a2a1f1d96d71192f3b70e31e58&units=metric"
                ),
                new TypeReference<>() { });

        request.setAttribute("weatherData", weatherData);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/weather.jsp");
        requestDispatcher.forward(request, response);
    }
}
