package p1HelloWorld;

import java.util.Scanner;

public class UserInput {
    public static void main(String[] args) {
        System.out.print("Enter your age: ");
        try (Scanner S = new Scanner(System.in)) {
            int age = S.nextInt();
            System.out.print("\nAge = ");
            System.out.println(age);
        }
    }
}