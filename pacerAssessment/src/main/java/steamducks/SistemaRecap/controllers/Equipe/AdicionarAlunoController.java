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

    @FXML
    private Button btnAdicionar, btnRemover, btnSalvar, btnCancelar;

    @FXML
    private ListView<Usuario> listViewDisponiveis, listViewSelecionados;

    private final EquipeDAO equipeDAO = new EquipeDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private ObservableList<Usuario> alunosDisponiveis;
    private ObservableList<Usuario> alunosSelecionados;

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
        log("ID da Equipe recebido: " + idEquipe);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log("Inicializando controlador...");
        carregarAlunos();
    }

    private void carregarAlunos() {
        // Busca alunos disponíveis no banco
        List<Usuario> semEquipe = usuarioDAO.buscarUsuariosSemEquipe();
        log("Quantidade de alunos sem equipe: " + semEquipe.size());

        alunosDisponiveis = FXCollections.observableArrayList(semEquipe);
        alunosSelecionados = FXCollections.observableArrayList();

        // Configura ListViews
        atualizarListViews();
    }

    @FXML
    private void adicionarAluno(ActionEvent event) {
        moverUsuario(listViewDisponiveis, alunosDisponiveis, alunosSelecionados, "Nenhum aluno selecionado para adicionar.");
    }

    @FXML
    private void removerAluno(ActionEvent event) {
        moverUsuario(listViewSelecionados, alunosSelecionados, alunosDisponiveis, "Nenhum aluno selecionado para remover.");
    }

    @FXML
    private void salvarEquipe(ActionEvent event) {
        if (alunosSelecionados.isEmpty()) {
            exibirAlerta("Erro", "Adicione pelo menos um aluno à equipe.", Alert.AlertType.ERROR);
            return;
        }

        if (idEquipe > 0) {
            log("Salvando equipe ID: " + idEquipe);
            equipeDAO.adicionarUsuarioAEquipe(idEquipe, alunosSelecionados);
            exibirAlerta("Sucesso", "Equipe salva com sucesso!", Alert.AlertType.INFORMATION);

            recarregarDados();
            fecharJanela();
        } else {
            exibirAlerta("Erro", "Erro ao salvar equipe.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelarOperacao(ActionEvent event) {
        fecharJanela();
    }

    private void moverUsuario(ListView<Usuario> listViewOrigem, ObservableList<Usuario> origem, ObservableList<Usuario> destino, String mensagemErro) {
        Usuario selecionado = listViewOrigem.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            origem.remove(selecionado);
            destino.add(selecionado);
            atualizarListViews();
        } else {
            exibirAlerta("Erro", mensagemErro, Alert.AlertType.ERROR);
        }
    }

    private void recarregarDados() {
        alunosSelecionados.clear();
        alunosDisponiveis.setAll(usuarioDAO.buscarUsuariosSemEquipe());
        atualizarListViews();
    }

    private void atualizarListViews() {
        alunosDisponiveis.sort((a1, a2) -> a1.getNome().compareToIgnoreCase(a2.getNome()));
        alunosSelecionados.sort((a1, a2) -> a1.getNome().compareToIgnoreCase(a2.getNome()));

        listViewDisponiveis.setItems(alunosDisponiveis);
        listViewSelecionados.setItems(alunosSelecionados);
    }

    private void exibirAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        // Adiciona ícone
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));
        alert.showAndWait();
    }

    private void fecharJanela() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void log(String mensagem) {
        System.out.println(mensagem);
    }
}
