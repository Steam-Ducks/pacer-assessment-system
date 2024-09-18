module org.example.telateste {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.telateste to javafx.fxml;
    exports org.example.telateste;
}