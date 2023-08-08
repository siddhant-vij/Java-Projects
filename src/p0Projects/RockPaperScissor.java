package p0Projects;

import java.util.Random;
import java.util.Scanner;

public class RockPaperScissor {
    public static boolean checkChoiceValidity(int n){
        return n >= 1 && n <= 3;
    }

    public static boolean checkChoiceInteger(String s, int radix) {
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

    public static void main(String[] args) {
        System.out.println("Conditionals - Rock, Paper Scissors (Game)");
        Random rn = new Random();
        try (Scanner sc = new Scanner(System.in)) {
            int userChoiceInt = 0;
            boolean userWins = false;
            int randomComputer = 0;
            do {
                System.out.println("Three Choices you have are as follows:");
                System.out.println("1. Rock");
                System.out.println("2. Paper");
                System.out.println("3. Scissor");
                System.out.print(" Make a guess here: ");
                String userChoiceStr = sc.nextLine();
                if (checkChoiceInteger(userChoiceStr,10))
                {
                    userChoiceInt = Integer.parseInt(userChoiceStr);
                    if (checkChoiceValidity(userChoiceInt))
                    {
                        randomComputer = rn.nextInt(3) + 1;
                        if (userChoiceInt != randomComputer)
                        {
                            if (userChoiceInt == 1 && randomComputer == 3)
                            {
                                userWins = true;
                            }
                            if (userChoiceInt == 2 && randomComputer == 1)
                            {
                                userWins = true;
                            }
                            if (userChoiceInt == 3 && randomComputer == 2)
                            {
                                userWins = true;
                            }
                        }
                    }
                }
            }while (!userWins);
            System.out.println("Congrats - You Won!");
            switch (userChoiceInt) {
                case 1 -> System.out.println("Your Choice: 1. Rock");
                case 2 -> System.out.println("Your Choice: 2. Paper");
                default -> System.out.println("Your Choice: 3. Scissor");
            }
            switch (randomComputer) {
                case 1 -> System.out.println("Computer's Choice: 1. Rock");
                case 2 -> System.out.println("Computer's Choice: 2. Paper");
                default -> System.out.println("Computer's Choice: 3. Scissor");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}