package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {
    EmployeeTO addEmployeeToDatabase(EmployeeTO employeeTO);

    Double findNumerOfHoursEmployeeAsCoach(EmployeeTO employeeTO, int year);

    int findNumberOfTrainingsByOneEmployeeInPeriodOfTime(EmployeeTO employeeTO, LocalDate startDate, LocalDate endDate);

    int findTotalcostOfTrainingsByEmployee(EmployeeTO employeeTO);

    List<EmployeeTO> findEmployeesWithLongestTimeOnTrainingsAsStudents();

    EmployeeTO updateEmployeeInDatabase(EmployeeTO employeeTO);

    void removeEmployeeFromDatabase(EmployeeTO employeeTO);

    List<EmployeeTO> findAllEmployeeList();

    EmployeeTO findOne(Long employeeId);

    void removeTrainingFromTrainingsAsCoaches(TrainingTO trainingTO, EmployeeTO employeeTO);

    void removeTrainingFromTrainingsAsStudent(TrainingTO trainingTO, EmployeeTO employeeTO);
}
