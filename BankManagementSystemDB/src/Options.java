import java.util.Scanner;

public class Options  {

    static Scanner myObjForScanner = new Scanner(System.in);


     public int singInOrSingUp(){

        System.out.println("Press 1 for sing up in a new account");
        System.out.println("Press 2 for log in in your account");
        System.out.println("Press 3 for exit");


        return myObjForScanner.nextInt();


    }

    public void editOptions(){
        //the option that you have in the account
        System.out.println("Press 1 for withdraw or add money to your account");
        System.out.println("Press 2 to view your balance and details account");
        System.out.println("Press 3 to transfer money");
        System.out.println("Press 4 for delete your account");

    }

    //currencyOption is the function that take the input from the user to choose the currency of the account when creating an account
    public static String currencyOption(){


        System.out.println("Chose your currency:");
        System.out.println("Press 1 for USD");
        System.out.println("Press 2 for EUR");
        System.out.println("Press 3 for RON");

        int cases = myObjForScanner.nextInt();

        return switch (cases) {
            case 1 -> "USD";
            case 2 -> "EUR";
            case 3 -> "RON";
            default -> "You didn't entered a correct currency";
        };
    }


}
