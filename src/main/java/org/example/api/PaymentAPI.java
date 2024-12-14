package org.example.api;

import org.example.coffeeshop.Invoice;
import java.util.Random;

/** Represents an API for processing payments (e.g., generating QR code for payments) */
public class PaymentAPI {
    // Phương thức giả lập để tạo mã QR cho hóa đơn thanh toán
    public String generateQRCode(Invoice invoice) {
        // Tạo mã QR ngẫu nhiên cho hóa đơn
        Random random = new Random();
        String qrCode = "QR-" + invoice.getId() + "-" + random.nextInt(10000);
        System.out.println("Mã QR thanh toán cho hóa đơn " + invoice.getId() + ": " + qrCode);
        return qrCode;
    }

    // Phương thức giả lập để xử lý thanh toán qua ví điện tử
    public boolean processPayment(Invoice invoice) {
        // Giả lập việc xử lý thanh toán
        if (invoice.isPaid()) {
            System.out.println("Hóa đơn đã được thanh toán.");
            return false; // Nếu hóa đơn đã thanh toán rồi, không cần thanh toán lại
        } else {
            invoice.setPaid(true);
            System.out.println("Thanh toán thành công cho hóa đơn " + invoice.getId() + ".");
            return true;
        }
    }
}
