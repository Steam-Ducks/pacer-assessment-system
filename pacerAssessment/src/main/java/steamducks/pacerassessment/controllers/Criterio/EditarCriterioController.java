package steamducks.pacerassessment.controllers.Criterio;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import steamducks.pacerassessment.dao.CriteriosDAO;
import steamducks.pacerassessment.models.Criterio;

public class EditarCriterioController {

    private CriteriosDAO criteriosDAO = new CriteriosDAO();

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
        this.criterio = criterio;
        txtEdtNome.setText(criterio.getNome()); // Preenche o campo com o valor atual
        txtEditDescricao.setText(criterio.getDescricao());
    }

    @FXML
    private void initialize() {
        // Botão Cancelar - fecha a janela sem salvar mudanças
        btnCancelEdit.setOnAction(event -> fecharJanela());

        // Botão Concluir - salva o novo valor e fecha a janela
        btnConcluirEdt.setOnAction(event -> {
            criterio.setNome(txtEdtNome.getText()); // Atualiza o valor do critério
            criterio.setDescricao(txtEditDescricao.getText());
            criteriosDAO.editarCriterio(criterio);

            fecharJanela();
        });
    }

    private void fecharJanela() {
        Stage stage = (Stage) btnCancelEdit.getScene().getWindow();
        stage.close(); // Fecha a janela
    }
}