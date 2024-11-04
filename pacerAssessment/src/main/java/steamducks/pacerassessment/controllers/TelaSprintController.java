package steamducks.pacerassessment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;
import javafx.util.StringConverter;

import steamducks.pacerassessment.dao.SprintDAO;
import steamducks.pacerassessment.dao.SemestreDAO;
import steamducks.pacerassessment.dao.GrupoAlunoDAO;
import steamducks.pacerassessment.dao.PontuacaoDAO;
import steamducks.pacerassessment.models.Semestre;
import steamducks.pacerassessment.models.Sprint;
import steamducks.pacerassessment.models.Pontuacao;

public class TelaSprintController {

    @FXML
    private ComboBox<String> cmb_SelSprint;
    @FXML
    private ComboBox<Semestre> cmb_SelTurma;
    @FXML
    private VBox vbox_equipes;
    @FXML
    private Button btn_Cancelar;
    @FXML
    private Button btn_Salvar;
    @FXML
    private ScrollPane scrollBox;

    private GrupoAlunoDAO grupoAlunoDAO;
    private SprintDAO sprintDAO;
    private SemestreDAO semestreDAO;
    private PontuacaoDAO pontuacaoDAO;

    @FXML
    public void initialize() {
        grupoAlunoDAO = new GrupoAlunoDAO();
        sprintDAO = new SprintDAO();
        semestreDAO = new SemestreDAO();
        pontuacaoDAO = new PontuacaoDAO();

        // Preenche a ComboBox de semestres
        List<Semestre> semestreData = semestreDAO.getSemestres();
        ObservableList<Semestre> obsSemestre = FXCollections.observableArrayList(semestreData);
        cmb_SelTurma.setItems(obsSemestre);

        // Configura o StringConverter para exibir apenas o nome do semestre
        cmb_SelTurma.setConverter(new StringConverter<Semestre>() {
            @Override
            public String toString(Semestre semestre) {
                return semestre != null ? semestre.getNome() : "";
            }

            @Override
            public Semestre fromString(String string) {
                return semestreData.stream()
                        .filter(semestre -> semestre.getNome().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Listener para atualizar sprints e equipes ao selecionar uma turma
        cmb_SelTurma.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                atualizarSprints(newValue);
                atualizarEquipes(newValue);
            }
        });

        // Ações para os botões
        btn_Cancelar.setOnAction(event -> fecharJanela());
        btn_Salvar.setOnAction(event -> verificarCampos());
    }

    @FXML
    private void atualizarSprints(Semestre semestreSelecionado) {
        int idSemestre = semestreSelecionado.getId();
        List<Sprint> sprints = sprintDAO.buscarSprintPorID(idSemestre);
        ObservableList<String> nomeSprints = FXCollections.observableArrayList(
                sprints.stream().map(Sprint::getNome).collect(Collectors.toList())
        );
        cmb_SelSprint.setItems(nomeSprints);
    }

    private void atualizarEquipes(Semestre semestreSelecionado) {
        int idSemestre = semestreSelecionado.getId();
        List<String> equipes = grupoAlunoDAO.buscarEquipesPorIdSemestre(idSemestre);
        int numeroDeCriterios = semestreDAO.contarCriteriosPorIdSemestre(idSemestre); // Supondo que esse método retorna o número de critérios do semestre

        vbox_equipes.getChildren().clear();

        for (String equipe : equipes) {
            // Obtém o número de membros para cada equipe
            int numeroDeMembros = grupoAlunoDAO.getNumeroDeMembros(equipe, idSemestre);

            // Calcula o limite de pontos com a fórmula fornecida
            int limiteDePontos = numeroDeMembros * numeroDeCriterios * 3;

            HBox labelHBox = new HBox();
            Label label = new Label(equipe);
            label.setFont(new Font("Arial", 14));
            label.setPrefWidth(100);
            labelHBox.getChildren().add(label);

            HBox textFieldHBox = new HBox();
            TextField textField = new TextField();
            textField.setPromptText("0");
            textField.setPrefWidth(45);

            // Listener para limitar a pontuação no TextField
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    textField.setText(oldValue);
                } else if (!newValue.isEmpty()) {
                    int valor = Integer.parseInt(newValue);
                    if (valor < 1 || valor > limiteDePontos) {
                        textField.setText(oldValue);
                    }
                }
            });

