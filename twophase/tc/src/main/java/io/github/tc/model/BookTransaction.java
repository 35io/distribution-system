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

    // 0: new 1: processing 2: success 3: fail
    private Integer status;

    private Date createdDate;
}
