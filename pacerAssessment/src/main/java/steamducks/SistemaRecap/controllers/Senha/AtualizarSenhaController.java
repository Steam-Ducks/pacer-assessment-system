package steamducks.SistemaRecap.controllers.Senha;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import steamducks.SistemaRecap.dao.UsuarioDAO;

public class AtualizarSenhaController {

    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField txtFieldEmail;

    @FXML
    private PasswordField txtFieldSenha;

    @FXML
    void atualizarSenha(ActionEvent event) {
        String email = txtFieldEmail.getText();
        String novaSenha = txtFieldSenha.getText();

        if (email == null || email.isEmpty() || !email.contains("@")) {
            showAlert("Erro", "Por favor, insira um e-mail válido.", Alert.AlertType.ERROR);
            return;
        }

        if (novaSenha == null || novaSenha.length() < 8) {
            showAlert("Erro", "A senha deve ter pelo menos 8 caracteres.", Alert.AlertType.ERROR);
            return;
        }

        try {
            UsuarioDAO passwordDAO = new UsuarioDAO();
            boolean sucesso = passwordDAO.atualizarSenha(email, novaSenha);

            if (sucesso) {
                showAlert("Sucesso", "Senha atualizada com sucesso!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Erro", "E-mail não encontrado. Verifique e tente novamente.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Erro", "Ocorreu um erro ao atualizar a senha: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png"))); // Caminho do ícone
        alert.showAndWait();
    }
}
