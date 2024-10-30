package steamducks.pacerassessment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import steamducks.pacerassessment.dao.AvaliacaoDAO;
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

    public void inicializar(Usuario alunoAvaliador) {
        this.avaliacaoDAO = new AvaliacaoDAO();
        this.alunoAvaliador = alunoAvaliador;
        carregarAlunosEquipe();
        carregarSprints();
        configurarOuvintesComboBox();
        configurarColunas();
        carregarCriteriosComNotas();
    }

    private void carregarAlunosEquipe() {
        List<Usuario> colegasEquipe = avaliacaoDAO.obterColegasEquipe(alunoAvaliador.getIdEquipe());
        ObservableList<Usuario> usuariosObservableList = FXCollections.observableArrayList(colegasEquipe);
        cmbAluno.setItems(usuariosObservableList);
    }

    private void carregarSprints() {
        List<Sprint> sprints = avaliacaoDAO.obterSprintsPorEquipe(alunoAvaliador.getIdEquipe());
        ObservableList<Sprint> sprintsObservableList = FXCollections.observableArrayList(sprints);
        cmbSprint.setItems(sprintsObservableList);
    }

    private void configurarOuvintesComboBox() {
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
            criterio.setNota(event.getNewValue());
        });
    }

    @FXML
    private void carregarCriteriosComNotas() {
        Usuario alunoAvaliado = cmbAluno.getValue();
        Sprint sprintSelecionada = cmbSprint.getValue();

        if (alunoAvaliado != null && sprintSelecionada != null) {
            List<Criterio> criteriosComNotas = avaliacaoDAO.obterNotasPorCriterio(
                    alunoAvaliador.getEmail(),
                    alunoAvaliado.getEmail(),
                    sprintSelecionada.getIdSprint()
            );
            ObservableList<Criterio> criteriosObservableList = FXCollections.observableArrayList(criteriosComNotas);
            tvAvaliacao.setItems(criteriosObservableList);
            tvAvaliacao.setEditable(true);

            // Obter e exibir o total de pontos da Sprint para o grupo específico
            int totalPontos = avaliacaoDAO.obterTotalPontosPorSprintEEquipe(
                    sprintSelecionada.getIdSprint(), alunoAvaliado.getIdEquipe()
            );
            lblPontosTotais.setText("Total de Pontos: " + totalPontos);
        }
    }
}
