package steamducks.pacerassessment;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroSemestreController implements Initializable {

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnRemove;

    @FXML
    private ListView<String> listViewOpcoes;

    @FXML
    private ListView<String> listViewCriterios;

    @FXML
    private TextField txtFieldNome;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> opcoes = FXCollections.observableArrayList("Autonomia", "Trabalho em Equipe",
                "Organização", "Comunicação", "Criatividade", "Resolução de Problemas", "Pontualidade", "Entrega");
        listViewOpcoes.setItems(opcoes);
        listViewCriterios.setItems(FXCollections.observableArrayList()); 
    }

    @FXML
    void addOption(ActionEvent event) {
        String selectedOption = listViewOpcoes.getSelectionModel().getSelectedItem();
        if (selectedOption != null) {
            ObservableList<String> criterios = listViewCriterios.getItems();
            if (!criterios.contains(selectedOption)) {
                criterios.add(selectedOption); 
            }
        } else {
            showAlert("Erro", "Nenhuma opção selecionada para adicionar.", Alert.AlertType.ERROR); 
        }
    }

    @FXML
    void removeOption(ActionEvent event) {
        String selectedOption = listViewCriterios.getSelectionModel().getSelectedItem();
        if (selectedOption != null) {
            ObservableList<String> criterios = listViewCriterios.getItems();
            criterios.remove(selectedOption); 
        } else {
            showAlert("Erro", "Nenhuma opção selecionada para remover.", Alert.AlertType.ERROR); 
        }
    }

    @FXML
    void cadastrarSemestre(ActionEvent event) {
        String nome = txtFieldNome.getText();

        if (nome.isEmpty()) {
            showAlert("Erro", "O campo nome não pode estar vazio.", Alert.AlertType.ERROR); 
        }

        List<String> criteriosSelecionados = listViewCriterios.getItems();
        if (criteriosSelecionados.isEmpty()) {
            showAlert("Erro", "Adicione pelo menos um critério.", Alert.AlertType.ERROR);
            return;
        }

        String[] criteriosArray = criteriosSelecionados.toArray(new String[0]);
        String criterios = String.join(", ", criteriosArray);

        System.out.println("Semestre cadastrado:");
        System.out.println("Nome: " + nome);
        System.out.println("Critérios: " + criterios);

        showAlert("Cadastro Realizado", "Semestre cadastrado com sucesso!", Alert.AlertType.INFORMATION); 

        txtFieldNome.clear();
        listViewCriterios.getItems().clear();
    }

    @FXML
    void cancelarCadastrarSemestre(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close(); 
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type); 
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
