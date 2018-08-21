package com.capgemini.jstk.CompanyTrainings.types.builders;

import com.capgemini.jstk.CompanyTrainings.enums.EmployeePosition;
import com.capgemini.jstk.CompanyTrainings.enums.Grade;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;

public class EmployeeTOBuilder {
    private String firstName;
    private String lastName;
    private EmployeePosition employeePosition;
    private Grade grade;

    public EmployeeTOBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeTOBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeTOBuilder setEmployeePosition(EmployeePosition employeePosition) {
        this.employeePosition = employeePosition;
        return this;
    }

    public EmployeeTOBuilder setGrade(Grade grade) {
        this.grade = grade;
        return this;
    }

    public EmployeeTO buildEmployeeTO() {
        return new EmployeeTO(firstName, lastName, employeePosition, grade);
    }
}