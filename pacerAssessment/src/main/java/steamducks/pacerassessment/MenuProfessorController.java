package steamducks.pacerassessment;

import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MenuProfessorController {

    @FXML
    private Button btnPontuacaoSprint;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnCadastrarAluno;

    @FXML
    private Button btnCadastrarSemestre;

    @FXML
    private Button btnExportar;

    @FXML
    private Button btnGerarRelatorio;

    @FXML
    private Button btnGerenciarCriterios;

    @FXML
    private Button btnGerenciarEquipes;

    @FXML
    private Button btnGerenciarSemestre;

    @FXML
    private ComboBox<?> cmbBoxEquipe;

    @FXML
    private ComboBox<?> cmbBoxSemestre;

    @FXML
    private ComboBox<?> cmbBoxSprint;

    @FXML
    void abrirLogin(ActionEvent event) {

        Stage stageAtual = (Stage) btnLogout.getScene().getWindow();
        stageAtual.close();

        loadView("/steamducks.pacerassessment/telaLogin.fxml","Login");
    }

    @FXML
    void abrirCadastroSprint(ActionEvent event) {
    }
  
    @FXML
    void abrirCadastroAlunoEquipes(ActionEvent event) {
        loadView("/steamducks.pacerassessment/cadastroEquipeAlunoView.fxml", "Cadastro de Equipe");
    }

    @FXML
    void abrirPontuacaoSprint(ActionEvent event) {
        loadView("/steamducks.pacerassessment/avaliacaoSprintView.fxml", "Pontuar Sprint");
    }

    @FXML
    void abrirCadastroCriterios(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/criteriosMenuView.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Gerenciador de Critérios");

            Image logo = new Image(getClass().getResourceAsStream("/assets/logo-teste.png"));
            stage.getIcons().add(logo);


            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirCadastroSemestre(ActionEvent event) {
        loadView("/steamducks.pacerassessment/cadastrarSemestreView.fxml", "Cadastro de Semestre");
    }

    @FXML
    void abrirGerenciarCriterios(ActionEvent event) {

    }

    @FXML
    void abrirGerenciarEquipes(ActionEvent event) {

    }

    @FXML
    void abrirGerenciarSemestre(ActionEvent event) {

    }

    @FXML
    void exportar(ActionEvent event) {

    }

    @FXML
    void gerarRelatorio(ActionEvent event) {

    }

    private void loadView(String fxmlFile, String nomeTela) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle(nomeTela);

            Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo-teste.png")));
            stage.getIcons().add(logo);


            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
