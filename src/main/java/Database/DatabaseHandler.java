package Database;

import model.Task;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;

public class DatabaseHandler extends Configs {
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + dbHost + ":" +dbPort +"/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString,dbUser,dbPass);
        return dbConnection;
    }
    private static final DatabaseHandler databaseHandler = new DatabaseHandler();

    public DatabaseHandler() {

    }

    public static DatabaseHandler getInstance(){
        return databaseHandler;
    }


    // Write

    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USERS_TABLE +"("  +Const.USERS_FIRSTNAME + "," +
                Const.USERS_LASTNAME + "," + Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + ","
                + Const.USERS_LOCATION + "," + Const.USERS_GENDER + ")" + " VALUES(?,?,?,?,?,?)";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getLocation());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.execute();
            System.out.println(preparedStatement);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTask(Task task){
        String insert = "INSERT INTO " + Const.TASKS_TABLE +"("  +Const.TASKS_DATE + "," +
                Const.TASKS_DEADLINE + "," + Const.TASK_DES + "," + Const.TASK + ")"+ " VALUES(?,?,?,?)";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,task.getDatecreated());
            preparedStatement.setString(2,task.getDeadline());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4,task.getTask());
            preparedStatement.execute();
            System.out.println(preparedStatement);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean checkUserExist(User user){
        PreparedStatement stmt ;
        ResultSet rs;
        try{
            String select = "SELECT * FROM " + Const.USERS_TABLE +  " WHERE username = ?";
            stmt = getDbConnection().prepareStatement(select);
            stmt.setString(1,user.getUsername());
            rs = stmt.executeQuery();
            if(rs.next()) return true;
            else return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getUser(User user){
        ResultSet rs = null;
        if(!user.getUsername().equals("") && !user.getPassword().equals("")){
            String query = "SELECT * FROM " + Const.USERS_TABLE + " Where " + "username=?" +
                     " AND "  + "password=?";
            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1,user.getUsername());
                preparedStatement.setString(2,user.getPassword());
                rs =  preparedStatement.executeQuery();
                return rs;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            System.out.println("Pls enter your credentials");
        }
        return null;

    }
    public ResultSet getTasks(){
        ResultSet rs = null;

            String query = "SELECT * FROM " + Const.TASKS_TABLE ;
            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

                rs =  preparedStatement.executeQuery();
                return rs;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }
    public void deleteTask(Task task){
        String query = "Delete from " + Const.TASKS_TABLE + " where " + "taskid =?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1,task.getId());
            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateTask(Task task) throws Exception {
        String query = "Update " + Const.TASKS_TABLE + " SET " + Const.TASKS_DEADLINE + "=?" +"," + Const.TASK+"=?"+","+
                Const.TASK_DES + "=?" + " Where " + "taskid =?";
            try{
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1,task.getDeadline());
                preparedStatement.setString(2,task.getTask());
                preparedStatement.setString(3,task.getDescription());
                preparedStatement.setInt(4,task.getId());

                preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new Exception(e);
            }
    }
    public ResultSet getDeadLine() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM tasks WHERE deadline <= "+"?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        String dateNow = LocalDateTime.now().getYear() + "-" + LocalDateTime.now().getMonth()+"-"+LocalDateTime.now().getDayOfMonth();
        preparedStatement.setString(1,"2023-05-20" );
        ResultSet rs =  preparedStatement.executeQuery();
        return rs;
    }

}
