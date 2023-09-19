package p6Threads;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ThreadsAndBytes {
  public static Map<String, Integer> resultMap = new HashMap<String, Integer>();

  public static class ReadThread extends Thread {
    String fileName;

    public ReadThread(String fileName) {
      this.fileName = fileName;
    }

    @Override
    public void run() {
      Map<Integer, Integer> freqMap = new HashMap<>();
      try (FileInputStream fis = new FileInputStream(fileName)) {
        while (fis.available() > 0) {
          int byteRead = fis.read();
          freqMap.put(byteRead, freqMap.getOrDefault(byteRead, 0) + 1);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      int maxFreq = Integer.MIN_VALUE;
      for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
        if (entry.getValue() > maxFreq) {
          maxFreq = entry.getValue();
        }
      }
      for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
        if (entry.getValue() == maxFreq) {
          resultMap.put(fileName, entry.getKey());
        }
      }
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String line;
    while (!((line = br.readLine()).equals("exit"))) {
      ReadThread th = new ReadThread(line);
      th.start();
    }
    System.out.println(resultMap);
  }
}
