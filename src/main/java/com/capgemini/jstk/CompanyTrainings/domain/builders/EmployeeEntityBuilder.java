package com.capgemini.jstk.CompanyTrainings.domain;

import com.capgemini.jstk.CompanyTrainings.enums.EmployeePosition;
import com.capgemini.jstk.CompanyTrainings.enums.Grade;

public class EmployeeEntityBuilder {
    private Long version;
    private Long id;
    private String firstName;
    private String lastName;
    private EmployeePosition employeePosition;
    private Grade grade;
    private EmployeeEntity superior;

    public EmployeeEntityBuilder setVersion(Long version) {
        this.version = version;
        return this;
    }

    public EmployeeEntityBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public EmployeeEntityBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeEntityBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeEntityBuilder setEmployeePosition(EmployeePosition employeePosition) {
        this.employeePosition = employeePosition;
        return this;
    }

    public EmployeeEntityBuilder setGrade(Grade grade) {
        this.grade = grade;
        return this;
    }

    public EmployeeEntityBuilder setSuperior(EmployeeEntity superior) {
        this.superior = superior;
        return this;
    }

    public EmployeeEntity buildEmployeeEntity() {
        return new EmployeeEntity(version, id, firstName, lastName, employeePosition, grade, superior);
    }
}