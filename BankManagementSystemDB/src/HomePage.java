import java.util.Objects;

public class HomePage {

    public String welcomeMessage;

    public HomePage() {
        welcomeMessage = "Welcome to the Bank Management System";
        System.out.println("Welcome to the Bank Management System");
    }

    public static void startingTheProgram(String username, String password) {
        User user = new User("", "", "", "");

        if (Objects.equals(username, ""))
            user.logINorSingUp();
        else{

            user.logIn(username, password);
        }

    }


}
