# Weather-app 🌦

A simple weather application that allows users to check real-time weather conditions by entering a city name. It fetches data from the **OpenWeather API** and displays details like **temperature, wind speed, humidity, and weather conditions**.

## 🚀 Features

- 🌍 Search for weather by city name
- 🌡️ Display current temperature in Celsius
- 💨 Show wind speed and humidity
- ☀️ Weather condition descriptions (e.g., sunny, cloudy, rainy)
- 🎨 Clean and responsive UI
- 🔗 API integration with OpenWeather

## 🛠️ Tech Stack

### **Frontend**
- React (Vite)
- HTML, CSS (Styled with basic styles)
- Fetch API for data retrieval

### **Backend (Work in Progress)**
- Java (Servlets)
- Tomcat Server
- OpenWeather API integration

> **⚠️ Note:** The backend is a work in progress. I initially set it up manually without Maven, which has caused some challenges in integrating it with the front end. I plan to refactor and improve the backend for better communication between client and server.

## 📸 Screenshots
![Weather App Screenshot](frontend/public/screenshots/Screenshot%202025-03-16%20234101.png)



# 📦 Installation & Setup

## 1️⃣ Clone the Repository
git clone https://github.com/jakearator/Weather-app.git
cd Weather-app

## 2️⃣ Backend Setup (Java Servlet + Tomcat)

# Prerequisites:
# - Java JDK (17 or later)
# - Apache Tomcat (10+)
# - OpenWeather API Key (Sign up at https://home.openweathermap.org/users/sign_up)

# Navigate to the Backend folder
cd Backend

# Compile the Java files
javac -cp "lib/javax.servlet-api-4.0.1.jar;." -d bin src/*.java

# Deploy the app to Tomcat:
# 1. Copy the Backend folder into Tomcat's webapps/ directory
# 2. Ensure web.xml is correctly configured inside WEB-INF/

# Start Tomcat
C:\Apache\Tomcat\apache-tomcat-10.1.39\bin\startup.bat

# Access the backend in your browser:
http://localhost:8080/weatherapp/WeatherServlet?city=New%20York

## 3️⃣ Frontend Setup (React)

# Prerequisites:
# - Node.js (16+)
# - npm (comes with Node.js)

# Navigate to the frontend folder
cd frontend

# Install dependencies
npm install

# Run the development server
npm run dev

# Open the frontend in your browser:
http://localhost:5173

## 🌟 Notes
# - If the backend is not sending responses to the frontend, check:
#   - CORS policy
#   - Correct API URL endpoints
# - If you get a 404 error on Tomcat, ensure WEB-INF/web.xml is properly set up.



