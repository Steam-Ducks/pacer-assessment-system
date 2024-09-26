package steamducks.pacerassessment;

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
public class TelaSprintController {

    @FXML
    private ComboBox<String> cmb_SelSprint;// Combobox Selecionar Sprint

    @FXML
    private ComboBox<String> cmb_SelTurma; // ComboBox Selecionar Turma

    @FXML
    private VBox vbox_equipes; // VBox para a lista de equipes

    @FXML
    private Button btn_Cancelar; // Botão Cancelar

    @FXML
    private Button btn_Salvar;// Botão Salvar

    @FXML
    private ScrollPane scrollBox;//ScrollBox onde estão localizadas as equipes

    // Inicialização da tela
    @FXML
    public void initialize() {
        // Define as opções da ComboBox (as turmas)
        ObservableList<String> turmas = FXCollections.observableArrayList(
                "BD-1", "BD-2", "BD-3", "BD-4", "BD-5", "BD-6"
        );

        // introduz as opções para a combobox
        cmb_SelTurma.setItems(turmas);

        // Define as opções da Combobox de Sprints
        ObservableList<String> sprints = FXCollections.observableArrayList(
                "Sprint 1", "Sprint 2", "Sprint 3", "Sprint 4"
        );

        //introduz as opções para a combobox
        cmb_SelSprint.setItems(sprints);

        // Adiciona um listener para atualizar as equipes ao selecionar uma turma
        cmb_SelTurma.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                atualizarEquipes(newValue); // Chama o metodo para atualizar as equipes
            }
        });

        // Ações para os botões
        // Botão cancelar fecha a janela
        btn_Cancelar.setOnAction(event -> fecharJanela());
        // Botão Salvar confirma as alterações feitas
        btn_Salvar.setOnAction(event -> verificarCampos());
    }

    // Metodo que atualiza as equipes conforme a turma selecionada
    private void atualizarEquipes(String turmaSelecionada) {

        // Lista que contém as equipes de cada turma
        List<String> equipes = switch (turmaSelecionada) {
            // De acordo com a equipe selecionada podemos ter diferentes equipes que estão definidas nas listas

            case "BD-1" -> List.of("Equipe A", "Equipe B", "Equipe C", "Equipe D", "Equipe E");
            case "BD-2" -> List.of("SteamDucks", "SQLutions", "DenariusData", "AlphaCode", "CyberNexus");
            case "BD-3" -> List.of("Equipe 1", "Equipe 2", "Equipe 3", "Equipe 4", "Equipe 5");
            case "BD-4" -> List.of("Equipe01", "Equipe02", "Equipe03", "Equipe04", "Equipe05");
            case "BD-5" -> List.of("Equipe_1", "Equipe_2", "Equipe_3", "Equipe_4", "Equipe_5");
            case "BD-6" -> List.of("Equipe I", "Equipe II", "Equipe III", "Equipe IV", "Equipe V");
            default -> List.of();
        };

        // Limpa a VBox para evitar duplicatas
        vbox_equipes.getChildren().clear();

        // o for faz com que sejam criados "linhas" para todos os itens das listas
        for (String equipe : equipes) {

            // Cria HBox para o Label
            HBox labelHBox = new HBox();
            Label label = new Label(equipe);
            label.setFont(new Font("Arial", 14));
            label.setPrefWidth(100);
            labelHBox.getChildren().add(label);

            // Cria HBox para o TextField
            HBox textFieldHBox = new HBox();
            TextField textField = new TextField();
            textField.setPromptText("0");
            textField.setPrefWidth(45); // Largura preferida para o TextField
            textFieldHBox.getChildren().add(textField);

            // Listener para garantir que a entrada esteja entre 1 e 100
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*") || (newValue.length() > 0 && (Integer.parseInt(newValue) < 1 || Integer.parseInt(newValue) > 100))) {
                    textField.setText(oldValue); // Reverte para o valor anterior se a entrada não for válida
                }
            });

            // Cria uma HBox principal para organizar os HBoxes dos elementos
            HBox hbox = new HBox(labelHBox, textFieldHBox);
            hbox.setSpacing(0); // Espaçamento entre as HBoxes
            //como os elementos estao em hboxes "filhas", podemos definir a margem deles
            VBox.setMargin(hbox, new Insets(5, 70, 5, 60));

            // Faz o hbox ocupar o espaço restante
            HBox.setHgrow(textFieldHBox, Priority.ALWAYS);

            //definimos as posições dos comboboxes
            labelHBox.setAlignment(Pos.CENTER_LEFT);
            textFieldHBox.setAlignment(Pos.CENTER_RIGHT);

            // Adiciona o HBox principal à VBox
            vbox_equipes.getChildren().add(hbox);
        }
    }

    // Metodo que fecha a janela quando o botão Cancelar é clicado
    private void fecharJanela() {
        ((Stage) btn_Cancelar.getScene().getWindow()).close();
    }

    // Metodo para verificar se o usuário preencheu todos os campos antes de salvar
    private void verificarCampos() {

        //caso o usuario não tenha selecionado uma turma aparece um popup de erro
        if (cmb_SelTurma.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhuma turma selecionada");
            alert.setContentText("Você deve selecionar uma turma antes de salvar a avaliação.");
            alert.showAndWait();
            return; // Retorna após mostrar o alerta
        }

        //caso o usuario não selecione uma sprint aparece um popup de erro
        if (cmb_SelSprint.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhuma sprint selecionada");
            alert.setContentText("Você deve selecionar uma sprint antes de salvar a avaliação.");
            alert.showAndWait();
            return; // Retorna após mostrar o alerta
        }

        //veridica se todos os campos de notas foram preenchidos para todas as equipes
        for (var node : vbox_equipes.getChildren()) {
            if (node instanceof HBox hbox) {
                //loop que faz com que todos os campos sejam verificados
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
                                    return; // Retorna após mostrar o alerta
                                }
                            }
                        }
                    }
                }
            }
        }

        // Se todas as verificações passarem, mostra a mensagem de sucesso
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Avaliação concluída");
        alert.setContentText("A avaliação foi salva com sucesso para a turma " + cmb_SelTurma.getValue() + "!");
        alert.showAndWait();
    }
}

