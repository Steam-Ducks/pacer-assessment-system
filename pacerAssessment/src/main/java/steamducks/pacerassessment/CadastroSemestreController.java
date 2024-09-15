package steamducks.pacerassessment;

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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
    private ListView<String> listViewAtributos;

    @FXML
    private TextField txtFieldNome;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Opções disponíveis no ListView de opções
        ObservableList<String> opcoes = FXCollections.observableArrayList("Autonomia", "Trabalho em Equipe",
                "Organização", "Comunicação", "Criatividade", "Resolução de Problemas", "Pontualidade", "Entrega");
        listViewOpcoes.setItems(opcoes);
        listViewAtributos.setItems(FXCollections.observableArrayList()); // Inicializa a lista de atributos vazia
    }

    @FXML
    void addOption(ActionEvent event) {
        String selectedOption = listViewOpcoes.getSelectionModel().getSelectedItem();
        if (selectedOption != null) {
            ObservableList<String> atributos = listViewAtributos.getItems();
            if (!atributos.contains(selectedOption)) {
                atributos.add(selectedOption); // Adiciona o atributo selecionado se ainda não estiver na lista
            }
        } else {
            showAlert("Erro", "Nenhuma opção selecionada para adicionar.", Alert.AlertType.ERROR); // Alerta de erro
        }
    }

    @FXML
    void removeOption(ActionEvent event) {
        String selectedOption = listViewAtributos.getSelectionModel().getSelectedItem();
        if (selectedOption != null) {
            ObservableList<String> atributos = listViewAtributos.getItems();
            atributos.remove(selectedOption); // Remove o atributo selecionado
        } else {
            showAlert("Erro", "Nenhuma opção selecionada para remover.", Alert.AlertType.ERROR); // Alerta de erro
        }
    }

    @FXML
    void cadastrarSemestre(ActionEvent event) {
        String nome = txtFieldNome.getText(); // Obtém o nome do semestre

        if (nome.isEmpty()) {
            showAlert("Erro", "O campo nome não pode estar vazio.", Alert.AlertType.ERROR); // Valida se o campo de nome está vazio
            return;
        }

        List<String> atributosSelecionados = listViewAtributos.getItems();
        if (atributosSelecionados.isEmpty()) {
            showAlert("Erro", "Adicione pelo menos um atributo.", Alert.AlertType.ERROR); // Valida se há atributos selecionados
            return;
        }

        // Convertendo a lista de atributos em uma string
        String[] atributosArray = atributosSelecionados.toArray(new String[0]);
        String atributos = String.join(", ", atributosArray);

        // Exibindo o semestre cadastrado no console (ou salve no banco de dados, se necessário)
        System.out.println("Semestre cadastrado:");
        System.out.println("Nome: " + nome);
        System.out.println("Atributos: " + atributos);

        showAlert("Cadastro Realizado", "Semestre cadastrado com sucesso!", Alert.AlertType.INFORMATION); // Alerta de sucesso

        // Limpar campos
        txtFieldNome.clear();
        listViewAtributos.getItems().clear();
    }

    @FXML
    void cancelarCadastrarSemestre(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close(); // Fecha a janela
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type); // Alerta customizado com o tipo adequado
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
