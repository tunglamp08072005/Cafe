package org.example.coffeeshop;
import java.util.List;

public class Invoice {
    private int id;
    private String customerName;
    private List<MenuItem> orderItems;
    private double totalAmount;
    private boolean status;

    public Invoice(int id, String customerName, List<MenuItem> orderItems, double totalAmount, boolean status) {
        this.id = id;
        this.customerName = customerName;
        this.orderItems = orderItems;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Invoice(int id, String customerName, double totalAmount, boolean status) {
        this.id = id;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderItems = null;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<MenuItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<MenuItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isPaid() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invoice{id=" + id + ", customerName='" + customerName + '\'' +
                ", totalAmount=" + totalAmount + ", status=" + (status ? "Paid" : "Unpaid") + '}';
    }


}
