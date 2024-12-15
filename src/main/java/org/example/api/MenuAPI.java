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
        specialMenuItems.add(new MenuItem(1, "Espresso", 25000, "Cà phê đậm đặc kiểu Ý, thơm ngon.", true));
        specialMenuItems.add(new MenuItem(2, "Double Espresso", 35000, "Gấp đôi espresso cho người yêu cà phê mạnh.", true));
        specialMenuItems.add(new MenuItem(3, "Americano (Nóng/Đá)", 30000, "Cà phê loãng pha thêm nước, thanh mát.", true));
        specialMenuItems.add(new MenuItem(4, "Cappuccino (Nóng/Đá)", 35000, "Kết hợp giữa cà phê espresso và sữa.", true));
        specialMenuItems.add(new MenuItem(5, "Latte (Nóng/Đá)", 40000, "Cà phê pha sữa mềm mại, hương vị nhẹ nhàng.", true));
        specialMenuItems.add(new MenuItem(6, "Mocha (Nóng/Đá)", 45000, "Cà phê espresso hòa quyện với sô cô la.", true));
        specialMenuItems.add(new MenuItem(7, "Kem 1 viên", 20000, "Một viên kem hương vị tự chọn, ngọt ngào.", true));
        specialMenuItems.add(new MenuItem(8, "Kem Hồ Thiên Nga (2 viên)", 40000, "Hai viên kem trang trí độc đáo như thiên nga.", true));
        specialMenuItems.add(new MenuItem(9, "Kem Thuyền Tình Yêu (3 viên)", 60000, "Ba viên kem trên thuyền trang trí lãng mạn.", true));
        specialMenuItems.add(new MenuItem(10, "Kem Trái Dừa (3 viên)", 70000, "Kem ba viên trong trái dừa, hương vị mát lạnh.", true));
        specialMenuItems.add(new MenuItem(11, "Kem Hộp Scoope 240ml", 50000, "Kem hộp 240ml, tiện lợi mang đi.", true));
        specialMenuItems.add(new MenuItem(12, "Cà Phê Đen", 20000, "Cà phê phin đen truyền thống, đậm đà.", true));
        specialMenuItems.add(new MenuItem(13, "Cà Phê Đen Pha Máy", 25000, "Cà phê đen pha máy, giữ trọn hương vị nguyên bản.", true));
        specialMenuItems.add(new MenuItem(14, "Cà Phê Sữa", 25000, "Cà phê phin hòa quyện với sữa đặc ngọt ngào.", true));
        specialMenuItems.add(new MenuItem(15, "Cà Phê Sữa Pha Máy", 30000, "Cà phê sữa pha máy, lớp bọt sữa mịn màng.", true));
        specialMenuItems.add(new MenuItem(16, "Bạc Sỉu", 25000, "Sữa đặc hòa chút cà phê, vị nhẹ nhàng, dễ uống.", true));
        specialMenuItems.add(new MenuItem(17, "Cà Phê Cốt Dừa (Đá)", 35000, "Cà phê đen kết hợp cốt dừa mát lạnh, béo ngậy.", true));
        specialMenuItems.add(new MenuItem(18, "Cà Phê Trứng", 40000, "Cà phê đen với lớp kem trứng bông mịn, đặc biệt.", true));
        specialMenuItems.add(new MenuItem(19, "Hồng Trà (Nóng/Đá)", 25000, "Trà đen thơm dịu, uống nóng hoặc thêm đá mát.", true));
        specialMenuItems.add(new MenuItem(20, "Hồng Trà Sữa (Nóng/Đá)", 30000, "Hồng trà kết hợp sữa đặc, béo ngậy.", true));
        specialMenuItems.add(new MenuItem(21, "Trà Hoa Quả Nhiệt Đới (Đá)", 40000, "Trà đen pha hoa quả nhiệt đới, tươi mát.", true));
        specialMenuItems.add(new MenuItem(22, "Trà Ổi Hồng (Đá)", 35000, "Trà hòa vị ổi hồng thanh mát, hấp dẫn.", true));
        specialMenuItems.add(new MenuItem(23, "Trà Đào Cam Sả (Đá)", 40000, "Trà đào kết hợp cam và sả, vị chua ngọt hài hòa.", true));
        specialMenuItems.add(new MenuItem(24, "Trà Xanh Sữa", 30000, "Trà xanh kết hợp sữa ngọt nhẹ, thanh tao.", true));
        specialMenuItems.add(new MenuItem(25, "Trà Sữa Thạch Trà", 35000, "Trà sữa kèm thạch dai dai, ngọt ngào.", true));
        specialMenuItems.add(new MenuItem(26, "Trà Sữa Sô-Cô-La Bạc Hà", 40000, "Trà sữa vị sô cô la và bạc hà, thơm mát đặc trưng.", true));
        specialMenuItems.add(new MenuItem(27, "Sinh Tố Xoài", 40000, "Sinh tố xoài tươi, ngọt mát và bổ dưỡng.", true));
        specialMenuItems.add(new MenuItem(28, "Sinh Tố Dừa Xiêm", 45000, "Sinh tố từ dừa xiêm tươi, vị béo ngậy tự nhiên.", true));
        specialMenuItems.add(new MenuItem(29, "Sinh Tố Chanh Tuyết", 35000, "Sinh tố vị chanh mát lạnh, thanh tao.", true));
        specialMenuItems.add(new MenuItem(30, "Hoa Quả Dầm Sữa Chua", 50000, "Hoa quả tươi dầm cùng sữa chua, cực kỳ hấp dẫn.", true));
        specialMenuItems.add(new MenuItem(31, "Matcha Đá Xay", 50000, "Matcha nguyên chất xay cùng sữa, thơm béo.", true));
        specialMenuItems.add(new MenuItem(32, "Bạc Hà Sô Cô La Đá xay", 55000, "Hương bạc hà hòa quyện với sô cô la xay mịn.", true));
        specialMenuItems.add(new MenuItem(33, "Sô Cô La Đá Xay", 50000, "Sô cô la đậm đà, thơm ngậy với lớp kem mịn.", true));
        specialMenuItems.add(new MenuItem(34, "Cookies & Cream Đá Xay", 55000, "Bánh quy giòn xay cùng kem tươi, cực hấp dẫn.", true));
        specialMenuItems.add(new MenuItem(35, "Chanh Tươi", 30000, "Nước chanh tươi mát lạnh, vị chua ngọt tự nhiên.", true));
        specialMenuItems.add(new MenuItem(36, "Chanh Leo", 35000, "Nước chanh leo đậm vị, bổ sung năng lượng.", true));
        specialMenuItems.add(new MenuItem(37, "Ổi Ép", 35000, "Nước ép ổi nguyên chất, thơm ngon bổ dưỡng.", true));
        specialMenuItems.add(new MenuItem(38, "Dưa Hấu Ép", 30000, "Nước ép dưa hấu mát lạnh, giải nhiệt.", true));
        specialMenuItems.add(new MenuItem(39, "Dứa Ép", 35000, "Nước ép dứa tự nhiên, thơm và thanh mát.", true));
        specialMenuItems.add(new MenuItem(40, "Dừa Xiêm", 40000, "Nước dừa tươi, ngọt mát tự nhiên.", true));
        specialMenuItems.add(new MenuItem(41, "Cam Vắt", 35000, "Cam tươi nguyên chất, giàu vitamin C.", true));
        specialMenuItems.add(new MenuItem(42, "Táo Ép", 40000, "Nước ép táo ngọt dịu, thanh mát.", true));
        specialMenuItems.add(new MenuItem(43, "Nước Bưởi Ép", 40000, "Nước bưởi tươi, hỗ trợ sức khỏe và thanh lọc cơ thể.", true));
        return specialMenuItems;
    }

    // Phương thức để cập nhật giá đồ uống từ API bên ngoài (Giả lập)
    public void updateDrinkPrice(MenuItem item, double newPrice) {
        // Lấy thông tin từ API và cập nhật giá đồ uống
        item.setPrice(newPrice);
        System.out.println("Đã cập nhật giá đồ uống " + item.getName() + " thành " + newPrice);
    }
}
