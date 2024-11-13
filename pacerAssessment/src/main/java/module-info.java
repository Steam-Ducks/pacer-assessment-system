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
    exports steamducks.pacerassessment.controllers.Criterio;
    opens steamducks.pacerassessment.controllers.Criterio to javafx.fxml;
    exports steamducks.pacerassessment.controllers.Semestre;
    opens steamducks.pacerassessment.controllers.Semestre to javafx.fxml;
    exports steamducks.pacerassessment.controllers.Avaliacao;
    opens steamducks.pacerassessment.controllers.Avaliacao to javafx.fxml;
    exports steamducks.pacerassessment.controllers.Equipe;
    opens steamducks.pacerassessment.controllers.Equipe to javafx.fxml;
    exports steamducks.pacerassessment.controllers.Menu;
    opens steamducks.pacerassessment.controllers.Menu to javafx.fxml;
    exports steamducks.pacerassessment.controllers.Sprint;
    opens steamducks.pacerassessment.controllers.Sprint to javafx.fxml;
    exports steamducks.pacerassessment.controllers.Relatorio;
    opens steamducks.pacerassessment.controllers.Relatorio to javafx.fxml;
}