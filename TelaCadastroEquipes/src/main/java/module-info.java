module com.example.telacadastro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.telacadastro to javafx.fxml;
    exports com.example.telacadastro;
}