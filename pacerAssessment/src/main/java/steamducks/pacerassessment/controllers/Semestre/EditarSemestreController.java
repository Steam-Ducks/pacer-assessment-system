package steamducks.pacerassessment.controllers.Semestre;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import steamducks.pacerassessment.dao.SemestreDAO;
import steamducks.pacerassessment.models.Semestre;

public class EditarSemestreController {

    @FXML
    private Button btnCancelEdit;

    @FXML
    private Button btnConcluirEdt;

    @FXML
    private TextField txtEdtSem;

    private Semestre semestreSelecionado;

    @FXML
    public void initialize() {
        btnCancelEdit.setOnAction(event -> fecharJanela());
        btnConcluirEdt.setOnAction(event -> concluirEdicao());
    }

    public void inicializarCampos(Semestre semestre) {
        this.semestreSelecionado = semestre;
        txtEdtSem.setText(semestre.getNome());
    }

    private void concluirEdicao() {
        String novoNome = txtEdtSem.getText();

        if (semestreSelecionado != null && semestreSelecionado.getId() > 0 && novoNome != null && !novoNome.trim().isEmpty()) {
            semestreSelecionado.setNome(novoNome);

            SemestreDAO dao = new SemestreDAO();
            dao.atualizarNomeSemestre(semestreSelecionado.getId(), novoNome);

            fecharJanela();

        } else {
            mostrarAlerta("Erro", "Semestre não encontrado ou nome inválido.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private void fecharJanela() {
        Stage stage = (Stage) btnCancelEdit.getScene().getWindow();
        stage.close();
    }
}


