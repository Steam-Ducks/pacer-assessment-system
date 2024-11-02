package steamducks.pacerassessment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import steamducks.pacerassessment.dao.EquipeDAO;
import steamducks.pacerassessment.models.Equipe;
import steamducks.pacerassessment.models.Usuario;

import java.io.*;

public class CadastroGrupoAlunoController {

    @FXML
    private TextField txtEquipe;
    @FXML
    private TextField txtGithub;

    @FXML
    private ComboBox<String> cmbSemestre;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnUpload;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnAddAluno;

    @FXML
    private TableColumn<Usuario, String> tcEmail;

    @FXML
    private TableColumn<Usuario, String> tcNome;

    @FXML
    private TableColumn<Usuario, String> tcSenha;

    @FXML
    private TableColumn<Usuario, String> tcDelete;

    @FXML
    private TableView<Usuario> tvAlunos;

    private Equipe equipe;
    private final ObservableList<Usuario> alunoList = FXCollections.observableArrayList();

    public void initialize() {
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));

        configurarEdicao();
        configurarDeletar();
        tvAlunos.setItems(alunoList);

        carregarSemestres();
    }

    private void configurarEdicao() {
        tcNome.setCellFactory(TextFieldTableCell.forTableColumn());
        tcNome.setOnEditCommit(event -> {
            Usuario aluno = event.getRowValue();
            aluno.setNome(event.getNewValue());
        });

        tcEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        tcEmail.setOnEditCommit(event -> {
            Usuario aluno = event.getRowValue();
            aluno.setEmail(event.getNewValue());
        });

        tcSenha.setCellFactory(TextFieldTableCell.forTableColumn());
        tcSenha.setOnEditCommit(event -> {
            Usuario aluno = event.getRowValue();
            aluno.setSenha(event.getNewValue());
        });

        tvAlunos.setEditable(true);
        tvAlunos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TablePosition<Usuario, ?> pos = tvAlunos.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                tvAlunos.edit(row, pos.getTableColumn());
            }
        });
    }

    private void configurarDeletar() {
        tcDelete.setCellFactory(column -> new TableCell<Usuario, String>() {
            private final Button deleteButton = new Button("x");

            {
                deleteButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-font-size: 14; -fx-alignment: center;");
                deleteButton.setPrefSize(15, 10);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getIndex() < 0 || getIndex() >= alunoList.size()) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        Usuario usuarioToDelete = getTableView().getItems().get(getIndex());
                        alunoList.remove(usuarioToDelete);
                    });
                }
            }
        });
    }

    private void carregarSemestres() {
        EquipeDAO equipeDAO = new EquipeDAO();
        ObservableList<String> semestreList = FXCollections.observableArrayList(equipeDAO.buscarSemestres());
        cmbSemestre.setItems(semestreList);
    }

    @FXML
    void registrar(ActionEvent event) {
        String nomeEquipe = txtEquipe.getText();
        String github = txtGithub.getText();
        String semestreSelecionado = cmbSemestre.getValue();

        if (nomeEquipe.isEmpty() || github.isEmpty() || semestreSelecionado == null) {
            mostrarAlerta("Erro", "Por favor, preencha todos os campos da equipe.", Alert.AlertType.WARNING);
            return;
        }

        /* precisa ter um aluno?
        if (alunoList.isEmpty()) {
            mostrarAlerta("Erro", "A equipe deve ter pelo menos um aluno.", Alert.AlertType.WARNING);
            return;
        }
        */

        for (Usuario aluno : alunoList) {
            if (aluno.getNome().isEmpty() || aluno.getEmail().isEmpty() || aluno.getSenha().isEmpty()) {
                mostrarAlerta("Erro", "Todos os campos dos alunos devem ser preenchidos.", Alert.AlertType.WARNING);
                return;
            }
        }

        EquipeDAO dao = new EquipeDAO();
        int idSemestre;

        try {
            idSemestre = dao.obterIdSemestre(semestreSelecionado);
        } catch (RuntimeException e) {
            mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.WARNING);
            return;
        }

        int idEquipe;

        try {
            idEquipe = dao.criarEquipe(nomeEquipe, github, idSemestre);
        } catch (RuntimeException e) {
            mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.WARNING);
            return;
        }

        if (idEquipe == -1) {
            mostrarAlerta("Erro", "Falha ao criar a equipe. Tente novamente.", Alert.AlertType.WARNING);
            return;
        }

        for (Usuario aluno : alunoList) {
            aluno.setIdEquipe(idEquipe);
        }

        try {
            boolean alunosAdicionados = dao.adicionarAlunos(idEquipe, alunoList);
            if (!alunosAdicionados) {
                mostrarAlerta("Erro", "Falha ao adicionar alunos. Tente novamente.", Alert.AlertType.WARNING);
                return;
            }
        } catch (RuntimeException e) {
            mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.WARNING);
            return;
        }

        mostrarAlerta("Sucesso", "Equipe registrada com sucesso!", Alert.AlertType.INFORMATION);

        txtEquipe.setText("");
        txtGithub.setText("");
        alunoList.clear();
        tvAlunos.setItems(alunoList);
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Sistema RECAP");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-dark.png")));
        alerta.showAndWait();
    }

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void upload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            importarCsv(file);
        }
    }

    private void importarCsv(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean primeiraLinha = true;
            alunoList.clear();

            while ((line = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] values = line.split(",");
                if (values.length >= 3) {
                    Usuario aluno = new Usuario(values[0], values[1], values[2], 0, false);
                    alunoList.add(aluno);
                }
            }

            equipe = new Equipe(txtEquipe.getText(), txtGithub.getText(), alunoList, cmbSemestre.getValue());

        } catch (IOException e) {
            mostrarAlerta("Erro", "Falha ao importar o arquivo CSV: " + e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    @FXML
    void downloadModel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("modelo.csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            exportarCsvModelo(file);
        }
    }

    private void exportarCsvModelo(File file) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            pw.println("nome,email,senha"); // Cabeçalho do modelo
            pw.println("Exemplo Nome,exemplo@email.com,senha123"); // Linha de exemplo

            System.out.println("Modelo CSV exportado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addAluno(ActionEvent event) {
        Usuario newUser = new Usuario("", "", "", 0, false);
        alunoList.add(newUser);
        int newIndex = alunoList.size() - 1;
        tvAlunos.scrollTo(newUser);
        tvAlunos.edit(newIndex, tcNome);
    }
}
