package io.github.account.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户
 */
@Entity
@Getter
@Setter
public class Account {
    @Id
    private String id;

    private String name;

    private BigDecimal num;

    private Date createdDate;
}
