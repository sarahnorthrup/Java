package hangman;

import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {


//defining variables
    private static String[] words = {"cheese", "cat", "mouse", "dog", "door", "desk" };
    private static String word = words[(int) (Math.random() * words.length)];
    private static String space = new String(new char[word.length()]).replace("\0", "_");
    private static int count = 0;

    public static void main(String[] args) {
        //Starting game
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("HANGMAN");
            System.out.println("   ____________");
            System.out.println("   |          _|_");
            System.out.println("   |         /   \\");
            System.out.println("   |        |     |");
            System.out.println("   |         \\_ _/");
            System.out.println("   |          _|_");
            System.out.println("   |         / | \\");
            System.out.println("   |          / \\ ");
            System.out.println("___|___      /   \\");
//while loop for guessing letter
            while (count < 7 && space.contains("_")) {
                System.out.println("Missed letters:");

                System.out.println(space);
                System.out.println("Guess a letter.");
                String guess = sc.next();
                hang(guess);
                ArrayList<String> already = new ArrayList<String>();
                already.add(guess);
                for (int i = 0; i < already.size(); i++) {
                    System.out.println(already.get(i));
                }

            }
            sc.close();
        }
        catch (Exception e){
            System.out.println("oops");
        }
    }
        public static void hang (String guess){
            String newspace = "";
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess.charAt(0)) {
                    newspace += guess.charAt(0);
                } else if (space.charAt(i) != '_') {
                    newspace += word.charAt(i);
                } else {
                    newspace += "_";
                }
            }

            if (space.equals(newspace)) {
                count++;
                hangmanImage();
            } else {
                space = newspace;
            }
            if (space.equals(word)) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Yes! The secret word is " + word + "! You have won!");
                System.out.println("Would you like to play again?");
                String maybe = sc.nextLine();
                String yes = "yes";
                String no = "no";
                if (maybe.equals(yes)) {

                    Loop newobject = new Loop();
                    newobject.main();
                }
            }

        }

        public static void hangmanImage () {
            if (count == 1) {
                System.out.println("Wrong guess, try again");
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println("___|___");
                System.out.println();
            }
            if (count == 2) {
                System.out.println("Wrong guess, try again");
                System.out.println("   |");
                System.out.println("   |");
                System.out.println("   |");
                System.out.println("   |");
                System.out.println("   |");
                System.out.println("   |");
                System.out.println("   |");
                System.out.println("___|___");
            }
            if (count == 3) {
                System.out.println("Wrong guess, try again");
                System.out.println("   ____________");
                System.out.println("   |");
                System.out.println("   |");
                System.out.println("   |");
                System.out.println("   |");
                System.out.println("   |");
                System.out.println("   |");
                System.out.println("   | ");
                System.out.println("___|___");
            }
            if (count == 4) {
                System.out.println("Wrong guess, try again");
                System.out.println("   ____________");
                System.out.println("   |          _|_");
                System.out.println("   |         /   \\");
                System.out.println("   |        |     |");
                System.out.println("   |         \\_ _/");
                System.out.println("   |");
                System.out.println("   |");
                System.out.println("   |");
                System.out.println("___|___");
            }
            if (count == 5) {
                System.out.println("Wrong guess, try again");
                System.out.println("   ____________");
                System.out.println("   |          _|_");
                System.out.println("   |         /   \\");
                System.out.println("   |        |     |");
                System.out.println("   |         \\_ _/");
                System.out.println("   |           |");
                System.out.println("   |           |");
                System.out.println("   |");
                System.out.println("___|___");
            }
            if (count == 6) {
                System.out.println("Wrong guess, try again");
                System.out.println("   ____________");
                System.out.println("   |          _|_");
                System.out.println("   |         /   \\");
                System.out.println("   |        |     |");
                System.out.println("   |         \\_ _/");
                System.out.println("   |           |");
                System.out.println("   |           |");
                System.out.println("   |          / \\ ");
                System.out.println("___|___      /   \\");
            }
            if (count == 7) {
                System.out.println("GAME OVER!");
                System.out.println("   ____________");
                System.out.println("   |          _|_");
                System.out.println("   |         /   \\");
                System.out.println("   |        |     |");
                System.out.println("   |         \\_ _/");
                System.out.println("   |          _|_");
                System.out.println("   |         / | \\");
                System.out.println("   |          / \\ ");
                System.out.println("___|___      /   \\");
                System.out.println("GAME OVER! The word was " + word);
                System.out.println("Would you like to play again?");
                Scanner sc = new Scanner(System.in);
                String maybe = sc.nextLine();
                String yes = "yes";
                String no = "no";
                if (maybe.equals(yes)) {

                    Loop newobject = new Loop();
                    newobject.main();
                }
            }
        }


}

