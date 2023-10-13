package p6Threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SequentialFileOutput {
  public static String firstFileName;
    public static String secondFileName;

    static {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            firstFileName = br.readLine();
            secondFileName = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        systemOutPrintln(firstFileName);
        systemOutPrintln(secondFileName);
    }

    public static void systemOutPrintln(String fileName) throws InterruptedException {
        ReadFileInterface f = new ReadFileThread();
        f.setFileName(fileName);
        f.start();
        f.join();
        System.out.println(f.getFileContents());
    }

    public interface ReadFileInterface {
        void setFileName(String fullFileName);
        String getFileContents();
        void join() throws InterruptedException;
        void start();
    }

    public static class ReadFileThread extends Thread implements ReadFileInterface {
        private String fullFileName;
        String fileContent = "";

        @Override
        public void setFileName(String fullFileName) {
            this.fullFileName = fullFileName;
        }

        @Override
        public String getFileContents() {
            return fileContent;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader(this.fullFileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    fileContent += line + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }  
}
