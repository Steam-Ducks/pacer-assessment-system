package org.example.telateste;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class telaCadNota extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(telaCadNota.class.getResource("telaCadNota-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 332, 280);
        stage.setTitle("Steam-Ducks PAS");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}