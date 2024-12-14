package org.example.coffeeshop;

import java.util.List;

/** Represents an invoice in the coffee shop. */
public class Invoice {
    private final int id;
    private final String customerName;
    private final List<MenuItem> orderList;
    private double totalAmount;
    private boolean isPaid;

    // Constructor
    public Invoice(int id, String customerName, List<MenuItem> orderList, boolean isPaid) {
        this.id = id;
        this.customerName = customerName;
        this.orderList = orderList;
        this.totalAmount = calculateTotal();
        this.isPaid = isPaid;
    }

    // Calculate the total amount
    private double calculateTotal() {
        return orderList.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<MenuItem> getOrderList() {
        return orderList;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }


    @Override
    public String toString() {
        return "Invoice [id=" + id + ", customerName=" + customerName +
                ", totalAmount=" + totalAmount + ", isPaid=" + isPaid + "]";
    }
}
