package steamducks.pacerassessment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button btnCadastrarAluno;

    @FXML
    private Button btnCadastrarSemestre;

    @FXML
    void abrirCadastroAluno(ActionEvent event) {

    }

    @FXML
    void abrirCadastroSemestre(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/cadastrarSemestreView.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Semestre");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
