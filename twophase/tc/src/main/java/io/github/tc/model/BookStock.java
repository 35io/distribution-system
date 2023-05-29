package io.github.tc.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 库存
 */
@Getter
@Setter
public class BookStock {
    private String id;

    private String name;

    private Integer num;

    private Date createdDate;
}
