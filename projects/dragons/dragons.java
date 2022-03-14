import java.util.*;


public class dragons {
    public static void main (String[] args) {

        public static final String SET_PLAIN_TEXT = "\033[0;0m";
        public static final String SET_BOLD_TEXT = "\033[0;1m";

        System.out.print("You are in a land full of dragons. In front of you, you see" +
                "two caves. In one cave, the dragon is friendly and will share his " +
                "treasure with you. The other dragon is greedy and hungry and will eat you" +
                "on sight. Which cave will you go into? (1 or 2):");
        Scanner input = new Scanner(System.in);
        string choice = input.nextLine();


        return (SET_BOLD_TEXT + choice + SET_PLAIN_TEXT);
        System.out.println();
    }
}
