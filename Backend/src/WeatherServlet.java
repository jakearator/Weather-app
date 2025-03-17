import java.net.URI;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/WeatherServlet")  // Sets API path
public class WeatherServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Enable CORS for all origins (you can change '*' to a specific domain if you want to limit access)
        response.setHeader("Access-Control-Allow-Origin", "*");  // Allows all domains
        response.setHeader("Access-Control-Allow-Methods", "GET");  // Allow GET requests
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");  // Allow content-type headers

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String city = request.getParameter("city"); // Get city from React
        if (city == null || city.isEmpty()) {
            response.getWriter().write("{\"error\": \"City parameter is missing\"}");
            return;
        }

        String apiKey = System.getenv("OPENWEATHER_API_KEY");  // Get API key
        if (apiKey == null || apiKey.isEmpty()) {
            response.getWriter().write("{\"error\": \"API key is missing\"}");
            return;
        }

        try {
            // Encode city name & call OpenWeather API
            String encodedCity = java.net.URLEncoder.encode(city, java.nio.charset.StandardCharsets.UTF_8);
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&appid=" + apiKey + "&units=metric";

            // Use URI and then convert it to URL
            URI uri = new URI(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setRequestMethod("GET");

            // Read JSON response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String jsonResponse = reader.lines().reduce("", (acc, line) -> acc + line);
            reader.close();

            response.getWriter().write(jsonResponse); // Return JSON to React frontend
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\": \"Failed to fetch weather data\"}");
        }
    }
}
