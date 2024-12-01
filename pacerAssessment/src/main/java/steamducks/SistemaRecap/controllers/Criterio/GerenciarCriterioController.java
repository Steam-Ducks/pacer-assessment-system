package steamducks.SistemaRecap.controllers.Criterio;

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
import steamducks.SistemaRecap.dao.CriteriosDAO;
import steamducks.SistemaRecap.models.Criterio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

public class GerenciarCriterioController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<Criterio> tableCriterios;

    @FXML
    private TableColumn<Criterio, Integer> idColumn;

    @FXML
    private TableColumn<Criterio, String> criteriosColumn;

    @FXML
    private TableColumn<Criterio, String> descricaoColumn;

    private ObservableList<Criterio> criterioData;
    private final CriteriosDAO criteriosDao = new CriteriosDAO();

    private static final BoxBlur BLUR_EFFECT = new BoxBlur(10, 10, 3);
    private static final String LOGO_PATH = "/assets/logo-dark.png";
    private static final String CADASTRO_CRITERIO_PATH = "/SistemaRecap/Criterio/cadastroCriterioView.fxml";
    private static final String EDICAO_CRITERIO_PATH = "/SistemaRecap/Criterio/editarCriteriosView.fxml";
    private static final String SISTEMA_RECAP_TITLE = "Sistema RECAP";

    @FXML
    public void initialize() {
        criterioData = FXCollections.observableArrayList(criteriosDao.buscarCriterios());
        configurarTabela();
    }

    private void configurarTabela() {
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        criteriosColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        descricaoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
        tableCriterios.setItems(criterioData);
    }

    @FXML
    void abrirTelaCadastroCriterio(ActionEvent event) {
        abrirTelaModal(CADASTRO_CRITERIO_PATH, "Cadastro de Critério");
    }

    @FXML
    void abrirTelaEdicao(ActionEvent event) {
        Criterio criterioSelecionado = tableCriterios.getSelectionModel().getSelectedItem();
        if (criterioSelecionado == null) {
            mostrarAlerta(SISTEMA_RECAP_TITLE, "Nenhum critério selecionado. Por favor, selecione um critério para editar.", Alert.AlertType.WARNING);
            return;
        }

        abrirTelaModal(EDICAO_CRITERIO_PATH, "Editar Critério", controller -> {
            if (controller instanceof EditarCriterioController editarCriterioController) {
                editarCriterioController.setCriterio(criterioSelecionado);
            }
        });

        tableCriterios.refresh();
    }

    private void abrirTelaModal(String fxmlPath, String titulo) {
        abrirTelaModal(fxmlPath, titulo, null);
    }

    private void abrirTelaModal(String fxmlPath, String titulo, TelaConfigurarCallback callback) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            if (callback != null) {
                callback.configurar(loader.getController());
            }

            aplicarBlur();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(LOGO_PATH))));
            stage.setOnHidden(event -> {
                atualizarListaCriterios();
                removerBlur();
            });

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(SISTEMA_RECAP_TITLE, "Erro ao carregar a tela: " + titulo, Alert.AlertType.ERROR);
        }
    }

    @FXML
    void removerCriterio(ActionEvent event) {
        Criterio criterioSelecionado = tableCriterios.getSelectionModel().getSelectedItem();
        if (criterioSelecionado == null) {
            mostrarAlerta(SISTEMA_RECAP_TITLE, "Por favor, selecione um critério para excluir.", Alert.AlertType.WARNING);
            return;
        }

        if (confirmarAcao("Você realmente quer excluir o critério?", "Essa ação não pode ser desfeita.")) {
            try {
                criteriosDao.removerCriterio(criterioSelecionado.getId());
                atualizarListaCriterios();
                mostrarAlerta(SISTEMA_RECAP_TITLE, "Critério excluído com sucesso.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta(SISTEMA_RECAP_TITLE, "Erro ao excluir o critério. Tente novamente.", Alert.AlertType.ERROR);
            }
        }
    }

    private void atualizarListaCriterios() {
        criterioData.setAll(criteriosDao.buscarCriterios());
    }

    private void aplicarBlur() {
        if (contentPane != null) {
            contentPane.setEffect(BLUR_EFFECT);
        }
    }

    private void removerBlur() {
        if (contentPane != null) {
            contentPane.setEffect(null);
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(LOGO_PATH))));
        alerta.showAndWait();
    }

    private boolean confirmarAcao(String header, String content) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle(SISTEMA_RECAP_TITLE);
        confirmAlert.setHeaderText(header);
        confirmAlert.setContentText(content);

        Stage stage = (Stage) confirmAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(LOGO_PATH))));

        Optional<ButtonType> result = confirmAlert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FunctionalInterface
    private interface TelaConfigurarCallback {
        void configurar(Object controller);
    }
}