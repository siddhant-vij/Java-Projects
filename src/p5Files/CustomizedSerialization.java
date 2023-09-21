package p5Files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

class BankAccount implements Serializable {
  String name;
  transient String password;
  double balance;
  transient int accountPIN;

  public BankAccount(String name, String password, double balance, int accountPIN) {
    this.name = name;
    this.password = password;
    this.balance = balance;
    this.accountPIN = accountPIN;
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();

    String ePassword = PasswordEncryptor.encrypt(this.password);
    out.writeObject(ePassword);

    int ePIN = this.accountPIN + 1003;
    out.writeInt(ePIN);
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    
    String ePassword = (String) in.readObject();
    this.password = PasswordEncryptor.decrypt(ePassword);

    int ePIN = in.readInt();
    this.accountPIN = ePIN - 1003;
  }

  @Override
  public String toString() {
    return "BankAccount{" +
        "name='" + name + '\'' +
        ", password='" + password + '\'' +
        ", balance=" + balance +
        ", accountPIN=" + accountPIN +
        '}';
  }
}

public class CustomizedSerialization {
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    String file;
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      file = br.readLine();
    }
    BankAccount account = new BankAccount("John", "Password@1234", 1000, 1234);
    System.out.println(account);

    // Serialize the Account Object
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
      oos.writeObject(account);
    }

    // Deserialize the Account Object
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
      BankAccount account2 = (BankAccount) ois.readObject();
      System.out.println(account2);
    }
  }
}

class PasswordEncryptor {
  private static final String ALGORITHM = "AES";
  private static final String SECRET_KEY = "?TrialAndErrorKey!";

  public static String encrypt(String password) {
    try {
      SecretKeySpec keySpec = new SecretKeySpec(truncateKey(SECRET_KEY), ALGORITHM);
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, keySpec);
      byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(encrypted);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String decrypt(String encryptedPassword) {
    try {
      SecretKeySpec keySpec = new SecretKeySpec(truncateKey(SECRET_KEY), ALGORITHM);
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, keySpec);
      byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
      byte[] decrypted = cipher.doFinal(encryptedBytes);
      return new String(decrypted, StandardCharsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static byte[] truncateKey(String key) {
    byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
    return Arrays.copyOfRange(keyBytes, 0, 16); // Truncate to 16 bytes (128 bits)
  }
}
