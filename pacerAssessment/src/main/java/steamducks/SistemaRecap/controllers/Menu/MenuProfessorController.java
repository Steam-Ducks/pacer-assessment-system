package steamducks.SistemaRecap.controllers.Menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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
        carregarView("/SistemaRecap/Equipe/telaGerenciarEquipesView.fxml");
    }

    @FXML
    public void abrirGerenciarCriterios(ActionEvent event) throws IOException {
        carregarView("/SistemaRecap/Criterio/gerenciarCriteriosMenuView.fxml");
    }

    @FXML
    public void abrirGerenciarPontuacaoSprint(ActionEvent event) throws IOException {
        carregarView("/SistemaRecap/Sprint/gerenciarPontuacaoSprintView.fxml");
    }

    @FXML
    public void abrirRelatorios() throws IOException {
        carregarView("/SistemaRecap/Relatorio/relatoriosView.fxml");
    }

    @FXML
    public void abrirGerenciarSemestre() throws IOException {
        carregarView("/SistemaRecap/Semestre/gerenciarSemestreView.fxml");
    }

    @FXML
    public void abrirGerenciarSprints(ActionEvent event) throws IOException {
        carregarView("/SistemaRecap/Sprint/telaGerenciarSprintView.fxml");
    }

    @FXML
    void abrirLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SistemaRecap/Menu/loginView.fxml"));
        Node view = loader.load();

        Stage stageLogin = new Stage();
        stageLogin.setTitle("Sistema RECAP");

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo-dark.png")));
        stageLogin.getIcons().add(logo);


        stageLogin.setScene(new Scene((Parent) view));
        stageLogin.setMaximized(false);
        stageLogin.setResizable(false);
        stageLogin.show();

        Stage stageAtual = (Stage) btnLogout.getScene().getWindow();
        stageAtual.close();
    }



    private void carregarView(String viewName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewName));
        Node view = loader.load();

        contentPane.getChildren().setAll(view);
    }
}
