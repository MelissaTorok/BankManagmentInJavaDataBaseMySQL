import java.util.Scanner;

public class ATM implements MoneyMagment{

    static Scanner myObjForScanner = new Scanner(System.in);


    public double getingTheBalanceIntoDoubleValue(String entireName){

        Float money = DataBaseHandaling.readingBalance(entireName);
        System.out.println("Your balance is: "+ money);

        //transforming the information from the file from a string in double
        Double moneyDouble = Double.valueOf(money);

        return moneyDouble;

    }

    public void deposit(String nameUser){

        System.out.println("How much money do you want to deposit?");
        Double addingMoney = myObjForScanner.nextDouble();

        //transforming the information from the file from a string in double
        Double moneyDouble = getingTheBalanceIntoDoubleValue(nameUser);


        if(addingMoney < 100){


            //subtract the withdrawal money from the account
            double minus =  moneyDouble + addingMoney;
            System.out.println("Now your balance is: "+ minus);

           //adding the money in the database user account
            DataBaseHandaling.updateBalanceInDatebase(nameUser, minus);


        }else{

            System.out.println("You can't add more than 100");
        }
    }

    public void withdraw(String nameUser ){

        System.out.println("How much money do you want to withdraw?");
        Double withdrawMoney = myObjForScanner.nextDouble();

        //transforming the information from the file from a string in double
        Double moneyDouble = getingTheBalanceIntoDoubleValue(nameUser);

        if(withdrawMoney <= 50) {

            if (withdrawMoney > moneyDouble) {
                System.out.println("Your balance was: " + moneyDouble + " you don't have enough money");
            } else {
                //subtract the withdrawal money from the account
                double minus = moneyDouble - withdrawMoney;
                System.out.println("Now your balance is: " + minus);

                DataBaseHandaling.updateBalanceInDatebase(nameUser, minus);

            }
        }else{
            System.out.println("The maximum amount to withdraw is 50");
        }

    }

}
