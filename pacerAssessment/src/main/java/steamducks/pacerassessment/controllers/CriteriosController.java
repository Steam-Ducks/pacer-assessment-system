package steamducks.pacerassessment.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import steamducks.pacerassessment.dao.CriteriosDAO;
import steamducks.pacerassessment.models.Criterio;

import java.io.IOException;
import java.util.List;
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
    private TableColumn<Criterio, String> criteriosColumn;

    @FXML
    private TableColumn<Criterio, String> descricaoColumn;

    @FXML
    private TableView<Criterio> tableCriterios;

    @FXML
    private Button btnNovoCriterio;

    @FXML
    private TableColumn<Criterio, Integer > idColumn;

    private ObservableList<Criterio> criterioData = FXCollections.observableArrayList();

    CriteriosDAO criteriosDao;

    public void initialize() {
        criterioData = FXCollections.observableArrayList();

        criteriosDao = new CriteriosDAO();

        List<Criterio> criterios = criteriosDao.buscarCriterios(); // usa essa funcao da DAO pra criar uma lista de criterios
        criterioData.addAll(criterios); // Adiciona essa lista do banco na lista do controlador



        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        criteriosColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        descricaoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));

        tableCriterios.setItems(criterioData);

    }


    @FXML
    void adicionarCriterio(ActionEvent event) {
        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();

        if (!nome.isEmpty() && !descricao.isEmpty()) {
            Criterio criterio = new Criterio(nome, descricao);
            try {
                int tempId = criteriosDao.adicionarCriterio(criterio);
                criterio.setId(tempId);
                criterioData.add(criterio);

                txtNome.clear();
                txtDescricao.clear();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Nome e Descrição não podem estar vazios.");
        }
    }

    // Metodo para abrir a tela de edição
    @FXML
    void abrirTelaEdicao(ActionEvent event) {

        Criterio criterioSelecionado = tableCriterios.getSelectionModel().getSelectedItem();

        if (criterioSelecionado == null) {
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
            edtCriteriosController.setCriterio(criterioSelecionado);  // Passa o critério selecionado

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
        Criterio criterioSelecionado = tableCriterios.getSelectionModel().getSelectedItem();

        if (criterioSelecionado == null) {
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
        try {
            criteriosDao.removerCriterio(criterioSelecionado.getId()); //chama a funcao da DAO enviando o ID do objeto selecionado

            // Remove o critério da lista e atualiza a tabela
            tableCriterios.getItems().remove(criterioSelecionado);
            criterioData.remove(criterioSelecionado);

            // Atualiza a tabela após a exclusão
            tableCriterios.refresh();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}