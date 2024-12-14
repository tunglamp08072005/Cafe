package org.example.api;

import java.util.ArrayList;
import java.util.List;

public class CoffeeShopAPI {
    private static final List<String[]> MENU_DATA = new ArrayList<>();

    static {
        // Cà phê Ý
        MENU_DATA.add(new String[]{"Espresso", "Cà phê Ý", "25000", "Cà phê đậm đặc kiểu Ý, thơm ngon."});
        MENU_DATA.add(new String[]{"Double Espresso", "Cà phê Ý", "35000", "Gấp đôi espresso cho người yêu cà phê mạnh."});
        MENU_DATA.add(new String[]{"Americano (Nóng/Đá)", "Cà phê Ý", "30000", "Cà phê loãng pha thêm nước, thanh mát."});
        MENU_DATA.add(new String[]{"Cappuccino (Nóng/Đá)", "Cà phê Ý", "35000", "Kết hợp giữa cà phê espresso và sữa."});
        MENU_DATA.add(new String[]{"Latte (Nóng/Đá)", "Cà phê Ý", "40000", "Cà phê pha sữa mềm mại, hương vị nhẹ nhàng."});
        MENU_DATA.add(new String[]{"Mocha (Nóng/Đá)", "Cà phê Ý", "45000", "Cà phê espresso hòa quyện với sô cô la."});

        // Kem Scoops
        MENU_DATA.add(new String[]{"Kem 1 viên", "Kem Scoops", "20000", "Một viên kem hương vị tự chọn, ngọt ngào."});
        MENU_DATA.add(new String[]{"Kem Hồ Thiên Nga (2 viên)", "Kem Scoops", "40000", "Hai viên kem trang trí độc đáo như thiên nga."});
        MENU_DATA.add(new String[]{"Kem Thuyền Tình Yêu (3 viên)", "Kem Scoops", "60000", "Ba viên kem trên thuyền trang trí lãng mạn."});
        MENU_DATA.add(new String[]{"Kem Trái Dừa (3 viên)", "Kem Scoops", "70000", "Kem ba viên trong trái dừa, hương vị mát lạnh."});
        MENU_DATA.add(new String[]{"Kem Hộp Scoope 240ml", "Kem Scoops", "50000", "Kem hộp 240ml, tiện lợi mang đi."});

        // Cà phê truyền thống
        MENU_DATA.add(new String[]{"Cà Phê Đen", "Cà phê truyền thống", "20000", "Cà phê phin đen truyền thống, đậm đà."});
        MENU_DATA.add(new String[]{"Cà Phê Đen Pha Máy", "Cà phê truyền thống", "25000", "Cà phê đen pha máy, giữ trọn hương vị nguyên bản."});
        MENU_DATA.add(new String[]{"Cà Phê Sữa", "Cà phê truyền thống", "25000", "Cà phê phin hòa quyện với sữa đặc ngọt ngào."});
        MENU_DATA.add(new String[]{"Cà Phê Sữa Pha Máy", "Cà phê truyền thống", "30000", "Cà phê sữa pha máy, lớp bọt sữa mịn màng."});
        MENU_DATA.add(new String[]{"Bạc Sỉu", "Cà phê truyền thống", "25000", "Sữa đặc hòa chút cà phê, vị nhẹ nhàng, dễ uống."});
        MENU_DATA.add(new String[]{"Cà Phê Cốt Dừa (Đá)", "Cà phê truyền thống", "35000", "Cà phê đen kết hợp cốt dừa mát lạnh, béo ngậy."});
        MENU_DATA.add(new String[]{"Cà Phê Trứng", "Cà phê truyền thống", "40000", "Cà phê đen với lớp kem trứng bông mịn, đặc biệt."});

        // Trà
        MENU_DATA.add(new String[]{"Hồng Trà (Nóng/Đá)", "Trà", "25000", "Trà đen thơm dịu, uống nóng hoặc thêm đá mát."});
        MENU_DATA.add(new String[]{"Hồng Trà Sữa (Nóng/Đá)", "Trà", "30000", "Hồng trà kết hợp sữa đặc, béo ngậy."});
        MENU_DATA.add(new String[]{"Trà Hoa Qủa Nhiệt Đới (Đá)", "Trà", "40000", "Trà đen pha hoa quả nhiệt đới, tươi mát."});
        MENU_DATA.add(new String[]{"Trà Ổi Hồng (Đá)", "Trà", "35000", "Trà hòa vị ổi hồng thanh mát, hấp dẫn."});
        MENU_DATA.add(new String[]{"Trà Đào Cam Sả (Đá)", "Trà", "40000", "Trà đào kết hợp cam và sả, vị chua ngọt hài hòa."});
        MENU_DATA.add(new String[]{"Trà Xanh Sữa", "Trà", "30000", "Trà xanh kết hợp sữa ngọt nhẹ, thanh tao."});
        MENU_DATA.add(new String[]{"Trà Sữa Thạch Trà", "Trà", "35000", "Trà sữa kèm thạch dai dai, ngọt ngào."});
        MENU_DATA.add(new String[]{"Trà Sữa Sô-Cô-La Bạc Hà", "Trà", "40000", "Trà sữa vị sô cô la và bạc hà, thơm mát đặc trưng."});

        // Sinh tố hoa quả
        MENU_DATA.add(new String[]{"Sinh Tố Xoài", "Sinh tố", "40000", "Sinh tố xoài tươi, ngọt mát và bổ dưỡng."});
        MENU_DATA.add(new String[]{"Sinh Tố Dừa Xiêm", "Sinh tố", "45000", "Sinh tố từ dừa xiêm tươi, vị béo ngậy tự nhiên."});
        MENU_DATA.add(new String[]{"Sinh Tố Chanh Tuyết", "Sinh tố", "35000", "Sinh tố vị chanh mát lạnh, thanh tao."});
        MENU_DATA.add(new String[]{"Hoa Quả Dầm Sữa Chua", "Sinh tố", "50000", "Hoa quả tươi dầm cùng sữa chua, cực kỳ hấp dẫn."});

        // Đồ uống đá xay
        MENU_DATA.add(new String[]{"Matcha Đá Xay", "Đá xay", "50000", "Matcha nguyên chất xay cùng sữa, thơm béo."});
        MENU_DATA.add(new String[]{"Bạc Hà Sô Cô La", "Đá xay", "55000", "Hương bạc hà hòa quyện với sô cô la xay mịn."});
        MENU_DATA.add(new String[]{"Sô Cô La Đá Xay", "Đá xay", "50000", "Sô cô la đậm đà, thơm ngậy với lớp kem mịn."});
        MENU_DATA.add(new String[]{"Cookies & Cream Đá Xay", "Đá xay", "55000", "Bánh quy giòn xay cùng kem tươi, cực hấp dẫn."});

        // Nước ép trái cây
        MENU_DATA.add(new String[]{"Chanh Tươi", "Nước ép trái cây", "30000", "Nước chanh tươi mát lạnh, vị chua ngọt tự nhiên."});
        MENU_DATA.add(new String[]{"Chanh Leo", "Nước ép trái cây", "35000", "Nước chanh leo đậm vị, bổ sung năng lượng."});
        MENU_DATA.add(new String[]{"Ổi Ép", "Nước ép trái cây", "35000", "Nước ép ổi nguyên chất, thơm ngon bổ dưỡng."});
        MENU_DATA.add(new String[]{"Dưa Hấu Ép", "Nước ép trái cây", "30000", "Nước ép dưa hấu mát lạnh, giải nhiệt."});
        MENU_DATA.add(new String[]{"Dứa Ép", "Nước ép trái cây", "35000", "Nước ép dứa tự nhiên, thơm và thanh mát."});
        MENU_DATA.add(new String[]{"Dừa Xiêm", "Nước ép trái cây", "40000", "Nước dừa tươi, ngọt mát tự nhiên."});
        MENU_DATA.add(new String[]{"Cam Vắt", "Nước ép trái cây", "35000", "Cam tươi nguyên chất, giàu vitamin C."});
        MENU_DATA.add(new String[]{"Táo Ép", "Nước ép trái cây", "40000", "Nước ép táo ngọt dịu, thanh mát."});
        MENU_DATA.add(new String[]{"Nước Bưởi Ép", "Nước ép trái cây", "40000", "Nước bưởi tươi, hỗ trợ sức khỏe và thanh lọc cơ thể."});
    }

    public static List<String[]> fetchMenuData() {
        return MENU_DATA;
    }
}
