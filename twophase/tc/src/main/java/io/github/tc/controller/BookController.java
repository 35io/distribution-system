package io.github.tc.controller;

import io.github.tc.model.BookTransaction;
import io.github.tc.model.TransactionStatus;
import io.github.tc.service.BookTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class BookController {
    private BookTransactionService bookTransactionService;
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/book")
    public String book(Integer accountId, Integer bookId, Integer num) {
        BookTransaction transaction = bookTransactionService.createTx();

        boolean okOfStock = sendMsg2Stock(bookId, num, transaction.getId());

        // 假设1本书10块钱
        boolean okOfAccount = sendMsg2Account(accountId, new BigDecimal(10 * num), transaction.getId());

        if (okOfAccount && okOfStock) {
            Boolean ok1 = commitOrRollback(transaction.getId(), "http://localhost:8081/stock/commit");
            Boolean ok2 = commitOrRollback(transaction.getId(), "http://localhost:8081/account/commit");
            if (ok1 && ok2) {
                bookTransactionService.finishTx(transaction.getId(), TransactionStatus.SUCCESS);
            }
        } else {
            bookTransactionService.finishTx(transaction.getId(), TransactionStatus.FAIL);
            commitOrRollback(transaction.getId(), "http://localhost:8081/stock/rollback");
            commitOrRollback(transaction.getId(), "http://localhost:8081/account/rollback");
        }
        return "success";
    }

    private Boolean sendMsg2Stock(Integer bookId, Integer num, String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("bookId", bookId);
        params.add("num", num);
        params.add("transactionId", id);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        Boolean result = restTemplate.postForObject("http://localhost:8081/stock/prepare", request, Boolean.class);
        return result;
    }

    private Boolean sendMsg2Account(Integer accountId, BigDecimal amount, String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("accountId", accountId);
        params.add("amount", amount);
        params.add("transactionId", id);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        Boolean result = restTemplate.postForObject("http://localhost:8082/account/prepare", request, Boolean.class);
        return result;
    }

    private Boolean commitOrRollback(String id, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("transactionId", id);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);

        Boolean result = restTemplate.postForObject(url, request, Boolean.class);
        return result;
    }
}
