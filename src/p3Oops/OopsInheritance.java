package p3Oops;

// Take care to create - toString(), hashCode() & equals() method implementation for classes

class Employee{
    private final int salary;
    private final String name;

    public Employee(int salary, String name){
        this.salary = salary;
        this.name = name;
    }

    public int getSalary(){ return salary; }

    public String getName(){ return name; }
}
class Manager extends Employee{
    private final int bonus;
    public int getBonus() {
        return bonus;
    }
    public Manager(int salary, String name, int bonus){
        super(salary,name);
        this.bonus = bonus;
    }
}

public class OopsInheritance {
    public static void main(String[] args) {
        Employee emp = new Employee(265000,"Test Employee");
        System.out.println("Employee Name: " + emp.getName());
        System.out.println("Total Employee Salary: " + emp.getSalary());
        System.out.println();
        Manager man = new Manager(350000,"Test Manager",60000);
        System.out.println("Manager Name: " + man.getName());
        System.out.println("Total Manager Salary: " + (man.getSalary()+man.getBonus()));

    }
}