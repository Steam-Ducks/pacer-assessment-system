module org.steamducks.criteriosavaliacao {
    requires javafx.controls;
    requires javafx.fxml;


    opens steamducks.pacerassessment to javafx.fxml;
    exports steamducks.pacerassessment;
}