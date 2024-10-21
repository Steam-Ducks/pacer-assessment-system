package steamducks.pacerassessment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class TelaGerenciarEquipesController {

    @FXML
    private Button btnCadastrarEquipe;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnRemover;

    @FXML
    void btnCadastrarEquipe(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/cadastroEquipeAlunoView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Cadastrar Grupo");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void btnEditarEquipe(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/telaEditarEquipesView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Editar Grupo");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
