package steamducks.SistemaRecap.controllers.Semestre;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import steamducks.SistemaRecap.dao.CriteriosDAO;
import steamducks.SistemaRecap.dao.SemestreDAO;
import steamducks.SistemaRecap.models.Criterio;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CadastrarSemestreController implements Initializable {

    // FXML Components
    @FXML
    private Button btnCadastrar, btnCancelar, btnAdd, btnRemove;
    @FXML
    private ListView<Criterio> listViewOpcoes, listViewCriterios;
    @FXML
    private TextField txtFieldNome;

    // DAOs e listas
    private final SemestreDAO semestreDAO = new SemestreDAO();
    private final CriteriosDAO criteriosDAO = new CriteriosDAO();
    private ObservableList<Criterio> opcoes; // Critérios disponíveis
    private ObservableList<Criterio> criteriosSelecionadosView; // Critérios selecionados

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarCriterios();
        inicializarListViews();
    }

    /**
     * Carrega os critérios disponíveis do banco de dados.
     */
    private void carregarCriterios() {
        List<Criterio> criterioDisponiveis = criteriosDAO.buscarCriterios();
        opcoes = FXCollections.observableArrayList(criterioDisponiveis);
        criteriosSelecionadosView = FXCollections.observableArrayList();
        ordenarListas();
    }

    /**
     * Inicializa as ListViews com os dados carregados.
     */
    private void inicializarListViews() {
        listViewOpcoes.setItems(opcoes);
        listViewCriterios.setItems(criteriosSelecionadosView);
    }

    /**
     * Ordena as listas para exibição organizada.
     */
    private void ordenarListas() {
        opcoes.sort((c1, c2) -> c1.getNome().compareToIgnoreCase(c2.getNome()));
        criteriosSelecionadosView.sort((c1, c2) -> c1.getNome().compareToIgnoreCase(c2.getNome()));
    }

    // --- Eventos ---

    @FXML
    void addOption(ActionEvent event) {
        Criterio selectedOption = listViewOpcoes.getSelectionModel().getSelectedItem();
        if (selectedOption != null) {
            criteriosSelecionadosView.add(selectedOption);
            opcoes.remove(selectedOption);
            ordenarListas();
        } else {
            showAlert("Erro", "Selecione um critério para adicionar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void removeOption(ActionEvent event) {
        Criterio selectedOption = listViewCriterios.getSelectionModel().getSelectedItem();
        if (selectedOption != null) {
            opcoes.add(selectedOption);
            criteriosSelecionadosView.remove(selectedOption);
            ordenarListas();
        } else {
            showAlert("Erro", "Selecione um critério para remover.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void cadastrarSemestre(ActionEvent event) {
        String nome = txtFieldNome.getText();

        if (!validarCadastro(nome)) return;

        int idSemestre = semestreDAO.criarSemestre(nome);
        if (idSemestre > 0) {
            semestreDAO.vincularCriterios(idSemestre, criteriosSelecionadosView);
            showAlert("Sucesso", "Semestre cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            limparFormulario();
        } else {
            showAlert("Erro", "Erro ao cadastrar o semestre. Tente novamente.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void cancelarCadastrarSemestre(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    // --- Métodos auxiliares ---

    /**
     * Valida o formulário de cadastro antes de salvar.
     */
    private boolean validarCadastro(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            showAlert("Erro", "O campo nome não pode estar vazio.", Alert.AlertType.ERROR);
            return false;
        }
        if (criteriosSelecionadosView.isEmpty()) {
            showAlert("Erro", "Adicione pelo menos um critério.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    /**
     * Limpa o formulário após o cadastro.
     */
    private void limparFormulario() {
        txtFieldNome.clear();
        criteriosSelecionadosView.clear();
        carregarCriterios(); // Recarrega critérios disponíveis
        inicializarListViews();
    }

    /**
     * Exibe um alerta para o usuário.
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));
        alert.showAndWait();
    }
}