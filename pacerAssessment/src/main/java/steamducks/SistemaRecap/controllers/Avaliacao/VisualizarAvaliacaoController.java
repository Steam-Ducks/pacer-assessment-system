package steamducks.SistemaRecap.controllers.Avaliacao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import steamducks.SistemaRecap.dao.AvaliacaoDAO;
import steamducks.SistemaRecap.dao.SprintDAO;
import steamducks.SistemaRecap.models.Criterio;
import steamducks.SistemaRecap.models.Usuario;
import steamducks.SistemaRecap.models.Sprint;

import java.util.List;

public class VisualizarAvaliacaoController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableColumn<Criterio, String> criterioaluno;

    @FXML
    private ComboBox<Usuario> escolheraluno; // Armazenando objetos Usuario

    @FXML
    private ComboBox<Sprint> escolhersprint; // Armazenando objetos Sprint

    @FXML
    private TableColumn<Criterio, Integer> notaaluno;

    @FXML
    private TableView<Criterio> tabelanotaaluno;

    private final AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    private final SprintDAO sprintDAO = new SprintDAO();

    private Usuario usuario;

    @FXML
    public void initialize(Usuario usuario) {
        this.usuario = usuario;

        // Configuração das colunas da tabela
        criterioaluno.setCellValueFactory(new PropertyValueFactory<>("nome"));
        notaaluno.setCellValueFactory(new PropertyValueFactory<>("nota"));

        // Carregar alunos e sprints diretamente como objetos nos ComboBoxes
        carregarAlunos(usuario.getIdEquipe());
        carregarSprints(usuario.getIdEquipe());

        // Adicionar listeners para capturar a seleção de aluno e sprint
        escolheraluno.setOnAction(event -> atualizarNotas());
        escolhersprint.setOnAction(event -> atualizarNotas());
    }

    private void carregarAlunos(int idEquipe) {
        List<Usuario> alunos = avaliacaoDAO.getAlunosEquipe(idEquipe);
        ObservableList<Usuario> alunosObservable = FXCollections.observableArrayList(alunos);
        escolheraluno.setItems(alunosObservable);
    }

    private void carregarSprints(int idEquipe) {
        List<Sprint> sprints = sprintDAO.buscarSprintsPorEquipeESemestre(idEquipe); // Método retorna uma lista de objetos Sprint filtrados por equipe e semestre
        ObservableList<Sprint> sprintsObservable = FXCollections.observableArrayList(sprints);
        escolhersprint.setItems(sprintsObservable);
    }

    private void atualizarNotas() {
        Usuario alunoSelecionado = escolheraluno.getValue();
        Sprint sprintSelecionada = escolhersprint.getValue();

        if (alunoSelecionado != null && sprintSelecionada != null) {
            String emailAluno = alunoSelecionado.getEmail();
            int idSprint = sprintSelecionada.getIdSprint();
            int idSemestre = sprintSelecionada.getIdSemestre();

            List<Criterio> criterios = avaliacaoDAO.getNotasPorCriterio(usuario.getEmail(), emailAluno, idSprint, idSemestre);
            for (Criterio criterio : criterios) {
                int mediaNota = avaliacaoDAO.getMediaAlunoPorCriterio(idSprint, emailAluno, criterio.getId());
                criterio.setNota(mediaNota);
            }
            ObservableList<Criterio> criteriosObservable = FXCollections.observableArrayList(criterios);
            tabelanotaaluno.setItems(criteriosObservable);
        }
    }
}