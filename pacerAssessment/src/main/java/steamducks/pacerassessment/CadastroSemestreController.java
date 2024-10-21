package steamducks.pacerassessment;

import java.net.URL;
import java.util.ArrayList;
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
import steamducks.pacerassessment.dao.SemestreDAO;

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
    private ListView<Criterios> listViewOpcoes;

    @FXML
    private ListView<Criterios> listViewCriterios;

    @FXML
    private TextField txtFieldNome;

    private final SemestreDAO semestreDAO = new SemestreDAO();
    private List<String> criteriosSelecionados = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Criterios> criteriosDisponiveis = semestreDAO.buscarCriterios();
        ObservableList<Criterios> opcoes = FXCollections.observableArrayList(criteriosDisponiveis);
        listViewOpcoes.setItems(opcoes);
        listViewCriterios.setItems(FXCollections.observableArrayList());
    }

    @FXML
    void addOption(ActionEvent event) {
        Criterios selectedOption = listViewOpcoes.getSelectionModel().getSelectedItem();
        if (selectedOption != null && !listViewCriterios.getItems().contains(selectedOption)) {
            listViewCriterios.getItems().add(selectedOption);
            criteriosSelecionados.add(String.valueOf(selectedOption.getId()));
        } else {
            showAlert("Erro", "Nenhuma opção selecionada para adicionar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void removeOption(ActionEvent event) {
        Criterios selectedOption = listViewCriterios.getSelectionModel().getSelectedItem();
        if (selectedOption != null) {
            listViewCriterios.getItems().remove(selectedOption);
            criteriosSelecionados.remove(selectedOption.getId());
        } else {
            showAlert("Erro", "Nenhuma opção selecionada para remover.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void cadastrarSemestre(ActionEvent event) {
        String nome = txtFieldNome.getText();

        if (nome.isEmpty()) {
            showAlert("Erro", "O campo nome não pode estar vazio.", Alert.AlertType.ERROR);
            return;
        }

        if (criteriosSelecionados.isEmpty()) {
            showAlert("Erro", "Adicione pelo menos um critério.", Alert.AlertType.ERROR);
            return;
        }

        int idSemestre = semestreDAO.criarSemestre(nome);

        if (idSemestre > 0) {
            // Vincular critérios ao semestre criado
            semestreDAO.vincularCriterios(idSemestre, listViewCriterios.getItems());

            showAlert("Sucesso", "Semestre cadastrado com sucesso!", Alert.AlertType.INFORMATION);

            txtFieldNome.clear();
            listViewCriterios.getItems().clear();
            criteriosSelecionados.clear();
        } else {
            showAlert("Erro", "Erro ao cadastrar semestre.", Alert.AlertType.ERROR);
        }
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
