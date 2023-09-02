package p0Projects;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

interface BankAccount {
  void withdraw(double amount);

  void deposit(double amount);

  double getBalance();
}

class SavingsBankAccount implements BankAccount {
  private double balance;

  public SavingsBankAccount(double initialBalance) {
    this.balance = initialBalance;
  }

  @Override
  public synchronized void deposit(double amount) {
    balance += amount;
    System.out.println(Thread.currentThread().getName() + " deposited " + amount + ". New balance: " + balance);
  }

  @Override
  public synchronized void withdraw(double amount) {
    if (balance >= amount) {
      balance -= amount;
      System.out.println(Thread.currentThread().getName() + " withdrew " + amount + ". New balance: " + balance);
    } else {
      System.out.println("Insufficient funds for " + Thread.currentThread().getName() + " to withdraw " + amount);
    }
  }

  @Override
  public synchronized double getBalance() {
    return balance;
  }
}

class JointBankAccount implements BankAccount {
  private double balance;
  private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

  public JointBankAccount(double initialBalance) {
    this.balance = initialBalance;
  }

  @Override
  public void deposit(double amount) {
    rwLock.writeLock().lock();
    try {
      balance += amount;
      System.out.println(Thread.currentThread().getName() + " deposited " + amount + ". New balance: " + balance);
    } finally {
      rwLock.writeLock().unlock();
    }
  }

  @Override
  public void withdraw(double amount) {
    rwLock.writeLock().lock();
    try {
      if (balance >= amount) {
        balance -= amount;
        System.out.println(Thread.currentThread().getName() + " withdrew " + amount + ". New balance: " + balance);
      } else {
        System.out.println("Insufficient funds for " + Thread.currentThread().getName() + " to withdraw " + amount);
      }
    } finally {
      rwLock.writeLock().unlock();
    }
  }

  @Override
  public double getBalance() {
    rwLock.readLock().lock();
    try {
      return balance;
    } finally {
      rwLock.readLock().unlock();
    }
  }
}

public class TestingBankAccounts {
  public static void main(String[] args) {
    // Testing SavingsBankAccount with a single thread
    BankAccount savingsAccount = new SavingsBankAccount(1000);
    new Thread(() -> {
      savingsAccount.deposit(500);
      savingsAccount.withdraw(200);
      System.out.println("Savings Account balance: " + savingsAccount.getBalance());
    }, "SavingsThread").start();

    // Testing JointBankAccount with a thread pool of 10 threads
    BankAccount jointAccount = new JointBankAccount(1000);
    ExecutorService executor = Executors.newFixedThreadPool(10);

    for (int i = 0; i < 10; i++) {
      int finalI = i;
      executor.submit(() -> {
        if (finalI % 3 == 0) {
          jointAccount.deposit(500);
        } else if (finalI % 3 == 1) {
          jointAccount.withdraw(200);
        } else {
          System.out.println(Thread.currentThread().getName() + " balance: " + jointAccount.getBalance());
        }
      });
    }

    executor.shutdown();
  }
}
