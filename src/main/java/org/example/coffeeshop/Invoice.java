package org.example.coffeeshop;

import java.util.ArrayList;
import java.util.List;

/** Represents an invoice in the coffee shop. */
public class Invoice {
    private final int id;
    private final String customerName;
    private final List<MenuItem> orderList;
    private double totalAmount;
    private boolean isPaid;

    public Invoice(int id, String customerName, List<MenuItem> orderList, boolean isPaid) {
        this.id = id;
        this.customerName = customerName;
        this.orderList = orderList != null ? orderList : new ArrayList<>();
        this.totalAmount = calculateTotal();
        this.isPaid = isPaid;
    }

    private double calculateTotal() {
        if (orderList == null) {
            return 0.0; // Hoặc giá trị mặc định nếu orderList là null
        }
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
