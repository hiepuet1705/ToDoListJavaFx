package Controller;

import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import Database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Task;

public class AddItemControllerForm implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField desField;

    @FXML
    private TableColumn<Task, String> descriptionColumn;

    @FXML
    private Button saveTaskButton;
    @FXML
    private Button deleteButton;


    @FXML
    private TableView<Task> tableView;

    @FXML
    private TableColumn<Task, String> taskColumn;

    @FXML
    private TextField taskField;
    @FXML
    private TableColumn<Task,String> dateCreatedColumn;
    @FXML
    private TableColumn<Task,String> deadlineColumn;
    @FXML
    private TextField deadlineField;
    private ObservableList<Task> taskList;

    public void addNewTask() throws ParseException {

            Task newTask = new Task();
            newTask.setTask(taskField.getText());
            newTask.setDescription(desField.getText());
            newTask.setDeadline(deadlineField.getText());
            newTask.setDatecreated(LocalDateTime.now().toString());
            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.createTask(newTask);
            taskList.add(newTask);


    }
    public void deleteTask(){
        Task selected = tableView.getSelectionModel().getSelectedItem();
        taskList.remove(selected);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskList = FXCollections.observableArrayList(

        );
        taskColumn.setCellValueFactory(new PropertyValueFactory<Task,String>("task"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Task,String>("description"));
        dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<Task,String>("datecreated"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<Task,String>("deadline"));
        tableView.setItems(taskList);

        saveTaskButton.setOnMouseClicked(mouseEvent -> {
            try {
                addNewTask();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        deleteButton.setOnMouseClicked(mouseEvent -> {
            deleteTask();
        });


    }
}
