package p0Projects;

import java.util.Random;
import java.util.Scanner;

class Game{
    private final int computerNumber;
    private String userInputString;
    private int userInputInt;
    private boolean userWins;
    private int numberOfAttempts = 0;

    public Game(){
        System.out.println("Guess The Number Game");
        Random rn = new Random();
        computerNumber = rn.nextInt(100) + 1;
        System.out.println("Game Starts: The computer has already chosen a random number b/w 1 & 100");
        System.out.println();
    }
    public void displayGameMenu(){
        System.out.println("Guess The Number Game");
        System.out.print("Please choose a number here: ");
    }
    public void printComputerNumber(){
        System.out.println("Computer's Choice: " + computerNumber);
    }
    public void takeUserInput(){
        try (Scanner sc = new Scanner(System.in)) {
            userInputString = sc.next();
        }
    }
    public void printUserNumber(){
        System.out.println("Your Choice: " + userInputInt);
    }
    public boolean checkInputValidity(int n){
        return n>=1 && n<=100;
    }
    public boolean checkIntegerValidity(String s, int radix){
        if(s.isEmpty()){
            return false;
        }
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1){
                    return false;
                }
                else{
                    continue;
                }
            }
            if(Character.digit(s.charAt(i),radix) < 0){
                return false;
            }
        }
        return true;
    }
    public void setUserInput(String s){
        userInputInt = Integer.parseInt(s);
    }
    public String getUserInputStr(){
        return userInputString;
    }
    public int getUserInput(){
        return userInputInt;
    }
    public int getComputerNumber(){
        return computerNumber;
    }
    public void oneMoreAttempt(){
        numberOfAttempts++;
    }
    public void printNumberOfAttempts(){
        System.out.println("Total Number of Attempts: " + numberOfAttempts);
    }
    public boolean isUserWins(){
        return userWins;
    }
    public void yesUserWins(boolean b){
        userWins = b;
    }
}

public class GuessTheNumberGame {
    public static void main(String[] args) {
        Game gm = new Game();
        do {
            gm.displayGameMenu();
            gm.takeUserInput();
            gm.oneMoreAttempt();
            if (gm.checkIntegerValidity(gm.getUserInputStr(),10))
            {
                gm.setUserInput(gm.getUserInputStr());
                if (gm.checkInputValidity(gm.getUserInput()))
                {
                    if (gm.getUserInput() < gm.getComputerNumber())
                    {
                        System.out.println("Your choice is lower than what computer chose");
                        System.out.println();
                        gm.yesUserWins(false);
                    }
                    else if (gm.getUserInput() > gm.getComputerNumber())
                    {
                        System.out.println("Your choice is higher than what computer chose");
                        System.out.println();
                        gm.yesUserWins(false);
                    }
                    else
                    {
                        gm.yesUserWins(true);
                    }
                }
                else
                {
                    System.out.println("Please enter a valid number between 1 to 100");
                    System.out.println();
                }
            }
            else
            {
                System.out.println("Please enter a valid number");
                System.out.println();
            }
        } while(!gm.isUserWins());
        System.out.println();
        gm.printComputerNumber();
        gm.printUserNumber();
        gm.printNumberOfAttempts();
    }
}