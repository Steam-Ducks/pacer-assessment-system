package steamducks.SistemaRecap.controllers.Relatorio;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import steamducks.SistemaRecap.dao.RelatoriosDAO;
import steamducks.SistemaRecap.models.Equipe;
import steamducks.SistemaRecap.models.Semestre;
import steamducks.SistemaRecap.models.Usuario;

import java.util.List;

public class RelatoriosController {

    @FXML
    private ComboBox<String> cmbSprint;

    @FXML
    private ComboBox<String> cmbSemestre;

    @FXML
    private ComboBox<String> cmbEquipes;

    @FXML
    private TableView<Usuario> tableAlunos;

    @FXML
    private TableColumn<Usuario, String> columnAlunos;

    private final RelatoriosDAO relatoriosDAO;

    public RelatoriosController() {
        this.relatoriosDAO = new RelatoriosDAO();
    }

    @FXML
    public void initialize() {
        configurarTabela();
        carregarSemestres();

        // Listener para carregar equipes, sprints e alunos ao selecionar um semestre
        cmbSemestre.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                int idSemestre = buscarIdSemestre(newVal);
                carregarEquipes(idSemestre);
                carregarSprints(idSemestre);
                carregarAlunosPorSemestre(idSemestre);
            } else {
                limparEquipesESprints();
                limparTabela();
            }
        });

        // Listener para carregar alunos ao selecionar uma equipe
        cmbEquipes.valueProperty().addListener((obs, oldVal, newVal) -> {
            String semestreSelecionado = cmbSemestre.getValue();
            if (newVal != null && semestreSelecionado != null) {
                int idSemestre = buscarIdSemestre(semestreSelecionado);
                int idEquipe = buscarIdEquipe(newVal);
                carregarAlunosPorSemestreEEquipe(idSemestre, idEquipe);
            } else if (semestreSelecionado != null) {
                int idSemestre = buscarIdSemestre(semestreSelecionado);
                carregarAlunosPorSemestre(idSemestre);
            }
        });

        // Listener para resetar as equipes ao mudar a sprint
        cmbSprint.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                limparEquipesESprints();
            }
        });
    }

    private void configurarTabela() {
        // Configura a tabela para exibir o nome do aluno
        columnAlunos.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNome())
        );
    }

    private void carregarSemestres() {
        try {
            List<Semestre> semestres = relatoriosDAO.buscarTodosSemestres();
            ObservableList<String> nomesSemestres = FXCollections.observableArrayList();
            for (Semestre semestre : semestres) {
                nomesSemestres.add(semestre.getNome());
            }
            cmbSemestre.setItems(nomesSemestres);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar semestres: " + e.getMessage());
        }
    }

    private void carregarEquipes(int idSemestre) {
        try {
            List<Equipe> equipes = relatoriosDAO.buscarEquipesPorSemestre(idSemestre);
            ObservableList<String> nomesEquipes = FXCollections.observableArrayList();
            for (Equipe equipe : equipes) {
                nomesEquipes.add(equipe.getNome());
            }
            cmbEquipes.setItems(nomesEquipes);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar equipes: " + e.getMessage());
        }
    }

    private void carregarSprints(int idSemestre) {
        try {
            List<String> sprints = relatoriosDAO.buscarSprintsPorSemestre(idSemestre);
            ObservableList<String> nomesSprints = FXCollections.observableArrayList(sprints);
            cmbSprint.setItems(nomesSprints);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar sprints: " + e.getMessage());
        }
    }

    private void carregarAlunosPorSemestre(int idSemestre) {
        try {
            List<Usuario> alunos = relatoriosDAO.buscarAlunosPorSemestre(idSemestre);
            ObservableList<Usuario> alunosObservable = FXCollections.observableArrayList(alunos);
            tableAlunos.setItems(alunosObservable);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar alunos: " + e.getMessage());
        }
    }

    private void carregarAlunosPorSemestreEEquipe(int idSemestre, int idEquipe) {
        try {
            List<Usuario> alunos = relatoriosDAO.buscarAlunosPorSemestreEEquipe(idSemestre, idEquipe);
            ObservableList<Usuario> alunosObservable = FXCollections.observableArrayList(alunos);
            tableAlunos.setItems(alunosObservable);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar alunos por semestre e equipe: " + e.getMessage());
        }
    }

    private void limparTabela() {
        tableAlunos.getItems().clear();
    }

    private void limparEquipesESprints() {
        cmbEquipes.getItems().clear();
        cmbSprint.getItems().clear();
    }

    private int buscarIdSemestre(String nomeSemestre) {
        try {
            List<Semestre> semestres = relatoriosDAO.buscarTodosSemestres();
            for (Semestre semestre : semestres) {
                if (semestre.getNome().equals(nomeSemestre)) {
                    return semestre.getId();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 se não encontrar
    }

    private int buscarIdEquipe(String nomeEquipe) {
        try {
            List<Equipe> equipes = relatoriosDAO.buscarEquipesPorSemestre(buscarIdSemestre(cmbSemestre.getValue()));
            for (Equipe equipe : equipes) {
                if (equipe.getNome().equals(nomeEquipe)) {
                    return equipe.getIdEquipe();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 se não encontrar
    }
}
