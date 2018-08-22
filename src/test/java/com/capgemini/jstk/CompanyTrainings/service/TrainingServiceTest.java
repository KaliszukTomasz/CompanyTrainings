package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.enums.TrainingCharacter;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingType;
import com.capgemini.jstk.CompanyTrainings.exceptions.NoSuchTrainingIdInDatabaseException;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;
import com.capgemini.jstk.CompanyTrainings.types.builders.TrainingTOBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class TrainingServiceTest {

    @Autowired
    TrainingService trainingService;
    @Test
    public void shouldAddNewTrainingToDatabase() {
        // given
        int startSize = trainingService.findAllTrainingList().size();
        assertThat(trainingService.findAllTrainingList().size() - startSize, is(0));
        // when
        trainingService.addTrainingTOToDatabase(buildTrainingTO());
        // then
        assertThat(trainingService.findAllTrainingList().size() - startSize, is(1));

    }

    @Test
    public void shouldUpdateTrainingInDatabase() {
        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        // when
        trainingTO.setTrainingCharacter(TrainingCharacter.INTERNAL);
        trainingService.updateTrainingInDatabase(trainingTO);
        // then
        assertThat(trainingService.findOne(trainingTO.getId()).getTrainingCharacter(), is(TrainingCharacter.INTERNAL));

    }

    @Test
    public void shouldRemoveTrainingFromDatabase() {
        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        // when
        trainingService.removeTrainingFromDatabase(trainingTO);
        // then
        assertThat(trainingService.findOne(trainingTO.getId()), is(nullValue()));

    }

    @Test
    public void shouldThrowNoSuchTrainingInDatabaseException() {
        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        trainingService.removeTrainingFromDatabase(trainingTO);
        // when
        try {
            trainingService.removeTrainingFromDatabase(trainingTO);
        } catch (NoSuchTrainingIdInDatabaseException e) {
            //Exception no such training in database
        }
    }

    private TrainingTO buildTrainingTO() {
        return new TrainingTOBuilder()
                .setId(1L)
                .setTrainingType(TrainingType.MANAGEMENT)
                .setTrainingName("StarterKit")
                .setTrainingCharacter(TrainingCharacter.EXTERNAL)
                .setTags("Java, Spring")
                .setStartDate(new Date(2018, 12,1))
                .setEndDate(new Date(2018,12,4))
                .setDuration(4d)
                .setCostPerStudent(2000)
                .buildTrainingTO();

    }
}
