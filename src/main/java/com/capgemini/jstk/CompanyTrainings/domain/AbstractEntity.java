package com.capgemini.jstk.CompanyTrainings.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public class AbstractEntity {
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;

    @Version
    private Long version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
