package Controller;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private TableColumn<Task, String> dateCreatedColumn;
    @FXML
    private TableColumn<Task, String> deadlineColumn;
    @FXML
    private TextField deadlineField;
    private ObservableList<Task> taskList;

    DatabaseHandler databaseHandler = new DatabaseHandler();


    public void renderTask() throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        ResultSet rs = databaseHandler.getTasks();
        while (rs.next()) {
            int id = rs.getInt(1);
            taskList.add(new Task(id,rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            //System.out.println(id);
        }

    }
    public void addNewTask() throws ParseException, SQLException {

        Task newTask = new Task();
        newTask.setTask(taskField.getText());
        newTask.setDescription(desField.getText());
        newTask.setDeadline(deadlineField.getText());
        newTask.setDatecreated(LocalDateTime.now().toString());

        databaseHandler.createTask(newTask);

        taskList.add(newTask);

    }

    public void deleteTask() {
        ObservableList<Task> selectedTasks = tableView.getSelectionModel().getSelectedItems();
        for(Task task : selectedTasks){
            databaseHandler.deleteTask(task);
        }
        taskList.removeAll(selectedTasks);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskList = FXCollections.observableArrayList(

        );
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        taskColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("datecreated"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));
        try {
            renderTask();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableView.setItems(taskList);

        saveTaskButton.setOnMouseClicked(mouseEvent -> {
            try {
                addNewTask();
            } catch (ParseException | SQLException e) {
                throw new RuntimeException(e);
            }
        });

        deleteButton.setOnMouseClicked(mouseEvent -> {
            deleteTask();
        });


    }
}
