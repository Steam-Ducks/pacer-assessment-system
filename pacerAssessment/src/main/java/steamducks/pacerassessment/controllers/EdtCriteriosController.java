package steamducks.pacerassessment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import steamducks.pacerassessment.dao.CriteriosDAO;
import steamducks.pacerassessment.models.Criterios;

public class EdtCriteriosController {

    private CriteriosDAO criteriosDAO = new CriteriosDAO();

    @FXML
    private TextField txtEdtNome;

    @FXML
    private Button btnConcluirEdt;

    @FXML
    private Button btnCancelEdit;

    @FXML
    private TextArea txtEditDescricao;


    private Criterios criterios;

    public void setCriterio(Criterios criterios) {
        this.criterios = criterios;
        txtEdtNome.setText(criterios.getNome()); // Preenche o campo com o valor atual
        txtEditDescricao.setText(criterios.getDescricao());
    }

    @FXML
    private void initialize() {
        // Botão Cancelar - fecha a janela sem salvar mudanças
        btnCancelEdit.setOnAction(event -> fecharJanela());

        // Botão Concluir - salva o novo valor e fecha a janela
        btnConcluirEdt.setOnAction(event -> {
            criterios.setNome(txtEdtNome.getText()); // Atualiza o valor do critério
            criterios.setDescricao(txtEditDescricao.getText());
            criteriosDAO.editarCriterio(criterios);

            fecharJanela();
        });
    }

    private void fecharJanela() {
        Stage stage = (Stage) btnCancelEdit.getScene().getWindow();
        stage.close(); // Fecha a janela
    }
}