package io.github.sharapaid.weatherapp.View.ViewInfoBar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class InfoBar {
    private GridPane wrapInfoBar;
    private Label temperatureLabel;
    private Label humidityLabel;
    private ImageView weatherIconView;
    private Label descriptionLabel;

    public InfoBar() {
        wrapInfoBar = new GridPane(30,10);
        wrapInfoBar.setPadding(new Insets(10));
        wrapInfoBar.setAlignment(Pos.CENTER);

        weatherIconView = new ImageView();
        weatherIconView.setFitWidth(110);
        weatherIconView.setPreserveRatio(true);

        descriptionLabel = new Label();
        descriptionLabel.setId("infoLabel");

        temperatureLabel = new Label();
        temperatureLabel.setId("tempLabel");

        humidityLabel = new Label();
        humidityLabel.setId("humidityLabel");

        wrapInfoBar.add(temperatureLabel, 0, 0);

        wrapInfoBar.add(weatherIconView, 1, 0, 1, 2);

        GridPane.setValignment(weatherIconView, VPos.CENTER);

        VBox textDataBox = new VBox(5);
        textDataBox.setAlignment(Pos.CENTER_LEFT);
        textDataBox.getChildren().addAll(humidityLabel, descriptionLabel);

        wrapInfoBar.add(textDataBox, 0, 1);
    }

    public Label getHumidityLabel() {
        return humidityLabel;
    }

    public GridPane getWrapInfoBar() {
        return wrapInfoBar;
    }

    public Label getTemperatureLabel() {
        return temperatureLabel;
    }

    public ImageView getWeatherIconView() {
        return weatherIconView;
    }

    public Label getDescriptionLabel() {
        return descriptionLabel;
    }
}
