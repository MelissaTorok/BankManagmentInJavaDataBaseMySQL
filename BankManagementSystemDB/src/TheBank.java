import java.util.Scanner;

public class TheBank implements MoneyMagment{

    static Scanner myObjForScanner = new Scanner(System.in);


    public double getingTheBalanceIntoDoubleValue(String entireName){

        //getting the balance from the user file
        Float money = DataBaseHandaling.readingBalance(entireName);
        System.out.println("Your balance was: "+ money);

        //transforming the information from the file from a string in double
        Double moneyDouble = Double.valueOf(money);

        return moneyDouble;

    }

    public void deposit(String nameUser){

        System.out.println("How much money do you want to deposit?");
        Double addingMoney = myObjForScanner.nextDouble();

        //transforming the information from the file from a string in double
        Double moneyDouble = getingTheBalanceIntoDoubleValue(nameUser);


        //subtract the withdrawal money from the account
        double plus =  moneyDouble + addingMoney;
        System.out.println("Now your balance is: "+ plus);

        DataBaseHandaling.updateBalanceInDatebase(nameUser, plus);



    }

    public void withdraw(String nameUser ){

        System.out.println("How much money do you want to withdraw?");
        Double withdrawMoney = myObjForScanner.nextDouble();

        //transforming the information from the file from a string in double
        Double moneyDouble = getingTheBalanceIntoDoubleValue(nameUser);


        if (withdrawMoney > moneyDouble) {
            System.out.println("Your balance is: " + moneyDouble + " you don't have enough money");
        } else {
            //subtract the withdrawal money from the account
            double minus = moneyDouble - withdrawMoney;
            System.out.println("Now your balance is: " + minus);

            DataBaseHandaling.updateBalanceInDatebase(nameUser, minus);


        }
    }



}
