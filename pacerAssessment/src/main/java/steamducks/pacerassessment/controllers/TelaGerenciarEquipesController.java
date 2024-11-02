package steamducks.pacerassessment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import steamducks.pacerassessment.dao.EquipeDAO;
import steamducks.pacerassessment.dao.SemestreDAO;
import steamducks.pacerassessment.models.Equipe;
import steamducks.pacerassessment.models.Semestre;

import java.io.IOException;

public class TelaGerenciarEquipesController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Button btnCadastrarEquipe;

    @FXML
    private Button btnEditarEquipe;

    @FXML
    private Button btnExcluirEquipe;

    @FXML
    private TableView<Equipe> tbEquipes;

    private ObservableList<Equipe> listaEquipes = FXCollections.observableArrayList();

    private static final BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    @FXML
    void cadastrarEquipe(ActionEvent event) {
        try {
            if (contentPane != null) {
                contentPane.setEffect(blurEffect);
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/cadastroGrupoAlunoView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Sistema RECAP");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));

            stage.setOnHidden(e -> contentPane.setEffect(null));

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void abrirEditarEquipe(ActionEvent event) {
        if (contentPane != null) {
            contentPane.setEffect(blurEffect);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/telaEditarEquipesView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Sistema RECAP");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));
            stage.show();

            stage.setOnHidden(e -> contentPane.setEffect(null));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void excluirEquipe(ActionEvent actionEvent) {
        Equipe equipeSelecionada = tbEquipes.getSelectionModel().getSelectedItem();

        if (contentPane != null) {
            contentPane.setEffect(blurEffect);
        }

        if (equipeSelecionada != null) {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação de exclusão");

            Stage stage = (Stage) confirmacao.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));

            confirmacao.setHeaderText(null);
            confirmacao.setContentText("Tem certeza de que deseja excluir o semestre selecionado?");

            confirmacao.showAndWait().ifPresent(resposta -> {
                if (resposta == ButtonType.OK) {
                    EquipeDAO dao = new EquipeDAO();
                    boolean sucesso = dao.excluirEquipe(equipeSelecionada.getIdEquipe());

                    if (sucesso) {
                        listaEquipes.remove(equipeSelecionada);
                        mostrarAlerta("Sucesso", "Semestre excluído com sucesso.", Alert.AlertType.INFORMATION);
                    } else {
                        mostrarAlerta("Erro", "Erro ao excluir o semestre. Tente novamente.", Alert.AlertType.ERROR);

                    }
                }

                if (contentPane != null) {
                    contentPane.setEffect(null);
                }
            });
        } else {
            mostrarAlerta("Seleção Inválida", "Selecione um semestre para excluir.", Alert.AlertType.WARNING);

            if (contentPane != null) {
                contentPane.setEffect(null);
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        if (contentPane != null) {
            contentPane.setEffect(blurEffect);
        }

        Alert alerta = new Alert(tipo);
        alerta.setTitle("Sistema RECAP");
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
