package io.github.tc.service;

import io.github.tc.dao.BookTransactionRepository;
import io.github.tc.model.BookTransaction;
import io.github.tc.model.TransactionStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class BookTransactionService {
    private BookTransactionRepository bookTransactionRepository;

    public BookTransaction createTx() {
        BookTransaction transaction = new BookTransaction();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setCreatedDate(new Date());
        transaction.setStatus(TransactionStatus.NEW);
        bookTransactionRepository.save(transaction);
        return transaction;
    }

    public void finishTx(String id, TransactionStatus status) {
        BookTransaction transaction = bookTransactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setStatus(status);
        bookTransactionRepository.save(transaction);
    }
}
