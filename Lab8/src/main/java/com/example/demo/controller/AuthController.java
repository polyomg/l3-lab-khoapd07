package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import jakarta.servlet.http.HttpSession; // Import HttpSession
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth") // Optional: Group all auth endpoints under /auth
public class AuthController {

    @Autowired
    AccountService accountService;
    @Autowired
    HttpSession session;

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "/login"; // Refers to src/main/resources/templates/auth/login.html
    }

    @PostMapping("/login")
    public String loginProcess(Model model,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password) {
        Account user = accountService.findById(username);
        if (user == null) {
            model.addAttribute("error", "Invalid username!"); // Use 'error' for consistency
        } else if (!user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid password!");
        } else {
            session.setAttribute("user", user);
            model.addAttribute("message", "Login successfully! Welcome, " + user.getFullname() + "!");

            // Bài 5: Quay trở lại URI bảo mật sau khi đăng nhập thành công
            String securityUri = (String) session.getAttribute("securityUri");
            if (securityUri != null && !securityUri.isEmpty()) {
                session.removeAttribute("securityUri"); // Remove it after use
                return "redirect:" + securityUri;
            }

            return "redirect:/";
        }
        return "/login";
    }

    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("user");
        return "redirect:/auth/login";
    }
}