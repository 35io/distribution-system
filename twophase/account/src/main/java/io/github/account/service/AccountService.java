package io.github.account.service;

import io.github.account.dao.AccountRepository;
import io.github.account.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;
    private PlatformTransactionManager transactionManager;
    private static final Map<String, TransactionStatus> pools = new HashMap<>();

    public boolean deduct(Integer accountId, BigDecimal money, String transactionId) {
        TransactionStatus exist = pools.get(transactionId);
        if (exist != null) {
            return false;
        }

        TransactionStatus transaction = transactionManager.getTransaction(null);
        pools.put(transactionId, transaction);

        try {
            Optional<Account> optional = accountRepository.findById(accountId);
            if (optional.isEmpty()) {
                return false;
            }
            Account account = optional.get();
            if (account.getNum().compareTo(money) < 0) {
                return false;
            }
            account.setNum(account.getNum().subtract(money));
            accountRepository.save(account);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean commit(String transactionId) {
        TransactionStatus exist = pools.get(transactionId);
        if (exist == null) {
            return false;
        }
        try {
            transactionManager.commit(exist);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean rollback(String transactionId) {
        TransactionStatus exist = pools.get(transactionId);
        if (exist == null) {
            return false;
        }
        try {
            transactionManager.rollback(exist);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Account show(Integer accountId) {
        TransactionStatus transaction = transactionManager.getTransaction(null);
        Optional<Account> result = accountRepository.findById(accountId);
        transactionManager.commit(transaction);
        return result.orElseThrow();
    }
}
