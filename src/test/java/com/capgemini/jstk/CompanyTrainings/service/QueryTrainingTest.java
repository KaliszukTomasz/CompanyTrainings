package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.enums.TrainingType;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QueryTest extends AbstractTest {

    @Autowired
    TrainingService trainingService;
    @Autowired
    EmployeeService employeeService;

    @Test
    public void shouldFindTrainingsWithTheHighestEdition() {

        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        TrainingTO trainingTO3 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        TrainingTO trainingTO4 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Spring", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Spring", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Spring", TrainingType.TECHNICAL));

        List<TrainingTO> trainingTOList = trainingService.findTrainingsWithTheHighestEdition();
        Iterator<TrainingTO> iterator = trainingTOList.iterator();
        assertThat(trainingTOList.size(), is(4));
        assertThat(iterator.next().getId(), is(trainingTO1.getId()));
        assertThat(iterator.next().getId(), is(trainingTO2.getId()));
        assertThat(iterator.next().getId(), is(trainingTO3.getId()));
        assertThat(iterator.next().getId(), is(trainingTO4.getId()));


    }

    @Test
    public void shouldFindDwoTitlesTrainingsWithTheHighestEdition() {

        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Spring", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Spring", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("JavaScript", TrainingType.TECHNICAL));

        List<TrainingTO> trainingTOList = trainingService.findTrainingsWithTheHighestEdition();
        Iterator<TrainingTO> iterator = trainingTOList.iterator();
        assertThat(trainingTOList.size(), is(4));
        assertThat(iterator.next().getTrainingName(), is("Java"));
        assertThat(iterator.next().getTrainingName(), is("Java"));
        assertThat(iterator.next().getTrainingName(), is("Spring"));
        assertThat(iterator.next().getTrainingName(), is("Spring"));
    }
}
