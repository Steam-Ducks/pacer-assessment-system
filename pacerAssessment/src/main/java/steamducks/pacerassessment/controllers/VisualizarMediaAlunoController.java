package steamducks.pacerassessment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import steamducks.pacerassessment.dao.AvaliacaoDAO;
import steamducks.pacerassessment.dao.SprintDAO;
import steamducks.pacerassessment.models.Criterio;
import steamducks.pacerassessment.models.Usuario;
import steamducks.pacerassessment.models.Sprint;

import java.util.List;

public class VisualizarMediaAlunoController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableColumn<Criterio, String> criterioaluno;

    @FXML
    private ComboBox<Usuario> escolheraluno; // Armazenando objetos Usuario

    @FXML
    private ComboBox<Sprint> escolhersprint; // Armazenando objetos Sprint

    @FXML
    private TableColumn<Criterio, Double> notaaluno;

    @FXML
    private TableView<Criterio> tabelanotaaluno;

    private final AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    private final SprintDAO sprintDAO = new SprintDAO();

    @FXML
    public void initialize() {
        // Configuração das colunas da tabela
        criterioaluno.setCellValueFactory(new PropertyValueFactory<>("nome"));
        notaaluno.setCellValueFactory(new PropertyValueFactory<>("nota"));

        // Carregar alunos e sprints diretamente como objetos nos ComboBoxes
        carregarAlunos(1);  // ID da equipe, adapte conforme necessário
        carregarSprints();

        // Adicionar listeners para capturar a seleção de aluno e sprint
        escolheraluno.setOnAction(event -> atualizarNotas());
        escolhersprint.setOnAction(event -> atualizarNotas());
    }

    private void carregarAlunos(int idEquipe) {
        List<Usuario> alunos = avaliacaoDAO.getAlunosEquipe(idEquipe);
        ObservableList<Usuario> alunosObservable = FXCollections.observableArrayList(alunos);
        escolheraluno.setItems(alunosObservable);
    }

    private void carregarSprints() {
        List<Sprint> sprints = sprintDAO.buscarSprint(); // Método retorna uma lista de objetos Sprint
        ObservableList<Sprint> sprintsObservable = FXCollections.observableArrayList(sprints);
        escolhersprint.setItems(sprintsObservable);
    }

    private void atualizarNotas() {
        Usuario alunoSelecionado = escolheraluno.getValue();
        Sprint sprintSelecionada = escolhersprint.getValue();

        if (alunoSelecionado != null && sprintSelecionada != null) {
            String emailAluno = alunoSelecionado.getEmail(); // Obtém o email direto do objeto Usuario
            int idSprint = sprintSelecionada.getIdSprint(); // Obtém o ID direto do objeto Sprint

            List<Criterio> notas = avaliacaoDAO.getNotasPorCriterio("avaliador@exemplo.com", emailAluno, idSprint); // Substitua com o email do avaliador real
            ObservableList<Criterio> criterios = FXCollections.observableArrayList(notas);
            tabelanotaaluno.setItems(criterios);
        }
    }
}
