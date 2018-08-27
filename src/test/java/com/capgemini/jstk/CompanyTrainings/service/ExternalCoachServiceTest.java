package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.exceptions.NoSuchExternalCoachIdInDatabaseException;
import com.capgemini.jstk.CompanyTrainings.mappers.ExternalCoachMapper;
import com.capgemini.jstk.CompanyTrainings.types.ExternalCoachTO;
import com.capgemini.jstk.CompanyTrainings.types.builders.ExternalCoachTOBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class ExternalCoachServiceTest extends AbstractTest {

    @Autowired
    ExternalCoachService externalCoachService;

    @Test
    public void shouldAddNewEmployeeToDatabaseTest() {

        // given
        int startSize = externalCoachService.findAllCoachList().size();
        assertThat(externalCoachService.findAllCoachList().size() - startSize, is(0));

        // when
        externalCoachService.addExternalCoachToDatabase(buildCoachTO());

        // then
        assertThat(externalCoachService.findAllCoachList().size() - startSize, is(1));

    }

    @Test
    public void shouldUpdateEmployeeInDatabaseTest() {

        // given
        ExternalCoachTO externalCoachTO = externalCoachService.addExternalCoachToDatabase(buildCoachTO());

        // when
        externalCoachTO.setCompany("Capgemini Wroclaw");
        externalCoachService.updateExternalCoachInDatabase(externalCoachTO);

        // then
        assertThat(externalCoachService.findOne(externalCoachTO.getId()).getCompany(), is("Capgemini Wroclaw"));
    }

    @Test
    public void shouldRemoveEmployeeFromDatabaseTest() {

        // given
        ExternalCoachTO externalCoachTO = externalCoachService.addExternalCoachToDatabase(buildCoachTO());

        // when
        externalCoachService.removeExternalCoachFromDatabase(externalCoachTO);

        // then
        assertThat(externalCoachService.findOne(externalCoachTO.getId()), is(nullValue()));

    }

    @Test
    public void shouldThrowNoSuchExternalCoachInDatabaseExceptionTest() {

        // given
        ExternalCoachTO externalCoachTO = externalCoachService.addExternalCoachToDatabase(buildCoachTO());
        externalCoachService.removeExternalCoachFromDatabase(externalCoachTO);

        // when
        try {
            externalCoachService.removeExternalCoachFromDatabase(externalCoachTO);
            Assert.fail("Exception wasn't thrown");
            // then
        } catch (NoSuchExternalCoachIdInDatabaseException e) {
            //Exception no such employee in database
        }
    }


}
