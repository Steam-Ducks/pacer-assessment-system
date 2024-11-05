package steamducks.pacerassessment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class VisualizarMediaAlunoController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableColumn<?, ?> criterioaluno;

    @FXML
    private ComboBox<?> escolheraluno;

    @FXML
    private ComboBox<?> escolhersprint;

    @FXML
    private TableColumn<?, ?> notaaluno;

    @FXML
    private TableView<?> tabelanotaaluno;

}
