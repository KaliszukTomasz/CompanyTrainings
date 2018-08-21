package com.capgemini.jstk.CompanyTrainings.types;

import com.capgemini.jstk.CompanyTrainings.enums.EmployeePosition;
import com.capgemini.jstk.CompanyTrainings.enums.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class EmployeeTO {

private Long varsion;
private String firstName;
private String lastName;
private EmployeePosition employeePosition;
private Grade grade;

    public EmployeeTO() {
    }

    public EmployeeTO(String firstName, String lastName, EmployeePosition employeePosition, Grade grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeePosition = employeePosition;
        this.grade = grade;
    }
}
