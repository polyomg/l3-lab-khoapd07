package com.poly.lab5.controller;

import com.poly.lab5.service.CookieService;
import com.poly.lab5.service.ParamService;
import com.poly.lab5.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

    @Autowired
    CookieService cookieService;

    @Autowired
    ParamService paramService;

    @Autowired
    SessionService sessionService;

    // GET: Hiển thị form đăng nhập
    @GetMapping("/account/login")
    public String login1() {
        return "/account/login";
    }

    // POST: Xử lý đăng nhập
    @PostMapping("/account/login")
    public String login2() {
        // Đọc các tham số từ form
        String username = paramService.getString("username", "");
        String password = paramService.getString("password", "");
        boolean remember = paramService.getBoolean("remember", false);

        // Kiểm tra đăng nhập
        if (username.equals("poly") && password.equals("123")) {
            //  Lưu username vào session
            sessionService.set("username", username);

            //  Xử lý ghi nhớ tài khoản
            if (remember) {
                // Ghi cookie user tồn tại 10 ngày
                cookieService.add("user", username, 24 * 10);
            } else {
                // Xóa cookie nếu trước đó có
                cookieService.remove("user");
            }

            // Chuyển hướng đến trang chào mừng (ví dụ)
            return "redirect:/account/welcome";
        } else {
            // Nếu sai thông tin đăng nhập
            sessionService.set("message", "Sai tên đăng nhập hoặc mật khẩu!");
            return "/account/login";
        }
    }

    // Trang chào mừng (tuỳ chọn)
    @GetMapping("/account/welcome")
    public String welcome() {
        return "/account/welcome";
    }
}
