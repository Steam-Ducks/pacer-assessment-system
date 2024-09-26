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
    private TableColumn<Criterios, Void> acoesColumn;

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

        // Adiciona os botões de ação na coluna ações
        addBtnAcoes();

        tableCriterios.setItems(criteriosData);

    }


    //@FXML
    //public void abrirNovoCriterio(ActionEvent event) {
    //    try {
    //        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/novoCriterioView.fxml"));
    //        Scene scene = new Scene(fxmlLoader.load());
    //        Stage stage = new Stage();
    //        stage.setTitle("Novo Criterio");
    //        stage.setScene(scene);
    //        stage.show();
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //}

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

    private void addBtnAcoes() {
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
                            actionButtons.setAlignment(Pos.CENTER);
                            setGraphic(actionButtons);
                        }
                    }
                };
                return cell;
            }
        };

        acoesColumn.setCellFactory(cellFactory);
    }

    // Método para abrir a tela de edição
    private void abrirTelaEdicao(Criterios criterios) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/edtCriteriosView.fxml"));
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
            tableCriterios.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

