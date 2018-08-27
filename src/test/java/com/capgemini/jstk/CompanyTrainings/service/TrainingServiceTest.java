package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.enums.Grade;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingCharacter;
import com.capgemini.jstk.CompanyTrainings.exceptions.*;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.ExternalCoachTO;
import com.capgemini.jstk.CompanyTrainings.types.SearchCriteriaObject;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class TrainingServiceTest extends AbstractTest {

    @Autowired
    TrainingService trainingService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ExternalCoachService externalCoachService;

    @Test
    public void shouldAddNewTrainingToDatabaseTest() {

        // given
        int startSize = trainingService.findAllTrainingList().size();
        assertThat(trainingService.findAllTrainingList().size() - startSize, is(0));

        // when
        trainingService.addTrainingTOToDatabase(buildTrainingTO());

        // then
        assertThat(trainingService.findAllTrainingList().size() - startSize, is(1));

    }

    @Test
    public void shouldUpdateTrainingInDatabaseTest() {

        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());

        // when
        trainingTO.setTrainingCharacter(TrainingCharacter.INTERNAL);
        trainingService.updateTrainingInDatabase(trainingTO);

        // then
        assertThat(trainingService.findOne(trainingTO.getId()).getTrainingCharacter(), is(TrainingCharacter.INTERNAL));

    }

    @Test
    public void shouldRemoveTrainingFromDatabaseTest() {

        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());

        // when
        trainingService.removeTrainingFromDatabase(trainingTO);

        // then
        assertThat(trainingService.findOne(trainingTO.getId()), is(nullValue()));

    }

    @Test
    public void shouldThrowNoSuchTrainingInDatabaseExceptionTest() {

        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        trainingService.removeTrainingFromDatabase(trainingTO);

        // when
        try {
            trainingService.removeTrainingFromDatabase(trainingTO);
            // then
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
    public void shouldThrowSerachCriteriaObjectIsNullExceptionTest() {
        // given
        SearchCriteriaObject searchCriteriaObject = null;
        // when
        try {
            trainingService.findTrainingsBySearchCriteria(searchCriteriaObject);
            Assert.fail("Exception wasn't thrown");
            // then
        } catch (SerachCriteriaObjectIsNullException e) {
            // test pass
        }

    }

    @Test
    public void shouldAddAndRemoveEmployeeToAndFromTrainingAsCoachTest() {

        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        trainingService.addCoachToTraining(trainingTO, employeeTO);
        assertThat(trainingService.findSizeOfCoaches(trainingTO.getId()), is(1));

        // when
        trainingService.removeEmployeeFromEmployeesAsCoaches(trainingTO, employeeTO);

        // then
        assertThat(trainingService.findSizeOfCoaches(trainingTO.getId()), is(0));
    }

    @Test
    public void shouldAddAndRemoveEmployeeToAndFromTrainingAsStudentTest() {

        // given
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        trainingService.addStudentToTraining(trainingTO, employeeTO);
        assertThat(trainingService.findSizeOfStudents(trainingTO.getId()), is(1));

        // when
        trainingService.removeEmployeeFromEmployeesAsStudents(trainingTO, employeeTO);

        // then
        assertThat(trainingService.findSizeOfStudents(trainingTO.getId()), is(0));
    }

    @Test
    public void shouldAddExternalCoachToTrainingTest() {

        // given
        ExternalCoachTO externalCoachTO = externalCoachService.addExternalCoachToDatabase(buildCoachTO());
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        assertThat(trainingService.findSizeOfExternalCoach(trainingTO.getId()), is(0));

        // when
        trainingService.addExternalCoachToTraining(trainingTO, externalCoachTO);

        // then
        assertThat(trainingService.findSizeOfExternalCoach(trainingTO.getId()), is(1));

    }

    @Test
    public void shouldRemoveExternalCoachFromTrainingTest() {
        // given
        ExternalCoachTO externalCoachTO = externalCoachService.addExternalCoachToDatabase(buildCoachTO());
        TrainingTO trainingTO = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        trainingService.addExternalCoachToTraining(trainingTO, externalCoachTO);
        assertThat(trainingService.findSizeOfExternalCoach(trainingTO.getId()), is(1));

        // when
        trainingService.removeExternalCoachFromTraining(trainingTO, externalCoachTO);
        // then
        assertThat(trainingService.findSizeOfExternalCoach(trainingTO.getId()), is(0));


    }
}
