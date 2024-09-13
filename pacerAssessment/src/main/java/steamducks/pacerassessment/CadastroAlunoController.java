package steamducks.pacerassessment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CadastroAlunoController implements Initializable {
    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<String> cmbEquipe;

    @FXML
    private ComboBox<String> cmbSemestre;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtSenha;

    private Map<String, ObservableList<String>> semestreEquipeMap;

    @FXML
    void cadastrar(ActionEvent event) {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String senha = txtSenha.getText();
        String semestre = cmbSemestre.getValue();
        String equipe = cmbEquipe.getValue();

        Aluno aluno = new Aluno(nome, email, senha, semestre, equipe);

        System.out.println("Aluno cadastrado: " + aluno);
    }


    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> semestres = FXCollections.observableArrayList("Primeiro", "Segundo", "Terceiro");
        cmbSemestre.setItems(semestres);

        semestreEquipeMap = new HashMap<>();
        semestreEquipeMap.put("Primeiro", FXCollections.observableArrayList("teste1", "2", "3"));
        semestreEquipeMap.put("Segundo", FXCollections.observableArrayList("Steam Ducks", "sqlutions", "outros"));
        semestreEquipeMap.put("Terceiro", FXCollections.observableArrayList("testando", "123", "pedrinho"));

        cmbSemestre.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                cmbEquipe.setItems(semestreEquipeMap.getOrDefault(newValue, FXCollections.observableArrayList()));
            }
        });
    }
}
