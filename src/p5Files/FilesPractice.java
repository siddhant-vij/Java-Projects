package p5Files;

// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.Scanner;

public class FilesPractice {
    public static void main(String[] args) {
        // Code to create a folder, if it doesn't exist
        /*File directory = new File("Tables");
        if (!directory.exists()){
            directory.mkdir();
        }*/

        // Code to create a new file
        /*File myFileC = new File("ExampleFile.txt");
        try {
            myFileC.createNewFile();
        } catch (IOException e) {
            System.out.println("Unable to create this file");
            e.printStackTrace();
        }*/

        //Code to write to a file - overwrites every time (without second parameter set to true - default).
        //Second parameter to FileWriter. If set to true, then the data will be written to the end of the file.
        /*try {
            FileWriter fileWriterU = new FileWriter("ExampleFile.txt", true);
            fileWriterU.write("This is our first file from this java course\nOkay now bye");
            fileWriterU.write("\nAre you sure?");
            fileWriterU.close();
        } catch (IOException e) {
            System.out.println("Unable to write to this file");
            e.printStackTrace();
        }*/

        // Reading a file
        /*File myFileR = new File("ExampleFile.txt");
        try {
            Scanner sc = new Scanner(myFileR);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                System.out.println(line);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to read this file");
            e.printStackTrace();
        }*/

        // Deleting a file
        /*File myFileD = new File("ExampleFile.txt");
        if(myFileD.delete()){
            System.out.println("Deleted: " + myFileD.getName());
        }
        else{
            System.out.println("Unable to delete this file");
        }*/

        //Delete a folder with files in it
        /*File directory = new File("Tables");
        String[]entries = directory.list();
        for(String s: entries){
            File currentFile = new File(directory.getPath(),s);
            currentFile.delete();
        }
        directory.delete();*/
    }
}