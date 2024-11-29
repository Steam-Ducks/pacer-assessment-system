package steamducks.SistemaRecap.controllers.Menu;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import steamducks.SistemaRecap.controllers.Avaliacao.VisualizarAvaliacaoController;
import steamducks.SistemaRecap.controllers.Avaliacao.AvaliacaoController;
import steamducks.SistemaRecap.dao.AvaliacaoDAO;
import steamducks.SistemaRecap.dao.PontuacaoDAO;
import steamducks.SistemaRecap.models.Sprint;
import steamducks.SistemaRecap.models.Usuario;

public class MenuAlunoController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Label emailAluno;

    @FXML
    private Label nomeAluno;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnSuasMedias;

    @FXML
    private Button btnAvaliar;

    private Usuario usuarioLogado;

    @FXML
    private Text saudacaoAluno;

    private AvaliacaoDAO avaliacaoDAO;
    private PontuacaoDAO pontuacaoDAO;

    public MenuAlunoController() {
        this.avaliacaoDAO = new AvaliacaoDAO();
        this.pontuacaoDAO = new PontuacaoDAO();
    }

    @FXML
    public void initialize() {
        // Initialization logic if needed
    }

    public void inicializar(Usuario usuario) {
        this.usuarioLogado = usuario;
        String nomeCompleto = usuario.getNome();

        String primeiroNome = nomeCompleto.contains(" ") ? nomeCompleto.split(" ")[0] : nomeCompleto;

        nomeAluno.setText(nomeCompleto);
        emailAluno.setText(usuario.getEmail());
        saudacaoAluno.setText("Olá, " + primeiroNome);

        try {
            abrirMediaAluno();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDownloadManual(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Manual_do_Usuario.pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        File selectedFile = fileChooser.showSaveDialog(new Stage());

        if (selectedFile != null) {
            try {
                Path pdfPath = Path.of(getClass().getResource("/Manual_do_Usuário_Sistema_RECAP.pdf").toURI());

                if (Files.exists(pdfPath)) {
                    Files.copy(pdfPath, selectedFile.toPath());
                    System.out.println("O arquivo foi salvo em: " + selectedFile.getAbsolutePath());
                } else {
                    System.out.println("Erro: O arquivo Manual_do_Usuario.pdf não foi encontrado na raiz do projeto.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao salvar o arquivo.");
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void abrirMediaAluno() throws IOException {
        carregarView("/SistemaRecap/Avaliacao/visualizarAvaliacaoView.fxml", usuarioLogado);
    }

    @FXML
    void avaliar(ActionEvent event) throws IOException {
        carregarSprintAtiva();
    }

    @FXML
    void abrirLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SistemaRecap/Menu/loginView.fxml"));
        Node view = loader.load();

        Stage stageLogin = new Stage();
        stageLogin.setTitle("Sistema RECAP");

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo-dark.png")));
        stageLogin.getIcons().add(logo);

        stageLogin.setScene(new Scene((Parent) view));
        stageLogin.setMaximized(false);
        stageLogin.setResizable(false);
        stageLogin.show();

        Stage stageAtual = (Stage) btnLogout.getScene().getWindow();
        stageAtual.close();
    }

    private void carregarView(String viewName, Usuario usuario) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewName));
        Node view = loader.load();

        Object controller = loader.getController();
        if (controller instanceof VisualizarAvaliacaoController) {
            ((VisualizarAvaliacaoController) controller).initialize(usuario);
        } else if (controller instanceof AvaliacaoController) {
            ((AvaliacaoController) controller).initialize(usuario);
        }

        contentPane.getChildren().setAll(view);
    }

    private void carregarSprintAtiva() {
        Sprint sprintAtiva = avaliacaoDAO.getSprintAtivaPorDataEEquipe(LocalDate.now(), usuarioLogado.getIdEquipe());
        if (sprintAtiva != null && pontuacaoDAO.existePontuacaoParaSprint(sprintAtiva.getIdSprint())) {
            try {
                carregarView("/SistemaRecap/Avaliacao/avaliacaoAlunoView.fxml", usuarioLogado);
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Sistema RECAP", "Erro ao carregar a tela de avaliação: " + e.getMessage());
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Sistema RECAP", "Nenhuma sprint em periodo de avaliação!.");
        }
    }



    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));
        alerta.showAndWait();
    }
}