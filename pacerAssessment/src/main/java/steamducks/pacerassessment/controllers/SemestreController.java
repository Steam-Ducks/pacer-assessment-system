package steamducks.pacerassessment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import steamducks.pacerassessment.dao.SemestreDAO;
import steamducks.pacerassessment.models.Semestre;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javafx.scene.image.Image;

public class SemestreController {

    @FXML
    private AnchorPane contentPane; // Pane para aplicar o efeito de desfoque

    @FXML
    private Button bntEditSem;

    @FXML
    private Button bntAdcSemestre;

    @FXML
    private Button bntRmvCrit;

    @FXML
    private TextField txtSemestre;

    @FXML
    private ListView<Semestre> listView; // <Semestre> especifica o tipo da ListView

    private ObservableList<Semestre> semestreNome = FXCollections.observableArrayList();

    private static final BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    @FXML
    private void initialize() {
        carregarSemestres(); // Adiciona semestres do banco de dados na ObservableList

        listView.setCellFactory(param -> new ListCell<Semestre>() {
            @Override
            protected void updateItem(Semestre item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome());
                }
            }
        });

        listView.setItems(semestreNome);

        bntAdcSemestre.setOnAction(event -> adicionarSemestre());
        bntEditSem.setOnAction(event -> abrirTelaEdicao());
        bntRmvCrit.setOnAction(event -> removerSemestre());
    }

    private void carregarSemestres() {
        SemestreDAO dao = new SemestreDAO();

        try {
            List<Semestre> semestres = dao.getSemestres();
            semestreNome.addAll(semestres);
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao carregar semestres: " + e.getMessage(), AlertType.ERROR);
        }
    }


    public void adicionarSemestre() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/cadastrarSemestreView.fxml"));
            Parent root = fxmlLoader.load();

            // Aplica efeito de desfoque no contentPane se não for nulo
            if (contentPane != null) {
                contentPane.setEffect(blurEffect);
            }

            Scene scene = new Scene(root);

            Stage stage = new Stage(StageStyle.DECORATED); // Define a janela como sem decoração
            stage.setTitle("Sistema RECAP");
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.centerOnScreen();

            Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo-dark.png")));
            stage.getIcons().add(logo);

            stage.setScene(scene);
            stage.show();

            // Remove o efeito quando o popup é fechado
            stage.setOnHidden(event -> {
                if (contentPane != null) {
                    contentPane.setEffect(null);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para abrir a tela de edição
    public void abrirTelaEdicao() {
        Semestre semestreSelecionado = listView.getSelectionModel().getSelectedItem();

        if (semestreSelecionado != null) {
            try {
                // Carregar o FXML da tela de edição
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/edtSemestreView.fxml"));
                Parent root = loader.load();

                // Aplica efeito de desfoque no contentPane se não for nulo
                if (contentPane != null) {
                    contentPane.setEffect(blurEffect);
                }

                // Exibir a nova janela
                Stage stage = new Stage();
                stage.setTitle("Sistema RECAP");
                stage.setScene(new Scene(root));
                stage.setMaximized(false);
                stage.setResizable(false);
                Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo-dark.png")));
                stage.getIcons().add(logo);
                stage.initModality(Modality.APPLICATION_MODAL); // Bloquear interação com a janela anterior
                stage.show();

                // Passar os dados para o controller de edição
                EdtSemestreController controller = loader.getController();
                controller.inicializarCampos(semestreSelecionado);

                // Atualizar a ListView após o fechamento da janela de edição
                stage.setOnHidden(event -> listView.refresh());

                // Remove o efeito quando o popup é fechado
                stage.setOnHidden(event -> {
                    if (contentPane != null) {
                        contentPane.setEffect(null);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Seleção Inválida", "Selecione um semestre para editar.", AlertType.WARNING);
        }
    }

    // Método para remover o semestre selecionado
    public void removerSemestre() {
        Semestre semestreSelecionado = listView.getSelectionModel().getSelectedItem();

        if (semestreSelecionado != null) {
            semestreNome.remove(semestreSelecionado); // Remove o semestre da lista
        } else {
            mostrarAlerta("Seleção Inválida", "Selecione um semestre para remover.", AlertType.WARNING);
        }
    }

    // Limpar o campo de texto
    private void limparCampos() {
        txtSemestre.clear();
    }

    // Exibir uma caixa de alerta
    private void mostrarAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
