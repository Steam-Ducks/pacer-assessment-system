package steamducks.pacerassessment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import steamducks.pacerassessment.dao.CriteriosDAO;
import steamducks.pacerassessment.models.Criterio;

public class CadastroCriterioController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextArea txtDescricao;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCancelEdt;

    private final CriteriosDAO criteriosDao = new CriteriosDAO();

    // Metodo para adicionar o ícone aos alerts
    private void adicionarIcone(Alert alert) {
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));
    }

    @FXML
    void adicionarCriterio(ActionEvent event) {
        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();

        if (nome.isEmpty() || descricao.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sistema RECAP");
            alert.setHeaderText("Todos os campos devem ser preenchidos");
            alert.setContentText("Por favor, preencha os campos Nome e Descrição.");
            adicionarIcone(alert); // Adiciona o ícone ao alert
            alert.showAndWait();
            return;
        }

        // Verifica se o critério já existe
        if (criteriosDao.existeCriterioComNome(nome)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sistema RECAP");
            alert.setHeaderText("Este critério já foi cadastrado");
            alert.setContentText("Por favor, escolha um nome diferente para o critério.");
            adicionarIcone(alert); // Adiciona o ícone ao alert
            alert.showAndWait();
            return;
        }

        Criterio criterio = new Criterio(nome, descricao);
        try {
            criteriosDao.adicionarCriterio(criterio);
            Stage stage = (Stage) btnCadastrar.getScene().getWindow();
            stage.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancelarCadastrarCriterio(ActionEvent event) {
        Stage stage = (Stage) btnCancelEdt.getScene().getWindow();
        stage.close();
    }
}
