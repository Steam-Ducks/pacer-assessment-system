package steamducks.pacerassessment;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SistemaRecap extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/loginView.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sistema RECAP");
        stage.setScene(scene);

        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setMaximized(false);
        stage.setResizable(false);


        Image logo = new Image(getClass().getResourceAsStream("/assets/logo-dark.png"), 200, 200, true, true);
        stage.getIcons().add(logo);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}