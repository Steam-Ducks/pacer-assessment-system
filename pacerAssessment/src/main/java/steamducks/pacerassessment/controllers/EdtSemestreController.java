package steamducks.pacerassessment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import steamducks.pacerassessment.models.Semestre;

public class EdtSemestreController {

    @FXML
    private Button btnCancelEdit;

    @FXML
    private Button btnConcluirEdt;

    @FXML
    private TextField txtEdtSem;

    private Semestre semestreSelecionado;

    @FXML
    public void initialize() {
        // Ação do botão "Cancelar"
        btnCancelEdit.setOnAction(event -> fecharJanela());

        // Ação do botão "Concluir Edição"
        btnConcluirEdt.setOnAction(event -> concluirEdicao());
    }

    // Método para inicializar os campos com os dados do semestre selecionado
    public void inicializarCampos(Semestre semestre) {
        this.semestreSelecionado = semestre;
        txtEdtSem.setText(semestre.getNome());
    }

    // Método para concluir a edição e atualizar os dados
    private void concluirEdicao() {
        semestreSelecionado.setNome(txtEdtSem.getText());
        fecharJanela();
    }

    // Método para fechar a janela de edição
    private void fecharJanela() {
        Stage stage = (Stage) btnCancelEdit.getScene().getWindow();
        stage.close();
    }
}


