package steamducks.pacerassessment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuProfessorController {

    @FXML
    private Button btnGerenciarAlunosEquipes;

    @FXML
    private Button btnGerenciarCriterios;

    @FXML
    private Button btnGerenciarPontuacaoSprint;

    @FXML
    private Button btnGerenciarSemestre;

    @FXML
    private Button btnGerenciarSprints;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnRelatorios;

    @FXML
    private AnchorPane contentPane;

    @FXML
    public void initialize() {
        try {
            abrirRelatorios();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirGerenciarEquipes(ActionEvent event) throws IOException {
        carregarView("/steamducks.pacerassessment/telaGerenciarEquipesView.fxml");
    }

    @FXML
    public void abrirGerenciarCriterios(ActionEvent event) throws IOException {
        carregarView("/steamducks.pacerassessment/gerenciarCriteriosMenuView.fxml");
    }

    @FXML
    public void abrirGerenciarPontuacaoSprint(ActionEvent event) throws IOException {

    }

    @FXML
    public void abrirRelatorios() throws IOException {
        carregarView("/steamducks.pacerassessment/relatoriosView.fxml");
    }

    @FXML
    public void abrirGerenciarSemestre() throws IOException {
        carregarView("/steamducks.pacerassessment/gerenciarSemestreView.fxml");
    }

    @FXML
    public void abrirGerenciarSprints(ActionEvent event) {

    }

    @FXML
    void abrirLogin(ActionEvent event) throws IOException {

        Stage stageAtual = (Stage) btnLogout.getScene().getWindow();
        stageAtual.close();

        carregarView("/steamducks.pacerassessment/loginView.fxml");
    }

    private void carregarView(String viewName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewName));
        Node view = loader.load();

        contentPane.getChildren().setAll(view);
    }
}
