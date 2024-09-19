package steamducks.pacerassessment;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SampleApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/steamducks.pacerassessment/menuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Pacer Assessment");
        Image logo = new Image(getClass().getResourceAsStream("/assets/logo-teste.png"));
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}