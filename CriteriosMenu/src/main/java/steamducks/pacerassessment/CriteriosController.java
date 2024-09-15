package steamducks.pacerassessment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class CriteriosController {

    @FXML
    private TextField txtCriterios;

    @FXML
    private TableView<Criterios> tabelaCriterios;

    @FXML
    private TableColumn<Criterios, Integer> idColumn;

    @FXML
    private TableColumn<Criterios, String> criteriosColumn;

    @FXML
    private TableColumn<Criterios, Void> actionColunm;

    private final ObservableList<Criterios> criteriosData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configura a coluna de IDs
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        // Configura a coluna de critérios
        criteriosColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCriterio()));

        // Adiciona os botões de ação na coluna
        addButtonToTable();

        // Define os dados na tabela
        tabelaCriterios.setItems(criteriosData);
    }

    @FXML
    private void onButtonAdicionarCriterioAcition() {
        String novoCriterio = txtCriterios.getText().trim();
        if (!novoCriterio.isEmpty()) {
            criteriosData.add(new Criterios(novoCriterio));
            txtCriterios.clear();
        }
    }

    private void addButtonToTable() {
        Callback<TableColumn<Criterios, Void>, TableCell<Criterios, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Criterios, Void> call(final TableColumn<Criterios, Void> param) {
                final TableCell<Criterios, Void> cell = new TableCell<>() {

                    private final Button btnEdit = new Button("Editar");
                    private final Button btnRemove = new Button("Remover");

                    {
                        // Botão Editar - Abre a nova tela de edição
                        btnEdit.setStyle("-fx-background-color: #C2CC29; -fx-text-fill: white;");
                        btnEdit.setOnAction(event -> {
                            Criterios criterios = getTableView().getItems().get(getIndex());
                            abrirTelaEdicao(criterios);
                        });

                        // Botão Remover
                        btnRemove.setStyle("-fx-background-color: #CC2936; -fx-text-fill: white;");
                        btnRemove.setOnAction(event -> {
                            Criterios criterios = getTableView().getItems().get(getIndex());
                            criteriosData.remove(criterios);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox actionButtons = new HBox(btnEdit, btnRemove);
                            actionButtons.setSpacing(10);
                            setGraphic(actionButtons);
                        }
                    }
                };
                return cell;
            }
        };

        actionColunm.setCellFactory(cellFactory);
    }

    // Método para abrir a tela de edição
    private void abrirTelaEdicao(Criterios criterios) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edtCriteriosView.fxml"));
            Parent root = loader.load();

            // Pega o controlador da tela de edição
            EdtCriteriosController edtCriteriosController = loader.getController();
            edtCriteriosController.setCriterio(criterios);

            // Cria uma janela para a edição
            Stage stage = new Stage();
            stage.setTitle("Editar Critério");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a tela anterior enquanto edita
            stage.showAndWait();

            // Atualiza a tabela após a edição
            tabelaCriterios.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
