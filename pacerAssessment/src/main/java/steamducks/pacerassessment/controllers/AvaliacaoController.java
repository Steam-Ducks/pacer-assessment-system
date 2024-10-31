package steamducks.pacerassessment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import steamducks.pacerassessment.dao.AvaliacaoDAO;
import steamducks.pacerassessment.models.Avaliacao;
import steamducks.pacerassessment.models.Criterio;
import steamducks.pacerassessment.models.Sprint;
import steamducks.pacerassessment.models.Usuario;

import java.util.List;

public class AvaliacaoController {

    @FXML
    private ComboBox<Usuario> cmbAluno;
    @FXML
    private ComboBox<Sprint> cmbSprint;

    @FXML
    private TableColumn<Criterio, String> tcCriterio;
    @FXML
    private TableColumn<Criterio, Integer> tcNota;
    @FXML
    private TableView<Criterio> tvAvaliacao;

    @FXML
    private Label lblPontosTotais;

    private AvaliacaoDAO avaliacaoDAO;
    private Usuario alunoAvaliador;
    private int totalPontosDisponiveis;

    public void inicializar(Usuario alunoAvaliador) {
        this.avaliacaoDAO = new AvaliacaoDAO();
        this.alunoAvaliador = alunoAvaliador;
        carregarAlunosEquipe();
        carregarSprints();
        configurarListeners();
        configurarColunas();
        carregarCriteriosComNotas();
    }

    private void carregarAlunosEquipe() {
        List<Usuario> colegasEquipe = avaliacaoDAO.getAlunosEquipe(alunoAvaliador.getIdEquipe());
        ObservableList<Usuario> usuariosObservableList = FXCollections.observableArrayList(colegasEquipe);
        cmbAluno.setItems(usuariosObservableList);
    }

    private void carregarSprints() {
        List<Sprint> sprints = avaliacaoDAO.getSpritsDaEquipe(alunoAvaliador.getIdEquipe());
        ObservableList<Sprint> sprintsObservableList = FXCollections.observableArrayList(sprints);
        cmbSprint.setItems(sprintsObservableList);
    }

    private void configurarListeners() {
        cmbAluno.valueProperty().addListener((observable, oldValue, newValue) -> carregarCriteriosComNotas());
        cmbSprint.valueProperty().addListener((observable, oldValue, newValue) -> carregarCriteriosComNotas());
    }

    private void configurarColunas() {
        tcCriterio.setCellValueFactory(new PropertyValueFactory<>("nome"));

        ObservableList<Integer> notas = FXCollections.observableArrayList(0, 1, 2, 3);
        tcNota.setCellValueFactory(new PropertyValueFactory<>("nota"));
        tcNota.setCellFactory(ComboBoxTableCell.forTableColumn(notas));

        tcNota.setOnEditCommit(event -> {
            Criterio criterio = event.getRowValue();
            int novaNota = event.getNewValue();
            int notaAntiga = criterio.getNota();

            int pontosUtilizados = obterPontosUtilizados();
            int pontosRestantes = totalPontosDisponiveis - pontosUtilizados + notaAntiga;

            if (novaNota <= pontosRestantes) {
                criterio.setNota(novaNota);
                atualizarPontosTotais();
            } else {
                //reverter mudanca
                event.getTableView().getItems().set(event.getTablePosition().getRow(), criterio);
            }
        });
    }

    @FXML
    private void carregarCriteriosComNotas() {
        Usuario alunoAvaliado = cmbAluno.getValue();
        Sprint sprintSelecionada = cmbSprint.getValue();

        if (alunoAvaliado != null && sprintSelecionada != null) {
            // puxar todos os pontos que o aluno logado ja deu aos outros alunos (excluindo o aluno que ele esta avaliando)
            int pontosJaAtribuidos = avaliacaoDAO.getPontosTotaisExcluindoAluno(
                    alunoAvaliador.getEmail(), alunoAvaliado.getEmail(), sprintSelecionada.getIdSprint()
            );

            // Obter o total de pontos da Sprint para o grupo
            int totalPontosSprint = avaliacaoDAO.getTotalDePontosDaEquipeNaSprint(
                    sprintSelecionada.getIdSprint(), alunoAvaliado.getIdEquipe()
            );

            // Calcular os pontos disponíveis subtraindo os pontos já atribuídos
            totalPontosDisponiveis = totalPontosSprint - pontosJaAtribuidos;

            // Carregar os critérios e notas do aluno avaliado
            List<Criterio> criteriosComNotas = avaliacaoDAO.getNotasPorCriterio(
                    alunoAvaliador.getEmail(),
                    alunoAvaliado.getEmail(),
                    sprintSelecionada.getIdSprint()
            );
            ObservableList<Criterio> criteriosObservableList = FXCollections.observableArrayList(criteriosComNotas);
            tvAvaliacao.setItems(criteriosObservableList);
            tvAvaliacao.setEditable(true);

            atualizarPontosTotais();
        }
    }

    private int obterPontosUtilizados() {
        //pega a soma de todas as notas carregadas na TV
        return tvAvaliacao.getItems().stream().mapToInt(Criterio::getNota).sum();
    }

    private void atualizarPontosTotais() {
        int pontosUtilizados = obterPontosUtilizados();
        int pontosRestantes = totalPontosDisponiveis - pontosUtilizados;
        lblPontosTotais.setText("Pontos Disponíveis: " + pontosRestantes);
    }

    @FXML
    void cancelar(ActionEvent event) {
        carregarCriteriosComNotas(); // faz um refresh
    }

    @FXML
    void salvarAvaliacoes(ActionEvent event) {
        Usuario alunoAvaliado = cmbAluno.getValue();
        Sprint sprintSelecionada = cmbSprint.getValue();

        if (alunoAvaliado != null && sprintSelecionada != null) {
            try {
                for (Criterio criterio : tvAvaliacao.getItems()) {
                    Avaliacao avaliacao = new Avaliacao();
                    avaliacao.setNota(criterio.getNota());
                    avaliacao.setEmailAvaliador(alunoAvaliador.getEmail());
                    avaliacao.setEmailAvaliado(alunoAvaliado.getEmail());
                    avaliacao.setIdSprint(sprintSelecionada.getIdSprint());
                    avaliacao.setIdCriterio(criterio.getId());
                    avaliacaoDAO.cadastrarOuAtualizarAvaliacao(avaliacao);
                }
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Avaliações salvas com sucesso!");
                carregarCriteriosComNotas(); // faz um refresh
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao salvar avaliações: " + e.getMessage());
            }
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}