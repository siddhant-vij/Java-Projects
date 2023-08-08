package p4Exceptions;

import java.util.Scanner;

class StringException extends Exception{
    @Override
    public String getMessage() {
        return "You entered a text string. Please enter a valid integer!";
    }
}

class DecimalException extends Exception{
    @Override
    public String getMessage() {
        return "You entered a decimal number. Please enter a valid integer!";
    }
}

class OutOfBoundsException extends Exception{
    @Override
    public String getMessage() { return "You entered an array index - beyond array length. Please enter a valid index!"; }
}

class DivisionByZeroException extends Exception{
    @Override
    public String getMessage() {
        return "You entered 0. Division by 0 not allowed. Please enter a valid integer!";
    }
}

class Validations{
    public boolean isDecimal (String str) throws DecimalException{
        return str.matches("^[+-]?\\d?\\.\\d+");
    }

    public boolean isString(String str) throws StringException{
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 48 && str.charAt(i) <= 57 || str.charAt(i) == 46 || str.charAt(0) == 45
                    && str.charAt(str.length()-1) != 46){
                count++;
            }
        }
        return count != str.length();
    }

    public boolean isInteger(String s) {
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
            if(Character.digit(s.charAt(i),10) < 0){
                return false;
            }
        }
        return true;
    }

    public boolean validateInteger(int[] x, int y) throws OutOfBoundsException{
        return y >= 0 && y < x.length;
    }

    public boolean isZero(int x) throws DivisionByZeroException{
        return x == 0;
    }
}

public class ErrorsExceptions {
    public static void main(String[] args) throws StringException, DecimalException, OutOfBoundsException, DivisionByZeroException {
        int [] marks = new int[3];
        marks[0] = 7;
        marks[1] = 56;
        marks[2] = 6;
        Validations v = new Validations();
        
        try (Scanner sc = new Scanner(System.in)) {
            String ind;
            boolean flagLevel1 = true;
            while (flagLevel1) {
                System.out.println("Enter the array index:");
                ind = sc.next();
                if (v.isString(ind))
                {
                    try{
                        throw new StringException();
                    } catch (StringException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else if (v.isDecimal(ind))
                {
                    try{
                        throw new DecimalException();
                    } catch (DecimalException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else {
                    int indInt = Integer.parseInt(ind);
                    if (!v.validateInteger(marks,indInt))
                    {
                        try{
                            throw new OutOfBoundsException();
                        } catch (OutOfBoundsException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else
                    {
                        String number;
                        boolean flagLevel2 = true;
                        while (flagLevel2) {
                            flagLevel1 = false;
                            System.out.println("\nThe value at array index entered is: " + marks[indInt]);
                            System.out.println("Enter the number you want to divide the value with:");
                            number = sc.next();
                            if (v.isString(number))
                            {
                                try{
                                    throw new StringException();
                                } catch (StringException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            else if (v.isDecimal(number))
                            {
                                try{
                                    throw new DecimalException();
                                } catch (DecimalException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            else {
                                int numberInt = Integer.parseInt(number);
                                if (v.isZero(numberInt)) {
                                    try{
                                        throw new DivisionByZeroException();
                                    } catch (DivisionByZeroException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                else {
                                    try {
                                        flagLevel2 = false;
                                        System.out.println();
                                        System.out.println();
                                        System.out.println("The value at array index entered is: " + marks[indInt]);
                                        System.out.println("The value of array-value/number is: " + marks[indInt] / numberInt);
                                    } catch (Exception e) {
                                        System.out.println("Some unknown exception occurred!");
                                        System.out.println(e.getMessage());
                                        System.out.println("Best luck next time!");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}