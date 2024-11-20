package steamducks.SistemaRecap.controllers.Equipe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import steamducks.SistemaRecap.dao.EquipeDAO;
import steamducks.SistemaRecap.dao.UsuarioDAO;
import steamducks.SistemaRecap.models.Equipe;
import steamducks.SistemaRecap.models.Usuario;

import java.io.IOException;
import java.util.List;

import static java.sql.Types.NULL;

public class EditarEquipeController {

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtGithub;

    @FXML
    private TableView<Usuario> tbUsuarios;

    @FXML
    private TableColumn<Usuario, String> tcNome;

    @FXML
    private TableColumn<Usuario, String> tcSenha;

    @FXML
    private TableColumn<Usuario, String> tcEmail;

    @FXML
    private TableColumn<Usuario, Void> tcRemover;

    private ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

    private Equipe equipeSelecionado;

    private Usuario usuarioTabela;

    EquipeDAO dao = new EquipeDAO();

    Usuario usuario = new Usuario();

    @FXML
    private void initialize() {
        btnConfirmar.setOnAction(event -> confirmarEdicao());
        btnCancelar.setOnAction(event -> cancelarEdicao());

        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tbUsuarios.setEditable(true);
        tbUsuarios.setItems(listaUsuarios);

        tcNome.setCellFactory(TextFieldTableCell.forTableColumn());
        tcEmail.setCellFactory(TextFieldTableCell.forTableColumn());

        configurarListenersDeEdicao();
        carregarUsuarios(usuario.getIdEquipe());

        // Adicionando a coluna de botão para remover o usuário dinamicamente
        tcRemover.setCellFactory(param -> new TableCell<Usuario, Void>() {
            private final Button btnRemover = new Button("X");

            {
                btnRemover.setStyle("-fx-background-color: black; -fx-text-fill: white;");
                btnRemover.setOnAction(event -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    removerUsuarioDaEquipe(usuario);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Coloca o botão dentro de um StackPane para centralizá-lo
                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().add(btnRemover);
                    StackPane.setAlignment(btnRemover, Pos.CENTER); // Centraliza o botão no StackPane
                    setGraphic(stackPane);
                }
            }
        });
    }

    public void inicializarCampos(int idEquipe) {
        Equipe equipe = dao.buscarEquipePorId(idEquipe);

        if (equipe != null) {
            this.equipeSelecionado = equipe;
            txtNome.setText(equipe.getNome());
            txtGithub.setText(equipe.getGithub());
        } else {
            mostrarAlerta("Equipe não encontrada.", "Erro");
        }

        carregarUsuarios(idEquipe);
    }

    private void carregarUsuarios(int idEquipe) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.getUsuariosPorEquipe(idEquipe);

        listaUsuarios.setAll(usuarios);
    }

    @FXML
    void confirmarEdicao() {
        String novoNome = txtNome.getText();
        String novoGithub = txtGithub.getText();

        if (equipeSelecionado != null && equipeSelecionado.getIdEquipe() > 0 && novoNome != null && !novoNome.trim().isEmpty() && novoGithub != null && !novoGithub.trim().isEmpty()) {
            equipeSelecionado.setNome(novoNome);
            equipeSelecionado.setGithub(novoGithub);

            EquipeDAO dao = new EquipeDAO();
            dao.atualizarEquipe(equipeSelecionado.getIdEquipe(), novoNome, novoGithub, equipeSelecionado.getIdSemestre());

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            for (Usuario usuario : listaUsuarios) {
                boolean sucesso = usuarioDAO.atualizarUsuario(usuario);
                if (!sucesso) {
                    mostrarAlerta("Erro ao atualizar o usuário: " + usuario.getNome(), "Erro");
                }
            }

            cancelarEdicao();
        } else {
            mostrarAlerta("Erro ao editar a equipe", "Erro");
        }
    }

    private void mostrarAlerta(String alerta, String titulo) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(alerta);
        alert.showAndWait();
    }

    @FXML
    private void cancelarEdicao() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void configurarListenersDeEdicao() {
        tcNome.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.setNome(event.getNewValue());
        });

        tcEmail.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.setEmail(event.getNewValue());
        });

    }

    private void atualizarUsuarioNoBanco(Usuario usuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean sucesso = usuarioDAO.atualizarUsuario(usuario);
        if (!sucesso) {
            mostrarAlerta("Erro ao atualizar usuário", "Erro");
        }
    }

    public void adicionarAluno(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SistemaRecap/Equipe/adicionarAlunoView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Sistema RECAP");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void removerUsuarioDaEquipe(Usuario usuario) {
        // Atualizar o id_equipe do usuário para null no banco de dados
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean sucesso = usuarioDAO.removerDaEquipe(usuario);

        if (sucesso) {
            // Atualizar a lista de usuários na tabela
            listaUsuarios.remove(usuario);
        } else {
            mostrarAlerta("Erro ao remover o usuário da equipe", "Erro");
        }
    }
}
