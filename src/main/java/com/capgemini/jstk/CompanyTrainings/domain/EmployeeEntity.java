package com.capgemini.jstk.CompanyTrainings.domain;


import com.capgemini.jstk.CompanyTrainings.enums.EmployeePosition;
import com.capgemini.jstk.CompanyTrainings.enums.Grade;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class EmployeeEntity {

    @Version
    private Long version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 30)
    private String firstName;
    @Column(nullable = false, length = 30)
    private String lastName;
    @Enumerated
    @Column(nullable = false)
    private EmployeePosition employeePosition;
    @Enumerated
    @Column(nullable = false)
    private Grade grade;

    @ManyToOne
    private EmployeeEntity superior;

    @ManyToMany
    Set<TrainingEntity> trainingsAsCoach = new HashSet<>();
    @ManyToMany
    Set<TrainingEntity> trainingsAsStudent = new HashSet<>();


}
