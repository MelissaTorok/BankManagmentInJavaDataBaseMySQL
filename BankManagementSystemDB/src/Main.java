
public class Main {
    public static void main(String[] args) {



        // first is the username second is the password
        if (args.length == 0){

            HomePage.startingTheProgram("", "");
        } else if (args.length == 2){

            HomePage.startingTheProgram(args[0], args[1]);


        } else {

            System.out.println("PLEASE PROVIDE EITHER 0 OR 2 ARGUMENTS (USERNAME, PASSWORD)");
        }



    }
}