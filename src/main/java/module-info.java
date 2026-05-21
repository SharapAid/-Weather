module io.github.sharapaid.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    exports io.github.sharapaid.weatherapp.Controller;
    opens io.github.sharapaid.weatherapp.Controller to javafx.fxml;
}