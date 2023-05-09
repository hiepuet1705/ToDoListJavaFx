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
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private ComboBox<String> hourBox = new ComboBox<>();

    @FXML
    private ComboBox<String> minuteBox = new ComboBox<>();

    @FXML
    private Button saveTaskButton;
    @FXML
    private Button deleteButton;
    @FXML
    private DatePicker deadlinePicker;

    @FXML
    private TableView<Task> tableView;

    @FXML
    private Button updateButton;

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

    DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


    public void renderTask() throws SQLException {
        //DatabaseHandler databaseHandler = new DatabaseHandler();

        ResultSet rs = databaseHandler.getTasks();
        while (rs.next()) {
            int id = rs.getInt(1);
            Task newTask = new Task(id, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            taskList.add(newTask);
            //System.out.println(id);
        }

    }

    public void checkNotify() throws SQLException {


        ResultSet rs = databaseHandler.getTasks();
        while (rs.next()) {
            int id = rs.getInt(1);
            Task newTask = new Task(id, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            notifyTask(newTask, newTask.getDatecreated().substring(0, 10));
        }

    }

    public void addNewTask() throws ParseException, SQLException {

        Task newTask = new Task();
        newTask.setTask(taskField.getText());
        newTask.setDescription(desField.getText());
        newTask.setDeadline(deadlinePicker.getValue().toString()+" " + hourBox.getValue() + ":" + minuteBox.getValue() );

        newTask.setDatecreated(LocalDateTime.now().toString());

        databaseHandler.createTask(newTask);

        taskList.add(newTask);

    }

    public void deleteTask() {
        ObservableList<Task> selectedTasks = tableView.getSelectionModel().getSelectedItems();
        for (Task task : selectedTasks) {
            databaseHandler.deleteTask(task);
        }
        taskList.removeAll(selectedTasks);
    }

    public void notifyTask(Task task, String localTime) {
        if (task.getDeadline().equals(localTime)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            alert.setContentText("Hãy thực hiện việc " + task.getTask());
            alert.showAndWait();
        } else return;
    }

    public void getSelectedTask() {
        tableView.setRowFactory(taskTableView -> {
            TableRow<Task> myRow = new TableRow<>();
            myRow.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 1 && (!myRow.isEmpty())) {
                    Task selectedItems = tableView.getSelectionModel().getSelectedItem();
                    taskField.setText(selectedItems.getTask());
                    desField.setText(selectedItems.getDescription());
                    deadlinePicker.setPromptText(selectedItems.getDeadline());


                }
            });
            return myRow;
        });
    }

    public void updateTask() {

        Task selectedItems = tableView.getSelectionModel().getSelectedItem();
        String newDeadline = deadlinePicker.getValue().toString()+" " + hourBox.getValue() + ":" + minuteBox.getValue();

        selectedItems.setTask(taskField.getText());
        selectedItems.setDeadline(deadlinePicker.getValue() != null ? newDeadline
                : selectedItems.getDeadline());
        selectedItems.setDescription(desField.getText());
        selectedItems.setDatecreated(LocalDateTime.now().toString());

        try {
            databaseHandler.updateTask(selectedItems);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        tableView.refresh();
    }

    public void initComboBox() {
        ObservableList<String> hours = FXCollections.observableArrayList();
        for (int i = 0; i < 24; i++) {
            if(i>=10)
            hours.add((String.valueOf(i)));
            else {
                hours.add("0"+String.valueOf(i));
            }
        }
        ObservableList<String> minutes = FXCollections.observableArrayList();
        for (int i = 0; i < 60; i++) {
            if(i>=10)
                minutes.add((String.valueOf(i)));
            else {
                minutes.add("0"+String.valueOf(i));
            }
        }
        minuteBox.setItems(minutes);
        hourBox.setItems(hours);
    }

    public void initTaskTable() {
        taskList = FXCollections.observableArrayList(

        );
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        taskColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("datecreated"));

        deadlineColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initComboBox();
        initTaskTable();

        // render tu database
        try {
            renderTask();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableView.setItems(taskList);

        getSelectedTask();
        updateButton.setOnMouseClicked(mouseEvent -> {
            System.out.println("updated");
            updateTask();


        });

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
