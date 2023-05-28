package io.github.tc.controller;

import io.github.tc.model.BookTransaction;
import io.github.tc.service.BookTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookController {
    private BookTransactionService bookTransactionService;

    @PostMapping("/book")
    public String book() {
        BookTransaction transaction = bookTransactionService.createTx();
        return "success";
    }
}
