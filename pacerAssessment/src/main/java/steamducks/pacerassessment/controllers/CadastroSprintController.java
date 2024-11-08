package steamducks.pacerassessment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import steamducks.pacerassessment.dao.SemestreDAO;
import steamducks.pacerassessment.models.Semestre;

public class CadastroSprintController {

    @FXML
    private Button btn_Cadastrar;

    @FXML
    private Button btn_Cancelar;

    @FXML
    private Button btn_Download;

    @FXML
    private Button btn_Upload;

    @FXML
    private ComboBox<Semestre> cmb_SelSem;

    @FXML
    private DatePicker dp_Fim;

    @FXML
    private DatePicker dp_Inicio;

    @FXML
    private TableView<?> tableViewSprint;

    @FXML
    private TableColumn<?, ?> tcNome;

    @FXML
    private TableColumn<?, ?> tc_DataFim;

    @FXML
    private TableColumn<?, ?> tc_DataInicio;

    @FXML
    private TableColumn<?, ?> tc_Sem;

    @FXML
    private TextField txtFieldNome;

    @FXML
    public void initialize() {
        carregarSemestres();
    }

    private void carregarSemestres() {
        SemestreDAO semestreDAO = new SemestreDAO();
        ObservableList<Semestre> semestreList = FXCollections.observableArrayList(semestreDAO.getSemestres());
        cmb_SelSem.setItems(semestreList);
    }

    @FXML
    void cadastrarSprint(ActionEvent event) {
        // Lógica para cadastrar a sprint
        String nomeSprint = txtFieldNome.getText();
        Semestre semestreSelecionado = cmb_SelSem.getValue();
        // Validar os campos e cadastrar a sprint
    }

    @FXML
    void cancelarCadastrarSprint(ActionEvent event) {
        // Lógica para cancelar o cadastro
    }
}
