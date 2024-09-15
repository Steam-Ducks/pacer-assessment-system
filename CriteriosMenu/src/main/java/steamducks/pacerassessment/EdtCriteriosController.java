package steamducks.pacerassessment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EdtCriteriosController {

    @FXML
    private TextField txtEditar;

    @FXML
    private Button cancelEdtBtn;

    @FXML
    private Button concluirEdtBtn;

    private Criterios criterios;

    public void setCriterio(Criterios criterios) {
        this.criterios = criterios;
        txtEditar.setText(criterios.getCriterio()); // Preenche o campo com o valor atual
    }

    @FXML
    private void initialize() {
        // Botão Cancelar - fecha a janela sem salvar mudanças
        cancelEdtBtn.setOnAction(event -> fecharJanela());

        // Botão Concluir - salva o novo valor e fecha a janela
        concluirEdtBtn.setOnAction(event -> {
            criterios.setCriterio(txtEditar.getText()); // Atualiza o valor do critério
            fecharJanela();
        });
    }

    private void fecharJanela() {
        Stage stage = (Stage) cancelEdtBtn.getScene().getWindow();
        stage.close(); // Fecha a janela
    }
}
