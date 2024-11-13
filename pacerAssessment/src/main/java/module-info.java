module steamducks.pacerassessment {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;

    opens steamducks.SistemaRecap to javafx.fxml;
    exports steamducks.SistemaRecap;
    exports steamducks.SistemaRecap.models;
    opens steamducks.SistemaRecap.models to javafx.fxml;
    exports steamducks.SistemaRecap.controllers.Criterio;
    opens steamducks.SistemaRecap.controllers.Criterio to javafx.fxml;
    exports steamducks.SistemaRecap.controllers.Semestre;
    opens steamducks.SistemaRecap.controllers.Semestre to javafx.fxml;
    exports steamducks.SistemaRecap.controllers.Avaliacao;
    opens steamducks.SistemaRecap.controllers.Avaliacao to javafx.fxml;
    exports steamducks.SistemaRecap.controllers.Equipe;
    opens steamducks.SistemaRecap.controllers.Equipe to javafx.fxml;
    exports steamducks.SistemaRecap.controllers.Menu;
    opens steamducks.SistemaRecap.controllers.Menu to javafx.fxml;
    exports steamducks.SistemaRecap.controllers.Sprint;
    opens steamducks.SistemaRecap.controllers.Sprint to javafx.fxml;
    exports steamducks.SistemaRecap.controllers.Relatorio;
    opens steamducks.SistemaRecap.controllers.Relatorio to javafx.fxml;
}