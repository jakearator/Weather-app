package com.weatherapp;

import java.net.URI;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/WeatherServlet")
public class WeatherServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String city = request.getParameter("city");
        if (city == null || city.isEmpty()) {
            response.getWriter().write("{\"error\": \"City parameter is missing\"}");
            return;
        }

        String apiKey = System.getenv("OPENWEATHER_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            response.getWriter().write("{\"error\": \"API key is missing\"}");
            return;
        }

        try {
            String encodedCity = java.net.URLEncoder.encode(city, java.nio.charset.StandardCharsets.UTF_8);
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&appid=" + apiKey + "&units=metric";

            URI uri = new URI(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String jsonResponse = reader.lines().reduce("", (acc, line) -> acc + line);
            reader.close();

            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\": \"Failed to fetch weather data\"}");
        }
    }
}
