package Controller;

import animations.Shaker;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController {
    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ImageView addButton;
    @FXML
   private Label noTaskLabel;
    @FXML
    private AnchorPane footer;

    @FXML
    void initialize(){
        footer.getStyleClass().add("footer.css");
        addButton.setOnMouseClicked(event -> {
            System.out.println("Add button clicked");
            Shaker addItemShaker = new Shaker(addButton);
            addItemShaker.shake();


            FadeTransition fadeTransition = new FadeTransition(Duration.millis(0),addButton);
            FadeTransition fadeTransitionLabel = new FadeTransition(Duration.millis(0),noTaskLabel);
            footer.setVisible(false );

            addButton.setOpacity(0);
            noTaskLabel.setOpacity(0);


            fadeTransition.play();

;
            fadeTransitionLabel.play();


            try {
                AnchorPane formPane = FXMLLoader.load(getClass().getResource("../addItemForm.fxml"));
                FadeTransition transition = new FadeTransition(Duration.millis(2200),formPane);
                transition.setFromValue(0f);
                transition.setFromValue(0f);
                transition.setToValue(1f);
                transition.setCycleCount(1);
                transition.setAutoReverse(false);
                transition.play();
                rootAnchorPane.getChildren().set(2,formPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
