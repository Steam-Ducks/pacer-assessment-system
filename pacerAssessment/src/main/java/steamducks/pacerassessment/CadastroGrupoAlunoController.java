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

import steamducks.pacerassessment.dao.GrupoAlunoDAO;

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
    private TableColumn<Usuario, String> tcEmail;

    @FXML
    private TableColumn<Usuario, String> tcNome;

    @FXML
    private TableColumn<Usuario, String> tcSenha;

    @FXML
    private TableView<Usuario> tvAlunos;

    private Equipe equipe;
    private final ObservableList<Usuario> alunoList = FXCollections.observableArrayList();

    // Inicializa a tela e popula o ComboBox com os semestres do banco
    @FXML
    public void initialize() {
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));

        tvAlunos.setItems(alunoList);

        // Chamar o método para buscar semestres do banco
        carregarSemestres();
    }

    // Método para carregar semestres do banco de dados
    private void carregarSemestres() {
        GrupoAlunoDAO grupoAlunoDAO = new GrupoAlunoDAO();
        ObservableList<String> semestreList = FXCollections.observableArrayList(grupoAlunoDAO.buscarSemestres());
        cmbSemestre.setItems(semestreList);
    }

    @FXML
    void registrar(ActionEvent event) {
        String nomeEquipe = txtEquipe.getText();
        String github = txtGithub.getText();
        String semestreSelecionado = cmbSemestre.getValue();

        if (nomeEquipe.isEmpty() || github.isEmpty() || semestreSelecionado == null) {
            System.out.println("Por favor, preencha todos os campos.");
            return;
        }

        GrupoAlunoDAO dao = new GrupoAlunoDAO();

        // Obter o ID do semestre baseado no nome selecionado
        int idSemestre = dao.obterIdSemestre(semestreSelecionado);

        int idEquipe = dao.criarEquipe(nomeEquipe, github, idSemestre); // Cria a equipe

        // Update `idEquipe` for each `Usuario`
        for (Usuario aluno : alunoList) {
            aluno.setIdEquipe(idEquipe);
        }

        dao.adicionarAlunos(idEquipe, alunoList); // Adiciona os alunos à equipe

        // Limpar campos e lista de alunos
        txtEquipe.setText("");
        txtGithub.setText("");
        alunoList.clear();
        tvAlunos.setItems(alunoList); // Limpa a tabela de alunos
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
                    Usuario aluno = new Usuario(values[0], values[1], values[2], 0, false);  // False indicates the user is a student
                    alunoList.add(aluno);
                }
            }

            equipe = new Equipe(txtEquipe.getText(), txtGithub.getText(), alunoList, cmbSemestre.getValue());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
