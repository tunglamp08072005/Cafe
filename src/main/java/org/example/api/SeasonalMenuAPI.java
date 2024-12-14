package org.example.api;

import org.example.coffeeshop.MenuItem;
import java.util.List;
import java.util.ArrayList;

/** Represents an API for suggesting seasonal menu items */
public class SeasonalMenuAPI {
    // Phương thức giả lập để lấy các món đồ uống theo mùa
    public List<MenuItem> fetchSeasonalMenuItems(String season) {
        List<MenuItem> seasonalMenuItems = new ArrayList<>();
        if ("summer".equalsIgnoreCase(season)) {
            seasonalMenuItems.add(new MenuItem(4, "Sinh tố dưa hấu", 50.0, "Sinh tố mát lạnh", true));
            seasonalMenuItems.add(new MenuItem(5, "Cà phê đá xay", 55.0, "Cà phê đá xay mát lạnh", true));
        } else if ("winter".equalsIgnoreCase(season)) {
            seasonalMenuItems.add(new MenuItem(6, "Cà phê nóng", 40.0, "Cà phê ấm nóng", true));
            seasonalMenuItems.add(new MenuItem(7, "Trà gừng mật ong", 35.0, "Trà ấm cho mùa đông", true));
        } else {
            seasonalMenuItems.add(new MenuItem(8, "Cà phê Americano", 30.0, "Cà phê đen không đường", true));
        }
        return seasonalMenuItems;
    }
}
