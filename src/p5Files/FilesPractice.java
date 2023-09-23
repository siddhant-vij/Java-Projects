package p5Files;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
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

        // Code to write to a file - overwrites every time (without second parameter
        // set to true - default).
        // Second parameter to FileWriter. If set to true, then the data will be
        // written to the end of the file.
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

        // Merge content of two files (file1.txt & file2.txt) into a third file, but
        // do it line by line from both files
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

        // Given an input file (inputFile.txt) and a delete file (deleteFile.txt)
        // delete all the lines from the input file that are present in the delete
        // file and create a new file called output.txt from the input file.
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

        // Given an input file (inputWithDups.txt) - remove the duplicates from the
        // file and create a new file called outputWithoutDups.txt from the input
        // file which contains only the unique values from the input file.
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

        // Use a PrintStream to reverse the output.
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outputStream);
        try (Scanner scanner = new Scanner(System.in)) {
            stream.print(scanner.nextLine());
            String result = outputStream.toString();
            outputStream.reset();
            StringBuilder stringBuilder = new StringBuilder(result);
            String reverse = stringBuilder.reverse().toString();
            stream.print(reverse);
            System.out.println(outputStream);
        }

        // Read a file (Input.txt) and create 3 output files
        // OpFile1.txt - contains every other character from the input file
        // OpFile2.txt - contains all the characters from the input file
        // OpFile3.txt - contains last quarter of characters from the input file
        try (FileReader inputFile4 = new FileReader("./src/p5Files/Input.txt");
                BufferedReader bufferedReader4 = new BufferedReader(inputFile4);
                FileWriter fileWriter1 = new FileWriter("./src/p5Files/OpFile1.txt");
                FileWriter fileWriter2 = new FileWriter("./src/p5Files/OpFile2.txt");
                FileWriter fileWriter3 = new FileWriter("./src/p5Files/OpFile3.txt")) {
            int fileLength = 0;
            int nextRead;
            StringBuilder sb = new StringBuilder();
            while ((nextRead = bufferedReader4.read()) != -1) {
                fileWriter2.write(nextRead);
                if (fileLength % 2 == 0) {
                    fileWriter1.write(nextRead);
                }
                fileLength++;
                sb.append(Character.toString((char) nextRead));
            }
            fileWriter3.write(sb.toString(), 3 * fileLength / 4, fileLength - 3 * fileLength / 4);
        }

        // Reads from the console the path to a file, reads lines from the file, and
        // displays them on the screen.
        Scanner sc = null;
        BufferedReader reader = null;
        try {
            sc = new Scanner(System.in);
            reader = Files.newBufferedReader(Path.of(sc.nextLine()));
            String lineX;
            while ((lineX = reader.readLine()) != null) {
                System.out.println(lineX);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sc != null)
                sc.close();
            if (reader != null)
                reader.close();
        }

        // Read the path to file1 and the path to file2 from the console.
        // Write all the bytes from file1 to file2,
        // but in doing so swap them according to this rule: swap the first with the
        // second, the third with the fourth, and so on.
        // If the last byte in file1 is odd, then write it to file2 as is.
        InputStream fileInputStream = null;
        OutputStream fileOutputStream = null;
        try {
            sc = new Scanner(System.in);
            fileInputStream = Files.newInputStream(Paths.get(sc.nextLine()));
            fileOutputStream = Files.newOutputStream(Paths.get(sc.nextLine()));
            int fileSize = fileInputStream.available();
            byte[] buffer = new byte[fileSize];
            fileInputStream.read(buffer);
            int bufferCounter = 0;
            while (bufferCounter < fileSize - 1) {
                byte byte1 = buffer[bufferCounter];
                byte byte2 = buffer[bufferCounter + 1];
                buffer[bufferCounter] = byte2;
                buffer[bufferCounter + 1] = byte1;
                fileOutputStream.write(buffer, bufferCounter, 2);
                bufferCounter += 2;
            }
            fileOutputStream.write(buffer, bufferCounter, fileSize - bufferCounter);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sc != null)
                sc.close();
            if (fileInputStream != null)
                fileInputStream.close();
            if (fileOutputStream != null)
                fileOutputStream.close();
        }

        // Read the name of a text file from the console
        // Then read characters from this file (use the readAllLines(Path) method of
        // the Files class) and displays everything except periods, commas, and spaces.
        try {
            sc = new Scanner(System.in);
            String fileName = sc.nextLine();
            List<String> linesX = Files.readAllLines(Paths.get(fileName));
            for (String lineX : linesX) {
                System.out.println(lineX.replaceAll("[,. ]", ""));
            }
        } finally {
            if (sc != null)
                sc.close();
        }

        // Read a file name from the console. Read a set of numbers from the file.
        // Display only the even numbers, sorted in ascending order.
        BufferedReader br = null;
        List<Integer> list = new ArrayList<>();
        try {
            sc = new Scanner(System.in);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(sc.nextLine())));
            String lineY;
            while ((lineY = br.readLine()) != null) {
                int num = Integer.parseInt(lineY);
                if (num % 2 == 0)
                    list.add(num);
            }
        } finally {
            if (sc != null)
                sc.close();
            if (br != null)
                br.close();
        }
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));
    }
}