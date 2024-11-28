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

    /**
     * Ação executada ao confirmar a atualização da senha.
     *
     * @param event Evento do botão Confirmar.
     */
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
                limparCampos();
            } else {
                mostrarAlerta("Erro", "E-mail não encontrado. Verifique e tente novamente.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Erro", "Ocorreu um erro ao atualizar a senha: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Valida o formato do e-mail.
     *
     * @param email E-mail a ser validado.
     * @return true se for válido; false caso contrário.
     */
    private boolean validarEmail(String email) {
        return email != null && !email.trim().isEmpty() && email.contains("@") && email.contains(".");
    }

    /**
     * Valida o comprimento da senha.
     *
     * @param senha Senha a ser validada.
     * @return true se a senha for válida; false caso contrário.
     */
    private boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 8;
    }

    /**
     * Exibe um alerta para o usuário.
     *
     * @param titulo   Título da janela de alerta.
     * @param mensagem Mensagem exibida no corpo do alerta.
     * @param tipo     Tipo do alerta (INFORMATION, WARNING, ERROR).
     */
    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png"))); // Caminho do ícone
        alerta.showAndWait();
    }

    /**
     * Limpa os campos de entrada.
     */
    private void limparCampos() {
        txtFieldEmail.clear();
        txtFieldSenha.clear();
    }
}
