package steamducks.pacerassessment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import steamducks.pacerassessment.dao.EquipeDAO;
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

    @FXML
    private TableColumn<Equipe, String> tcNome;

    @FXML
    private TableColumn<Equipe, String> tcGithub;

    private ObservableList<Equipe> listaEquipes = FXCollections.observableArrayList();

    private static final BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    @FXML
    public void initialize() {
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcGithub.setCellValueFactory(new PropertyValueFactory<>("github"));
        carregarEquipes();
    }

    private void carregarEquipes() {
        EquipeDAO equipeDAO = new EquipeDAO();
        listaEquipes.setAll(equipeDAO.getEquipes());
        tbEquipes.setItems(listaEquipes);
    }

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
        Equipe equipeSelecionado = tbEquipes.getSelectionModel().getSelectedItem();

        if(equipeSelecionado != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/telaEditarEquipesView.fxml"));
                Parent root = fxmlLoader.load();

                TelaEditarEquipesController controller = fxmlLoader.getController();
                controller.inicializarCampos(equipeSelecionado.getIdEquipe());

                Stage stage = new Stage();
                stage.setTitle("Sistema RECAP");
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));
                stage.show();

                stage.setOnHidden(e -> contentPane.setEffect(null));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            mostrarAlerta("Seleção Inválida", "Selecione uma equipe para editar.", Alert.AlertType.WARNING);
            if (contentPane != null) {
                contentPane.setEffect(null);
            }
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
            confirmacao.setContentText("Tem certeza de que deseja excluir a equipe selecionada?");

            confirmacao.showAndWait().ifPresent(resposta -> {
                if (resposta == ButtonType.OK) {
                    EquipeDAO dao = new EquipeDAO();
                    boolean sucesso = dao.excluirEquipe(equipeSelecionada.getIdEquipe());

                    if (sucesso) {
                        listaEquipes.remove(equipeSelecionada);
                        mostrarAlerta("Sucesso", "Equipe excluída com sucesso.", Alert.AlertType.INFORMATION);
                    } else {
                        mostrarAlerta("Erro", "Erro ao excluir a equipe. Tente novamente.", Alert.AlertType.ERROR);
                    }
                }

                if (contentPane != null) {
                    contentPane.setEffect(null);
                }
            });
        } else {
            mostrarAlerta("Seleção Inválida", "Selecione uma equipe para excluir.", Alert.AlertType.WARNING);

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