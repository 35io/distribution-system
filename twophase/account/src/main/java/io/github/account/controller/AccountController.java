package io.github.account.controller;

import io.github.account.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;

    @PostMapping("/account/prepare")
    public boolean prepare(Integer accountId, BigDecimal money, String transactionId) {
        return accountService.deduct(accountId, money, transactionId);
    }

    @PostMapping("/account/commit")
    public boolean commit(String transactionId) {
        return accountService.commit(transactionId);
    }

    @PostMapping("/account/rollback")
    public boolean rollback(String transactionId) {
        return accountService.rollback(transactionId);
    }
}
