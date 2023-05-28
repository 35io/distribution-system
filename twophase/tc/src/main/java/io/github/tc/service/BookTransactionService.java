package io.github.tc.service;

import io.github.tc.dao.BookTransactionRepository;
import io.github.tc.model.BookTransaction;
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
        transaction.setStatus(0);
        bookTransactionRepository.save(transaction);
        return transaction;
    }
}
