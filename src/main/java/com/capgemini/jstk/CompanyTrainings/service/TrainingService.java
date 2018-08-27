package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.SearchCriteriaObject;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;

import java.util.List;

public interface TrainingService {
    TrainingTO addTrainingTOToDatabase(TrainingTO trainingTO);

    TrainingTO updateTrainingInDatabase(TrainingTO trainingTO);

    void removeTrainingFromDatabase(TrainingTO trainingTO);

    List<TrainingTO> findAllTrainingList();

    TrainingTO findOne(Long trainingID);

    int findSizeOfCoaches(Long trainingID);

    int findSizeOfStudents(Long trainingID);

    void addCoachToTraining(TrainingTO trainingTO, EmployeeTO employeeTO);

    void addStudentToTraining(TrainingTO trainingTO, EmployeeTO employeeTO);

    List<TrainingTO> findTrainingsBySearchCriteria(SearchCriteriaObject searchCriteriaObject);

    List<TrainingTO> findTrainingsWithTheHighestEdition();

    void removeEmployeeFromEmployeesAsCoaches(TrainingTO trainingTO, EmployeeTO employeeTO);

    void removeEmployeeFromEmployeesAsStudents(TrainingTO trainingTO, EmployeeTO employeeTO);
}
