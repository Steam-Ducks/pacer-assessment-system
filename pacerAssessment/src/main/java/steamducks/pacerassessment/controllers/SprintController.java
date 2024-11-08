package steamducks.pacerassessment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

import static steamducks.pacerassessment.controllers.SemestreController.blurEffect;

public class SprintController {

    @FXML
    private Button btn_AdcSprint;

    @FXML
    private Button btn_EditSprint;

    @FXML
    private Button btn_RmvSprint;

    @FXML
    private AnchorPane contentPane;
    
    @FXML
    void adicionarSprint(ActionEvent event) {
        try {
            if (contentPane != null) {
                contentPane.setEffect(blurEffect);
            }


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/cadastroSprintView.fxml"));
            Parent root = fxmlLoader.load();


            Stage stage = new Stage();
            stage.setTitle("Cadastrar Sprint");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));


            stage.setOnHidden(e -> contentPane.setEffect(null));

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    void abrirTelaEdicao(ActionEvent event) {

    }

    @FXML
    void removerSprint(ActionEvent event) {

    }
}
