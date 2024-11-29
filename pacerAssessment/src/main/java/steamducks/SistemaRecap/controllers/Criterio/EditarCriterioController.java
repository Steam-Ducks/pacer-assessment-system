package steamducks.SistemaRecap.controllers.Criterio;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import steamducks.SistemaRecap.dao.CriteriosDAO;
import steamducks.SistemaRecap.models.Criterio;

public class EditarCriterioController {

    private final CriteriosDAO criteriosDAO = new CriteriosDAO();

    @FXML
    private TextField txtEdtNome;

    @FXML
    private Button btnConcluirEdt;

    @FXML
    private Button btnCancelEdit;

    @FXML
    private TextArea txtEditDescricao;

    private Criterio criterio;

    public void setCriterio(Criterio criterio) {
        if (criterio == null) {
            throw new IllegalArgumentException("Critério não pode ser nulo.");
        }
        this.criterio = criterio;
        preencherCampos();
    }

    @FXML
    private void initialize() {
        configurarEventos();
    }

    private void configurarEventos() {
        btnCancelEdit.setOnAction(event -> fecharJanela());
        btnConcluirEdt.setOnAction(event -> salvarAlteracoes());
    }

    private void preencherCampos() {
        txtEdtNome.setText(criterio.getNome());
        txtEditDescricao.setText(criterio.getDescricao());
    }

    private void salvarAlteracoes() {
        if (criterio == null) {
            mostrarErro("Critério inválido. Não foi possível salvar as alterações.");
            return;
        }

        criterio.setNome(txtEdtNome.getText().trim());
        criterio.setDescricao(txtEditDescricao.getText().trim());

try {
            criteriosDAO.editarCriterio(criterio);
            fecharJanela();
        } catch (Exception e) {
            mostrarErro("Erro ao salvar as alterações: " + e.getMessage());
        }
    }

    private void fecharJanela() {
        Stage stage = (Stage) btnCancelEdit.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }

    private void mostrarErro(String mensagem) {
        // Aqui você pode implementar uma lógica para exibir um alerta de erro ao usuário,
        // como um Alert ou algum log visual.
        System.err.println(mensagem);
    }
}