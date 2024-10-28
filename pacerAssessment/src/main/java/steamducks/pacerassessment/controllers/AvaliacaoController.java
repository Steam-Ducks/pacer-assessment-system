package steamducks.pacerassessment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.beans.property.SimpleStringProperty;
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
    private TableColumn<Criterio, String> tcNota;
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
        configurarColunas(); // Mover configuração para cá
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

        // Se 'nota' for Integer, convertê-lo para String na exibição
        tcNota.setCellValueFactory(cellData -> {
            Integer nota = cellData.getValue().getNota();
            return new SimpleStringProperty(nota != null ? nota.toString() : ""); // Usar String
        });
        tcNota.setCellFactory(TextFieldTableCell.forTableColumn());

        // Permitir edição de notas
        tcNota.setOnEditCommit(event -> {
            Criterio criterio = event.getRowValue();
            try {
                criterio.setNota(Integer.parseInt(event.getNewValue())); // Certifique-se de que 'setNota' aceita Integer
            } catch (NumberFormatException e) {
                criterio.setNota(0); // Define como 0 se a nota não for numérica
            }
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
            tvAvaliacao.setEditable(true); // Definindo a tabela como editável
        }
    }
}
