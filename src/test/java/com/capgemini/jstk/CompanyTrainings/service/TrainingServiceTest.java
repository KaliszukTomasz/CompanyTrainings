package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.dao.TrainingDao;
import com.capgemini.jstk.CompanyTrainings.enums.EmployeePosition;
import com.capgemini.jstk.CompanyTrainings.enums.Grade;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingCharacter;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingType;
import com.capgemini.jstk.CompanyTrainings.exceptions.*;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.SearchCriteriaObject;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;
import com.capgemini.jstk.CompanyTrainings.types.builders.EmployeeTOBuilder;
import com.capgemini.jstk.CompanyTrainings.types.builders.SearchCriteriaObjectBuilder;
import com.capgemini.jstk.CompanyTrainings.types.builders.TrainingTOBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class TrainingServiceTest {

    @Autowired
    TrainingService trainingService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    TrainingDao trainingDao;

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

    @Test
    public void shouldAddEmployeeToTrainingCoachesTest() {
        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        assertThat(trainingService.findSizeOfCoaches(trainingTO.getId()), is(0));
        // when
        trainingService.addCoachToTraining(trainingTO, employeeTO);
        // then
        assertThat(trainingService.findSizeOfCoaches(trainingTO.getId()), is(1));
    }

    @Test
    public void shouldNOTAddEmployeeToTrainingCoachesWhenIsStudentAlreadyTest() {
        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        trainingService.addStudentToTraining(trainingTO, employeeTO);
        // when
        try {
            trainingService.addCoachToTraining(trainingTO, employeeTO);
            // then
            Assert.fail("Expected that employee is student and coach");
        } catch (EmployeeIsAlreadyStudentDuringThisTrainingException e) {
            assertThat(trainingService.findSizeOfCoaches(trainingTO.getId()), is(0));
        }

    }

    @Test
    public void shouldNOTAddEmployeeToTrainingStudentsWhenIsCoachAlreadyTest() {
        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        trainingService.addCoachToTraining(trainingTO, employeeTO);
        // when
        try {
            trainingService.addStudentToTraining(trainingTO, employeeTO);
            // then
            Assert.fail("Expected that employee is student and coach");
        } catch (EmployeeIsAlreadyCoachDuringThisTrainingException e) {
            assertThat(trainingService.findSizeOfStudents(trainingTO.getId()), is(0));
        }

    }

    @Test
    public void shouldAddEmployeeToTrainingStudentsTest() {
        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        assertThat(trainingService.findSizeOfCoaches(trainingTO.getId()), is(0));
        // when
        trainingService.addStudentToTraining(trainingTO, employeeTO);
        // then
        assertThat(trainingService.findSizeOfStudents(trainingTO.getId()), is(1));
    }

    @Test
    public void shouldNOTAddEmployeeToFourthTrainingTest() {
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
        try {
            trainingService.addStudentToTraining(trainingTO4, employeeTO);
            // then
            Assert.fail("Expected that employee was added to fourth training in this year");
        } catch (TooManyTrainingsInThisYearException e) {
            // test pass
        }

    }

    @Test
    public void shouldAddEmployeeToFourthTrainingWhenGradeOverThirdGradeTest() {
        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        TrainingTO trainingTO3 = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        TrainingTO trainingTO4 = trainingService.addTrainingTOToDatabase(buildTrainingTO());

        EmployeeTO employeeTO = buildEmployeeTO();
        employeeTO.setGrade(Grade.FOURTH);
        employeeTO = employeeService.addEmployeeToDatabase(employeeTO);
        trainingService.addStudentToTraining(trainingTO1, employeeTO);
        trainingService.addStudentToTraining(trainingTO2, employeeTO);
        trainingService.addStudentToTraining(trainingTO3, employeeTO);

        // when
        try {
            trainingService.addStudentToTraining(trainingTO4, employeeTO);
            // then
            // test pass
        } catch (TooManyTrainingsInThisYearException e) {
            Assert.fail("Expected that employee was added to fourth training in this year");
        }
    }

    @Test
    public void shouldNOTAddEmployeeToTrainingWhenGradeOverThirdGradeAndBudgetOver50KTest() {
        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(build20KCostTrainingTO());
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(build20KCostTrainingTO());
        TrainingTO trainingTO3 = trainingService.addTrainingTOToDatabase(build20KCostTrainingTO());

        EmployeeTO employeeTO = buildEmployeeTO();
        employeeTO.setGrade(Grade.FOURTH);
        employeeTO = employeeService.addEmployeeToDatabase(employeeTO);
        trainingService.addStudentToTraining(trainingTO1, employeeTO);
        trainingService.addStudentToTraining(trainingTO2, employeeTO);

        // when
        try {
            trainingService.addStudentToTraining(trainingTO3, employeeTO);
            Assert.fail("Expected that employee was added to fourth training in this year");
        } catch (Budher50KLimitForEmployeeOverThirdGradeException e) {
            // then
            // test pass
        }
    }

    @Test
    public void shouldNOTAddEmployeeToTrainingWhenGradeUnderFourthGradeAndBudgetOver15KTest() {
        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(build20KCostTrainingTO());

        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());

        // when
        try {
            trainingService.addStudentToTraining(trainingTO, employeeTO);
            Assert.fail("Expected that employee was added to fourth training in this year");
        } catch (Budget15KLimitForEmployeeUnderFourthGradeException e) {
            // then
            // test pass
        }
    }

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

    private TrainingTO buildTrainingTO() {
        return new TrainingTOBuilder()
                .setId(1L)
                .setTrainingType(TrainingType.MANAGEMENT)
                .setTrainingName("StarterKit")
                .setTrainingCharacter(TrainingCharacter.EXTERNAL)
                .setTags("Java, Spring")
                .setStartDate(LocalDate.of(2018, 12, 1))
                .setEndDate(LocalDate.of(2018, 12, 4))
                .setDuration(4d)
                .setCostPerStudent(5000)
                .buildTrainingTO();

    }

    private TrainingTO build20KCostTrainingTO() {
        return new TrainingTOBuilder()
                .setId(1L)
                .setTrainingType(TrainingType.MANAGEMENT)
                .setTrainingName("StarterKit")
                .setTrainingCharacter(TrainingCharacter.EXTERNAL)
                .setTags("Java, Spring")
                .setStartDate(LocalDate.of(2018, 12, 1))
                .setEndDate(LocalDate.of(2018, 12, 4))
                .setDuration(4d)
                .setCostPerStudent(20000)
                .buildTrainingTO();
    }


    private TrainingTO buildTrainingTOWithTitleAndType(String title, TrainingType trainingType) {
        return new TrainingTOBuilder()
                .setId(1L)
                .setTrainingType(trainingType)
                .setTrainingName(title)
                .setTrainingCharacter(TrainingCharacter.EXTERNAL)
                .setTags("Java, Spring")
                .setStartDate(LocalDate.of(2018, 12, 1))
                .setEndDate(LocalDate.of(2018, 12, 4))
                .setDuration(4d)
                .setCostPerStudent(20000)
                .buildTrainingTO();
    }

    private TrainingTO buildTrainingTOWithDate(LocalDate startDate, LocalDate endDate) {
        return new TrainingTOBuilder()
                .setId(1L)
                .setTrainingType(TrainingType.TECHNICAL)
                .setTrainingName("STARTER")
                .setTrainingCharacter(TrainingCharacter.EXTERNAL)
                .setTags("Java, Spring")
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setDuration(4d)
                .setCostPerStudent(20000)
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
