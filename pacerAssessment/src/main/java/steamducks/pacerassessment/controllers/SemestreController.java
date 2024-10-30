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
import javafx.scene.image.ImageView;
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
    private AnchorPane contentPane;

    @FXML
    private Button bntEditSem;

    @FXML
    private Button bntAdcSemestre;

    @FXML
    private Button bntRmvSem;

    @FXML
    private TextField txtSemestre;

    @FXML
    private ListView<Semestre> listView;

    private ObservableList<Semestre> listaSemestres = FXCollections.observableArrayList();

    private static final BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    @FXML
    private void initialize() {
        carregarSemestres();

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

        listView.setItems(listaSemestres);

        bntAdcSemestre.setOnAction(event -> adicionarSemestre());
        bntEditSem.setOnAction(event -> abrirTelaEdicao());
        bntRmvSem.setOnAction(event -> removerSemestre());
    }

    private void carregarSemestres() {
        SemestreDAO dao = new SemestreDAO();

        try {
            List<Semestre> semestres = dao.getSemestres();
            listaSemestres.setAll(FXCollections.observableArrayList(semestres));
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao carregar semestres: " + e.getMessage(), AlertType.ERROR);
        }
    }

    public void adicionarSemestre() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/cadastrarSemestreView.fxml"));
            Parent root = fxmlLoader.load();

            if (contentPane != null) {
                contentPane.setEffect(blurEffect);
            }

            Scene scene = new Scene(root);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Sistema RECAP");
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.centerOnScreen();

            Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo-dark.png")));
            stage.getIcons().add(logo);

            stage.setScene(scene);
            stage.show();

            stage.setOnHidden(event -> {
                if (contentPane != null) {
                    contentPane.setEffect(null);
                }
                carregarSemestres();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirTelaEdicao() {
        Semestre semestreSelecionado = listView.getSelectionModel().getSelectedItem();

        if (semestreSelecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/edtSemestreView.fxml"));
                Parent root = loader.load();

                if (contentPane != null) {
                    contentPane.setEffect(blurEffect);
                }

                Stage stage = new Stage();
                stage.setTitle("Sistema RECAP");
                stage.setScene(new Scene(root));
                stage.setMaximized(false);
                stage.setResizable(false);

                Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo-dark.png")));
                stage.getIcons().add(logo);

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();

                EdtSemestreController controller = loader.getController();
                controller.inicializarCampos(semestreSelecionado);

                stage.setOnHidden(event -> {
                    carregarSemestres();
                });

                stage.setOnHidden(event -> {
                    if (contentPane != null) {
                        contentPane.setEffect(null);
                    }
                    carregarSemestres();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Seleção Inválida", "Selecione um semestre para editar.", AlertType.WARNING);
        }
    }

    public void removerSemestre() {
        Semestre semestreSelecionado = listView.getSelectionModel().getSelectedItem();

        if (contentPane != null) {
            contentPane.setEffect(blurEffect);
        }

        if (semestreSelecionado != null) {
            Alert confirmacao = new Alert(AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação de exclusão");

            Stage stage = (Stage) confirmacao.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));

            confirmacao.setHeaderText(null);
            confirmacao.setContentText("Tem certeza de que deseja excluir o semestre selecionado?");

            confirmacao.showAndWait().ifPresent(resposta -> {
                if (resposta == ButtonType.OK) {
                    SemestreDAO dao = new SemestreDAO();
                    boolean sucesso = dao.excluirSemestre(semestreSelecionado.getId());

                    if (sucesso) {
                        listaSemestres.remove(semestreSelecionado);
                        mostrarAlerta("Sucesso", "Semestre excluído com sucesso.", AlertType.INFORMATION);
                    } else {
                        mostrarAlerta("Erro", "Erro ao excluir o semestre. Tente novamente.", AlertType.ERROR);

                    }
                }

                if (contentPane != null) {
                    contentPane.setEffect(null);
                }
            });
        } else {
            mostrarAlerta("Seleção Inválida", "Selecione um semestre para excluir.", AlertType.WARNING);

            if (contentPane != null) {
                contentPane.setEffect(null);
            }
        }
    }

    private void limparCampos() {
        txtSemestre.clear();
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
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png"))); // Caminho do ícone
        alerta.showAndWait();

        if (contentPane != null) {
            contentPane.setEffect(null);
        }
    }

}
