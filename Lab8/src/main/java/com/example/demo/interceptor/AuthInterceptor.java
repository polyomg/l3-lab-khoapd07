package com.example.demo.interceptor;

import com.example.demo.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        Account user = (Account) session.getAttribute("user"); // Cast to Account

        // 1. Check if user is logged in
        if (user == null) {
            // Save the current URI to redirect back after successful login
            session.setAttribute("securityUri", uri);
            response.sendRedirect("/auth/login?message=Bạn cần đăng nhập để truy cập trang này!");
            return false; // Stop further processing
        }

        // 2. Check for admin role for /admin/** (excluding /admin/home/index)
        if (uri.startsWith("/admin") && !uri.equals("/admin/home/index")) {
            if (!user.isAdmin()) { // Assuming Account has an isAdmin() method
                session.setAttribute("securityUri", uri); // Still save for potential later admin login
                response.sendRedirect("/auth/login?message=Bạn không có quyền truy cập trang quản trị!");
                return false; // Stop further processing
            }
        }

        return true; // Continue processing if authenticated and authorized
    }
}