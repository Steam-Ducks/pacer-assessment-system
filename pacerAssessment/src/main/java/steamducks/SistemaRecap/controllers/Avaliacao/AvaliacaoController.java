package steamducks.SistemaRecap.controllers.Avaliacao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import steamducks.SistemaRecap.dao.AvaliacaoDAO;
import steamducks.SistemaRecap.dao.CriteriosDAO;
import steamducks.SistemaRecap.models.Avaliacao;
import steamducks.SistemaRecap.models.Criterio;
import steamducks.SistemaRecap.models.Sprint;
import steamducks.SistemaRecap.models.Usuario;
import javafx.geometry.Pos;


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
    private CriteriosDAO criteriosDAO;
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
        tcNota.setCellFactory(column -> new RadioButtonTableCell());
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

            // Fetch criterios with their notes for the current sprint and semester
            List<Criterio> criteriosComNotas = avaliacaoDAO.getNotasPorCriterio(
                    alunoAvaliador.getEmail(),
                    alunoAvaliado.getEmail(),
                    sprintAtiva.getIdSprint(),
                    sprintAtiva.getIdSemestre()
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

    private class RadioButtonTableCell extends TableCell<Criterio, Integer> {
        private final ToggleGroup toggleGroup = new ToggleGroup();
        private final RadioButton[] radioButtons = new RadioButton[4];
        private final HBox hBox = new HBox(40); // espacamento entre os radiobuttons

        public RadioButtonTableCell() {
            hBox.setPadding(new Insets(2, 5, 2, 15)); // (top, right, bottom, left)
            hBox.setAlignment(Pos.CENTER_LEFT);

            for (int i = 0; i < 4; i++) {
                radioButtons[i] = new RadioButton(String.valueOf(i));
                radioButtons[i].setToggleGroup(toggleGroup);
                radioButtons[i].setUserData(i);
                hBox.getChildren().add(radioButtons[i]);
            }

            toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    int selectedValue = (int) newValue.getUserData();
                    Criterio criterio = getTableView().getItems().get(getIndex());
                    int notaAntiga = criterio.getNota();
                    int pontosUtilizados = obterPontosUtilizados();
                    int pontosRestantes = totalPontosDisponiveis - pontosUtilizados + notaAntiga;

                    if (selectedValue <= pontosRestantes) {
                        criterio.setNota(selectedValue);
                        atualizarPontosTotais();
                    } else {
                        toggleGroup.selectToggle(oldValue);
                    }
                }
            });
        }

        @Override
        protected void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                Criterio criterio = getTableView().getItems().get(getIndex());
                radioButtons[criterio.getNota()].setSelected(true);
                setGraphic(hBox);
            }
        }
    }
}