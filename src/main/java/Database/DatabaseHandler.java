package Database;

import model.User;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + dbHost + ":" +dbPort +"/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString,dbUser,dbPass);
        return dbConnection;
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
        return rs;

    }

}