package steamducks.pacerassessment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import steamducks.pacerassessment.models.Semestre;

import java.io.IOException;

public class SemestreController {

    @FXML
    private Button bntEditSem;

    @FXML
    private Button bntAdcSemestre;

    @FXML
    private Button bntRmvCrit;

    @FXML
    private TextField txtSemestre;

    @FXML
    private ListView<Semestre> listView; // <Semestre> especifica o tipo da ListView

    // ObservableList que contém os semestres
    private ObservableList<Semestre> semestreNome = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Configurar a ListView para exibir o nome dos semestres
        listView.setCellFactory(param -> new ListCell<Semestre>() {
            @Override
            protected void updateItem(Semestre item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome()); // Exibir o nome do semestre
                }
            }
        });

        // Definir a lista observável como o modelo de dados da ListView
        listView.setItems(semestreNome);

        // Definir os eventos para os botões
        bntAdcSemestre.setOnAction(event -> adicionarSemestre());
        bntEditSem.setOnAction(event -> abrirTelaEdicao());
        bntRmvCrit.setOnAction(event -> removerSemestre());
    }

    // Método para adicionar um novo semestre
    public void adicionarSemestre() {
        String nomeSemestre = txtSemestre.getText();

        if (nomeSemestre.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos antes de adicionar um semestre.", AlertType.ERROR);
        } else {
            Semestre novoSemestre = new Semestre(nomeSemestre);
            semestreNome.add(novoSemestre); // Adiciona o novo semestre à lista observável
            limparCampos();
        }
    }

    // Método para abrir a tela de edição
    public void abrirTelaEdicao() {
        Semestre semestreSelecionado = listView.getSelectionModel().getSelectedItem();

        if (semestreSelecionado != null) {
            try {
                // Carregar o FXML da tela de edição
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/edtSemestreView.fxml"));
                Parent root = loader.load();

                // Exibir a nova janela
                Stage stage = new Stage();
                stage.setTitle("Editar Semestre");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL); // Bloquear interação com a janela anterior
                stage.show();

                // Passar os dados para o controller de edição
                EdtSemestreController controller = loader.getController();
                controller.inicializarCampos(semestreSelecionado);

                // Atualizar a ListView após o fechamento da janela de edição
                stage.setOnHidden(event -> listView.refresh());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Seleção Inválida", "Selecione um semestre para editar.", AlertType.WARNING);
        }
    }

    // Método para remover o semestre selecionado
    public void removerSemestre() {
        Semestre semestreSelecionado = listView.getSelectionModel().getSelectedItem();

        if (semestreSelecionado != null) {
            semestreNome.remove(semestreSelecionado); // Remove o semestre da lista
        } else {
            mostrarAlerta("Seleção Inválida", "Selecione um semestre para remover.", AlertType.WARNING);
        }
    }

    // Limpar o campo de texto
    private void limparCampos() {
        txtSemestre.clear();
    }

    // Exibir uma caixa de alerta
    private void mostrarAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
