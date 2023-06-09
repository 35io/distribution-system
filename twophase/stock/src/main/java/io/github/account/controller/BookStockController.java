package io.github.account.controller;

import io.github.account.model.BookStock;
import io.github.account.service.BookStockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookStockController {
    private BookStockService bookStockService;

    @PostMapping("/stock/prepare")
    public boolean prepare(Integer bookId, Integer num, String transactionId) {
        return bookStockService.deduct(bookId, num, transactionId);
    }

    @GetMapping("/stock")
    public BookStock prepare(Integer bookId) {
        return bookStockService.show(bookId);
    }

    @PostMapping("/stock/commit")
    public boolean commit(String transactionId) {
        return bookStockService.commit(transactionId);
    }

    @PostMapping("/stock/rollback")
    public boolean rollback(String transactionId) {
        return bookStockService.rollback(transactionId);
    }
}
