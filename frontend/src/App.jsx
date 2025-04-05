import { useState } from "react";
import "./App.css";

function App() {
  const [city, setCity] = useState("");
  const [weather, setWeather] = useState(null);
  const [error, setError] = useState(null);
  const [unit, setUnit] = useState("metric"); // "metric" = Celsius, "imperial" = Fahrenheit

  const toggleUnit = () => {
    setUnit((prev) => (prev === "metric" ? "imperial" : "metric"));
  };

  const convertTemp = (celsius) => {
    return unit === "imperial" ? Math.round((celsius * 9) / 5 + 32) : Math.round(celsius);
  };

  const fetchWeather = async () => {
    if (!city) {
      setError("Please enter a city name.");
      return;
    }

    setError(null);
    setWeather(null);

    try {
      const response = await fetch(
        `http://localhost:8080/weatherapp/WeatherServlet?city=${city}`
      );
      const data = await response.json();

      if (!response.ok || data.cod !== 200) {
        setError("City not found. Please enter a valid location.");
        return;
      }

      setWeather(data);
    } catch (error) {
      setError("Failed to fetch weather. Please try again.");
    }
  };

  return (
    <div className={`weather-app ${weather ? weather.weather[0].main.toLowerCase() : ""}`}>
      <div className="top-bar">
        <button className="unit-toggle" onClick={toggleUnit}>
          {unit === "metric" ? "°F" : "°C"}
        </button>
        <div className="search-box">
          <input
            type="text"
            placeholder="Enter city"
            value={city}
            onChange={(e) => setCity(e.target.value)}
          />
          <button onClick={fetchWeather}>Search</button>
        </div>
      </div>

      {error && <p className="error-message">{error}</p>}

      {weather && (
        <div className="weather-display card">
          <h2 className="city-name">{weather.name}</h2>
          <div className="temp-main">
            <span className="temp">{convertTemp(weather.main.temp)}°{unit === "metric" ? "C" : "F"}</span>
            <span className="condition">{weather.weather[0].description}</span>
          </div>
          <div className="info-row">
            <div className="info-box">
              <p className="label">Humidity</p>
              <p className="value">{weather.main.humidity}%</p>
            </div>
            <div className="info-box">
              <p className="label">Wind</p>
              <p className="value">{weather.wind.speed} {unit === "metric" ? "m/s" : "mph"}</p>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
