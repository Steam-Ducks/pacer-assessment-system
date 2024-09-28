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
        loadView("/steamducks.pacerassessment/telaLogin.fxml","Login");
    }
  
    @FXML
    void abrirAvaliacaoSprint(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/avaliacaoSprintView.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Pontuação Sprint");

            Image logo = new Image(getClass().getResourceAsStream("/assets/logo-teste.png"));
            stage.getIcons().add(logo);


            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    void abrirCadastroSprint(ActionEvent event) {
    }
  
    @FXML
    void abrirCadastroAlunoEquipes(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/cadastroGrupoAlunoView.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Equipe");

            Image logo = new Image(getClass().getResourceAsStream("/assets/logo-teste.png"));
            stage.getIcons().add(logo);


            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    @FXML
    void abrirPontuacaoSprint(ActionEvent event) {

    }

    @FXML
    void abrirCadastroCriterios(ActionEvent event) {

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
