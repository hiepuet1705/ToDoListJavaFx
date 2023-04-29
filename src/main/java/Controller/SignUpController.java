package Controller;

import Database.DatabaseHandler;
import com.sun.tools.javac.Main;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SignUpController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signupButton;

    @FXML
    private CheckBox signupCheckboxFemale;

    @FXML
    private CheckBox signupCheckboxMale;

    @FXML
    private TextField signupFirstName;

    @FXML
    private TextField signupLastName;

    @FXML
    private TextField signupLocation;

    @FXML
    private TextField signupPassword;

    @FXML
    private TextField signupUserName;

    private LoginController loginController;

    @FXML
    void initialize() {

        signupButton.setOnAction(event -> {
           createUser();
        });

    }

    private void createUser(){
        String firstName = signupFirstName.getText();
        String lastName = signupLastName.getText();
        String username = signupUserName.getText();
        String password = signupPassword.getText();
        String location = signupLocation.getText();
        String gender = "";

        if(signupCheckboxFemale.isSelected()) gender = "Female";
        else gender = "Male";

        User newUser = new User(firstName,lastName,username,password,location,gender);

        DatabaseHandler databaseHandler = new DatabaseHandler();

        if(databaseHandler.checkUserExist(newUser)){
            System.out.println("Username is existed");
            return;
        }
        else{
            databaseHandler.signUpUser(newUser);
            System.out.println("Register successfully");
//            loginController = new LoginController();
//            loginController.ChangeSlide(signupButton,"login.fxml");
            signupButton.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../login.fxml"));
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load(), 550, 400);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            stage.setTitle("TodoApp!");
            stage.setScene(scene);
            stage.show();

        }


    }

}
