package com.example.demo.interceptor;

import com.example.demo.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Date;

@Component
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        Account user = (Account) request.getSession().getAttribute("user"); // Get user from session

        // Log only if a user is logged in
        if (user != null) {
            System.out.println("LogInterceptor: " +
                    "URI=" + request.getRequestURI() +
                    ", Time=" + new Date() +
                    ", User=" + user.getFullname() +
                    " (Username: " + user.getUsername() + ")");
        } else {
            System.out.println("LogInterceptor: URI=" + request.getRequestURI() + ", Time=" + new Date() + ", User=Guest");
        }
        return true; // Continue processing
    }
}