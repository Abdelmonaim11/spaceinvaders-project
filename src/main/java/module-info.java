module com.example {


    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    opens com.example.controller to javafx.fxml;

    exports com.example;
}