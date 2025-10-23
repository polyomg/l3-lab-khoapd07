// src/main/java/com/example/demo/service/impl/AccountServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.dao.AccountDAO;
import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDAO accountDAO; // Autowire your DAO

    @Override
    public Account findById(String username) {
        return accountDAO.findById(username);
    }
}