package steamducks.SistemaRecap.controllers.Menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class MenuProfessorController {

    @FXML
    private Button btnGerenciarAlunosEquipes;

    @FXML
    private Button btnGerenciarCriterios;

    @FXML
    private Button btnGerenciarPontuacaoSprint;

    @FXML
    private Button btnGerenciarSemestre;

    @FXML
    private Button btnGerenciarSprints;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnRelatorios;

    @FXML
    private AnchorPane contentPane;

    @FXML
    public void initialize() {
        try {
            abrirRelatorios();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirGerenciarEquipes(ActionEvent event) throws IOException {
        carregarView("/SistemaRecap/Equipe/telaGerenciarEquipesView.fxml");
    }

    @FXML
    public void abrirGerenciarCriterios(ActionEvent event) throws IOException {
        carregarView("/SistemaRecap/Criterio/gerenciarCriteriosMenuView.fxml");
    }

    @FXML
    public void abrirGerenciarPontuacaoSprint(ActionEvent event) throws IOException {
        carregarView("/SistemaRecap/Sprint/gerenciarPontuacaoSprintView.fxml");
    }

    @FXML
    public void abrirRelatorios() throws IOException {
        carregarView("/SistemaRecap/Relatorio/relatoriosView.fxml");
    }

    @FXML
    public void abrirGerenciarSemestre() throws IOException {
        carregarView("/SistemaRecap/Semestre/gerenciarSemestreView.fxml");
    }

    @FXML
    public void abrirGerenciarSprints(ActionEvent event) throws IOException {
        carregarView("/SistemaRecap/Sprint/telaGerenciarSprintView.fxml");
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



    private void carregarView(String viewName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewName));
        Node view = loader.load();

        contentPane.getChildren().setAll(view);
    }
}
