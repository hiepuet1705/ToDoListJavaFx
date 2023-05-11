import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene;
        scene = new Scene(fxmlLoader.load(), 550, 400);
        stage.setTitle("TodoApp!");



        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}