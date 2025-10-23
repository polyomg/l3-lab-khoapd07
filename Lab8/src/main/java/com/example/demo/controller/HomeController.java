package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // Bạn có thể thêm dữ liệu vào model nếu muốn hiển thị trên trang chủ
        model.addAttribute("welcomeMessage", "Chào mừng bạn đến với trang chủ!");
        return "home/index"; // Trả về tên của view (file HTML)
    }

    // Nếu bạn có trang admin home riêng (từ Bài 5: /admin/home/index)
    @GetMapping("/admin/home/index")
    public String adminHome(Model model) {
        model.addAttribute("adminWelcome", "Chào mừng Admin!");
        return "admin/home/index"; // Trả về view cho trang admin home
    }

    // Các trang account khác
    @GetMapping("/account/edit-profile")
    public String editProfile(Model model) {
        model.addAttribute("pageTitle", "Chỉnh sửa thông tin cá nhân");
        return "account/edit-profile";
    }

    @GetMapping("/account/change-password")
    public String changePassword(Model model) {
        model.addAttribute("pageTitle", "Đổi mật khẩu");
        return "account/change-password";
    }

    // Ví dụ về trang order
    @GetMapping("/order/list")
    public String orderList(Model model) {
        model.addAttribute("pageTitle", "Danh sách đơn hàng");
        return "order/list";
    }
}