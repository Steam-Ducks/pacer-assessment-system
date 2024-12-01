package steamducks.SistemaRecap.controllers.Criterio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import steamducks.SistemaRecap.dao.CriteriosDAO;
import steamducks.SistemaRecap.models.Criterio;

public class CadastrarCriterioController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextArea txtDescricao;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCancelEdt;

    private final CriteriosDAO criteriosDao = new CriteriosDAO();

    // Constantes para mensagens
    private static final String TITULO_ALERTA = "Sistema RECAP";
    private static final String MENSAGEM_CAMPOS_VAZIOS = "Por favor, preencha os campos Nome e Descrição.";
    private static final String MENSAGEM_CRITERIO_EXISTENTE = "Por favor, escolha um nome diferente para o critério.";
    private static final String ERRO_SALVAR_CRITERIO = "Ocorreu um erro ao salvar o critério. Tente novamente.";

    /**
     * Adiciona o ícone padrão aos alertas.
     *
     * @param alert Alerta ao qual o ícone será adicionado.
     */
    private void adicionarIcone(Alert alert) {
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));
    }

    /**
     * Exibe uma mensagem de alerta genérica.
     *
     * @param tipo       Tipo de alerta (WARNING, ERROR, etc.).
     * @param mensagem   Mensagem exibida no corpo do alerta.
     */
    private void exibirAlerta(Alert.AlertType tipo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(TITULO_ALERTA);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        adicionarIcone(alert);
        alert.showAndWait();
    }

    /**
     * Valida os campos de entrada do formulário.
     *
     * @return true se os campos forem válidos, false caso contrário.
     */
    private boolean validarCampos(String nome, String descricao) {
        if (nome.isEmpty() || descricao.isEmpty()) {
            exibirAlerta(Alert.AlertType.WARNING, MENSAGEM_CAMPOS_VAZIOS);
            return false;
        }

        if (criteriosDao.existeCriterioComNome(nome)) {
            exibirAlerta(Alert.AlertType.WARNING, MENSAGEM_CRITERIO_EXISTENTE);
            return false;
        }

        return true;
    }

    /**
     * Adiciona um novo critério ao sistema.
     */
    @FXML
    void adicionarCriterio(ActionEvent event) {
        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();

        if (!validarCampos(nome, descricao)) {
            return; // Interrompe se os campos forem inválidos
        }

        Criterio criterio = new Criterio(nome, descricao);
        try {
            criteriosDao.adicionarCriterio(criterio);
            fecharJanela();
        } catch (RuntimeException e) {
            e.printStackTrace();
            exibirAlerta(Alert.AlertType.ERROR, ERRO_SALVAR_CRITERIO);
        }
    }

    /**
     * Fecha a janela atual.
     */
    private void fecharJanela() {
        Stage stage = (Stage) btnCadastrar.getScene().getWindow();
        stage.close();
    }

    /**
     * Cancela a operação e fecha a janela.
     */
    @FXML
    void cancelarCadastrarCriterio(ActionEvent event) {
        fecharJanela();
    }
}
