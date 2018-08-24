package com.capgemini.jstk.CompanyTrainings.types;

import com.capgemini.jstk.CompanyTrainings.enums.EmployeePosition;
import com.capgemini.jstk.CompanyTrainings.enums.Grade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeTO {

private Long version;
private Long id;
private String firstName;
private String lastName;
private EmployeePosition employeePosition;
private Grade grade;

    public EmployeeTO() {
    }

    public EmployeeTO(Long version, Long id, String firstName, String lastName, EmployeePosition employeePosition, Grade grade) {
        this.version = version;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeePosition = employeePosition;
        this.grade = grade;
    }
}
