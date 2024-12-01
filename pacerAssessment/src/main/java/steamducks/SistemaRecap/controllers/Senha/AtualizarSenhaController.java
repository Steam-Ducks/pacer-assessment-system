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

        if (!validarEmail(email)) {
            mostrarAlerta("Erro", "Por favor, insira um e-mail válido.", Alert.AlertType.ERROR);
            return;
        }

        if (!validarSenha(novaSenha)) {
            mostrarAlerta("Erro", "A senha deve ter pelo menos 8 caracteres.", Alert.AlertType.ERROR);
            return;
        }

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean sucesso = usuarioDAO.atualizarSenha(email, novaSenha);

            if (sucesso) {
                mostrarAlerta("Sucesso", "Senha atualizada com sucesso!", Alert.AlertType.INFORMATION);
                fecharJanela();
            } else {
                mostrarAlerta("Erro", "E-mail não encontrado. Verifique e tente novamente.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Erro", "Ocorreu um erro ao atualizar a senha: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarEmail(String email) {
        return email != null && !email.trim().isEmpty() && email.contains("@") && email.contains(".");
    }

    private boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 8;
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png"))); // Ícone personalizado
        alerta.showAndWait();
    }

    private void fecharJanela() {
        Stage stage = (Stage) btnConfirmar.getScene().getWindow();
        stage.close();
    }
}