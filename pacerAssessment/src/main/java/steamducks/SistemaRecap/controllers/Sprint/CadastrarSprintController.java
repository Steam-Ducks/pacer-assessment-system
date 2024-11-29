package steamducks.SistemaRecap.controllers.Sprint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import steamducks.SistemaRecap.dao.SemestreDAO;
import steamducks.SistemaRecap.dao.SprintDAO;
import steamducks.SistemaRecap.models.Semestre;
import steamducks.SistemaRecap.models.Sprint;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CadastrarSprintController {

    @FXML
    private Button btn_AddSprint;

    @FXML
    private Button btn_Cadastrar;

    @FXML
    private Button btn_Cancelar;

    @FXML
    private Button btn_Download;

    @FXML
    private Button btn_Upload;

    @FXML
    private ComboBox<Semestre> cmb_SelSem;

    @FXML
    private TableView<Sprint> tableViewSprint;

    @FXML
    private TableColumn<Sprint, String> tcNome;

    @FXML
    private TableColumn<Sprint, LocalDate> tc_DataFim;

    @FXML
    private TableColumn<Sprint, LocalDate> tc_DataInicio;

    @FXML
    private TableColumn<Sprint, Void> tcDelete;

    private ObservableList<Sprint> sprintList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        carregarSemestres();
        configurarTabela();
    }

    private void carregarSemestres() {
        SemestreDAO semestreDAO = new SemestreDAO();
        ObservableList<Semestre> semestreList = FXCollections.observableArrayList(semestreDAO.getSemestres());
        cmb_SelSem.setItems(semestreList);
    }

    private void configurarTabela() {
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tc_DataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        tc_DataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));

        // Make the name column editable
        tcNome.setCellFactory(TextFieldTableCell.forTableColumn());
        tcNome.setOnEditCommit(event -> {
            Sprint sprint = event.getRowValue();
            sprint.setNome(event.getNewValue());
        });

        tc_DataInicio.setCellFactory(getDatePickerCellFactory());
        tc_DataFim.setCellFactory(getDatePickerCellFactory());

        tcDelete.setCellFactory(column -> new TableCell<Sprint, Void>() {
            private final Button deleteButton = new Button("x");

            {
                deleteButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-font-size: 14; -fx-alignment: center;");
                deleteButton.setPrefSize(15, 10);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getIndex() < 0 || getIndex() >= sprintList.size()) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        Sprint sprintToDelete = getTableView().getItems().get(getIndex());
                        sprintList.remove(sprintToDelete);
                    });
                }
            }
        });

        tableViewSprint.setItems(sprintList);
        tableViewSprint.setEditable(true); // Enable editing on the TableView
    }

    private Callback<TableColumn<Sprint, LocalDate>, TableCell<Sprint, LocalDate>> getDatePickerCellFactory() {
        return column -> new TableCell<>() {
            private final DatePicker datePicker = new DatePicker();

            {
                datePicker.setConverter(new StringConverter<>() {
                    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    @Override
                    public String toString(LocalDate date) {
                        return date != null ? dateFormatter.format(date) : "";
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
                    }
                });

                datePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
                    if (newDate != null) {
                        Sprint sprint = getTableView().getItems().get(getIndex());
                        if (column == tc_DataInicio) {
                            sprint.setDataInicio(newDate);
                        } else if (column == tc_DataFim) {
                            sprint.setDataFim(newDate);
                        }
                    }
                });
            }

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    datePicker.setValue(item);
                    setGraphic(datePicker);
                }
            }
        };
    }

    @FXML
    void addSprint(ActionEvent event) {
        sprintList.add(new Sprint("", LocalDate.now(), LocalDate.now()));
    }

    @FXML
    void importarCsv(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            importarCsvSprint(file);
        }
    }

    private void importarCsvSprint(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean primeiraLinha = true;

            while ((line = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] values = line.split(",");
                if (values.length >= 3) {
                    Sprint sprint = new Sprint(values[0], LocalDate.parse(values[1]), LocalDate.parse(values[2]));
                    sprintList.add(sprint);
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Erro", "Falha ao importar o arquivo CSV: " + e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    void cadastrarSprint(ActionEvent event) {
        Semestre selectedSemestre = cmb_SelSem.getSelectionModel().getSelectedItem();
        if (selectedSemestre == null) {
            mostrarAlerta("Erro", "Por favor, selecione um semestre.", Alert.AlertType.WARNING);
            return;
        }

        SprintDAO sprintDAO = new SprintDAO();
        for (Sprint sprint : sprintList) {
            sprint.setIdSemestre(selectedSemestre.getId());
            sprintDAO.criarSprint(sprint);
        }

        mostrarAlerta("Sucesso", "Sprints cadastradas com sucesso!", Alert.AlertType.INFORMATION);

        // Fecha a janela atual após cadastrar
        fecharJanela();
    }


    @FXML
    void cancelarCadastrarSprint(ActionEvent event) {
        // Fecha a janela atual ao cancelar
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void baixarModeloCsv(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("modelo_sprint.csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            exportarCsvModelo(file);
        }
    }

    private void exportarCsvModelo(File file) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            pw.println("nome,dataInicio,dataFim"); // Cabeçalho do modelo
            pw.println("Exemplo Nome,yyyy-mm-dd,yyyy-mm-dd"); // Linha de exemplo

            System.out.println("Modelo CSV exportado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}