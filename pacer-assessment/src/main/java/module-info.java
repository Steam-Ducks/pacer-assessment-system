module pacer.pacerassessment {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens pacer.pacerassessment to javafx.fxml;
    exports pacer.pacerassessment;
}