package Java;

import java.util.Scanner;

public class Guessing {
    //objects including game
    // object = instance of class
    static Guessing game= new Guessing();
    //guessing is a class,game is object
    Player user=new Player();
    Computer ai=new Computer();
    Scanner sc= new Scanner(System.in);

    public static void main(String[] args) {
        game.start();



    }
    public void start (){
        System.out.println("Hello! What is your name?");
        //nextline can cause errors when using nextint
        user.setName(sc.next());

        //does everything in method play
        play();


    }
    public void play (){
        ai.setNumber(1 + (int) (20 * Math.random()));

        System .out.println("Well,"+user.getName()+", I am thinking of a number between 1 and 20."+"Take a guess.");
        user.setGuess(sc.nextInt());

    }
    //call this to end program
    public void end(){
        sc.close();
        System.exit(0);

    }

}
//class
//all of the data in player is properties of player
class Player{
    //properties of class
    private String name;
    //properties of class
    private int guess;
    //method of player class , variable "name"
    public void setName(String name){
        this.name=name;
    }
    //method of player class (method is a function that belongs to a class or object), object instance of class
    public String getName(){
        return this.name;
    }
    //method
    public void setGuess(int guess){
        this.guess= guess;
    }
}
//class
class Computer{
    //property
    private int number;
    //method
    public void setNumber (int number){
        this.number=number;
    }
}