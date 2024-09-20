package steamducks.pacerassessment;

import java.io.IOException;

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
    private Button btnAvaliarSprint;

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
    void abrirAvaliacaoSprint(ActionEvent event) {

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
    void abrirCadastroCriterios(ActionEvent event) {

    }

    @FXML
    void abrirCadastroSemestre(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/cadastrarSemestreView.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Semestre");

            Image logo = new Image(getClass().getResourceAsStream("/assets/logo-teste.png"));
            stage.getIcons().add(logo);


            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
