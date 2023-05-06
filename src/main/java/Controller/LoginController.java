package Controller;

import Database.DatabaseHandler;
import animations.Shaker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import model.User;




public class LoginController {
    @FXML
    ResourceBundle resources;

    @FXML
    URL location;

    @FXML
    Button cancelButton;

    @FXML
    Button loginButton;

    @FXML
    PasswordField loginPassword;


    private DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    @FXML
    TextField loginUsername;
    @FXML
    Button signUpButton;

    @FXML
    public void initialize() {


        loginButton.setOnAction(event -> {
            String loginUserName = loginUsername.getText().trim();
            String loginPwd = loginPassword.getText().trim();

            User user = new User();
            user.setUsername(loginUserName);
            user.setPassword(loginPwd);
            if(!loginUserName.equals("") && !loginPwd.equals("")) {
                ResultSet userRow = databaseHandler.getUser(user);


                try {
                    if (userRow.next()) {
                        String name = userRow.getString("firstname");
                        System.out.println("Welcome " + name.toUpperCase());
                        // move to add item

//                        loginButton.getScene().getWindow().hide();
//
//                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../addItem.fxml"));
//
//                        Parent root = fxmlLoader.load();
//                        Stage stage = new Stage();
//                        stage.setScene(new Scene(root));
//                        stage.showAndWait();
                        ChangeSlide(loginButton,"addItem.fxml");

                    } else {
                        System.out.println("Wrong user and password");
                        Shaker usernameShaker = new Shaker(loginUsername);
                        Shaker passwordShaker = new Shaker(loginPassword);
                        usernameShaker.shake();
                        passwordShaker.shake();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Please enter username and password");
            }
        });

        signUpButton.setOnAction(event -> {
            // take users to sign up

            signUpButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../signup.fxml"));

            try {
                loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

        });


    }

    public void ChangeSlide(Button btn,String fxmlFile){
        btn.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../" + fxmlFile));

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage() ;
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }


}