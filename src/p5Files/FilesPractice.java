package p5Files;

import java.io.BufferedReader;
import java.io.FileReader;

// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileWriter;
import java.io.IOException;
// import java.util.Scanner;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class FilesPractice {
    public static void main(String[] args) throws IOException {
        // Code to create a folder, if it doesn't exist
        /*
         * File directory = new File("Tables");
         * if (!directory.exists()){
         * directory.mkdir();
         * }
         */

        // Code to create a new file
        /*
         * File myFileC = new File("ExampleFile.txt");
         * try {
         * myFileC.createNewFile();
         * } catch (IOException e) {
         * System.out.println("Unable to create this file");
         * e.printStackTrace();
         * }
         */

        // Code to write to a file - overwrites every time (without second parameter set
        // to true - default).
        // Second parameter to FileWriter. If set to true, then the data will be written
        // to the end of the file.
        /*
         * try {
         * FileWriter fileWriterU = new FileWriter("ExampleFile.txt", true);
         * fileWriterU.
         * write("This is our first file from this java course\nOkay now bye");
         * fileWriterU.write("\nAre you sure?");
         * fileWriterU.close();
         * } catch (IOException e) {
         * System.out.println("Unable to write to this file");
         * e.printStackTrace();
         * }
         */

        // Reading a file
        /*
         * File myFileR = new File("ExampleFile.txt");
         * try {
         * Scanner sc = new Scanner(myFileR);
         * while(sc.hasNextLine()){
         * String line = sc.nextLine();
         * System.out.println(line);
         * }
         * sc.close();
         * } catch (FileNotFoundException e) {
         * System.out.println("Unable to read this file");
         * e.printStackTrace();
         * }
         */

        // Deleting a file
        /*
         * File myFileD = new File("ExampleFile.txt");
         * if(myFileD.delete()){
         * System.out.println("Deleted: " + myFileD.getName());
         * }
         * else{
         * System.out.println("Unable to delete this file");
         * }
         */

        // Delete a folder with files in it
        /*
        * File directory = new File("Tables");
        * String[]entries = directory.list();
        * for(String s: entries){
        * File currentFile = new File(directory.getPath(),s);
        * currentFile.delete();
        * }
        * directory.delete();
        */

        // Merge content of two files (file1.txt & file2.txt) into a third file
        BufferedReader bufferedReader = new BufferedReader(new FileReader("./src/p5Files/file1.txt"));
        String line1 = bufferedReader.readLine();
        PrintWriter printWriter = new PrintWriter("./src/p5Files/file3.txt");
        while (line1 != null) {
            printWriter.println(line1);
            line1 = bufferedReader.readLine();
        }
        bufferedReader.close();

        bufferedReader = new BufferedReader(new FileReader("./src/p5Files/file2.txt"));
        String line2 = bufferedReader.readLine();
        while (line2 != null) {
            printWriter.println(line2);
            line2 = bufferedReader.readLine();
        }
        bufferedReader.close();
        printWriter.flush();
        printWriter.close();

        // Merge content of two files (file1.txt & file2.txt) into a third file, but do
        // it line by line from both files
        printWriter = new PrintWriter("./src/p5Files/file4.txt");
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader("./src/p5Files/file1.txt"));
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader("./src/p5Files/file2.txt"));
        String lineOdd = bufferedReader1.readLine();
        String lineEven = bufferedReader2.readLine();
        while (lineOdd != null || lineEven != null) {
            if (lineOdd != null) {
                printWriter.println(lineOdd);
                lineOdd = bufferedReader1.readLine();
            }
            if (lineEven != null) {
                printWriter.println(lineEven);
                lineEven = bufferedReader2.readLine();
            }
        }
        bufferedReader1.close();
        bufferedReader2.close();
        printWriter.flush();
        printWriter.close();

        // Given an input file (inputFile.txt) and a delete file (deleteFile.txt) delete
        // all the lines from the input file that are present in the delete file and
        // create a new file called output.txt from the input file.
        printWriter = new PrintWriter("./src/p5Files/outputFile.txt");
        BufferedReader inputFile = new BufferedReader(new FileReader("./src/p5Files/inputFile.txt"));
        Set<String> linesToDelete = new HashSet<>();

        String lineToDelete = inputFile.readLine();
        while (lineToDelete != null) {
            linesToDelete.add(lineToDelete);
            lineToDelete = inputFile.readLine();
        }
        inputFile.close();

        BufferedReader deleteFile = new BufferedReader(new FileReader("./src/p5Files/deleteFile.txt"));
        String line = deleteFile.readLine();
        while (line != null) {
            linesToDelete.remove(line);
            line = deleteFile.readLine();
        }
        deleteFile.close();

        BufferedReader inputFile2 = new BufferedReader(new FileReader("./src/p5Files/inputFile.txt"));
        String line3 = inputFile2.readLine();
        while (line3 != null) {
            if (linesToDelete.contains(line3)) {
                printWriter.println(line3);
            }
            line3 = inputFile2.readLine();
        }
        inputFile2.close();

        printWriter.flush();
        printWriter.close();


        // Given an input file (inputWithDups.txt) - remove the duplicates from the file and create a new file called outputWithoutDups.txt from the input file which contains only the unique values from the input file.
        printWriter = new PrintWriter("./src/p5Files/outputWithoutDups.txt");
        BufferedReader inputFile3 = new BufferedReader(new FileReader("./src/p5Files/inputWithDups.txt"));
        Set<String> lines = new LinkedHashSet<>();
        String line4 = inputFile3.readLine();
        while (line4 != null) {
            lines.add(line4);
            line4 = inputFile3.readLine();
        }
        inputFile3.close();
        for (String s : lines) {
            printWriter.println(s);
        }
        printWriter.flush();
        printWriter.close();
    }
}