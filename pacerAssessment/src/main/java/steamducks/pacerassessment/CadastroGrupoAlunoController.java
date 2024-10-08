package steamducks.pacerassessment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CadastroGrupoAlunoController {

    @FXML
    private TextField txtEquipe;
    @FXML
    private TextField txtGithub;

    @FXML
    private ComboBox<String> cmbSemestre;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnUpload;

    @FXML
    private Button btnCancelar;

    @FXML
    private TableColumn<Aluno, String> tcEmail;

    @FXML
    private TableColumn<Aluno, String> tcNome;

    @FXML
    private TableColumn<Aluno, String> tcSenha;

    @FXML
    private TableView<Aluno> tvAlunos;

    private Equipe equipe;
    private final ObservableList<Aluno> alunoList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));

        tvAlunos.setItems(alunoList);

        ObservableList<String> semestreList = FXCollections.observableArrayList(
                "1º semestre", "2º semestre", "3º semestre", "4º semestre", "5º semestre", "6º semestre"
        );
        cmbSemestre.setItems(semestreList);
    }

    @FXML
    void registrar(ActionEvent event) {
        if (equipe != null) {
            System.out.println(equipe);
        } else {
            System.out.println("Equipe não criada.");
        }

        txtEquipe.setText("");
        txtGithub.setText("");

        alunoList.clear();
    }

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void upload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            importarCsv(file);
        }
    }

    private void importarCsv(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean primeiraLinha = true;
            alunoList.clear();

            while ((line = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] values = line.split(",");
                if (values.length >= 3) {
                    Aluno aluno = new Aluno(values[0], values[1], values[2]);
                    alunoList.add(aluno);
                }
            }

            equipe = new Equipe(txtEquipe.getText(), txtGithub.getText(), alunoList, cmbSemestre.getValue());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
