import java.sql.*;
import java.util.Objects;
import java.util.Scanner;
import static java.lang.System.exit;



public class User implements LogInSingIn {


    //objects of classes
    public Options options = new Options();          // Member of type A
    static Scanner myObjForScanner = new Scanner(System.in);
    public ATM atm = new ATM();
    public TheBank theBank = new TheBank();



    //variables
    private String balance;
    private String entireName;
    private String password;
    private String  currency;

    //constructor
    public User(String entireName, String password,String balance,String currency) {

        this.entireName = entireName;
        this.password = password;
        this.currency = currency;
        this.balance = balance;

    }

    //getters
    public String getEntireName() {return entireName;}

    public String getPassword() {return password;}

    public String getBalance() {return balance;}

    public String getCurrency() {return currency;}

    @Override
    public void logINorSingUp(){


        switch(options.singInOrSingUp()){
            case 1:
               SingUp();
                break;
            case 2:
               logIn("", "");
                break;
            case 3:
                exit(0);
                break;
            default:
                System.out.println("You didn't entered a right option");
                break;

        }
    }


    static public void SingUp() {



        String entireName;

        //entireName it will be the username
        entireName = creatingUserName();


        //function that creates the file and verify if the account already exist, if not is returning 1
        int verify = DataBaseHandaling.findingIfUsernameExistsInDatabase(entireName);


        switch (verify) {
            case 1:
                System.out.println("Create a password for the account");

                creatingTheAccount(entireName);

                System.out.println("Your account was created successfully.");
                break;
            case 0:
                System.out.println("You already have an account");

            case -1:
                System.out.println("An error occurred while creating the account.");
                break;
        }

    }

    public static void  creatingTheAccount(String entireName) {

        String password;

        password = myObjForScanner.nextLine();

        double balanced = 0;

        String currency =  Options.currencyOption();

        DataBaseHandaling.connectionDatabase(entireName,password, balanced, currency);

    }


    public void logIn(  String username, String password ) {

        if(Objects.equals(username, ""))
            entireName = creatingUserName();
        else
            entireName = username;

        //if the account exist the function will return 0 if not we will create an account and the function will return 1
        int verify = DataBaseHandaling.findingIfUsernameExistsInDatabase(entireName);

        switch (verify) {
            case 1:
                System.out.println("You don't have an account, please create an account.");

                SingUp();

                System.out.println("Your account was created successfully.");
                break;
            case 0:
                System.out.println("You have an account");

                //checkingPassword() returns 1 if password id correct and 0 if not
                if(checkingPassword(password, entireName) == 0) {


                    options.editOptions();

                    switch (myObjForScanner.nextInt()) {
                        case 1:
                            manageYourMoney(entireName);
                            break;
                        case 2:
                            DataBaseHandaling.viewAccount(entireName);
                            break;
                        case 3:
                            transferMoney(entireName);
                            break;
                        case 4:
                            DataBaseHandaling.DeleteAccount(entireName);
                            break;
                        default:
                            System.out.println("You didn't entered a right option");
                            break;
                     }
                }
            break;

        case -1:
            System.out.println("An error occurred while login in the account.");

        }

    }

    public void manageYourMoney(String entireName) {

        System.out.println("Press 1 for adding the money");
        System.out.println("Press 2 for withdrawing the money");
        System.out.println("Press 3 for exit");

        int verify = myObjForScanner.nextInt();

        switch (verify) {
            case 1:
                System.out.println("Press 1 for ATM");
                System.out.println("Press 2 for adding the money from the bank");
                System.out.println("Press 3 for exit");

                int atmOrBank = myObjForScanner.nextInt();

                switch (atmOrBank) {
                    case 1:
                        atm.deposit(entireName);
                        break;
                    case 2:
                        theBank.deposit(entireName);

                        break;
                    case 3:
                        exit(0);
                        break;

                    default:
                        System.out.println("You entered the wrong option.");
                        break;

                }

                break;
            case 2:
                System.out.println("Press 1 for ATM");
                System.out.println("Press 2 for withdrawing the money from the bank");
                System.out.println("Press 3 for exit");

                int atmOrBank2 = myObjForScanner.nextInt();

                switch (atmOrBank2) {
                    case 1:
                        atm.withdraw(entireName);
                        break;
                    case 2:
                        theBank.withdraw(entireName);
                        break;
                    case 3:
                        exit(0);
                        break;

                    default:
                        System.out.println("You entered the wrong option.");
                        break;
                }
                break;


            case 3:
                exit(0);
                break;

            default:
                System.out.println("You entered the wrong option.");
                break;
        }

    }




