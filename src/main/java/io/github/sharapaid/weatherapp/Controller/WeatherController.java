package io.github.sharapaid.weatherapp.Controller;

import io.github.sharapaid.weatherapp.View.ViewWindow.Window;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class WeatherController {
    private Window window;
    private final String API_KEY = "cf88c7e35a737246ce01c047285376b7";

    public WeatherController(Window window){
        this.window = window;

        this.window.getSearchBar().getSearchButton().setOnAction(event -> handleWeatherRequest());

        this.window.getSearchBar().getCityFilled().setOnAction(event -> handleWeatherRequest());

        window.getSearchBar().getDescriptionLabel().setText("Detecting your location...");
        window.getSearchBar().getDescriptionLabel().setStyle("-fx-font-size: 15;");
        fetchLocalWeatherAtStart();
    }

    private void handleWeatherRequest() {
        String city = window.getSearchBar().getCityFilled().getText().trim();

        if (city.isEmpty()) {
            window.getSearchBar().getDescriptionLabel().setText("Please, enter a city name!");
            window.getSearchBar().getDescriptionLabel().setStyle("-fx-font-size: 15;");
            return;
        }

        window.getSearchBar().getDescriptionLabel().setText("Searching for " + city + "...");
        window.getSearchBar().getDescriptionLabel().setStyle("-fx-font-size: 15;");

        new Thread(() -> fetchWeatherData(city)).start();
    }

    private void fetchWeatherData(String city) {
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&appid=" + API_KEY + "&units=metric";

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                updateUIError(responseCode == 404 ? "City not found!" : "API Error: " + responseCode);
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());

            JSONObject sysObj = jsonResponse.getJSONObject("sys");
            String country = sysObj.getString("country");
            String cityName = jsonResponse.getString("name");

            JSONObject mainObj = jsonResponse.getJSONObject("main");
            double temperature = mainObj.getDouble("temp");
            int humidity = mainObj.getInt("humidity");

            String description = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
            String iconCode = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("icon");

            String formattedDescription = description.substring(0, 1).toUpperCase() + description.substring(1);
            String fullLocation = cityName + ", " + country;

            Platform.runLater(() -> {
                String tempText = Math.round(temperature) + " °C";
                window.getInfoBar().getTemperatureLabel().setText(tempText);

                window.getInfoBar().getTemperatureLabel().setStyle("");

                if (temperature >= 15 && temperature < 20) {
                    window.getInfoBar().getTemperatureLabel().setStyle("-fx-text-fill: #7095ec;");
                } else if (temperature >= 20 && temperature <= 25) {
                    window.getInfoBar().getTemperatureLabel().setStyle("-fx-text-fill: #a0db8e;");
                } else if (temperature < 15) {
                    window.getInfoBar().getTemperatureLabel().setStyle("-fx-text-fill: #ffb347;");
                } else if (temperature > 25) {
                    window.getInfoBar().getTemperatureLabel().setStyle("-fx-text-fill: #ff6961;");
                }

                String iconPath = "/Icons/" + iconCode + "_t@4x.png";
                try {
                    Image iconImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath)));
                    window.getInfoBar().getWeatherIconView().setImage(iconImage);
                }
                catch (Exception e) {
                    System.out.println(iconPath);
                    window.getInfoBar().getWeatherIconView().setImage(null);
                }

                window.getRoot().getStyleClass().removeAll("day-background", "night-background");
                if (iconCode.endsWith("n")) {
                    window.getRoot().getStyleClass().add("night-background");
                } else {
                    window.getRoot().getStyleClass().add("day-background");
                }

                window.getInfoBar().getHumidityLabel().setText("Humidity: " + humidity + "%");
                window.getInfoBar().getDescriptionLabel().setText(formattedDescription);
                window.getSearchBar().getDescriptionLabel().setText(fullLocation);
                window.getSearchBar().getDescriptionLabel().setStyle("-fx-font-size: 35;");

                animateFadeIn(window.getInfoBar().getTemperatureLabel());
                animateFadeIn(window.getInfoBar().getWeatherIconView());
                animateFadeIn(window.getInfoBar().getHumidityLabel());
                animateFadeIn(window.getInfoBar().getDescriptionLabel());
                animateFadeIn(window.getInfoBar().getWrapInfoBar());
            });

        }
        catch (Exception e) {
            e.printStackTrace();
            updateUIError("No internet connection.");
        }
    }

    private void fetchLocalWeatherAtStart() {
        new Thread(() -> {
            try {
                URL url = new URL("http://ip-api.com/json/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);

                if (connection.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONObject json = new JSONObject(response.toString());

                    if ("success".equals(json.getString("status"))) {
                        String localCity = json.getString("city");

                        fetchWeatherData(localCity);
                        return;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                window.getSearchBar().getDescriptionLabel().setText("Enter a city to check the weather");
                window.getSearchBar().getDescriptionLabel().setStyle("-fx-font-size: 15;");
            });
        }).start();
    }

    private void animateFadeIn(Node node) {
        FadeTransition fade = new FadeTransition(Duration.millis(600), node);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.play();
    }

    private void updateUIError(String errorMessage) {
        Platform.runLater(() -> {
            window.getInfoBar().getTemperatureLabel().setText("-- °C");
            window.getInfoBar().getHumidityLabel().setText("Humidity: --%");
        });
    }
}
