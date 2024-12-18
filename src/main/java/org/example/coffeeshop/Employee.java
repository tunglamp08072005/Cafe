package org.example.coffeeshop;

/** Represents an employee in the coffee shop. */
public class Employee {
    private final int id;
    private final String name;
    private final String position;
    private final int salary; // Sửa kiểu dữ liệu salary thành int

    // Constructor
    public Employee(int id, String name, String position, int salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public int getId() { // Thêm phương thức getId()
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getSalary() { // Sửa kiểu trả về thành int
        return salary;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", position=" + position +
                ", salary=" + salary + "]"; // Hiển thị salary dưới dạng số nguyên
    }
}
