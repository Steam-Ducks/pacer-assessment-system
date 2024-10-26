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
        carregarCriterios();
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

    private void carregarCriterios() {
        List<Criterio> criterios = avaliacaoDAO.obterCriteriosPorEquipe(alunoAvaliador.getIdEquipe());
        ObservableList<Criterio> criteriosObservableList = FXCollections.observableArrayList(criterios);
        tvAvaliacao.setItems(criteriosObservableList);

        tcCriterio.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Configura a coluna de nota como editável
        tcNota.setCellValueFactory(new PropertyValueFactory<>("nota"));
        tcNota.setCellFactory(TextFieldTableCell.forTableColumn());
        tcNota.setOnEditCommit(event -> {
            Criterio criterio = event.getRowValue();
            criterio.setNota(Integer.valueOf(event.getNewValue())); // Atualiza a nota no objeto Criterio
        });

        tvAvaliacao.setEditable(true);
    }
}
