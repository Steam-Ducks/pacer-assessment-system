package steamducks.pacerassessment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TeladeLoginController {

    @FXML
    private PasswordField txtSenha;

    @FXML
    private TextField txtFieldLogin;

    @FXML
    private Button btnEntrar;

    @FXML
    void login(ActionEvent event) {
        String login = txtFieldLogin.getText();
        String senha = txtSenha.getText();

        // Verificação simples de login
        if (login.equals("admin") && senha.equals("1234")) {
            System.out.println("Login bem-sucedido!");

            // Chama a função para carregar a tela `menuProfessorView.fxml`
            loadView("/steamducks.pacerassessment/menuProfessorView.fxml", "Menu Professor");

            // Chama a função para registrar a tentativa de login bem-sucedida
            logLoginAttempt(login, true);

            // Fecha a janela atual de login
            Stage stage = (Stage) btnEntrar.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Login ou senha incorretos.");

            // Chama a função para registrar a tentativa de login falha
            logLoginAttempt(login, false);
        }
    }

    // Método para carregar uma nova tela
    private void loadView(String fxmlFile, String nomeTela) {
        try {
            // Imprime o caminho do FXML para depuração
            System.out.println("Tentando carregar: " + fxmlFile);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));

            // Verifica se o recurso foi encontrado
            if (fxmlLoader.getLocation() == null) {
                System.out.println("Erro: Arquivo FXML não encontrado -> " + fxmlFile);
                return;
            }

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(nomeTela);

            // Carrega o ícone - opcional
            Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo-teste.png")));
            stage.getIcons().add(logo);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo FXML: " + fxmlFile);
            e.printStackTrace();
        }
    }

    // Exemplo de uma função para registrar a tentativa de login
    private void logLoginAttempt(String login, boolean success) {
        String status = success ? "bem-sucedido" : "falho";
        System.out.println("Tentativa de login: " + login + " foi " + status);
    }
}