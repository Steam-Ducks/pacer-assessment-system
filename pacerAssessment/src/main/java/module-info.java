module steamducks.pacerassessment {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.graphics;

    opens steamducks.pacerassessment to javafx.fxml;
    exports steamducks.pacerassessment;
}