            textFieldHBox.getChildren().add(textField);

            HBox hbox = new HBox(labelHBox, textFieldHBox);
            hbox.setSpacing(0);
            VBox.setMargin(hbox, new Insets(5, 70, 5, 60));

            HBox.setHgrow(textFieldHBox, Priority.ALWAYS);
            labelHBox.setAlignment(Pos.CENTER_LEFT);
            textFieldHBox.setAlignment(Pos.CENTER_RIGHT);

            vbox_equipes.getChildren().add(hbox);
        }
    }


    private void fecharJanela() {
        ((Stage) btn_Cancelar.getScene().getWindow()).close();
    }

    private void verificarCampos() {
        if (cmb_SelTurma.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhuma turma selecionada");
            alert.setContentText("Você deve selecionar uma turma antes de salvar a avaliação.");
            alert.showAndWait();
            return;
        }

        if (cmb_SelSprint.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhuma sprint selecionada");
            alert.setContentText("Você deve selecionar uma sprint antes de salvar a avaliação.");
            alert.showAndWait();
            return;
        }

        for (var node : vbox_equipes.getChildren()) {
            if (node instanceof HBox hbox) {
                for (var innerNode : hbox.getChildren()) {
                    if (innerNode instanceof HBox textFieldHBox) {
                        for (var textFieldNode : textFieldHBox.getChildren()) {
                            if (textFieldNode instanceof TextField textField) {
                                if (textField.getText().isEmpty()) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Erro");
                                    alert.setHeaderText("Campo de nota em branco");
                                    alert.setContentText("Você deve preencher todos os campos de nota antes de salvar a avaliação.");
                                    alert.showAndWait();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

        salvarAvaliacoes();
    }

    private void salvarAvaliacoes() {
        String nomeSprint = cmb_SelSprint.getValue();
        int idSemestre = cmb_SelTurma.getValue().getId();
        Sprint sprintSelecionada = sprintDAO.buscarSprintPorNomeEIdSemestre(nomeSprint, idSemestre);
        boolean avaliacaoRealizada = false; // Variável para rastrear sucesso

        if (sprintSelecionada == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Sprint não encontrada");
            alert.setContentText("A sprint selecionada não foi encontrada para a turma escolhida.");
            alert.showAndWait();
            return;
        }

        int idSprint = sprintSelecionada.getIdSprint();

        for (var node : vbox_equipes.getChildren()) {
            if (node instanceof HBox hbox) {
                Label label = (Label) ((HBox) hbox.getChildren().get(0)).getChildren().get(0);
                TextField textField = (TextField) ((HBox) hbox.getChildren().get(1)).getChildren().get(0);
                int idEquipe = grupoAlunoDAO.buscarEquipePorNomeEIdSemestre(label.getText(), idSemestre).getIdEquipe();
                int pontos = Integer.parseInt(textField.getText());

                // Verifica se já existe pontuação
                if (pontuacaoDAO.pontuacaoJaExiste(idEquipe, idSprint)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Aviso");
                    alert.setHeaderText("Pontuação já atribuída");
                    alert.setContentText("A equipe " + label.getText() + " já possui pontuação para esta sprint.");
                    alert.showAndWait();
                    continue; // Pule para a próxima equipe sem cadastrar a pontuação duplicada
                }

                // Cadastra a pontuação se não houver duplicata
                Pontuacao pontuacao = new Pontuacao();
                pontuacao.setPontos(pontos);
                pontuacao.setIdSprint(idSprint);
                pontuacao.setIdEquipe(idEquipe);

                pontuacaoDAO.cadastrarPontuacao(pontuacao);
                avaliacaoRealizada = true; // Marca que pelo menos uma avaliação foi realizada
            }
        }

        // Exibe o alerta de sucesso apenas se houve uma nova avaliação realizada
        if (avaliacaoRealizada) {
            String nomeSemestre = cmb_SelTurma.getValue().getNome();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Avaliação concluída");
            alert.setContentText("A avaliação foi salva com sucesso para a turma " + nomeSemestre + " na " + nomeSprint + "!");
            alert.showAndWait();
        }
    }
}
