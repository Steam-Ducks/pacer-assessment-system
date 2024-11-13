package steamducks.pacerassessment.controllers.Semestre;

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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import steamducks.pacerassessment.dao.CriteriosDAO;
import steamducks.pacerassessment.dao.SemestreDAO;
import steamducks.pacerassessment.models.Criterio;

public class CadastrarSemestreController implements Initializable {

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnRemove;

    @FXML
    private ListView<Criterio> listViewOpcoes;

    @FXML
    private ListView<Criterio> listViewCriterios;

    @FXML
    private TextField txtFieldNome;

    private final SemestreDAO semestreDAO = new SemestreDAO();
    private final CriteriosDAO criteriosDAO = new CriteriosDAO();
    private List<String> criteriosSelecionados = new ArrayList<>();
    private ObservableList<Criterio> opcoes;
    private ObservableList<Criterio> criteriosSelecionadosView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Criterio> criterioDisponiveis = criteriosDAO.buscarCriterios();
        opcoes = FXCollections.observableArrayList(criterioDisponiveis);
        criteriosSelecionadosView = FXCollections.observableArrayList();

        opcoes.sort((c1, c2) -> c1.getNome().compareToIgnoreCase(c2.getNome()));
        criteriosSelecionadosView.sort((c1, c2) -> c1.getNome().compareToIgnoreCase(c2.getNome()));

        listViewOpcoes.setItems(opcoes);
        listViewCriterios.setItems(criteriosSelecionadosView);
    }

    @FXML
    void addOption(ActionEvent event) {
        Criterio selectedOption = listViewOpcoes.getSelectionModel().getSelectedItem();
        if (selectedOption != null && !listViewCriterios.getItems().contains(selectedOption)) {
            criteriosSelecionadosView.add(selectedOption);
            criteriosSelecionados.add(String.valueOf(selectedOption.getId()));
            opcoes.remove(selectedOption); // Remove da lista de opções

            opcoes.sort((c1, c2) -> c1.getNome().compareToIgnoreCase(c2.getNome()));
            criteriosSelecionadosView.sort((c1, c2) -> c1.getNome().compareToIgnoreCase(c2.getNome()));
        } else {
            showAlert("Erro", "Nenhuma opção selecionada para adicionar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void removeOption(ActionEvent event) {
        Criterio selectedOption = listViewCriterios.getSelectionModel().getSelectedItem();
        if (selectedOption != null) {
            criteriosSelecionadosView.remove(selectedOption);
            criteriosSelecionados.remove(String.valueOf(selectedOption.getId()));
            opcoes.add(selectedOption);

            opcoes.sort((c1, c2) -> c1.getNome().compareToIgnoreCase(c2.getNome()));
            criteriosSelecionadosView.sort((c1, c2) -> c1.getNome().compareToIgnoreCase(c2.getNome()));
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
            semestreDAO.vincularCriterios(idSemestre, listViewCriterios.getItems());

            showAlert("Sucesso", "Semestre cadastrado com sucesso!", Alert.AlertType.INFORMATION);

            txtFieldNome.clear();
            listViewCriterios.getItems().clear();
            criteriosSelecionados.clear();
            opcoes.setAll(criteriosDAO.buscarCriterios());


            opcoes.sort((c1, c2) -> c1.getNome().compareToIgnoreCase(c2.getNome()));
            criteriosSelecionadosView.clear();
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


        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png"))); // Caminho do ícone
        alert.showAndWait();
    }
}
