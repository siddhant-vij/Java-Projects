// Banking Application

package p0Projects;

import java.util.ArrayList;
import java.util.Scanner;

class Bank {
  private String name;
  private ArrayList<Branch> branches;

  public Bank(String name) {
    this.name = name;
    this.branches = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public boolean addBranch(String branch) {
    if (findBranch(branch) == null) {
      branches.add(new Branch(branch));
      return true;
    }
    return false;
  }

  public boolean addCustomer(String branch, String customer, double transaction) {
    Branch test = findBranch(branch);
    if (test != null) {
      return test.newCustomer(customer, transaction);
    }
    return false;
  }

  public boolean addCustomerTransaction(String branch, String customer, double transaction) {
    Branch test = findBranch(branch);
    if (test != null) {
      return test.addCustomerTransaction(customer, transaction);
    }
    return false;
  }

  private Branch findBranch(String branch) {
    ArrayList<String> branchNames = new ArrayList<>();
    for (int i = 0; i < branches.size(); i++) {
      branchNames.add(branches.get(i).getName());
    }
    return branchNames.contains(branch) ? branches.get(branchNames.indexOf(branch)) : null;
  }

  public boolean listCustomers(String branch, boolean print) {
    Branch tempBranch = findBranch(branch);
    if (tempBranch != null && print) {
      System.out.println("Customer details for branch " + tempBranch.getName());
      for (int i = 0; i < tempBranch.getCustomers().size(); i++) {
        System.out.println("Customer: " + tempBranch.getCustomers().get(i).getName() + "[" + (i + 1) + "]");
        System.out.println("Transactions");
        for (int j = 0; j < tempBranch.getCustomers().get(i).getTransactions().size(); j++) {
          System.out.println("[" + (j + 1) + "]  Amount " + tempBranch.getCustomers().get(i).getTransactions().get(j));
        }
      }
      return true;
    } else if (tempBranch != null) {
      System.out.println("Customer details for branch " + tempBranch.getName());
      for (int i = 0; i < tempBranch.getCustomers().size(); i++) {
        System.out.println("Customer: " + tempBranch.getCustomers().get(i).getName() + "[" + (i + 1) + "]");
      }
      return true;
    } else {
      return false;
    }
  }
}

class Branch {
  private String name;
  private ArrayList<Customer> customers;

  public Branch(String name) {
    this.name = name;
    this.customers = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public ArrayList<Customer> getCustomers() {
    return customers;
  }

  public boolean newCustomer(String name, double transaction) {
    if (findCustomer(name) == null) {
      customers.add(new Customer(name, transaction));
      return true;
    }
    return false;
  }

  public boolean addCustomerTransaction(String name, double transaction) {
    if (findCustomer(name) != null) {
      customers.get(customers.indexOf(findCustomer(name))).addTransaction(transaction);
      return true;
    }
    return false;
  }

  private Customer findCustomer(String name) {
    ArrayList<String> names = new ArrayList<>();
    for (int i = 0; i < customers.size(); i++) {
      names.add(customers.get(i).getName());
    }
    return names.contains(name) ? customers.get(names.indexOf(name)) : null;
  }
}

class Customer {
  private String name;
  private ArrayList<Double> transactions;

  public Customer(String name, double transaction) {
    this.name = name;
    this.transactions = new ArrayList<>();
    this.transactions.add(transaction);
  }

  public String getName() {
    return name;
  }

  public ArrayList<Double> getTransactions() {
    return transactions;
  }

  public void addTransaction(double transaction) {
    this.transactions.add(transaction);
  }
}

public class BankingApplication {
  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      Bank bank = new Bank("National Australia Bank");

      bank.addBranch("Adelaide");

      bank.addCustomer("Adelaide", "Tim", 50.05);
      bank.addCustomer("Adelaide", "Mike", 175.34);
      bank.addCustomer("Adelaide", "Percy", 220.12);

      bank.addCustomerTransaction("Adelaide", "Tim", 44.22);
      bank.addCustomerTransaction("Adelaide", "Tim", 12.44);
      bank.addCustomerTransaction("Adelaide", "Mike", 1.65);
      System.out.println(bank.getName());
      bank.listCustomers("Adelaide", true);
    }
  }
}
