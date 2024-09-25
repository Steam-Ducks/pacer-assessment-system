module org.example.telaAvaliacaoSprint {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens org.example.telaAvaliacaoSprint to javafx.fxml;
    exports org.example.telaAvaliacaoSprint;
}