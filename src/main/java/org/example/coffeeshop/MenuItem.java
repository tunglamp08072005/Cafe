package org.example.coffeeshop;

/** Represents a menu item in the coffee shop. */
public class MenuItem {
    private int id;
    private String name;
    private int price;
    private String description;
    private boolean isAvailable;

    // Constructor
    public MenuItem(int id, String name, int price, String description, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isAvailable = isAvailable;
    }

    public MenuItem(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;  // Lưu giá dưới dạng int
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {  // Thêm phương thức setId
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "MenuItem [id=" + id + ", name=" + name + ", price=" + price +
                ", description=" + description + ", available=" + isAvailable + "]";
    }
}
