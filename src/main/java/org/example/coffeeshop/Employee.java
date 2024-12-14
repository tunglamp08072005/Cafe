package org.example.coffeeshop;

/** Represents an employee in the coffee shop. */
public class Employee {
    private final int id;
    private final String name;
    private final String position;
    private final double salary;

    // Constructor
    public Employee(int id, String name, String position, double salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", position=" + position +
                ", salary=" + salary + "]";
    }
}
