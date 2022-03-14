package projecttwo;

import java.util.Scanner;

public class bestclass {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {



//output
        System.out.println("Hello! What is your name?");
//input
        String name = sc.nextLine();


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
            sc.nextLine();
            // If the number is guessed
            if (number == guess) {

                int b = 1;
                int sums = i + b;
                System.out.println(
                        "Good job," + name
                                + " You guessed the number in " + sums + " guesses");

                System.out.println("Would you like to play again? (y or n)");


                String yesrno = sc.nextLine();

                if (yesrno.equals("y"))
                {
                    guessclass newobject = new guessclass();
                    newobject.main();

                }
                else if (yesrno.equals("n")) {
                    System.out.println("Have a nice day!");
                    System.exit(0);

                }



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
                    "You have exhausted " + '6' + " trys.");

            System.out.println(
                    "The number was " + number);
        }
    }


    {
        System.out.println("Would you like to play again? (y or n)");

        String yesrno = sc.nextLine();



        if (yesrno.equals("y"))
        {
            guessclass newobject = new guessclass();
            newobject.main();

        }
        else if (yesrno.equals("n")) {
            System.out.println("Have a nice day!");
            System.exit(0);

        }


    }
}

