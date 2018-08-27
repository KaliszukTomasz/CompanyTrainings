package com.capgemini.jstk.CompanyTrainings.service.impl;

import com.capgemini.jstk.CompanyTrainings.dao.EmployeeDao;
import com.capgemini.jstk.CompanyTrainings.dao.ExternalCoachDao;
import com.capgemini.jstk.CompanyTrainings.dao.TrainingDao;
import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import com.capgemini.jstk.CompanyTrainings.domain.ExternalCoachEntity;
import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;
import com.capgemini.jstk.CompanyTrainings.enums.Grade;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingStatus;
import com.capgemini.jstk.CompanyTrainings.exceptions.*;
import com.capgemini.jstk.CompanyTrainings.mappers.TrainingMapper;
import com.capgemini.jstk.CompanyTrainings.service.TrainingService;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.ExternalCoachTO;
import com.capgemini.jstk.CompanyTrainings.types.SearchCriteriaObject;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    TrainingDao trainingDao;
    @Autowired
    TrainingMapper trainingMapper;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    ExternalCoachDao externalCoachDao;

    @Override
    public TrainingTO addTrainingTOToDatabase(TrainingTO trainingTO) {

        TrainingEntity trainingEntity = trainingMapper.mapTrainingTO2TrainingEntity(trainingTO);
        trainingEntity = trainingDao.save(trainingEntity);
        return trainingMapper.mapTrainingEntity2TrainingTO(trainingEntity);

    }

    @Override
    public TrainingTO updateTrainingInDatabase(TrainingTO trainingTO) {

        TrainingEntity trainingEntity = trainingDao.findOne(trainingTO.getId());
        if (trainingEntity == null) {
            throw new NoSuchTrainingIdInDatabaseException("No such training.id in database!");
        }

        if (!trainingEntity.getVersion().equals(trainingTO.getVersion())) {
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

        if (trainingTO.getTrainingStatus() != null) {
            if (trainingEntity.getTrainingStatus() != TrainingStatus.CANCELED) {
                trainingEntity.setTrainingStatus(trainingTO.getTrainingStatus());
            } else
                throw new CanceledTrainignStatusCantBeChangedException("This training has status CANCELED! You cant change it");
        }

        trainingEntity = trainingDao.save(trainingEntity);
        return trainingMapper.mapTrainingEntity2TrainingTO(trainingEntity);
    }

    @Override
    public void removeTrainingFromDatabase(TrainingTO trainingTO) {

        if (trainingDao.findOne(trainingTO.getId()) == null) {
            throw new NoSuchTrainingIdInDatabaseException("No such training.ID in database!");
        }
        trainingDao.delete(trainingTO.getId());
    }

    @Override
    public List<TrainingTO> findAllTrainingList() {

        return trainingMapper.mapTrainingEntityList2TrainingTOList(trainingDao.findAll());

    }

    @Override
    public TrainingTO findOne(Long trainingID) {

        TrainingEntity trainingEntity = trainingDao.findOne(trainingID);
        if (trainingEntity == null) {
            return null;
        }
        return trainingMapper.mapTrainingEntity2TrainingTO(trainingEntity);
    }

    @Override
    public int findSizeOfCoaches(Long trainingID) {
        return trainingDao.findOne(trainingID).getEmployeesAsCoaches().size();
    }

    @Override
    public int findSizeOfStudents(Long trainingID) {
        return trainingDao.findOne(trainingID).getEmployeesAsStudents().size();
    }

    @Override
    public void addCoachToTraining(TrainingTO trainingTO, EmployeeTO employeeTO) {
        TrainingEntity trainingEntity = trainingDao.findOne(trainingTO.getId());
        EmployeeEntity employeeEntity = employeeDao.findOne(employeeTO.getId());
        checkIfEntitiesNotNull(trainingEntity, employeeEntity);
        checkIfEmployeeDontExistInThisTraining(trainingEntity, employeeEntity);
        checkIfTrainingIsNotCanceled(trainingEntity);
        trainingEntity.addEmployeeToEmployeesAsCoaches(employeeEntity);
    }

    @Override
    public void addExternalCoachToTraining(TrainingTO trainingTO, ExternalCoachTO externalCoachTO) {
        TrainingEntity trainingEntity = trainingDao.findOne(trainingTO.getId());
        ExternalCoachEntity externalCoachEntity = externalCoachDao.findOne(externalCoachTO.getId());
        checkIfTrainingAndExternalCoachExist(trainingEntity, externalCoachEntity);
        checkIfTrainingIsNotCanceled(trainingEntity);
        trainingEntity.addExternalCoachToExternalCoaches(externalCoachEntity);

    }

    @Override
    public void addStudentToTraining(TrainingTO trainingTO, EmployeeTO employeeTO) {
        TrainingEntity trainingEntity = trainingDao.findOne(trainingTO.getId());
        EmployeeEntity employeeEntity = employeeDao.findOne(employeeTO.getId());
        checkIfEntitiesNotNull(trainingEntity, employeeEntity);
        checkIfEmployeeDontExistInThisTraining(trainingEntity, employeeEntity);
        checkIfTrainingIsNotCanceled(trainingEntity);

        List<TrainingEntity> listOfTrainingsAsStudent = employeeEntity.getTrainingsAsStudent().stream().filter(
                temp -> temp.getStartDate().getYear() == trainingEntity.getStartDate().getYear()).collect(Collectors.toList());
        listOfTrainingsAsStudent = listOfTrainingsAsStudent.stream().filter(temp -> temp.getTrainingStatus() != TrainingStatus.CANCELED).collect(Collectors.toList());

        Integer totalBudgetInThisYear = listOfTrainingsAsStudent.stream().map(temp -> temp.getCostPerStudent()).reduce(0, (a, b) -> a + b);

        checkBusinessConditionsToAddEmployeeAsStudentToTraining(employeeEntity, trainingEntity, totalBudgetInThisYear, listOfTrainingsAsStudent);

        trainingEntity.addEmployeeToEmployeesAsStudent(employeeEntity);
    }

    @Override
    public List<TrainingTO> findTrainingsBySearchCriteria(SearchCriteriaObject searchCriteriaObject) {

        List<TrainingEntity> trainingEntityList = trainingDao.findTrainingsByCriteria(searchCriteriaObject);
        return trainingMapper.mapTrainingEntityList2TrainingTOList(trainingEntityList);
    }

    @Override
    public List<TrainingTO> findTrainingsWithTheHighestEdition() {

        List<TrainingEntity> trainingEntityList = trainingDao.findTrainingsWithTheHighestEdition();
        return trainingMapper.mapTrainingEntityList2TrainingTOList(trainingEntityList);

    }

    @Override
    public void removeEmployeeFromEmployeesAsCoaches(TrainingTO trainingTO, EmployeeTO employeeTO) {
        TrainingEntity trainingEntity = trainingDao.findOne(trainingTO.getId());
        EmployeeEntity employeeEntity = employeeDao.findOne(employeeTO.getId());
        checkIfEntitiesNotNull(trainingEntity, employeeEntity);

        trainingEntity.removeEmployeeFromEmployeesAsCoaches(employeeEntity);

    }

    @Override
    public void removeEmployeeFromEmployeesAsStudents(TrainingTO trainingTO, EmployeeTO employeeTO){
        TrainingEntity trainingEntity = trainingDao.findOne(trainingTO.getId());
        EmployeeEntity employeeEntity = employeeDao.findOne(employeeTO.getId());
        checkIfEntitiesNotNull(trainingEntity, employeeEntity);

        trainingEntity.removeEmployeeFromEmployeesAsStudent(employeeEntity);


    }

    @Override
    public void removeExternalCoachFromTraining(TrainingTO trainingTO, ExternalCoachTO externalCoachTO) {

        TrainingEntity trainingEntity = trainingDao.findOne(trainingTO.getId());
        ExternalCoachEntity externalCoachEntity = externalCoachDao.findOne(externalCoachTO.getId());
        checkIfTrainingAndExternalCoachExist(trainingEntity, externalCoachEntity);

        trainingEntity.removeExternalCoachFromExternalCoaches(externalCoachEntity);
    }

    @Override
    public int findSizeOfExternalCoach(Long trainingId) {
        return trainingDao.findOne(trainingId).getExternalCoaches().size();
    }


    private void checkIfEntitiesNotNull(TrainingEntity trainingEntity, EmployeeEntity employeeEntity) {

        if (null == trainingEntity) {
            throw new NoSuchTrainingIdInDatabaseException("No such training in database");
        }
        if (null == employeeEntity) {
            throw new NoSuchEmployeeIdInDatabaseException("No such employee in database");
        }
    }

    private void checkIfEmployeeDontExistInThisTraining(TrainingEntity trainingEntity, EmployeeEntity employeeEntity) {
        if (trainingEntity.getEmployeesAsCoaches().contains(employeeEntity)) {
            throw new EmployeeIsAlreadyCoachDuringThisTrainingException(
                    "This Employee cant be student and coach during one training!");
        }

        if (trainingEntity.getEmployeesAsStudents().contains(employeeEntity)) {
            throw new EmployeeIsAlreadyStudentDuringThisTrainingException(
                    "This employee is already as student");
        }
    }

    private void checkIfTrainingIsNotCanceled(TrainingEntity trainingEntity) {
        if (trainingEntity.getTrainingStatus() == TrainingStatus.CANCELED) {
            throw new EmployeeCantBeAddedToCanceledTrainingException("Employee cant be add to canceled training!");
        }
    }

    private void checkBusinessConditionsToAddEmployeeAsStudentToTraining(
            EmployeeEntity employeeEntity, TrainingEntity trainingEntity,
            int totalBudgetInThisYear, List<TrainingEntity> listOfTrainingsAsStudent) {

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
    }

    private void checkIfTrainingAndExternalCoachExist(TrainingEntity trainingEntity, ExternalCoachEntity externalCoachEntity){
        if(trainingEntity == null ){
            throw new NoSuchTrainingIdInDatabaseException("No such training in database");
        }
        if(externalCoachEntity == null){
            throw new NoSuchExternalCoachIdInDatabaseException("No such ExternalCoach in database");
        }
    }


}
