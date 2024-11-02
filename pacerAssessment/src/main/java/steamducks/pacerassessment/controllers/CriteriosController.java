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
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import steamducks.pacerassessment.dao.CriteriosDAO;
import steamducks.pacerassessment.models.Criterio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

public class CriteriosController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Button btnAdcCriterio;

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
    private TableColumn<Criterio, Integer> idColumn;

    private ObservableList<Criterio> criterioData = FXCollections.observableArrayList();

    CriteriosDAO criteriosDao;

    private static final BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    public void initialize() {
        criterioData = FXCollections.observableArrayList();
        criteriosDao = new CriteriosDAO();

        List<Criterio> criterios = criteriosDao.buscarCriterios();
        criterioData.addAll(criterios);

        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        criteriosColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        descricaoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
        tableCriterios.setItems(criterioData);
    }

    @FXML
    void abrirTelaCadastroCriterio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/cadastroCriterioView.fxml"));
            Parent root = loader.load();

            if (contentPane != null) {
                contentPane.setEffect(blurEffect);
            }

            Stage stage = new Stage();
            stage.setTitle("Sistema RECAP");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo-dark.png")));
            stage.getIcons().add(logo);

            stage.setOnHidden(e -> {
                atualizarListaCriterios();
                if (contentPane != null) {
                    contentPane.setEffect(null);
                }
            });

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void atualizarListaCriterios() {
        tableCriterios.setItems(FXCollections.observableArrayList(criteriosDao.buscarCriterios()));
    }

    @FXML
    void abrirTelaEdicao(ActionEvent event) {
        Criterio criterioSelecionado = tableCriterios.getSelectionModel().getSelectedItem();

        if (criterioSelecionado == null) {
            mostrarAlerta("Sistema RECAP", "Nenhum critério selecionado. Por favor, selecione um critério para editar.", Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/edtCriteriosView.fxml"));
            Parent root = loader.load();
            EdtCriteriosController edtCriteriosController = loader.getController();
            edtCriteriosController.setCriterio(criterioSelecionado);

            if (contentPane != null) {
                contentPane.setEffect(blurEffect);
            }

            Stage stage = new Stage();
            stage.setTitle("Sistema RECAP");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo-dark.png")));
            stage.getIcons().add(logo);

            stage.showAndWait();

            contentPane.setEffect(null);
            tableCriterios.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void removerCriterio(ActionEvent event) {
        Criterio criterioSelecionado = tableCriterios.getSelectionModel().getSelectedItem();

        if (criterioSelecionado == null) {
            mostrarAlerta("Sistema RECAP", "Por favor, selecione um critério para excluir.", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Sistema RECAP");
        confirmAlert.setHeaderText("Você realmente quer excluir o critério?");
        confirmAlert.setContentText("Essa ação não pode ser desfeita.");

        Stage confirmStage = (Stage) confirmAlert.getDialogPane().getScene().getWindow();
        confirmStage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                criteriosDao.removerCriterio(criterioSelecionado.getId());
                atualizarListaCriterios();
                mostrarAlerta("Sistema RECAP", "Critério excluído com sucesso.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Sistema RECAP", "Erro ao excluir o critério. Tente novamente.", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        if (contentPane != null) {
            contentPane.setEffect(blurEffect);
        }

        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));
        alerta.showAndWait();

        if (contentPane != null) {
            contentPane.setEffect(null);
        }
    }
}
