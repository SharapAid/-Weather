package io.github.sharapaid.weatherapp.View.ViewSearchBar;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class SearchBar {
    private VBox wrapSearchBar;
    private TextField cityFilled;
    private Button searchButton;
    private Label descriptionLabel;

    public SearchBar(){
        wrapSearchBar = new VBox(10);
        wrapSearchBar.setAlignment(Pos.CENTER);
        wrapSearchBar.setFillWidth(false);

        descriptionLabel = new Label("");
        descriptionLabel.setId("descriptionLabel");
        descriptionLabel.setMaxWidth(300);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);

        cityFilled = new TextField();
        cityFilled.setPromptText("Enter city name...");
        cityFilled.setPrefWidth(200);

        searchButton = new Button("Get Weather");

        wrapSearchBar.getChildren().addAll(descriptionLabel,cityFilled, searchButton);
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public TextField getCityFilled() {
        return cityFilled;
    }

    public VBox getWrapSearchBar() {
        return wrapSearchBar;
    }

    public Label getDescriptionLabel() {
        return descriptionLabel;
    }
}
