package com.capgemini.jstk.CompanyTrainings.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class ExternalCoachEntity {

    @Version
    private Long version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 30)
    private String firstName;
    @Column(nullable = false, length = 30)
    private String lastName;
    @Column(nullable = false, length = 30)
    private String company;
    @ManyToMany
    private Set<TrainingEntity> trainingsAsExternalCoach = new HashSet<>();




}
