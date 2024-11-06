module steamducks.pacerassessment {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;

    opens steamducks.pacerassessment to javafx.fxml;
    exports steamducks.pacerassessment;
    exports steamducks.pacerassessment.models;
    opens steamducks.pacerassessment.models to javafx.fxml;
    exports steamducks.pacerassessment.controllers;
    opens steamducks.pacerassessment.controllers to javafx.fxml;
}