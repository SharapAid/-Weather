package io.github.sharapaid.weatherapp.Controller;

import io.github.sharapaid.weatherapp.View.ViewWindow.Window;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Window appWindow = new Window();
        appWindow.prepareStage(primaryStage);

        WeatherController controller = new WeatherController(appWindow);
        primaryStage.show();
    }
    public static void main(String[] args){
        launch();
    }
}
