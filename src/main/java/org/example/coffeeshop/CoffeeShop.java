package org.example.coffeeshop;

import java.util.List;
import java.util.ArrayList;

public class CoffeeShop {
    private List<MenuItem> menu;
    private List<Employee> employees;
    private List<Invoice> invoices;

    public void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    public void removeMenuItem(int id) {
        menu.removeIf(item -> item.getId() == id);
    }

    public void updateMenuItem(int id, String name, String type, double price, String description, boolean availability) {
        for (MenuItem item : menu) {
            if (item.getId() == id) {
                item.setName(name);
                item.setType(type);
                item.setPrice(price);
                item.setDescription(description);
                item.setAvailability(availability);
                break;
            }
        }
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void updateEmployee(int id, String name, String position, double salary){
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employee.setName(name);
                employee.setPosition(position);
                employee.setSalary(salary);
                break;
            }
        }
    }

    public void removeEmployee(int id){
        employees.removeIf(employee -> employee.getId() == id);
    }

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
    }

    public void updateInvoiceStatus(int id, boolean status){
        for (Invoice invoice : invoices) {
            if (invoice.getId() == id) {
                invoice.setStatus(status);
                break;
            }
        }
    }

    public List<Invoice> getInvoices(){
        return new ArrayList<>(invoices);
    }

    public MenuItem findMenuItemByName(String name){
        for (MenuItem item : menu) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public List<MenuItem> findMenuItemsByType(String type){
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : menu) {
            if (item.getType().equalsIgnoreCase(type)) {
                result.add(item);
            }
        }
        return result;
    }

    public Employee findEmployeeById(int id){
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "CoffeeShop{" +
                "menu=" + menu +
                ", employees=" + employees +
                ", invoices=" + invoices +
                '}';
    }

}

