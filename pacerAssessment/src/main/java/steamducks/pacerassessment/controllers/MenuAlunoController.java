package steamducks.pacerassessment.controllers;
import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MenuAlunoController {

    @FXML
    private Label emailAluno;

    @FXML
    private Label nomeAluno;

    @FXML
    private Button btnLogout;

    @FXML
    private Label DataAluno;


    @FXML
    void abrirLogin(ActionEvent event) {

            Stage stageAtual = (Stage) btnLogout.getScene().getWindow();
            stageAtual.close();

            loadView("/steamducks.pacerassessment/loginView.fxml","Login");
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
