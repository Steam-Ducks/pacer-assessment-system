package steamducks.SistemaRecap.controllers.Relatorio;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import steamducks.SistemaRecap.dao.RelatoriosDAO;
import steamducks.SistemaRecap.models.Equipe;
import steamducks.SistemaRecap.models.Semestre;
import steamducks.SistemaRecap.models.Usuario;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    @FXML
    private TableColumn<Usuario, Integer> columnMedia; // Define que é uma coluna de inteiros

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
            limparEquipesESprints();
            if (newVal != null) {
                int idSemestre = buscarIdSemestre(newVal);
                carregarEquipes(idSemestre);
                carregarSprints(idSemestre);
                carregarAlunosPorSemestre(idSemestre);
            } else {
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

        // Listener para recalcular médias ao selecionar uma sprint
        cmbSprint.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                atualizarMediasAlunos();
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



    private void atualizarMediasAlunos() {
        tableAlunos.refresh(); // Atualiza os valores calculados da tabela
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

    private int buscarIdSprint(String nomeSprint) {
        try {
            List<String> sprints = relatoriosDAO.buscarSprintsPorSemestre(buscarIdSemestre(cmbSemestre.getValue()));
            int index = sprints.indexOf(nomeSprint);
            return index + 1; // Exemplo: índice mapeado para ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 se não encontrar
    }

    @FXML
    private void exportarCSV() {
        // Verificar se a sprint está selecionada
        if (cmbSprint.getValue() == null) {
            emitirAlerta("Sistema RECAP", "Selecione uma Sprint antes de exportar o CSV.");
            return;
        }

        // Obter os alunos da tabela
        ObservableList<Usuario> alunos = tableAlunos.getItems();
        int idSprint = buscarIdSprint(cmbSprint.getValue());

        // Buscar os nomes dos critérios dinamicamente do banco
        List<String> criterios = relatoriosDAO.buscarNomesCriteriosPorSprint(idSprint);

        // Definir o arquivo para exportação
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Escrever o cabeçalho do CSV (Aluno, Critérios dinâmicos)
                writer.write("Aluno");
                for (String criterio : criterios) {
                    writer.write("," + criterio);
                }
                writer.newLine();

                // Percorrer a lista de alunos e escrever os dados no CSV
                for (Usuario aluno : alunos) {
                    // Obter o nome e email do aluno
                    String nomeAluno = aluno.getNome();
                    String emailAluno = aluno.getEmail();

                    // Escrever o nome do aluno na linha
                    writer.write(nomeAluno);

                    // Adicionar as notas para cada critério
                    for (String criterio : criterios) {
                        int idCriterio = relatoriosDAO.buscarIdCriterioPorNome(criterio); // Método para pegar o ID do critério
                        int nota = relatoriosDAO.getMediaAlunoPorCriterio(idSprint, emailAluno, idCriterio);
                        writer.write("," + nota);
                    }

                    writer.newLine();
                }

                // Mensagem de sucesso
                System.out.println("CSV exportado com sucesso!");

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Erro ao exportar CSV: " + e.getMessage());
            }
        }
    }


    @FXML
    private void exportarAlunoCSV() {
        // Verificar se a sprint está selecionada
        if (cmbSprint.getValue() == null) {
            emitirAlerta("Sistema RECAP", "Selecione uma Sprint antes de exportar o CSV do aluno.");
            return;
        }

        // Verificar se um aluno está selecionado
        Usuario alunoSelecionado = tableAlunos.getSelectionModel().getSelectedItem();
        if (alunoSelecionado == null) {
            emitirAlerta("Sistema RECAP", "Selecione um aluno antes de exportar o CSV.");
            return;
        }

        int idSprint = buscarIdSprint(cmbSprint.getValue()); // Metodo para pegar o idSprint

        // Buscar os nomes dos critérios dinamicamente do banco
        List<String> criterios = relatoriosDAO.buscarNomesCriteriosPorSprint(idSprint);

        // Definir o arquivo para exportação
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Escrever o cabeçalho do CSV (Aluno, Critérios dinâmicos)
                writer.write("Aluno");
                for (String criterio : criterios) {
                    writer.write("," + criterio);
                }
                writer.newLine();

                // Obter o nome e email do aluno
                String nomeAluno = alunoSelecionado.getNome();
                String emailAluno = alunoSelecionado.getEmail();

                // Escrever o nome do aluno na linha
                writer.write(nomeAluno);

                // Adicionar as notas para cada critério
                for (String criterio : criterios) {
                    int idCriterio = relatoriosDAO.buscarIdCriterioPorNome(criterio); // Método para pegar o ID do critério
                    int nota = relatoriosDAO.getMediaAlunoPorCriterio(idSprint, emailAluno, idCriterio);
                    writer.write("," + nota);
                }

                writer.newLine();

                // Mensagem de sucesso
                System.out.println("CSV do aluno exportado com sucesso!");

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Erro ao exportar CSV do aluno: " + e.getMessage());
            }
        }
    }


    private void emitirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));
        alert.showAndWait();
    }

    private void limparEquipesESprints() {
        cmbEquipes.setValue(null);
        cmbEquipes.getItems().clear();
        cmbEquipes.setPromptText("Selecione uma equipe");

        cmbSprint.setValue(null);
        cmbSprint.getItems().clear();
        cmbSprint.setPromptText("Selecione uma sprint");
    }





    private void limparTabela() {
        tableAlunos.getItems().clear();
    }

}
