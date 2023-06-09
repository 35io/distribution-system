package io.github.tc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class BookTransaction {
    @Id
    private String id;

    private TransactionStatus status;

    private Date createdDate;
}
