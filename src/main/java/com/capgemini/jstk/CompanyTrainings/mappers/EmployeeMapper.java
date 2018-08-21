package com.capgemini.jstk.CompanyTrainings.mappers;

import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.builders.EmployeeTOBuilder;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeTO mapEmployeeEntity2EmployeeTO(EmployeeEntity employeeEntity) {

        return new EmployeeTOBuilder()
                .setId(employeeEntity.getId())
                .setFirstName(employeeEntity.getFirstName())
                .setLastName(employeeEntity.getLastName())
                .setEmployeePosition(employeeEntity.getEmployeePosition())
                .setGrade(employeeEntity.getGrade()).buildEmployeeTO();
    }

}
