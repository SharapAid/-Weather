package io.github.sharapaid.weatherapp.View.ViewWindow;

import io.github.sharapaid.weatherapp.View.ViewInfoBar.InfoBar;
import io.github.sharapaid.weatherapp.View.ViewSearchBar.SearchBar;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class Window {
    private  VBox root;
    private SearchBar searchBar = new SearchBar();
    private InfoBar infoBar = new InfoBar();

    public Window(){}

    public void prepareStage(Stage stage) {
        root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);

        root.getChildren().addAll(searchBar.getWrapSearchBar(), infoBar.getWrapInfoBar());

        Scene scene = new Scene(root, 370, 430);

        Font.loadFont(getClass().getResourceAsStream("/Fonts/Raleway/Raleway-Regular.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/Fonts/Raleway/Raleway-Bold.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/Fonts/Raleway/Raleway-Italic.ttf"), 14);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Weather.css")).toExternalForm());

        String nameApp = "Weather App";
        stage.setTitle(nameApp);
        stage.setResizable(false);
        stage.setScene(scene);

        try {
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Icons/icon.png")))
            );
        }
        catch (Exception e) {
            System.out.println("Error cant load img.");
        }
    }

    public InfoBar getInfoBar() {
        return infoBar;
    }

    public SearchBar getSearchBar() {
        return searchBar;
    }

    public VBox getRoot() {
        return root;
    }
}
