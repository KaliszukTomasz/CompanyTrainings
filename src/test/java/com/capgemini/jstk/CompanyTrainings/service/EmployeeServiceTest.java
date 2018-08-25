package com.capgemini.jstk.CompanyTrainings.service;


import com.capgemini.jstk.CompanyTrainings.dao.EmployeeDao;
import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import com.capgemini.jstk.CompanyTrainings.enums.EmployeePosition;
import com.capgemini.jstk.CompanyTrainings.enums.Grade;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingStatus;
import com.capgemini.jstk.CompanyTrainings.exceptions.CanceledTrainignStatusCantBeChangedException;
import com.capgemini.jstk.CompanyTrainings.exceptions.EmployeeCantBeAddedToCanceledTrainingException;
import com.capgemini.jstk.CompanyTrainings.exceptions.NoSuchEmployeeIdInDatabaseException;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.OptimisticLockException;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class EmployeeServiceTest extends AbstractTest {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    TrainingService trainingService;

    @Test
    public void shouldAddNewEmployeeToDatabaseTest() {

        // given
        int startSize = employeeService.findAllEmployeeList().size();
        assertThat(employeeService.findAllEmployeeList().size() - startSize, is(0));

        // when
        employeeService.addEmployeeToDatabase(buildEmployeeTO());

        // then
        assertThat(employeeService.findAllEmployeeList().size() - startSize, is(1));

    }

    @Test
    public void shouldUpdateEmployeeInDatabaseTest() {

        // given
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());

        // when
        employeeTO.setEmployeePosition(EmployeePosition.MANAGER);
        employeeService.updateEmployeeInDatabase(employeeTO);

        // then
        assertThat(employeeService.findOne(employeeTO.getId()).getEmployeePosition(), is(EmployeePosition.MANAGER));
    }

    @Test
    public void shouldCheckTimePersistAndUpdateTest() {

        // given
        int startSize = employeeDao.findAll().size();

        // when
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        employeeTO.setGrade(Grade.FIFTH);
        EmployeeEntity employeeEntity1 = employeeDao.findOne(employeeTO.getId());
        employeeService.updateEmployeeInDatabase(employeeTO);
        EmployeeEntity employeeEntity2 = employeeDao.findOne(employeeTO.getId());
        assertThat(employeeDao.findAll().size() - startSize, is(1));

        // then
        assertThat(employeeEntity1.getCreateDate(), is(notNullValue()));
        assertThat(employeeEntity1.getUpdateDate(), is(nullValue()));
        assertThat(employeeEntity2.getCreateDate(), is(notNullValue()));
        assertThat(employeeEntity2.getUpdateDate(), is(notNullValue()));
        assertEquals(employeeEntity1.getCreateDate(), employeeEntity2.getCreateDate());
    }

    @Test
    public void shouldRemoveEmployeeFromDatabaseTest() {

        // given
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());

        // when
        employeeService.removeEmployeeFromDatabase(employeeTO);

        // then
        assertThat(employeeService.findOne(employeeTO.getId()), is(nullValue()));

    }

    @Test
    public void shouldThrowNoSuchEmployeeInDatabaseExceptionTest() {

        // given
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        employeeService.removeEmployeeFromDatabase(employeeTO);

        // when
        try {
            employeeService.removeEmployeeFromDatabase(employeeTO);
            Assert.fail("Exception wasn't thrown");
        // then
        } catch (NoSuchEmployeeIdInDatabaseException e) {
            //Exception no such employee in database
        }
    }

    @Test
    public void shouldThrowOptimisticLockingExceptionTest() {

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
        // then
        } catch (OptimisticLockException e) {
            // Test pass, Optimistic locking work!
        }
    }

    @Test
    public void shouldCascadeDontRemoveTrainingWithEmployeeFromDatabaseTest() {

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
    public void shouldThrowExceptionCanceledTrainingStatusCantBeChangedExceptionTest() {

        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildCanceledTrainingTO());
        trainingTO.setTrainingStatus(TrainingStatus.PLANNED);

        // when
        try {
            trainingService.updateTrainingInDatabase(trainingTO);
            Assert.fail("exception wasn't thrown");
            // then
        } catch (CanceledTrainignStatusCantBeChangedException e) {
            // test pass
        }
    }

    @Test
    public void shouldThrowEmployeeCantBeAddedToCanceledTrainingTest(){

        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildCanceledTrainingTO());
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());

        // when
        try {
            trainingService.addStudentToTraining(trainingTO, employeeTO);
            Assert.fail("exception wasn't thrown");
        // then
        }catch(EmployeeCantBeAddedToCanceledTrainingException e){
            // test pass
        }
    }

    @Test
    public void shouldntFindCanceledTrainingToLimitOfTrainingsByEmployeeTest(){

        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        TrainingTO trainingTO3 = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        TrainingTO trainingTO4 = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        trainingService.addStudentToTraining(trainingTO1, employeeTO);
        trainingService.addStudentToTraining(trainingTO2, employeeTO);
        trainingService.addStudentToTraining(trainingTO3, employeeTO);

        // when
        trainingTO3.setTrainingStatus(TrainingStatus.CANCELED);
        trainingService.updateTrainingInDatabase(trainingTO3);

        // then
        trainingService.addStudentToTraining(trainingTO4, employeeTO);
        // test pass -> student was added to 4. training (3. is canceled)
    }



}
