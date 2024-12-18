package org.example.coffeeshop;

import java.util.ArrayList;
import java.util.List;

/** Represents an invoice in the coffee shop. */
public class Invoice {
    private final int id;
    private final String customerName;
    private List<MenuItem> orderList;
    private int totalAmount;
    private boolean isPaid;

    public Invoice(int id, String customerName, List<MenuItem> orderList, boolean isPaid) {
        this.id = id;
        this.customerName = customerName;
        this.orderList = orderList != null ? orderList : new ArrayList<>();
        this.totalAmount = (int) (orderList != null ? calculateTotal() : 0.0);
        this.isPaid = isPaid;
    }

    private double calculateTotal() {
        if (orderList == null || orderList.isEmpty()) {
            return 0.0; // Trả về 0 nếu không có món ăn nào
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
    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }


    @Override
    public String toString() {
        return "Invoice [id=" + id + ", customerName=" + customerName +
                ", totalAmount=" + totalAmount + ", isPaid=" + isPaid + "]";
    }

    public void setOrderList(List<MenuItem> orderList) {
        this.orderList = orderList;
        this.totalAmount = (int) calculateTotal();  // Tính lại tổng số tiền
    }
}
