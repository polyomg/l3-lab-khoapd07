package com.example.demo.dao;

import com.example.demo.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountDAO {
    // Simple in-memory storage for demonstration
    private final Map<String, Account> accounts = new HashMap<>();

    public AccountDAO() {
        // Populate with some dummy accounts
        accounts.put("user1", new Account("user1", "123", "Nguyen Van A", false));
        accounts.put("admin1", new Account("admin1", "123", "Tran Thi B (Admin)", true));
        accounts.put("user2", new Account("user2", "abc", "Le Van C", false));
    }

    public Account findById(String username) {
        return accounts.get(username);
    }
}