package steamducks.pacerassessment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class TelaEditarEquipesController {

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtGithub;

    @FXML
    void confirmarEdicao(ActionEvent event) {

    }

    @FXML
    void cancelarEdicao(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

}