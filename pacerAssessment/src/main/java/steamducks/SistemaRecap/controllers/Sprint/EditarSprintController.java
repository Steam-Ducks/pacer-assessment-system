package steamducks.SistemaRecap.controllers.Sprint;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import steamducks.SistemaRecap.dao.SprintDAO;
import steamducks.SistemaRecap.dao.SprintDAO;
import steamducks.SistemaRecap.models.Sprint;
import steamducks.SistemaRecap.models.Sprint;

import java.sql.SQLException;


public class EditarSprintController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private TableColumn<Sprint, String> dataFimColumn;

    @FXML
    private TableColumn<Sprint, String> dataInicioColumn;

    @FXML
    private TableColumn<Sprint, String> nomeColumn;


