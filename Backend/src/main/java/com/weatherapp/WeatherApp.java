package com.weatherapp;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class WeatherApp {
    public static void main(String[] args) {
        String city = URLEncoder.encode("New York", java.nio.charset.StandardCharsets.UTF_8);
        String apiKey = System.getenv("OPENWEATHER_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("Error: API key is missing. Set the environment variable OPENWEATHER_API_KEY.");
            return;
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) new URI("https://api.openweathermap.org/data/2.5/weather?q=" 
                + city + "&appid=" + apiKey + "&units=metric").toURL().openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String jsonResponse = reader.lines().reduce("", (acc, line) -> acc + line);
            reader.close();

            System.out.println("🌍 City: " + extract(jsonResponse, "\"name\":\"", "\""));
            System.out.println("🌡️ Temperature: " + extract(jsonResponse, "\"temp\":", ",") + "°C");
            System.out.println("🌤️ Weather: " + extract(jsonResponse, "\"description\":\"", "\""));
            System.out.println("💨 Wind Speed: " + extract(jsonResponse, "\"speed\":", ",") + " m/s");
            System.out.println("💧 Humidity: " + extract(jsonResponse, "\"humidity\":", "}") + "%");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String extract(String json, String key, String endChar) {
        int start = json.indexOf(key);
        return (start == -1) ? "N/A" : json.substring(start + key.length(), json.indexOf(endChar, start + key.length()));
    }
}
