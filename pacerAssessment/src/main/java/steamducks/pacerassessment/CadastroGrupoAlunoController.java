package steamducks.pacerassessment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    private final ObservableList<Aluno> alunoList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));

        tvAlunos.setItems(alunoList);
    }

    @FXML
    void registrar(ActionEvent event) {
        System.out.println("Registrado com sucesso!");
        alunoList.forEach(aluno -> {
            System.out.println(aluno);
        });
        String equipe = txtEquipe.getText();
        String github = txtGithub.getText();
        System.out.println("Equipe: "+equipe);
        System.out.println("Github: "+github);
        Stage stage = (Stage) btnRegistrar.getScene().getWindow();
        stage.close();
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

            Equipe equipe = new Equipe(txtEquipe.getText(), txtGithub.getText());

            while ((line = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] values = line.split(",");
                if (values.length >= 3) {
                    Aluno aluno = new Aluno(values[0], values[1], values[2], equipe);
                    alunoList.add(aluno);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
