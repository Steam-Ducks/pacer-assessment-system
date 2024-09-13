module steamducks.pacerassessment {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens steamducks.pacerassessment to javafx.fxml;
    exports steamducks.pacerassessment;
}