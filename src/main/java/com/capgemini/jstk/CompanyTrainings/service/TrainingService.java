package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.dao.EmployeeDao;
import com.capgemini.jstk.CompanyTrainings.dao.TrainingDao;
import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;
import com.capgemini.jstk.CompanyTrainings.enums.Grade;
import com.capgemini.jstk.CompanyTrainings.exceptions.*;
import com.capgemini.jstk.CompanyTrainings.mappers.TrainingMapper;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.SearchCriteriaObject;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import javax.transaction.TransactionManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingService {

    @Autowired
    TrainingDao trainingDao;
    @Autowired
    TrainingMapper trainingMapper;
    @Autowired
    EmployeeDao employeeDao;

    public TrainingTO addTrainingTOToDatabase(TrainingTO trainingTO) {

        TrainingEntity trainingEntity = trainingMapper.mapTrainingTO2TrainingEntity(trainingTO);
        trainingEntity = trainingDao.save(trainingEntity);
        return trainingMapper.mapTrainingEntity2TrainingTO(trainingEntity);

    }

    public TrainingTO updateTrainingInDatabase(TrainingTO trainingTO) {

        TrainingEntity trainingEntity = trainingDao.findOne(trainingTO.getId());
        if (trainingEntity == null) {
            throw new NoSuchTrainingIdInDatabaseException("No such training.id in database!");
        }

        if (trainingEntity.getVersion() != trainingTO.getVersion()) {
            throw new OptimisticLockException();
        }

        if (trainingTO.getVersion() != null) {
            trainingEntity.setVersion(trainingTO.getVersion());
        }

        if (trainingTO.getCostPerStudent() != null) {
            trainingEntity.setCostPerStudent(trainingTO.getCostPerStudent());
        }

        if (trainingTO.getDuration() != null) {
            trainingEntity.setDuration(trainingTO.getDuration());
        }

        if (trainingTO.getStartDate() != null) {
            trainingEntity.setStartDate(trainingTO.getStartDate());
        }

        if (trainingTO.getEndDate() != null) {
            trainingEntity.setEndDate(trainingTO.getEndDate());
        }

        if (trainingTO.getTags() != null) {
            trainingEntity.setTags(trainingTO.getTags());
        }

        if (trainingTO.getTrainingCharacter() != null) {
            trainingEntity.setTrainingCharacter(trainingTO.getTrainingCharacter());
        }

        if (trainingTO.getTrainingName() != null) {
            trainingEntity.setTrainingName(trainingTO.getTrainingName());
        }

        if (trainingTO.getTrainingType() != null) {
            trainingEntity.setTrainingType(trainingTO.getTrainingType());
        }

        trainingEntity = trainingDao.save(trainingEntity);
        return trainingMapper.mapTrainingEntity2TrainingTO(trainingEntity);
    }

    public void removeTrainingFromDatabase(TrainingTO trainingTO) {

        if (trainingDao.findOne(trainingTO.getId()) == null) {
            throw new NoSuchTrainingIdInDatabaseException("No such training.ID in database!");
        }
        trainingDao.delete(trainingTO.getId());
    }

    public List<TrainingTO> findAllTrainingList() {

        return trainingMapper.mapTrainingEntityList2TrainingTOList(trainingDao.findAll());

    }

    public TrainingTO findOne(Long trainingID) {

        TrainingEntity trainingEntity = trainingDao.findOne(trainingID);
        if (trainingEntity == null) {
            return null;
        }
        return trainingMapper.mapTrainingEntity2TrainingTO(trainingEntity);
    }

    public TrainingEntity findOneEntity(Long trainingID) {
        return trainingDao.findOne(trainingID);
    }

    public int findSizeOfCoaches(Long trainingID) {
        return trainingDao.findOne(trainingID).getEmployeesAsCoaches().size();
    }

    public int findSizeOfStudents(Long trainingID) {
        return trainingDao.findOne(trainingID).getEmployeesAsStudents().size();
    }

    public void addCoachToTraining(TrainingTO trainingTO, EmployeeTO employeeTO) {
        TrainingEntity trainingEntity = trainingDao.findOne(trainingTO.getId());
        EmployeeEntity employeeEntity = employeeDao.findOne(employeeTO.getId());
        if (null == trainingEntity) {
            throw new NoSuchTrainingIdInDatabaseException("No such training in database");
        }
        if (null == employeeEntity) {
            throw new NoSuchEmployeeIdInDatabaseException("No such employee in database");
        }
        if (trainingEntity.getEmployeesAsStudents().contains(employeeEntity)) {
            throw new EmployeeIsAlreadyStudentDuringThisTrainingException(
                    "This Employee cant be student and coach during one training!");
        }
        trainingEntity.addEmployeeToEmployeesAsCoaches(employeeEntity);
    }

    public void addStudentToTraining(TrainingTO trainingTO, EmployeeTO employeeTO) {
        TrainingEntity trainingEntity = trainingDao.findOne(trainingTO.getId());
        EmployeeEntity employeeEntity = employeeDao.findOne(employeeTO.getId());
        if (null == trainingEntity) {
            throw new NoSuchTrainingIdInDatabaseException("No such training in database");
        }
        if (null == employeeEntity) {
            throw new NoSuchEmployeeIdInDatabaseException("No such employee in database");
        }
        if (trainingEntity.getEmployeesAsCoaches().contains(employeeEntity)) {
            throw new EmployeeIsAlreadyCoachDuringThisTrainingException(
                    "This Employee cant be student and coach during one training!");
        }
        List<TrainingEntity> listOfTrainingsAsStudent = employeeEntity.getTrainingsAsStudent().stream().filter(
                temp -> temp.getStartDate().getYear() == trainingEntity.getStartDate().getYear()).collect(Collectors.toList());

        Integer totalBudgetInThisYear = listOfTrainingsAsStudent.stream().map(temp -> temp.getCostPerStudent()).reduce(0, (a, b) -> a + b);
        boolean conditionTrueIfOverTwoTrainingsByGradeUnderFourth = listOfTrainingsAsStudent.size() > 2;
        boolean conditionUnderFourthGrade = employeeEntity.getGrade().ordinal() < Grade.FOURTH.ordinal();
        boolean conditionTrueIfBudgetOver15K = trainingEntity.getCostPerStudent() + totalBudgetInThisYear > 15000;
        boolean conditionTrueIfBudgetOver50K = trainingEntity.getCostPerStudent() + totalBudgetInThisYear > 50000;
        if (conditionTrueIfOverTwoTrainingsByGradeUnderFourth && conditionUnderFourthGrade) {
            throw new TooManyTrainingsInThisYearException("Cant be more trainings in year of this training by grade under 4");
        }
        if (conditionTrueIfBudgetOver15K && conditionUnderFourthGrade) {
            throw new Budget15KLimitForEmployeeUnderFourthGradeException("Budget over 15 000!");
        }

        if (!conditionUnderFourthGrade && conditionTrueIfBudgetOver50K) {
            throw new Budher50KLimitForEmployeeOverThirdGradeException("Budget over 50 000");
        }
        trainingEntity.addEmployeeToEmployeesAsStudent(employeeEntity);
    }

    public List<TrainingTO> findTrainingsBySearchCriteria(SearchCriteriaObject searchCriteriaObject) {

        List<TrainingEntity> trainingEntityList = trainingDao.findTrainingsByCriteria(searchCriteriaObject);
        return trainingMapper.mapTrainingEntityList2TrainingTOList(trainingEntityList);
//        return null;
    }

}
