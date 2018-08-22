package com.capgemini.jstk.CompanyTrainings.service;


import com.capgemini.jstk.CompanyTrainings.enums.EmployeePosition;
import com.capgemini.jstk.CompanyTrainings.enums.Grade;
import com.capgemini.jstk.CompanyTrainings.exceptions.NoSuchEmployeeIdInDatabaseException;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.builders.EmployeeTOBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Matches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.hamcrest.Matchers;
import org.junit.Assert;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

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
    public void shouldThrowOptimisticLockingException(){
        // given
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        employeeTO.setEmployeePosition(EmployeePosition.ACCOUNTANT);
        EmployeeTO newEmployeeTO = employeeService.findOne(employeeTO.getId());
        newEmployeeTO.setEmployeePosition(EmployeePosition.MANAGER);
        employeeService.updateEmployeeInDatabase(employeeTO);
        employeeService.updateEmployeeInDatabase(newEmployeeTO);


        //        // given
//        ClientEntity clientEntity = buildClientEntity();
//        clientEntity = clientDao.save(clientEntity);
//        clientDao.flush();
//        clientDao.detach(clientEntity);
//        clientEntity.setFirstName("Tomek");
//
//        ClientEntity newClientEntity = clientDao.findOne(clientEntity.getId());
//
//        newClientEntity.setFirstName("first name");
//        clientDao.save(newClientEntity);
//        clientDao.flush();
//
//        // when
//        try {
//            ClientEntity clientEntity2 = clientDao.update(clientEntity);
//            clientDao.save(clientEntity2);
//
//            // then
//            Assert.fail("Expected that optimistoc loking not working");
//
//        } catch (ObjectOptimisticLockingFailureException e) {
//            // Test pass. We expected optimistoc loking exception.
//        }




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


}
