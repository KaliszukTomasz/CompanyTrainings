package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.dao.EmployeeDao;
import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;
import com.capgemini.jstk.CompanyTrainings.exceptions.NoSuchEmployeeIdInDatabaseException;
import com.capgemini.jstk.CompanyTrainings.mappers.EmployeeMapper;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    EmployeeMapper employeeMapper;

    public EmployeeTO addEmployeeToDatabase(EmployeeTO employeeTO) {

        EmployeeEntity employeeEntity = employeeMapper.mapEmployeeTO2EmployeeEntity(employeeTO);
        employeeEntity = employeeDao.save(employeeEntity);
        return employeeMapper.mapEmployeeEntity2EmployeeTO(employeeEntity);

    }

    public Double findNumerOfHoursEmployeeAsCoach(EmployeeTO employeeTO, int year) {

        EmployeeEntity employeeEntity = employeeDao.findOne(employeeTO.getId());
        return employeeDao.findNumerOfHoursEmployeeAsCoachInYear(employeeEntity, year);

    }

    public int findNumberOfTrainingsByOneEmployeeInPeriodOfTime(EmployeeTO employeeTO, LocalDate startDate, LocalDate endDate) {
        EmployeeEntity employeeEntity = employeeDao.findOne(employeeTO.getId());
        List<TrainingEntity> trainingsList = employeeDao.findListOfTrainingsByOneEmployeeInPeriodOfTime
                (employeeEntity, startDate, endDate);
        return trainingsList.size();
    }

    public int findTotalcostOfTrainingsByEmployee(EmployeeTO employeeTO) {
        return employeeDao.findTotalCostOfTrainingsByEmployee(employeeTO.getId());
    }

    public List<EmployeeTO> findEmployeesWithLongestTimeOnTrainingsAsStudents(){
        List<EmployeeEntity> employeeEntitieList = employeeDao.findEmployeesWithLongestTimeOnTrainingsAsStudents();
        return employeeMapper.mapEmployeeEntityList2EmployeeTOList(employeeEntitieList);
    }

    public EmployeeTO updateEmployeeInDatabase(EmployeeTO employeeTO) {

        EmployeeEntity employeeEntity = employeeDao.findOne(employeeTO.getId());

        if(employeeEntity == null){
            throw new NoSuchEmployeeIdInDatabaseException("No such employee in database");
        }
        if (!employeeEntity.getVersion().equals(employeeTO.getVersion())) {
            throw new OptimisticLockException();
        }

        if (employeeEntity == null) {
            throw new NoSuchEmployeeIdInDatabaseException("No such employee.ID in database!");
        }

        if (employeeTO.getEmployeePosition() != null) {
            employeeEntity.setEmployeePosition(employeeTO.getEmployeePosition());
        }
        if (employeeTO.getFirstName() != null) {
            employeeEntity.setFirstName(employeeTO.getFirstName());
        }
        if (employeeTO.getLastName() != null) {
            employeeEntity.setLastName(employeeTO.getLastName());
        }
        if (employeeTO.getGrade() != null) {
            employeeEntity.setGrade(employeeTO.getGrade());
        }
        if (employeeTO.getVersion() != null) {
            employeeEntity.setVersion(employeeTO.getVersion());
        }


        employeeEntity = employeeDao.save(employeeEntity);

        return employeeMapper.mapEmployeeEntity2EmployeeTO(employeeEntity);

    }

    public void removeEmployeeFromDatabase(EmployeeTO employeeTO) {

        if (employeeDao.findOne(employeeTO.getId()) == null) {
            throw new NoSuchEmployeeIdInDatabaseException("No such employee.ID in database!");
        }
        employeeDao.delete(employeeTO.getId());

    }

    public List<EmployeeTO> findAllEmployeeList() {

        return employeeMapper.mapEmployeeEntityList2EmployeeTOList(employeeDao.findAll());

    }

    public EmployeeTO findOne(Long employeeId) {

        EmployeeEntity employeeEntity = employeeDao.findOne(employeeId);
        if (employeeEntity == null) {
            return null;
        }
        return employeeMapper.mapEmployeeEntity2EmployeeTO(employeeEntity);

    }

    public EmployeeEntity findOneEntity(Long employeeId) {
        return employeeDao.findOne(employeeId);
    }


}
