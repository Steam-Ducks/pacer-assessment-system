package steamducks.SistemaRecap.controllers.Sprint;

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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import steamducks.SistemaRecap.dao.SprintDAO;
import steamducks.SistemaRecap.models.Sprint;
import steamducks.SistemaRecap.models.Semestre;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

public class GerenciarSprintController {

    @FXML
    private Button btn_AdcSprint;

    @FXML
    private Button btn_EditSprint;

    @FXML
    private Button btn_RmvSprint;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<Sprint> tableSprints;

    @FXML
    private TableColumn<Sprint, Integer> idColumn;

    @FXML
    private TableColumn<Sprint, String> nomeColumn;

    @FXML
    private TableColumn<Sprint, String> descricaoColumn;

    @FXML
    private TableColumn<Sprint, String> dataInicioColumn;

    @FXML
    private TableColumn<Sprint, String> dataFimColumn;

    @FXML
    private TableColumn<Sprint, String> semestreColumn;

    private ObservableList<Sprint> sprintData = FXCollections.observableArrayList();

    private SprintDAO sprintDao;
    private Map<Integer, String> semestreMap = new HashMap<>(); // Mapa para armazenar o nome dos semestres

    private static final BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    public void initialize() {
        sprintDao = new SprintDAO();
        carregarSprints();

        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        dataInicioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDataInicio().toString()));
        dataFimColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDataFim().toString()));

        // Modificação na coluna semestre para exibir o nome ao invés do ID
        semestreColumn.setCellValueFactory(cellData -> {
            Integer idSemestre = cellData.getValue().getIdSemestre();
            String semestreNome = semestreMap.get(idSemestre);
            return new SimpleStringProperty(semestreNome != null ? semestreNome : "Semestre não encontrado");
        });

        tableSprints.setItems(sprintData);
    }

    private void carregarSprints() {
        sprintData.clear();  // Limpa a lista para evitar duplicação
        List<Sprint> sprints = sprintDao.buscarSprint();
        sprintData.addAll(sprints);

        // Carregar semestres e armazenar no mapa
        List<Semestre> semestres = sprintDao.buscarTodosSemestres();  // Método para buscar todos os semestres
        semestreMap.clear();
        for (Semestre semestre : semestres) {
            semestreMap.put(semestre.getId(), semestre.getNome());
        }
    }

    @FXML
    void adicionarSprint(ActionEvent event) {
        try {
            if (contentPane != null) {
                contentPane.setEffect(blurEffect);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SistemaRecap/Sprint/cadastroSprintView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Cadastrar Sprint");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));

            stage.setOnHidden(e -> {
                carregarSprints();  // Chama novamente para garantir que a tabela seja atualizada
                if (contentPane != null) {
                    contentPane.setEffect(null);
                }
            });

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removerSprint(ActionEvent event) {
        Sprint sprintSelecionada = tableSprints.getSelectionModel().getSelectedItem();

        if (sprintSelecionada == null) {
            mostrarAlerta("Sistema RECAP", "Por favor, selecione uma sprint para excluir.", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Sistema RECAP");
        confirmAlert.setHeaderText("Você realmente quer excluir a sprint?");
        confirmAlert.setContentText("Essa ação não pode ser desfeita.");

        Stage confirmStage = (Stage) confirmAlert.getDialogPane().getScene().getWindow();
        confirmStage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                sprintDao.removerSprint(sprintSelecionada.getIdSprint());
                carregarSprints();
                mostrarAlerta("Sistema RECAP", "Sprint excluída com sucesso.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Sistema RECAP", "Erro ao excluir a sprint. Tente novamente.", Alert.AlertType.ERROR);
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
