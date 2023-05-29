package io.github.account.service;

import io.github.account.dao.BookStockRepository;
import io.github.account.model.BookStock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookStockService {
    private BookStockRepository bookStockRepository;
    private PlatformTransactionManager transactionManager;
    private static final Map<String, TransactionStatus> pools = new HashMap<>();

    public BookStock show(Integer bookId) {
        TransactionStatus transaction = transactionManager.getTransaction(null);
        Optional<BookStock> result = bookStockRepository.findById(bookId);
        transactionManager.commit(transaction);
        return result.orElseThrow();
    }

    public boolean deduct(Integer bookId, Integer num, String transactionId) {
        TransactionStatus exist = pools.get(transactionId);
        if (exist != null) {
            return false;
        }

        TransactionStatus transaction = transactionManager.getTransaction(null);
        pools.put(transactionId, transaction);

        try {
            bookStockRepository.findById(bookId).ifPresent(bookStock -> {
                bookStock.setNum(bookStock.getNum() - num);
                bookStockRepository.save(bookStock);
            });
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
}
