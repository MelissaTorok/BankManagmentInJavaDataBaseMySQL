import java.sql.*;

public class DataBaseHandaling {


    public static void connectionDatabase(String name, String password, double balance, String currency) {
        Connection connection = null;
        PreparedStatement preparedStatementUsers = null;
        PreparedStatement preparedStatementPassword = null;
        PreparedStatement preparedStatementBalance = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/bankmanagmentsystem",
                    "root",
                    "12345678"
            );

            // Disable auto-commit for transaction
            connection.setAutoCommit(false);

            // Step 1: Insert into `users` table and retrieve generated user ID
            String insertUserSQL = "INSERT INTO users (username) VALUES (?)";
            preparedStatementUsers = connection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatementUsers.setString(1, name);
            preparedStatementUsers.executeUpdate();

            // Get the generated `idusers`
            ResultSet generatedKeys = preparedStatementUsers.getGeneratedKeys();
            int userId = 0;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1); // Retrieve the generated primary key
            } else {
                throw new SQLException("Failed to retrieve user ID.");
            }

            // Step 2: Insert into `password_table` with the user ID
            String insertPasswordSQL = "INSERT INTO password_table (id, password) VALUES (?, ?)";
            preparedStatementPassword = connection.prepareStatement(insertPasswordSQL);
            preparedStatementPassword.setInt(1, userId);
            preparedStatementPassword.setString(2, password);
            preparedStatementPassword.executeUpdate();

            // Step 3: Insert into `balance_table` with the retrieved user ID
            String insertBalanceSQL = "INSERT INTO balance_table (id, balance, currency) VALUES (?, ?, ?)";
            preparedStatementBalance = connection.prepareStatement(insertBalanceSQL);
            preparedStatementBalance.setInt(1, userId);
            preparedStatementBalance.setDouble(2, balance);
            preparedStatementBalance.setString(3, currency);
            preparedStatementBalance.executeUpdate();

            // Commit the transaction
            connection.commit();
            System.out.println("Success! User, password, and balance inserted.");

        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }

    public static void DeleteAccount(String entireName){


        Connection connection = null;
        PreparedStatement preparedStatementUsers = null;


        try {
            // Establish the connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/bankmanagmentsystem",
                    "root",
                    "12345678"
            );
            int foreign = gettingUserIdFromASpecificUser(entireName);

            Statement statement = connection.createStatement();

            statement.executeUpdate("DELETE FROM balance_table WHERE id = '" + foreign + "'");
            statement.executeUpdate("DELETE FROM password_table WHERE id = '" + foreign + "'");
            statement.executeUpdate("DELETE FROM users WHERE idusers = '" + foreign + "'");



        }catch (SQLException e) {

            e.printStackTrace();

        }
    }

    public static int gettingUserIdFromASpecificUser(String UserNAme) {

        Connection connection = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/bankmanagmentsystem",
                    "root",
                    "12345678"
            );

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT idusers, username FROM users WHERE username = '" + UserNAme + "'");

            while (resultSet.next()){

                if(resultSet.getString("username").equals(UserNAme)) {
                    int userId = resultSet.getInt("idusers");
                    //System.out.println(userId);
                    return userId;
                }
            }


        }catch (SQLException e) {

            e.printStackTrace();

        }

        return 0;
    }

    public static void viewAccount(String userName){

        Connection connection = null;
        PreparedStatement preparedStatementUsers = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/bankmanagmentsystem",
                    "root",
                    "12345678"
            );

            //finding the user in table users
            int foreign = gettingUserIdFromASpecificUser(userName);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT balance, currency FROM balance_table WHERE id = '" + foreign + "'");
            Statement statementPassword = connection.createStatement();
            ResultSet resultSetgetPassword = statementPassword.executeQuery("SELECT password FROM password_table WHERE id = '" + foreign + "'");

            System.out.println("NAME:" + userName );

            while (resultSet.next()) {
                System.out.println("BALANCE:" + resultSet.getString("balance") + '\n' + "CURRENCY:" + resultSet.getString("currency"));
            }

            while (resultSetgetPassword.next()) {
                System.out.println("PASSWORD:" + resultSetgetPassword.getString("password") );
            }



        }catch (SQLException e) {

            e.printStackTrace();

        }
    }

    public static float readingBalance( String UserNAme){

        Connection connection = null;
        PreparedStatement preparedStatementUsers = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/bankmanagmentsystem",
                    "root",
                    "12345678"
            );


            //finding the user in table users
            int foreign = gettingUserIdFromASpecificUser(UserNAme);

            Statement statement = connection.createStatement();
            //the sql code
            ResultSet resultSet = statement.executeQuery("SELECT id,balance FROM balance_table WHERE id = '" + foreign + "'");


            // printing the balance with the specific id(id is from )
            while (resultSet.next()) {//printing the result
                Float balance = resultSet.getFloat("balance");
                return balance;
            }

        }catch (SQLException e) {

            e.printStackTrace();

        }

        return 0;

    }

    public static void updateBalanceInDatebase(String UserNAme, double UpdateBalance){

        Connection connection = null;
        PreparedStatement preparedStatementBalance = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/bankmanagmentsystem",
                    "root",
                    "12345678"
            );


            //finding the user in table users
            int foreign = gettingUserIdFromASpecificUser(UserNAme);

            Statement statement = connection.createStatement();
            //the sql code

            //adding the balance in the db
//          ResultSet resultSet = statement.executeQuery( "UPDATE balance_table SET balance = '" + UpdateBalance + "'" + " WHERE id = '" + foreign + "'");
            statement.executeUpdate( "UPDATE balance_table SET balance = '" + UpdateBalance + "'" + " WHERE id = '" + foreign + "'");


            //preparedStatement.executeQuery();

        }catch (SQLException e) {

            e.printStackTrace();

        }


    }

    public static int findingIfUsernameExistsInDatabase(String name) {
        try {

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/bankmanagmentsystem",
                    "root",
                    "12345678"
            );//connection object



            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT username FROM users ");


            while (resultSet.next()) {//printing the result
                if(resultSet.getString("username").equals(name)) {
                    System.out.println(resultSet.getString("username"));
                    // System.out.println("You already have an account i'm in db");
                    return 0;
                }
            }

            return 1;

        }catch (SQLException e) {

            e.printStackTrace();
            return -1;
        }
    }



}
