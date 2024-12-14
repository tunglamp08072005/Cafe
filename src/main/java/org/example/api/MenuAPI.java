package org.example.api;

import org.example.coffeeshop.MenuItem;
import java.util.List;
import java.util.ArrayList;

/** Represents the menu API that interacts with an external source for drink information */
public class MenuAPI {
    // Đây là một phương thức giả lập để lấy thông tin đồ uống từ API bên ngoài
    public List<MenuItem> fetchSpecialMenuItems() {
        // Trong thực tế, bạn sẽ sử dụng HTTP client để gọi API bên ngoài và lấy dữ liệu
        // Ví dụ, đây là dữ liệu giả lập
        List<MenuItem> specialMenuItems = new ArrayList<>();
        specialMenuItems.add(new MenuItem(1, "Cà phê Espresso", 30.0, "Cà phê đậm đà", true));
        specialMenuItems.add(new MenuItem(2, "Cà phê Latte", 40.0, "Cà phê sữa béo", true));
        specialMenuItems.add(new MenuItem(3, "Cà phê Capuccino", 45.0, "Cà phê sữa bọt mịn", true));
        return specialMenuItems;
    }

    // Phương thức để cập nhật giá đồ uống từ API bên ngoài (Giả lập)
    public void updateDrinkPrice(MenuItem item, double newPrice) {
        // Lấy thông tin từ API và cập nhật giá đồ uống
        item.setPrice(newPrice);
        System.out.println("Đã cập nhật giá đồ uống " + item.getName() + " thành " + newPrice);
    }
}
