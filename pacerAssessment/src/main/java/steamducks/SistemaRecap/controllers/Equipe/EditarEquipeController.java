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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import steamducks.SistemaRecap.dao.EquipeDAO;
import steamducks.SistemaRecap.dao.UsuarioDAO;
import steamducks.SistemaRecap.models.Equipe;
import steamducks.SistemaRecap.models.Usuario;
import java.io.IOException;
import java.util.List;

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
            Stage stage = (Stage) btnConfirmar.getScene().getWindow();
            stage.close();
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



    public void adicionarAluno(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SistemaRecap/Equipe/adicionarAlunoView.fxml"));
            Parent root = fxmlLoader.load();

            // Acessar a controladora da tela de Adicionar Aluno
            AdicionarAlunoController controller = fxmlLoader.getController();
            int idEquipeSelecionado = equipeSelecionado.getIdEquipe();
            controller.setIdEquipe(idEquipeSelecionado); // Passar o ID da equipe

            Stage stage = new Stage();
            stage.setTitle("Sistema RECAP - Adicionar Aluno");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));

            // Atualiza os dados da equipe ao fechar a tela de adicionar aluno
            stage.setOnCloseRequest(event -> atualizarDadosEquipe());

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    private void removerUsuarioDaEquipe(Usuario usuario) {
        // Criar um popup de confirmação
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar remoção");
        alert.setHeaderText(null);
        alert.setContentText("Tem certeza de que deseja remover o usuário " + usuario.getNome() + " da equipe?");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));

        // Mostrar o popup e aguardar a resposta
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            // Se o usuário confirmar, remover do banco de dados
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

    public void atualizarDadosEquipe() {
        if (equipeSelecionado != null) {
            carregarUsuarios(equipeSelecionado.getIdEquipe());
            txtNome.setText(equipeSelecionado.getNome());
            txtGithub.setText(equipeSelecionado.getGithub());
        }
    }


}
