package steamducks.pacerassessment.controllers;

import javafx.fxml.FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import java.time.LocalDate;
import java.util.Optional;

    public class CadastroSprintController {

        @FXML
        private ComboBox<Integer> cmb_SelSem;

        @FXML
        private TextField txtFieldNome;

        @FXML
        private DatePicker dp_Inicio;

        @FXML
        private DatePicker dp_Fim;

        @FXML
        public void initialize() {
            // Preenche ComboBox com semestres de 1 a 6
            cmb_SelSem.getItems().addAll(1, 2, 3, 4, 5, 6);
        }

        @FXML
        private void cadastrarSprint() {
            // Verifica se todos os campos estão preenchidos
            String nomeSprint = txtFieldNome.getText();
            LocalDate dataInicio = dp_Inicio.getValue();
            LocalDate dataFim = dp_Fim.getValue();

            if (nomeSprint.isEmpty()) {
                showAlert("Erro", "O campo nome não pode estar vazio.");
                return;
            }

            if (dataInicio == null) {
                showAlert("Erro", "O campo de data de início não pode estar vazio.");
                return;
            }

            if (dataFim == null) {
                showAlert("Erro", "O campo de data de fim não pode estar vazio.");
                return;
            }

            // Verifica se as datas são válidas
            if (dataInicio.isEqual(dataFim) || dataFim.isBefore(dataInicio)) {
                showAlert("Erro", "Data de fim deve ser após a data de início.");
                return;
            }

            // Exibe mensagem de sucesso
            showAlert("Cadastro Realizado", "Sprint cadastrada com sucesso!");
        }

        @FXML
        private void cancelarCadastrarSprint() {
            // Alerta de cancelamento
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja mesmo cancelar o cadastro de Sprint?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Ação de cancelamento
                showAlert("Cancelado", "Cadastro de Sprint cancelado.");
            }
        }

        // Metodo utilitário para exibir alertas
        private void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
