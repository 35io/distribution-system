package io.github.tc.dao;

import io.github.tc.model.BookTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTransactionRepository extends CrudRepository<BookTransaction, String> {

}