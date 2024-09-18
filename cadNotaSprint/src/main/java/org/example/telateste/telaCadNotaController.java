package org.example.telateste;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class telaCadNotaController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private ChoiceBox<String> chcEquipe;

    private final Map<String, ObservableList<String>> equipesPorSemestre = new HashMap<>();

    @FXML
    private ChoiceBox<String> chcSemestre;

    @FXML
    private ChoiceBox<String> chcSprint;

    @FXML
    private TextField txtPontos;


    @FXML
    public void initialize() {
        // Inicializa as opções dos ChoiceBoxes
        ObservableList<String> semestres = FXCollections.observableArrayList("BD.1", "BD.2", "BD.3", "BD.4", "BD.5", "BD.6");
        chcSemestre.setItems(semestres);

        ObservableList<String> sprints = FXCollections.observableArrayList("Sprint 1", "Sprint 2", "Sprint 3", "Sprint 4");
        chcSprint.setItems(sprints);

        // Popula o mapa com as opções de equipes para cada semestre
        equipesPorSemestre.put("BD.1", FXCollections.observableArrayList("Equipe A", "Equipe B", "Equipe C", "Equipe D", "Equipe E"));
        equipesPorSemestre.put("BD.2", FXCollections.observableArrayList("SteamDucks", "SQLutions", "DenariusData", "AlphaCode", "CyberNexus"));
        equipesPorSemestre.put("BD.3", FXCollections.observableArrayList("Equipe 1", "Equipe 2", "Equipe 3", "Equipe 4", "Equipe 5"));
        equipesPorSemestre.put("BD.5", FXCollections.observableArrayList("Equipe01", "Equipe02", "Equipe03", "Equipe04", "Equipe05"));
        equipesPorSemestre.put("BD.6", FXCollections.observableArrayList("Equipe I", "Equipe II", "Equipe III", "Equipe IV", "Equipe V"));

        // Adiciona um listener para mudanças na seleção de Semestre
        chcSemestre.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                atualizarEquipes(newValue); // Atualiza a ChoiceBox de equipes com base na seleção
            }
        });

        // Configura a ação do botão Cancelar
        btnCancelar.setOnAction(this::fecharJanela);
        // Configura a ação do botão Confirmar
        btnConfirmar.setOnAction(this::verificarCampoTexto);

        // Configura um TextFormatter para limitar a entrada entre 1 e 100
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            // Verifica se o campo está vazio ou se contém apenas números válidos
            if (newText.matches("\\d*")) {
                if (!newText.isEmpty()) {
                    int newValue = Integer.parseInt(newText);
                    if (newValue >= 1 && newValue <= 100) {
                        return change; // Permite a mudança
                    }
                }
            }
            return null; // Rejeita a mudança se não for válida
        };

        // Aplica o filtro ao campo de texto
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        txtPontos.setTextFormatter(textFormatter);

    }

    private void atualizarEquipes(String semestreSelecionado) {
        ObservableList<String> equipes = equipesPorSemestre.getOrDefault(semestreSelecionado, FXCollections.observableArrayList("Sem equipes disponíveis"));
        chcEquipe.setItems(equipes); // Atualiza os itens da ChoiceBox de Equipes
        chcEquipe.getSelectionModel().clearSelection(); // Limpa a seleção atual
    }

    // Metodo que fecha a janela
    private void fecharJanela(ActionEvent event) {
        // Obtém o Stage (janela) atual
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close(); // Fecha a janela
    }

    // Metodo que exibe um popup ao clicar no botão Confirmar

    // Metodo que verifica se o campo de texto está vazio
    private void verificarCampoTexto(ActionEvent event) {
        if (txtPontos.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Campo obrigatório vazio");
            alert.setContentText("Por favor, insira um valor no campo de pontos.");
            alert.showAndWait();
        }
        // Verifica se todos os ChoiceBoxes estão selecionados
        else if (chcSemestre.getValue() == null) {
            mostrarErro("Semestre não selecionado", "Por favor, selecione um semestre.");
        } else if (chcEquipe.getValue() == null) {
            mostrarErro("Equipe não selecionada", "Por favor, selecione uma equipe.");
        } else if (chcSprint.getValue() == null) {
            mostrarErro("Sprint não selecionada", "Por favor, selecione uma sprint.");
        }
        else {
            int pontos = Integer.parseInt(txtPontos.getText());
                // Valor dentro do intervalo, você pode continuar
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Nota atribuída:");
                alert.setHeaderText("Nota atribuída com sucesso!");
                alert.setContentText(null);
                alert.showAndWait();
            }

        }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    }



