import { useState } from "react";
import "./App.css";  // Import styles

function App() {
  const [city, setCity] = useState("");
  const [weather, setWeather] = useState(null);
  const [error, setError] = useState(null);  // Track errors

  const fetchWeather = async () => {
    if (!city) {
      setError("Please enter a city name.");
      return;
    }
    setError(null); // Reset error message

    try {
      console.log("Fetching weather for:", city);  // Debugging Log

      const response = await fetch(`http://localhost:8080/weatherapp/WeatherServlet?city=${city}`);

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const data = await response.json();
      console.log("Weather Data:", data);  // Debugging Log

      setWeather(data);
    } catch (error) {
      console.error("Error fetching weather:", error);
      setError("Failed to fetch weather. Please try again.");
    }
  };

  return (
    <div className="container">
      <h1 className="weather-heading">
        🌤️ What's the weather today at 
      </h1> {/* Sun emoji with text */}
      <input 
        type="text" 
        placeholder="Enter city" 
        value={city} 
        onChange={(e) => setCity(e.target.value)} 
      />
      <button onClick={fetchWeather}>Get Weather</button>

      {error && <p className="error-message">{error}</p>}

      {weather && (
        <div className="weather-info">
          <h2>{weather.name}</h2>
          <p>🌡️ {weather.main.temp}°C</p>
          <p>💨 Wind Speed: {weather.wind.speed} m/s</p>
          <p>🌤️ Condition: {weather.weather[0].description}</p>
          <p>💧 Humidity: {weather.main.humidity}%</p>
        </div>
      )}
    </div>
  );
}

export default App;
