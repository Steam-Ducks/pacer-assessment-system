package org.example.telaAvaliacaoAluno;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

// Classe controller, onde definimos as ações por trás da interface e seus elementos
public class telaAlunoController {

    // Dentro da classe, primeiro referenciamos os elementos FXML, que estão presentes na nossa tela
    @FXML
    public Button btn_Salvar; // Botão Salvar

    @FXML
    private Button btn_Cancelar; // Botão Cancelar

    @FXML
    private ComboBox<String> cmb_SelAluno; // ComboBox Selecionar Aluno

    @FXML
    private ScrollPane scrollBox; // ScrollBox da lista de critérios

    @FXML
    private VBox vbox_criterios; // VBox de Critérios, onde fica a lista de critérios e a box de nota

    @FXML
    private Label lbl_pontosRestantes; // Label que armazena os pontos disponíveis para distribuir

    private int totalPontos = 10; // Variável que armazena os pontos e desconta conforme distribuímos

    // Esse metodo (initialize) será chamado ao inicializar a tela
    @FXML
    public void initialize() {

        // Inicializa o total de pontos restantes
        lbl_pontosRestantes.setText("Pontos restantes: " + totalPontos);

        // Criamos uma lista com os nomes dos participantes da equipe selecionada previamente pelo professor
        ObservableList<String> sprints = FXCollections.observableArrayList(
                "Alexander", "Carlos", "Isabelly", "Lucas", "Luiz", "Matheus", "Rafaella", "Samuel", "Theo"
        );

        // Após criar a lista, definimos os elementos para dentro do ComboBox "selecionar aluno"
        this.cmb_SelAluno.setItems(sprints);

        // Lista de critérios e descrições associadas
        List<String> criterios = List.of("Autonomia", "Prazo", "Colaboração", "Produtividade");
        //Lista de descrição dos critérios
        List<String> descricoes = List.of(
                "Capacidade de trabalhar de forma independente.",
                "Entrega dentro do prazo estabelecido.",
                "Capacidade de colaborar com os colegas.",
                "Nível de produtividade no trabalho."
        );

        // Definimos uma variável de critério, do tipo critérios, que irá "puxar" os itens da lista criada anteriormente
        for (int i = 0; i < criterios.size(); i++) {
            String criterio = criterios.get(i);
            String descricao = descricoes.get(i);

            // Cria um novo Label para cada critério da lista
            Label label = new Label(criterio);

            // Faz o Label ocupar o espaço disponível na vbox, criando assim uma HBox (Horizontal box)
            // as HBox funcionam como linhas de tabelas
            HBox.setHgrow(label, Priority.ALWAYS);

            // Cria um botão para mostrar a descrição do critério
            Button btnDescricao = new Button("Descrição");

            // Define a ação do botão para abrir um popup com a descrição do critério
            btnDescricao.setOnAction(e -> mostrarPopupDescricao(criterio, descricao));

            // Cria um ComboBox para a pontuação de 0 a 3
            ComboBox<Integer> comboBox = new ComboBox<>();
            comboBox.getItems().addAll(0, 1, 2, 3);  // Adiciona os valores 0, 1, 2 e 3 ao ComboBox
            comboBox.setValue(0);  // Valor padrão inicial

            // Define o tamanho preferido, mínimo e máximo do ComboBox
            comboBox.setPrefWidth(100);  // Tamanho preferencial
            comboBox.setMinWidth(20);    // Tamanho mínimo
            comboBox.setMaxWidth(50);    // Tamanho máximo

            // Listener para atualizar os pontos restantes
            // Definimos uma ação para caso o usuário mude o valor da ComboBox, os pontos sejam adicionados ou retirados do label
            comboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
                // A variável diff irá armazenar a diferença de pontos adicionados ou retirados ao alterar o valor da ComboBox
                int diff = newValue - oldValue;  // Calcula a diferença

                // Verifica se os pontos restantes não ficam negativos
                if (totalPontos - diff < 0) {
                    // Caso negativo, exibe uma mensagem de erro e reverte o valor, pois não há pontos disponíveis para distribuir
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Pontos insuficientes");
                    alert.setContentText("Você não tem pontos suficientes para esta ação.");
                    alert.showAndWait();

                    // Reverte a mudança dentro do ComboBox de pontos
                    comboBox.setValue(oldValue);

                    // Após setar o valor anterior ao erro na ComboBox,
                    // retiramos a diferença para que não seja adicionado mais pontos ao label
                    totalPontos -= diff;  // Atualiza o total de pontos
                    lbl_pontosRestantes.setText("Pontos restantes: " + totalPontos);  // Atualiza o label

                } else {
                    totalPontos -= diff;  // Atualiza o total de pontos
                    lbl_pontosRestantes.setText("Pontos restantes: " + totalPontos);  // Atualiza o label
                }
            });

