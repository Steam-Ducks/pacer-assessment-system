package steamducks.pacerassessment.controllers.Menu;

import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import steamducks.pacerassessment.controllers.Avaliacao.VisualizarAvaliacaoController;
import steamducks.pacerassessment.controllers.Avaliacao.AvaliacaoController;
import steamducks.pacerassessment.models.Usuario;

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
    private Button btnAvaliar;

    private Usuario usuarioLogado;

    @FXML
    private Text saudacaoAluno;

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

    @FXML
    void abrirMediaAluno() throws IOException {
        carregarView("/steamducks.pacerassessment/visualizarMediaAlunoView.fxml", usuarioLogado);
    }

    @FXML
    void avaliar(ActionEvent event) throws IOException {
        carregarView("/steamducks.pacerassessment/avaliacaoAlunoView.fxml", usuarioLogado);
    }

    @FXML
    void abrirLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/loginView.fxml"));
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
        if (controller instanceof VisualizarAvaliacaoController)
        {
            ((VisualizarAvaliacaoController) controller).initialize(usuario);
        }
        else if (controller instanceof AvaliacaoController)
        {
            ((AvaliacaoController) controller).initialize(usuario);
        }

        contentPane.getChildren().setAll(view);
    }
}