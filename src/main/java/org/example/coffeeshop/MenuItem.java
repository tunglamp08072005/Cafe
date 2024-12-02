package org.example.coffeeshop;

public class MenuItem {
    private int id;
    private String name;
    private String type;
    private double price;
    private String description;
    private boolean availability;

    public MenuItem(int id, String name, String type, double price, String description, boolean availability) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.availability = availability;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "MenuItem{id=" + id + ", name='" + name + '\'' +
                ", type='" + type + '\'' + ", price=" + price +
                ", description='" + description + '\'' +
                ", availability=" + (availability ? "Available" : "Not Available") + '}';
    }
}
