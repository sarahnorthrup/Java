package guessinggame;

import java.util.Scanner;

public class guessclass {
    //main
    public static void main() {
        //object of guessclass

        Scanner scan = new Scanner(System.in);


//output
        System.out.println("Hello! What is your name?");
//name input
        String name = scan.nextLine();

        Scanner sc = new Scanner(System.in);

        // Generate the numbers
        int number = 1 + (int) (20
                * Math.random());

        // Given 6 trys
        int K = 6;

        int i, guess;

        System.out.println(
                "Well," + name + ", I am thinking of a number between 1 and 20." + "Take a guess."
                        + " within 6 trys.");

        // Iterate over 6 Trys
        for (i = 0; i < K; i++) {

            System.out.println(
                    "Guess the number:");

            // Take input for guessing
            guess = sc.nextInt();

            // If the number is guessed
            if (number == guess) {
                System.out.println(
                        "Good job," + name
                                + " You guessed the number.");
                break;
            } else if (number > guess
                    && i != K - 1) {
                System.out.println(
                        "The number is "
                                + "greater than " + guess);
            } else if (number < guess
                    && i != K - 1) {
                System.out.println(
                        "The number is"
                                + " less than " + guess);
            }
        }

        if (i == K) {
            System.out.println(
                    "You have exhausted" + '6' + " trys.");

            System.out.println(
                    "The number was " + number);


        }
    }


}

