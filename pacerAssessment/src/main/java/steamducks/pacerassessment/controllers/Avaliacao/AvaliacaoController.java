package steamducks.pacerassessment.controllers.Avaliacao;

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
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import steamducks.pacerassessment.dao.AvaliacaoDAO;
import steamducks.pacerassessment.models.Avaliacao;
import steamducks.pacerassessment.models.Criterio;
import steamducks.pacerassessment.models.Sprint;
import steamducks.pacerassessment.models.Usuario;

import java.time.LocalDate;
import java.util.List;

public class AvaliacaoController {

    @FXML
    private ComboBox<Usuario> cmbAluno;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableColumn<Criterio, String> tcCriterio;

    @FXML
    private TableColumn<Criterio, Integer> tcNota;

    @FXML
    private TableView<Criterio> tvAvaliacao;

    @FXML
    private Text lblSprint;

    @FXML
    private Label lblPontosTotais;

    private static final BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    private AvaliacaoDAO avaliacaoDAO;

    private Usuario alunoAvaliador;

    private int totalPontosDisponiveis;

    private Sprint sprintAtiva;

    public void initialize(Usuario alunoAvaliador) {
        this.avaliacaoDAO = new AvaliacaoDAO();
        this.alunoAvaliador = alunoAvaliador;
        carregarAlunosEquipe();
        configurarListeners();
        configurarColunas();
        carregarSprintAtiva();
        carregarCriteriosComNotas();
    }

    private void carregarAlunosEquipe() {
        List<Usuario> colegasEquipe = avaliacaoDAO.getAlunosEquipe(alunoAvaliador.getIdEquipe());
        ObservableList<Usuario> usuariosObservableList = FXCollections.observableArrayList(colegasEquipe);
        cmbAluno.setItems(usuariosObservableList);
    }

    private void carregarSprintAtiva() {
        sprintAtiva = avaliacaoDAO.getSprintAtivaPorDataEEquipe(LocalDate.now(), alunoAvaliador.getIdEquipe());
        if (sprintAtiva != null) {
            lblSprint.setText("Avaliando: " + sprintAtiva.getNome());
        } else {
            lblSprint.setText("Sem Sprint Ativa");
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhuma sprint ativa encontrada.");
        }
    }

    private void configurarListeners() {
        cmbAluno.valueProperty().addListener((observable, oldValue, newValue) -> carregarCriteriosComNotas());
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
                // Revert the change if the new value exceeds the available points
                event.getTableView().getItems().set(event.getTablePosition().getRow(), criterio);
            }
        });
    }

    @FXML
    private void carregarCriteriosComNotas() {
        Usuario alunoAvaliado = cmbAluno.getValue();

        if (alunoAvaliado != null && sprintAtiva != null) {
            int pontosJaAtribuidos = avaliacaoDAO.getPontosTotaisExcluindoAluno(
                    alunoAvaliador.getEmail(), alunoAvaliado.getEmail(), sprintAtiva.getIdSprint()
            );

            int totalPontosSprint = avaliacaoDAO.getTotalDePontosDaEquipeNaSprint(
                    sprintAtiva.getIdSprint(), alunoAvaliado.getIdEquipe()
            );

            totalPontosDisponiveis = totalPontosSprint - pontosJaAtribuidos;

            List<Criterio> criteriosComNotas = avaliacaoDAO.getNotasPorCriterio(
                    alunoAvaliador.getEmail(),
                    alunoAvaliado.getEmail(),
                    sprintAtiva.getIdSprint()
            );
            ObservableList<Criterio> criteriosObservableList = FXCollections.observableArrayList(criteriosComNotas);
            tvAvaliacao.setItems(criteriosObservableList);
            tvAvaliacao.setEditable(true);

            atualizarPontosTotais();
        }
    }

    private int obterPontosUtilizados() {
        return tvAvaliacao.getItems().stream().mapToInt(Criterio::getNota).sum();
    }

    private void atualizarPontosTotais() {
        int pontosUtilizados = obterPontosUtilizados();
        int pontosRestantes = totalPontosDisponiveis - pontosUtilizados;
        lblPontosTotais.setText("Pontos Disponíveis: " + pontosRestantes);
    }

    @FXML
    void cancelar(ActionEvent event) {
        carregarCriteriosComNotas();
    }

    @FXML
    void salvarAvaliacoes(ActionEvent event) {
        Usuario alunoAvaliado = cmbAluno.getValue();

        if (alunoAvaliado != null && sprintAtiva != null) {
            try {
                for (Criterio criterio : tvAvaliacao.getItems()) {
                    Avaliacao avaliacao = new Avaliacao();
                    avaliacao.setNota(criterio.getNota());
                    avaliacao.setEmailAvaliador(alunoAvaliador.getEmail());
                    avaliacao.setEmailAvaliado(alunoAvaliado.getEmail());
                    avaliacao.setIdSprint(sprintAtiva.getIdSprint());
                    avaliacao.setIdCriterio(criterio.getId());
                    avaliacaoDAO.cadastrarOuAtualizarAvaliacao(avaliacao);
                }
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Avaliações salvas com sucesso!");
                carregarCriteriosComNotas();
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao salvar avaliações: " + e.getMessage());
            }
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        if (contentPane != null) {
            contentPane.setEffect(blurEffect);
        }

        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png"))); // Caminho do ícone
        alerta.showAndWait();

        if (contentPane != null) {
            contentPane.setEffect(null);
        }
    }

}