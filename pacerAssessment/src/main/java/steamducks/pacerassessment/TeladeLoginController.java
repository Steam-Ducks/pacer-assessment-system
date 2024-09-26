package steamducks.pacerassessment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

        if (login.equals("admin") && senha.equals("1234")) {
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("Login ou senha incorretos.");
        }
    }
}
