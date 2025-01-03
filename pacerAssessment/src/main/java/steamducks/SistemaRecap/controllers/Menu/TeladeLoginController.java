package steamducks.SistemaRecap.controllers.Menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import steamducks.SistemaRecap.dao.ConexaoDAO;
import steamducks.SistemaRecap.dao.LoginDAO;
import steamducks.SistemaRecap.models.Usuario;

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
    private Hyperlink esqueciMinhaSenha;

    private LoginDAO loginDAO;

    public TeladeLoginController() {
        loginDAO = new LoginDAO();
    }

    @FXML
    void login(ActionEvent event) {
        String email = txtFieldLogin.getText();
        String senha = txtSenha.getText();

        Usuario usuario = loginDAO.login(email, senha);

        if (usuario != null) {
            System.out.println("Login bem-sucedido!");

            if (usuario.isProfessor()) {
                ConexaoDAO.setFuncaoAtual("professor");
                loadView("/SistemaRecap/Menu/menuProfessorView.fxml", "Sistema RECAP", usuario);
            } else {
                ConexaoDAO.setFuncaoAtual("aluno");
                loadView("/SistemaRecap/Menu/menuAlunoView.fxml", "Sistema RECAP", usuario);
            }

            logLoginAttempt(email, true);

            Stage stage = (Stage) btnEntrar.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Login ou senha incorretos.");
            logLoginAttempt(email, false);
            showLoginErrorPopup();
        }
    }

    @FXML
    void abrirResetSenha(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SistemaRecap/Senha/atualizarSenhaView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Sistema RECAP");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.centerOnScreen();

            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));

            Stage currentStage = (Stage) btnEntrar.getScene().getWindow();
            currentStage.getScene().getRoot().setEffect(new javafx.scene.effect.GaussianBlur(10));

            stage.setOnHidden(e -> currentStage.getScene().getRoot().setEffect(null));

            stage.showAndWait();

        } catch (IOException e) {
            System.out.println("Erro ao abrir a tela de atualização de senha.");
            e.printStackTrace();
        }
    }


    private void loadView(String fxmlFile, String nomeTela, Usuario usuario) {
        try {
            System.out.println("Tentando carregar: " + fxmlFile);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));

            if (fxmlLoader.getLocation() == null) {
                System.out.println("Erro: Arquivo FXML não encontrado -> " + fxmlFile);
                return;
            }

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(nomeTela);


            Object controller = fxmlLoader.getController();
            if (controller instanceof MenuProfessorController)
            {
                //((MenuProfessorController) controller).inicializar(usuario);
            }
            else if (controller instanceof MenuAlunoController)
            {
                ((MenuAlunoController) controller).inicializar(usuario);
            }

            stage.setMaximized(false);
            stage.setResizable(false);
            stage.centerOnScreen();

            Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo-dark.png")));
            stage.getIcons().add(logo);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo FXML: " + fxmlFile);
            e.printStackTrace();
        }
    }


    private void logLoginAttempt(String login, boolean success) {
        String status = success ? "bem-sucedido" : "falho";
        System.out.println("Tentativa de login: " + login + " foi " + status);
    }

    private void showLoginErrorPopup() {
        Alert alert = new Alert(AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));

        alert.setTitle("Sistema RECAP");
        alert.setHeaderText("Login ou senha incorretos");
        alert.setContentText("Por favor, verifique seu e-mail e senha e tente novamente.");
        alert.showAndWait();
    }
}
