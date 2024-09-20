module org.example.telaAvaliacaoAluno {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.telaAvaliacaoAluno to javafx.fxml;
    exports org.example.telaAvaliacaoAluno;
}