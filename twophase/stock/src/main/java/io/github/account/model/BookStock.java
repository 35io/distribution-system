package io.github.account.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 库存
 */
@Entity
@Getter
@Setter
public class BookStock {
    @Id
    private String id;

    private String name;

    private Integer num;

    private Date createdDate;
}
