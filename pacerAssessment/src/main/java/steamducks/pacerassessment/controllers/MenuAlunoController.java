package steamducks.pacerassessment.controllers;

import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import steamducks.pacerassessment.models.Usuario;

public class MenuAlunoController {

    @FXML
    private Label emailAluno;

    @FXML
    private Label nomeAluno;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnAvaliar;

    @FXML
    private Label DataAluno;

    private Usuario usuarioLogado;

    @FXML
    void abrirLogin(ActionEvent event) {
        Stage stageAtual = (Stage) btnLogout.getScene().getWindow();
        stageAtual.close();
        loadView("/steamducks.pacerassessment/loginView.fxml", "Login");
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

    @FXML
    void avaliar(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/avaliacaoAlunoView.fxml"));
            Parent root = fxmlLoader.load();

            // Obtém o controlador da tela de avaliação e passa o usuário logado
            AvaliacaoController avaliacaoController = fxmlLoader.getController();
            avaliacaoController.inicializar(usuarioLogado);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Avaliação");

            Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo-teste.png")));
            stage.getIcons().add(logo);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inicializar(Usuario usuario) {
        this.usuarioLogado = usuario;
        nomeAluno.setText(usuario.getNome());
        emailAluno.setText(usuario.getEmail());
    }
}
