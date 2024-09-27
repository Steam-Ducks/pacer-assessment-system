package steamducks.pacerassessment;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Optional;

public class CriteriosController {

    @FXML
    private Button btnAdcCriterio;

    @FXML
    private Button btnCnlNovoCrit;

    @FXML
    private TextField txtNome;

    @FXML
    private TextArea txtDescricao;

    @FXML
    private Button btnRmvCrit;

    @FXML
    private Button btnEditCrit;

    @FXML
    private TableColumn<Criterios, String> criteriosColumn;

    @FXML
    private TableColumn<Criterios, String> descricaoColumn;

    @FXML
    private TableView<Criterios> tableCriterios;

    @FXML
    private Button btnNovoCriterio;

    @FXML
    private TableColumn<Criterios, Integer > idColumn;

    private ObservableList<Criterios> criteriosData = FXCollections.observableArrayList();

    public void initialize() {
        criteriosData = FXCollections.observableArrayList();

        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        criteriosColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        descricaoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));

        tableCriterios.setItems(criteriosData);

    }


    @FXML
    void adicionarCriterio(ActionEvent event) {
        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();

        if (!nome.isEmpty() && !descricao.isEmpty()) {
            criteriosData.add(new Criterios(nome, descricao));

            // Limpa os campos após adicionar
            txtNome.clear();
            txtDescricao.clear();
        }
    }

    // Metodo para abrir a tela de edição
    @FXML
    void abrirTelaEdicao(ActionEvent event) {

        Criterios criteriosSelecionado = tableCriterios.getSelectionModel().getSelectedItem();

        if (criteriosSelecionado == null) {
            // Caso nenhum item esteja selecionado, exibe uma mensagem de alerta
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum critério selecionado");
            alert.setContentText("Por favor, selecione um critério para editar.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/edtCriteriosView.fxml"));
            Parent root = loader.load();

            // Pega o controlador da tela de edição
            EdtCriteriosController edtCriteriosController = loader.getController();
            edtCriteriosController.setCriterio(criteriosSelecionado);  // Passa o critério selecionado

            // Cria uma janela para a edição
            Stage stage = new Stage();
            stage.setTitle("Editar Critério");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a tela anterior enquanto edita
            stage.showAndWait();

            // Atualiza a tabela após a edição
            tableCriterios.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removerCriterio(ActionEvent event) {
        // Pega o critério selecionado
        Criterios criteriosSelecionado = tableCriterios.getSelectionModel().getSelectedItem();

        if (criteriosSelecionado == null) {
            // Caso nenhum item esteja selecionado, você pode exibir uma mensagem de alerta
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum critério selecionado");
            alert.setContentText("Por favor, selecione um critério para excluir.");
            alert.showAndWait();
            return;
        }

        // Exibir um alerta de confirmação
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmação de Exclusão");
        confirmAlert.setHeaderText("Você realmente quer excluir o critério?");
        confirmAlert.setContentText("Essa ação não pode ser desfeita.");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Se o usuário confirmar, remove o critério da lista e atualiza a tabela
            tableCriterios.getItems().remove(criteriosSelecionado);

            // Opcionalmente, se estiver usando uma lista Observable, remova da lista associada à tabela
            criteriosData.remove(criteriosSelecionado);

            // Atualize a tabela após a exclusão
            tableCriterios.refresh();
        }
    }
}



