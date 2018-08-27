package com.capgemini.jstk.CompanyTrainings.mappers;

import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntityBuilder;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.builders.EmployeeTOBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public EmployeeTO mapEmployeeEntity2EmployeeTO(EmployeeEntity employeeEntity) {

        return new EmployeeTOBuilder()
                .setVersion(employeeEntity.getVersion())
                .setId(employeeEntity.getId())
                .setFirstName(employeeEntity.getFirstName())
                .setLastName(employeeEntity.getLastName())
                .setEmployeePosition(employeeEntity.getEmployeePosition())
                .setGrade(employeeEntity.getGrade())
                .buildEmployeeTO();
    }

    public EmployeeEntity mapEmployeeTO2EmployeeEntity(EmployeeTO employeeTO) {

        return new EmployeeEntityBuilder()
                .setVersion(employeeTO.getVersion())
                .setFirstName(employeeTO.getFirstName())
                .setLastName(employeeTO.getLastName())
                .setEmployeePosition(employeeTO.getEmployeePosition())
                .setGrade(employeeTO.getGrade())
                .buildEmployeeEntity();

    }

    public List<EmployeeTO> mapEmployeeEntityList2EmployeeTOList(List<EmployeeEntity> listEntitys) {

        return listEntitys.stream().map(employeeEntity -> {
            return new EmployeeTOBuilder()
                    .setVersion(employeeEntity.getVersion())
                    .setId(employeeEntity.getId())
                    .setFirstName(employeeEntity.getFirstName())
                    .setLastName(employeeEntity.getLastName())
                    .setEmployeePosition(employeeEntity.getEmployeePosition())
                    .setGrade(employeeEntity.getGrade())
                    .buildEmployeeTO();
        }).collect(Collectors.toList());
    }

}
