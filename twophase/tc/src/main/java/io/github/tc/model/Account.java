package io.github.tc.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户
 */
@Getter
@Setter
public class Account {
    private String id;

    private String name;

    private BigDecimal num;

    private Date createdDate;
}
