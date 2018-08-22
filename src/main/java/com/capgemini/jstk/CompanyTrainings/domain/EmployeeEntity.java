package com.capgemini.jstk.CompanyTrainings.domain;


import com.capgemini.jstk.CompanyTrainings.enums.EmployeePosition;
import com.capgemini.jstk.CompanyTrainings.enums.Grade;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Employees")
@Getter
@Setter
public class EmployeeEntity extends AbstractEntity {

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
    @JoinTable(name = "TrainingCoach", joinColumns = {
            @JoinColumn(name = "EMPLOYEE_ID", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "TRAINING_ID", nullable = false, updatable = false)})
    Set<TrainingEntity> trainingsAsCoach = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "TrainingStudent", joinColumns = {
            @JoinColumn(name = "EMPLOYEE_ID", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "TRAINING_id", nullable = false, updatable = false)})
    Set<TrainingEntity> trainingsAsStudent = new HashSet<>();

    public EmployeeEntity() {
    }



    public EmployeeEntity(Long version, Long id, String firstName, String lastName, EmployeePosition employeePosition, Grade grade, EmployeeEntity superior) {
        super(version, id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeePosition = employeePosition;
        this.grade = grade;
        this.superior = superior;
    }

}
