package steamducks.SistemaRecap.controllers.Sprint;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import steamducks.SistemaRecap.dao.SprintDAO;
import steamducks.SistemaRecap.models.Sprint;

public class EditarSprintController {

    private SprintDAO sprintDAO = new SprintDAO();

    @FXML
    private DatePicker DateFim;

    @FXML
    private DatePicker DateInicio;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField txtSprint;

    private Sprint sprint; // Objeto Sprint a ser editado

    // Método para receber e exibir os dados da sprint selecionada
    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
        txtSprint.setText(sprint.getNome()); // Preenche o campo do nome
        DateInicio.setValue(sprint.getDataInicio()); // Preenche a data de início
        DateFim.setValue(sprint.getDataFim()); // Preenche a data de fim
    }

    @FXML
    private void initialize() {
        // Botão Cancelar - fecha a janela sem salvar mudanças
        btnCancelar.setOnAction(event -> fecharJanela());

        // Botão Confirmar - salva o novo valor e fecha a janela
        btnConfirmar.setOnAction(event -> {
            // Valida as datas antes de salvar
            if (DateFim.getValue().isBefore(DateInicio.getValue())) {
                mostrarAlerta("A data de fim não pode ser anterior à data de início.", "Erro");
                return; // Interrompe o processo se as datas forem inválidas
            }

            sprint.setNome(txtSprint.getText()); // Atualiza o nome
            sprint.setDataInicio(DateInicio.getValue()); // Atualiza a data de início
            sprint.setDataFim(DateFim.getValue()); // Atualiza a data de fim
            sprintDAO.editarSprint(sprint); // Chama o DAO para salvar no banco

            mostrarAlerta("Sprint editada com sucesso!", "Sucesso");

            fecharJanela(); // Fecha a janela após salvar
        });
    }


    private void fecharJanela() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close(); // Fecha a janela
    }

    private void mostrarAlerta(String alerta, String titulo) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(alerta);
        alert.showAndWait();
    }
}
