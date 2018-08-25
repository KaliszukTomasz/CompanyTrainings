package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.enums.TrainingType;
import com.capgemini.jstk.CompanyTrainings.types.SearchCriteriaObject;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;
import com.capgemini.jstk.CompanyTrainings.types.builders.SearchCriteriaObjectBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QueryTrainingTest extends AbstractTest {

    @Autowired
    TrainingService trainingService;
    @Autowired
    EmployeeService employeeService;

    @Test
    public void shouldFindTrainingsByCriteriaQueryWithTitleAndTypeTest() {

        // given
        SearchCriteriaObject searchCriteriaObject = new SearchCriteriaObjectBuilder()
                .setMinCost(null)
                .setMaxCost(null)
                .setTag(null)
                .setTitle("Java")
                .setTrainingDate(null)
                .setTrainingType(TrainingType.TECHNICAL)
                .buildSearchCriteriaObject();
        int startSize = trainingService.findTrainingsBySearchCriteria(searchCriteriaObject).size();
        trainingService.addTrainingTOToDatabase(build20KCostTrainingTO());
        trainingService.addTrainingTOToDatabase(buildTrainingTO());

        // when
        assertThat(trainingService.findTrainingsBySearchCriteria(searchCriteriaObject).size() - startSize, is(0));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));

        // then
        assertThat(trainingService.findTrainingsBySearchCriteria(searchCriteriaObject).size() - startSize, is(1));
    }

    @Test
    public void shouldFindTrainingsByCriteriaQueryWithMinAndMaxCostTest() {

        // given
        SearchCriteriaObject searchCriteriaObject = new SearchCriteriaObjectBuilder()
                .setMinCost(1000)
                .setMaxCost(3000)
                .buildSearchCriteriaObject();
        int startSize = trainingService.findTrainingsBySearchCriteria(searchCriteriaObject).size();
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithCost(5000));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithCost(5000));

        // when
        assertThat(trainingService.findTrainingsBySearchCriteria(searchCriteriaObject).size() - startSize, is(0));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithCost(2000));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithCost(3000));

        // then
        assertThat(trainingService.findTrainingsBySearchCriteria(searchCriteriaObject).size() - startSize, is(2));
    }

    @Test
    public void shouldFindTrainingsByCriteriaQueryWithDateTest() {

        // given
        SearchCriteriaObject searchCriteriaObject = new SearchCriteriaObjectBuilder()
                .setTrainingDate(LocalDate.of(2018,5,6))
                .buildSearchCriteriaObject();
        int startSize = trainingService.findTrainingsBySearchCriteria(searchCriteriaObject).size();

        // when
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithDate(LocalDate.of(2018,05,05), LocalDate.of(2018,05,10)));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithDate(LocalDate.of(2018,05,07), LocalDate.of(2018,05,10)));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithDate(LocalDate.of(2018,06,05), LocalDate.of(2018,06,10)));

        // then
        assertThat(trainingService.findTrainingsBySearchCriteria(searchCriteriaObject).size() - startSize, is(1));
        searchCriteriaObject.setTrainingDate(LocalDate.of(2018,5,9));
        assertThat(trainingService.findTrainingsBySearchCriteria(searchCriteriaObject).size() - startSize, is(2));
        searchCriteriaObject.setTrainingDate(LocalDate.of(2018,5,20));
        assertThat(trainingService.findTrainingsBySearchCriteria(searchCriteriaObject).size() - startSize, is(0));
    }

    @Test
    public void shouldFindTrainingsWithTheHighestEditionTest() {

        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        TrainingTO trainingTO3 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        TrainingTO trainingTO4 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Spring", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Spring", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Spring", TrainingType.TECHNICAL));

        // when
        List<TrainingTO> trainingTOList = trainingService.findTrainingsWithTheHighestEdition();
        Iterator<TrainingTO> iterator = trainingTOList.iterator();

        // then
        assertThat(trainingTOList.size(), is(4));
        assertThat(iterator.next().getId(), is(trainingTO1.getId()));
        assertThat(iterator.next().getId(), is(trainingTO2.getId()));
        assertThat(iterator.next().getId(), is(trainingTO3.getId()));
        assertThat(iterator.next().getId(), is(trainingTO4.getId()));
    }

    @Test
    public void shouldFindDwoTitlesTrainingsWithTheHighestEditionTest() {

        // given
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Spring", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Spring", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("JavaScript", TrainingType.TECHNICAL));

        // when
        List<TrainingTO> trainingTOList = trainingService.findTrainingsWithTheHighestEdition();
        Iterator<TrainingTO> iterator = trainingTOList.iterator();

        // then
        assertThat(trainingTOList.size(), is(4));
        assertThat(iterator.next().getTrainingName(), is("Java"));
        assertThat(iterator.next().getTrainingName(), is("Java"));
        assertThat(iterator.next().getTrainingName(), is("Spring"));
        assertThat(iterator.next().getTrainingName(), is("Spring"));
    }

    @Test
    public void shouldFindOnlyOneTileTrainingWithTheHighestEditionCauseCanceledStatusTest() {

        // given
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Java", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Spring", TrainingType.TECHNICAL));
        TrainingTO trainingTO =trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("Spring", TrainingType.TECHNICAL));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithTitleAndType("JavaScript", TrainingType.TECHNICAL));

        cancelTraining(trainingTO, trainingService);

        // when
        List<TrainingTO> trainingTOList = trainingService.findTrainingsWithTheHighestEdition();
        Iterator<TrainingTO> iterator = trainingTOList.iterator();

        // then
        assertThat(trainingTOList.size(), is(2));
        assertThat(iterator.next().getTrainingName(), is("Java"));
        assertThat(iterator.next().getTrainingName(), is("Java"));
    }
}
