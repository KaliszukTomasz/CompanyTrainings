package com.capgemini.jstk.CompanyTrainings.dao;


import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;

import java.util.List;

public interface EmployeeDaoCustom {

//    List<EmployeeEntity> findEmployeesByCriteria()
    Double findNumerOfHoursEmployeeAsCoach(EmployeeEntity employeeEntity);
}
