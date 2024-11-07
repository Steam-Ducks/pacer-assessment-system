package steamducks.pacerassessment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import steamducks.pacerassessment.dao.EquipeDAO;
import steamducks.pacerassessment.models.Equipe;
import steamducks.pacerassessment.models.Usuario;

public class TelaEditarEquipesController {

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtGithub;

    @FXML
    private TableColumn<Usuario, String> tcNome;

    @FXML
    private TableColumn<Usuario, String> tcSenha;

    @FXML
    private TableColumn<Usuario, String> tcEmail;

    public Equipe equipeSelecionado;

    @FXML
    private void inicializar(){
        btnConfirmar.setOnAction(event -> confirmarEdicao());
        btnCancelar.setOnAction(event -> cancelarEdicao());
    }

    public void inicializarCampos(int idEquipe) {
        EquipeDAO dao = new EquipeDAO();
        Equipe equipe = dao.buscarEquipePorId(idEquipe);
        if (equipe != null) {
            this.equipeSelecionado = equipe;
            txtNome.setText(equipe.getNome());
            txtGithub.setText(equipe.getGithub());
        } else {
            mostrarAlerta("Equipe não encontrada.", "Erro");
        }
    }

    @FXML
    void confirmarEdicao() {
        String novoNome = txtNome.getText();
        String novoGithub = txtGithub.getText();

        if (equipeSelecionado != null && equipeSelecionado.getIdEquipe() > 0 && novoNome != null && !novoNome.trim().isEmpty() && novoGithub != null && !novoGithub.trim().isEmpty()) {
            equipeSelecionado.setNome(novoNome);
            equipeSelecionado.setGithub(novoGithub);

            EquipeDAO dao = new EquipeDAO();
            dao.atualizarEquipe(equipeSelecionado.getIdEquipe(), novoNome, novoGithub, 1);

            cancelarEdicao();
        } else {
            mostrarAlerta("Erro ao editar a equipe", "Erro");
        }
    }

    private void mostrarAlerta(String alerta, String titulo){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(alerta);
        alert.showAndWait();
    }

    @FXML
    void cancelarEdicao() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

}