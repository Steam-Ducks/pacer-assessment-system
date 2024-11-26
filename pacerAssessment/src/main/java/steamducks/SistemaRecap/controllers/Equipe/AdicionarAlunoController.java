package steamducks.SistemaRecap.controllers.Equipe;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import steamducks.SistemaRecap.dao.UsuarioDAO;
import steamducks.SistemaRecap.dao.EquipeDAO;
import steamducks.SistemaRecap.models.Usuario;

public class AdicionarAlunoController implements Initializable {

    private int idEquipe;

    private AtualizacaoEquipeCallback callback;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnRemover;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ListView<Usuario> listViewDisponiveis;

    @FXML
    private ListView<Usuario> listViewSelecionados;

    private final EquipeDAO equipeDAO = new EquipeDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private ObservableList<Usuario> alunosDisponiveis;
    private ObservableList<Usuario> alunosSelecionados;

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
        System.out.println("ID da Equipe recebido: " + idEquipe);
    }

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Método initialize chamado...");


        // Busca alunos disponíveis no banco
        List<Usuario> semEquipe = usuarioDAO.buscarUsuariosSemEquipe();
        System.out.println("Alunos sem equipe: " + semEquipe.size());  // Verifica a quantidade de usuários encontrados
        System.out.println(idEquipe);
        alunosDisponiveis = FXCollections.observableArrayList(semEquipe);
        alunosSelecionados = FXCollections.observableArrayList();

        // Configura as ListViews
        alunosDisponiveis.sort((a1, a2) -> a1.getNome().compareToIgnoreCase(a2.getNome()));
        listViewDisponiveis.setItems(alunosDisponiveis);

        alunosSelecionados.sort((a1, a2) -> a1.getNome().compareToIgnoreCase(a2.getNome()));
        listViewSelecionados.setItems(alunosSelecionados);
    }

    public void setCallback(AtualizacaoEquipeCallback callback) {
        this.callback = callback;
    }


    @FXML
    void adicionarAluno(ActionEvent event) {
        Usuario alunoSelecionado = listViewDisponiveis.getSelectionModel().getSelectedItem();
        if (alunoSelecionado != null && !listViewSelecionados.getItems().contains(alunoSelecionado)) {
            alunosSelecionados.add(alunoSelecionado);
            alunosDisponiveis.remove(alunoSelecionado);

            ordenarListViews();
        } else {
            showAlert("Erro", "Nenhum aluno selecionado para adicionar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void removerAluno(ActionEvent event) {
        Usuario alunoSelecionado = listViewSelecionados.getSelectionModel().getSelectedItem();
        if (alunoSelecionado != null) {
            alunosSelecionados.remove(alunoSelecionado);
            alunosDisponiveis.add(alunoSelecionado);

            ordenarListViews();
        } else {
            showAlert("Erro", "Nenhum aluno selecionado para remover.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void salvarEquipe(ActionEvent event) {
        if (alunosSelecionados.isEmpty()) {
            showAlert("Erro", "Adicione pelo menos um aluno à equipe.", Alert.AlertType.ERROR);
            return;
        }

        if (idEquipe > 0) {
            equipeDAO.adicionarUsuarioAEquipe(idEquipe, alunosSelecionados);
            showAlert("Sucesso", "Equipe salva com sucesso!", Alert.AlertType.INFORMATION);

            if (callback != null) {
                callback.atualizarEquipe();
            }

            Stage stage = (Stage) btnSalvar.getScene().getWindow();
            stage.close();
        } else {
            showAlert("Erro", "Erro ao salvar equipe.", Alert.AlertType.ERROR);
        }
    }

    public interface AtualizacaoEquipeCallback {
        void atualizarEquipe();
    }



    @FXML
    void cancelarOperacao(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void ordenarListViews() {
        alunosDisponiveis.sort((a1, a2) -> a1.getNome().compareToIgnoreCase(a2.getNome()));
        alunosSelecionados.sort((a1, a2) -> a1.getNome().compareToIgnoreCase(a2.getNome()));
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png"))); // Ícone
        alert.showAndWait();
    }



}