    public void transferMoney(String entireName) {

        //getting the money from the entireName account
        //Double moneyDouble = getingTheBalanceInDoubleValue(entireName);
        Float moneyDouble = DataBaseHandaling.readingBalance(entireName);
        System.out.println("Your balance is: "+ moneyDouble);

        System.out.println("How much money do you want to send?");
        Double withdrawMoney = myObjForScanner.nextDouble();


        if(withdrawMoney > moneyDouble) {

            System.out.println("Your balance is: "+ moneyDouble +" you don't have enough money");
        }
        else
        {
            System.out.println("What is the username of the person?");
            myObjForScanner.nextLine();
            String recevivUsername = myObjForScanner.nextLine();


            int verify = DataBaseHandaling.findingIfUsernameExistsInDatabase(recevivUsername);

            switch (verify) {
                case 1:
                    System.out.println("The account doesn't exist");
                    break;
                case 0:
                    //System.out.println("The account is active");

                    Float money = DataBaseHandaling.readingBalance(recevivUsername);

                    //transforming the information from the db from a string in double
                    Double reciverBalance = Double.valueOf(money);

                    //the final balance from the receiver
                    reciverBalance = reciverBalance + withdrawMoney;

                    System.out.println("");

                    //writing the balance in the receiver account

                    DataBaseHandaling.updateBalanceInDatebase(recevivUsername, reciverBalance);


                    //subtract the withdrawal money from the sender account
                    double minus =  moneyDouble - withdrawMoney;
                    System.out.println("Now your balance is: "+ minus);

                    //writing the balance in the sender account
                    DataBaseHandaling.updateBalanceInDatebase(entireName, minus);

                    break;

                case -1:
                    System.out.println("An error occurred while transferring the money.");
                    break;
            }
        }
    }



    public int checkingPassword(String pass, String entireName) {

        String password = pass;

        System.out.println("Enter your password");
        password = myObjForScanner.nextLine();

        int foreign = DataBaseHandaling.gettingUserIdFromASpecificUser(entireName);

        try {

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/bankmanagmentsystem",
                    "root",
                    "12345678"
            );//connection object


            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, password FROM password_table WHERE id = '" + foreign + "'");


            while (resultSet.next()) {//printing the result
                if(resultSet.getString("password").equals(password)) {
                    System.out.println(resultSet.getString("password"));
                    System.out.println("Your password is correct!");
                    return 0;
                }
            }

            System.out.println("You're password didnt match");
            return 1;

        }catch (SQLException e) {

            e.printStackTrace();
            return -1;
        }

    }


    public static boolean checkIfStringHasOnlyLetters(String entireName){

        if (entireName == null || entireName == "") {
            return false;
        }

        int n = entireName.length();

        for (int i = 0; i < n; i++) {
            // Check if the specified character is not a letter then return false,else return true
            if (!Character.isLetter(entireName.charAt(i))) {
                return false;
            }
        }
        return true;


    }

    public static String creatingUserName(){

        //saving the first and last name from the users in variables
        System.out.println("Please enter your first name");
        String firstName = myObjForScanner.nextLine();
        System.out.println("Please enter your last name");
        String LastaName =myObjForScanner.nextLine();


        //concatenate the first name with the last name;
        String entireName = firstName;
        entireName = firstName.concat(LastaName);

        entireName = entireName.toLowerCase();

        try {
            System.out.println(entireName.charAt(0));
        } catch(NullPointerException e) {
            System.out.println("NullPointerException..");
        }

        if(checkIfStringHasOnlyLetters(entireName)){
            return entireName;
        }else{
            System.out.println("You need to enter only letters");
            return creatingUserName();
        }


    }

    public boolean checkIfTwoStringsAreEqual(String firstString, String secondString) {

        //checking if two strings are equal
        if(firstString.equals(secondString))
            return true;
        else
            return false;

    }

}
