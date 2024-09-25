package org.example.telaAvaliacaoSprint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class telaSprintApplication extends Application {
    @Override
    //Dentro do Metodo Start estão as propriedades da tela que será carregada ao iniciar a aplicação
    public void start(Stage stage) throws IOException {
        //O FXMLLoader define qual tela será iniciada, o tamanho da janela e seu título
        FXMLLoader fxmlLoader = new FXMLLoader(telaSprintApplication.class.getResource("avaliacaoSprint-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 397, 459);
        stage.setTitle("Steams Ducks - PAS");
        stage.setScene(scene);
        stage.show();
    }

    //O Metodo main de fato faz o "Start" do código para que ele rode
    public static void main(String[] args) {
        launch();
    }
}