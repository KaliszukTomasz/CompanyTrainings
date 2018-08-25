package com.capgemini.jstk.CompanyTrainings.dao;


import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeDaoCustom {

    Double findNumerOfHoursEmployeeAsCoachInYear(EmployeeEntity employeeEntity, int year);

    List<TrainingEntity> findListOfTrainingsByOneEmployeeInPeriodOfTime(EmployeeEntity employeeEntity, LocalDate startDate, LocalDate endDate);

    Integer findTotalCostOfTrainingsByEmployee(Long employeeId);

    List<EmployeeEntity> findEmployeesWithLongestTimeOnTrainingsAsStudents();
}