            // Cria uma HBox para manter o Label, o Botão e o ComboBox
            HBox hbox = new HBox();
            hbox.setSpacing(10);  // Espaçamento entre o Label, Botão e o ComboBox
            hbox.setAlignment(Pos.CENTER_LEFT);

            // Faz o Label expandir para empurrar o ComboBox para a direita
            label.setMaxWidth(Double.MAX_VALUE);
            label.setFont(new Font("Arial", 14));

            // Adiciona o Label, o Botão e o ComboBox ao HBox
            hbox.getChildren().addAll(label, btnDescricao, comboBox);

            // Adiciona uma margem de 10 pixels no topo e na parte inferior de cada HBox
            // os valores setados como zero são referentes a esquerda e direita
            VBox.setMargin(hbox, new Insets(10, 0, 10, 0));

            // Adiciona o HBox ao VBox
            // VBox está definido como "pai" e os HBox "filhos"
            // Definimos os objetos dessa forma para que a visualização fique semelhante a uma tabela
            vbox_criterios.getChildren().add(hbox);
        }

        // Configura a ação do botão Cancelar para fechar a janela
        btn_Cancelar.setOnAction(this::fecharJanela);

        // Configura a ação do botão Salvar para validar e salvar
        btn_Salvar.setOnAction(this::verificarCampoTexto);
    }

    // Metodo para mostrar um popup personalizado com a descrição do critério
    private void mostrarPopupDescricao(String criterio, String descricao) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Descrição do Critério");

        Label lblCriterio = new Label("Critério: " + criterio);
        lblCriterio.setFont(new Font("Arial", 16));

        Label lblDescricao = new Label(descricao);
        lblDescricao.setWrapText(true);
        lblDescricao.setFont(new Font("Arial", 14));

        VBox vbox = new VBox(lblCriterio, lblDescricao);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 300, 150);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    // Metodo que fecha a janela quando o botão Cancelar é clicado
    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
        stage.close();
    }

    // Metodo que verifica se o usuário selecionou um aluno e gastou todos os pontos antes de salvar
    private void verificarCampoTexto(ActionEvent event) {
        // Verifica se um aluno foi selecionado
        if (cmb_SelAluno.getValue() == null) {
            // Exibe uma mensagem de erro se nenhum aluno foi selecionado
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhum aluno selecionado");
            alert.setContentText("Você deve selecionar um aluno antes de salvar a avaliação.");
            alert.showAndWait();

        } else if (totalPontos > 0) {
            // Exibe uma mensagem de erro se ainda houver pontos restantes
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Pontos restantes");
            alert.setContentText("Você ainda tem " + totalPontos + " pontos para distribuir.");
            alert.showAndWait();

        } else {
            // Exibe uma mensagem de sucesso se todos os pontos foram distribuídos corretamente e um aluno foi selecionado
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Avaliação concluída");
            alert.setContentText("Todos os pontos foram distribuídos e a avaliação foi salva com sucesso para o aluno " + cmb_SelAluno.getValue() + "!");
            alert.showAndWait();
        }
    }
}
