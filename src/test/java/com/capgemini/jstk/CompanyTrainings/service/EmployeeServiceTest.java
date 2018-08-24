package com.capgemini.jstk.CompanyTrainings.service;


import com.capgemini.jstk.CompanyTrainings.dao.EmployeeDao;
import com.capgemini.jstk.CompanyTrainings.enums.EmployeePosition;
import com.capgemini.jstk.CompanyTrainings.enums.Grade;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingCharacter;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingType;
import com.capgemini.jstk.CompanyTrainings.exceptions.NoSuchEmployeeIdInDatabaseException;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;
import com.capgemini.jstk.CompanyTrainings.types.builders.EmployeeTOBuilder;
import com.capgemini.jstk.CompanyTrainings.types.builders.TrainingTOBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.OptimisticLockException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    TrainingService trainingService;

    @Test
    public void shouldAddNewEmployeeToDatabase() {
        // given
        int startSize = employeeService.findAllEmployeeList().size();
        assertThat(employeeService.findAllEmployeeList().size() - startSize, is(0));
        // when
        employeeService.addEmployeeToDatabase(buildEmployeeTO());
        // then
        assertThat(employeeService.findAllEmployeeList().size() - startSize, is(1));

    }

    @Test
    public void shouldUpdateEmployeeInDatabase() {
        // given
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        // when
        employeeTO.setEmployeePosition(EmployeePosition.MANAGER);
        employeeService.updateEmployeeInDatabase(employeeTO);
        // then
        assertThat(employeeService.findOne(employeeTO.getId()).getEmployeePosition(), is(EmployeePosition.MANAGER));
    }

    @Test
    public void shouldCheckTimePersistAndUpdate() {
        // given
        // when
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        employeeTO.setGrade(Grade.FIFTH);
//        EmployeeEntity employeeEntity1 = employeeDao.findOne(employeeTO.getId());
        employeeService.updateEmployeeInDatabase(employeeTO);
//        EmployeeEntity employeeEntity2 = employeeDao.findOne(employeeTO.getId());
//        assertThat(employeeDao.findAll().size(), is(1));
        // then
//        assertThat(employeeEntity1.getCreateDate(), is(notNullValue()));
//        assertThat(employeeEntity1.getUpdateDate(), is(nullValue()));
//        assertThat(employeeEntity2.getCreateDate(), is(notNullValue()));
////        assertThat(employeeEntity2.getUpdateDate(), is(notNullValue()));
//        assertEquals(employeeEntity1.getCreateDate(), employeeEntity2.getCreateDate());
    }

    @Test
    public void shouldRemoveEmployeeFromDatabase() {
        // given
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        // when
        employeeService.removeEmployeeFromDatabase(employeeTO);
        // then
        assertThat(employeeService.findOne(employeeTO.getId()), is(nullValue()));

    }

    @Test
    public void shouldThrowNoSuchEmployeeInDatabaseException() {
        // given
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        employeeService.removeEmployeeFromDatabase(employeeTO);
        // when
        try {
            employeeService.removeEmployeeFromDatabase(employeeTO);
        } catch (NoSuchEmployeeIdInDatabaseException e) {
            //Exception no such employee in database
        }
    }

    @Test
    public void shouldThrowOptimisticLockingException() {
        // given
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        employeeTO.setEmployeePosition(EmployeePosition.ACCOUNTANT);
        EmployeeTO newEmployeeTO = employeeService.findOne(employeeTO.getId());
        newEmployeeTO.setEmployeePosition(EmployeePosition.MANAGER);
        employeeService.updateEmployeeInDatabase(employeeTO);
        // when
        try {
            employeeService.updateEmployeeInDatabase(newEmployeeTO);
            Assert.fail("Optimistic Locking doesnt work!");
        } catch (OptimisticLockException e) {
            // Test pass, Optimistic locking work!
        }
    }

    @Test
    public void shouldCascadeDontRemoveTrainingWithEmployeeFromDatabase() {
        // given
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        trainingService.addStudentToTraining(trainingTO, employeeTO);
        // when
        employeeService.removeEmployeeFromDatabase(employeeTO);
        // then
        assertThat(employeeService.findOne(employeeTO.getId()), is(nullValue()));
        assertThat(trainingService.findOne(trainingTO.getId()), is(notNullValue()));
        assertThat(trainingService.findSizeOfStudents(trainingTO.getId()), is(0));
    }

    @Test
    public void shouldFindNumerOfHoursEmployeeAsCoach() {

        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        // when
        trainingService.addCoachToTraining(trainingTO1, employeeTO);
        trainingService.addCoachToTraining(trainingTO2, employeeTO);
        // then
        assertThat(employeeService.findNumerOfHoursEmployeeAsCoach(employeeTO, 2018), is(8d));

    }

    @Test
    public void shouldFindNumberOfTrainingsByOneEmployeeInPeriodOfTime(){
        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTOInTime
                (LocalDate.of(2018,10,1), LocalDate.of(2018,10,5)));
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTOInTime
                (LocalDate.of(2018,10,3), LocalDate.of(2018,10,7)));
        TrainingTO trainingTO3 = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        trainingService.addStudentToTraining(trainingTO1, employeeTO);
        trainingService.addStudentToTraining(trainingTO2, employeeTO);

        // then
        assertThat(employeeService.findNumberOfTrainingsByOneEmployeeInPeriodOfTime(employeeTO,
                LocalDate.of(2018,10,1), LocalDate.of(2018,10,6)), is(1));
        assertThat(employeeService.findNumberOfTrainingsByOneEmployeeInPeriodOfTime(employeeTO,
                LocalDate.of(2018,10,1), LocalDate.of(2018,10,8)), is(2));
        assertThat(employeeService.findNumberOfTrainingsByOneEmployeeInPeriodOfTime(employeeTO,
                LocalDate.of(2018,10,10), LocalDate.of(2018,10,18)), is(0));

    }

    @Test
    public void shouldFindTotalCostOfTrainingsByEmployee(){
        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithCost(2000));
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithCost(5000));
        TrainingTO trainingTO3 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithCost(7000));
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        // when
        trainingService.addStudentToTraining(trainingTO1, employeeTO);
        trainingService.addStudentToTraining(trainingTO2, employeeTO);
        trainingService.addStudentToTraining(trainingTO3, employeeTO);
        // then
        assertThat(employeeService.findTotalcostOfTrainingsByEmployee(employeeTO), is(14000));

    }

    @Test
    public void shouldFindOneEmployeeWithLongestTimeOnTrainingsAsStudents(){
        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithDuration(20d));
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithDuration(20d));
        TrainingTO trainingTO3 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithDuration(20d));
        EmployeeTO employeeTO1 = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        EmployeeTO employeeTO2 = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        // when
        trainingService.addStudentToTraining(trainingTO1, employeeTO1);
        trainingService.addStudentToTraining(trainingTO1, employeeTO2);
        trainingService.addStudentToTraining(trainingTO2, employeeTO2);
        // then
        assertThat(employeeService.findEmployeesWithLongestTimeOnTrainingsAsStudents().size(), is(1));
        assertThat(employeeService.findEmployeesWithLongestTimeOnTrainingsAsStudents().iterator().next().getId(), is(employeeTO2.getId()));

    }
    @Test
    public void shouldFindTwoEmployeesWithLongestTimeOnTrainingsAsStudents(){
        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithDuration(30d));
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithDuration(20d));
        EmployeeTO employeeTO1 = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        EmployeeTO employeeTO2 = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        EmployeeTO employeeTO3 = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        // when
        trainingService.addStudentToTraining(trainingTO1, employeeTO1);
        trainingService.addStudentToTraining(trainingTO1, employeeTO2);
        trainingService.addStudentToTraining(trainingTO2, employeeTO2);
        trainingService.addStudentToTraining(trainingTO1, employeeTO3);
        trainingService.addStudentToTraining(trainingTO2, employeeTO3);
        // then
        List<EmployeeTO> employeeTOList = employeeService.findEmployeesWithLongestTimeOnTrainingsAsStudents();
        Iterator<EmployeeTO> iterator = employeeTOList.iterator();
        assertThat(employeeTOList.size(), is(2));
        assertThat(iterator.next().getId(), is(employeeTO2.getId()));
        assertThat(iterator.next().getId(), is(employeeTO3.getId()));

    }

    private EmployeeTO buildEmployeeTO() {
        return new EmployeeTOBuilder()
                .setId(1L)
                .setGrade(Grade.FIRST)
                .setEmployeePosition(EmployeePosition.DEALER)
                .setFirstName("Jan")
                .setLastName("Kowalski")
                .buildEmployeeTO();
    }

    private TrainingTO buildTrainingTO() {
        return new TrainingTOBuilder()
                .setId(1L)
                .setTrainingType(TrainingType.MANAGEMENT)
                .setTrainingName("StarterKit")
                .setTrainingCharacter(TrainingCharacter.EXTERNAL)
                .setTags("Java, Spring")
                .setStartDate(LocalDate.of(2018,12,4))
                .setEndDate(LocalDate.of(2018, 12, 4))
                .setDuration(4d)
                .setCostPerStudent(2000)
                .buildTrainingTO();

    }

    private TrainingTO buildTrainingTOInTime(LocalDate startTime, LocalDate endTime) {
        return new TrainingTOBuilder()
                .setId(1L)
                .setTrainingType(TrainingType.MANAGEMENT)
                .setTrainingName("StarterKit")
                .setTrainingCharacter(TrainingCharacter.EXTERNAL)
                .setTags("Java, Spring")
                .setStartDate(startTime)
                .setEndDate(endTime)
                .setDuration(4d)
                .setCostPerStudent(2000)
                .buildTrainingTO();

    }
    private TrainingTO buildTrainingTOWithDuration(Double duration) {
        return new TrainingTOBuilder()
                .setId(1L)
                .setTrainingType(TrainingType.MANAGEMENT)
                .setTrainingName("StarterKit")
                .setTrainingCharacter(TrainingCharacter.EXTERNAL)
                .setTags("Java, Spring")
                .setStartDate(LocalDate.of(2018, 12, 1))
                .setEndDate(LocalDate.of(2018, 12, 4))
                .setDuration(duration)
                .setCostPerStudent(5000)
                .buildTrainingTO();

    }
    private TrainingTO buildTrainingTOWithCost(Integer cost) {
        return new TrainingTOBuilder()
                .setId(1L)
                .setTrainingType(TrainingType.MANAGEMENT)
                .setTrainingName("Spring")
                .setTrainingCharacter(TrainingCharacter.EXTERNAL)
                .setTags("Java, Spring")
                .setStartDate(LocalDate.of(2018, 12, 1))
                .setEndDate(LocalDate.of(2018, 12, 4))
                .setDuration(4d)
                .setCostPerStudent(cost)
                .buildTrainingTO();

    }
}
