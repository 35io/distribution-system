package io.github.account.dao;

import io.github.account.model.BookStock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStockRepository extends CrudRepository<BookStock, Integer> {

}