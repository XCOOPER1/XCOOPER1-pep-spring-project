package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account register(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() ||
            account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        return accountRepository.save(account);
    }

    public Account login(Account account) {
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount == null || !existingAccount.getPassword().equals(account.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return existingAccount;
    }
}