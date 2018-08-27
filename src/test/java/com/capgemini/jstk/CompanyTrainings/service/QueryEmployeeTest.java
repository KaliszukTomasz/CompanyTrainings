package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.service.impl.EmployeeServiceImpl;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;
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
public class QueryEmployeeTest extends AbstractTest {

    @Autowired
    TrainingService trainingService;
    @Autowired
    EmployeeService employeeService;

    @Test
    public void shouldFindNumerOfHoursEmployeeAsCoachInYearTest() {

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
    public void shouldFindNumerOfHoursEmployeeAsCoachInYearWithoutTrainingsCanceledTest() {

        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        TrainingTO trainingTO3 = trainingService.addTrainingTOToDatabase(buildTrainingTO());
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());

        trainingService.addCoachToTraining(trainingTO1, employeeTO);
        trainingService.addCoachToTraining(trainingTO2, employeeTO);
        trainingService.addCoachToTraining(trainingTO3, employeeTO);

        // when
        cancelTraining(trainingTO2, trainingService);

        // then
        assertThat(employeeService.findNumerOfHoursEmployeeAsCoach(employeeTO, 2018), is(8d));

    }

    @Test
    public void shouldFindNumberOfTrainingsByOneEmployeeInPeriodOfTimeTest() {

        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTOInTime
                (LocalDate.of(2018, 10, 1), LocalDate.of(2018, 10, 5)));
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTOInTime
                (LocalDate.of(2018, 10, 3), LocalDate.of(2018, 10, 7)));
        trainingService.addTrainingTOToDatabase(buildTrainingTO());
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());

        trainingService.addStudentToTraining(trainingTO1, employeeTO);
        trainingService.addStudentToTraining(trainingTO2, employeeTO);

        // then
        assertThat(employeeService.findNumberOfTrainingsByOneEmployeeInPeriodOfTime(employeeTO,
                LocalDate.of(2018, 10, 1), LocalDate.of(2018, 10, 6)), is(1));
        assertThat(employeeService.findNumberOfTrainingsByOneEmployeeInPeriodOfTime(employeeTO,
                LocalDate.of(2018, 10, 1), LocalDate.of(2018, 10, 8)), is(2));
        assertThat(employeeService.findNumberOfTrainingsByOneEmployeeInPeriodOfTime(employeeTO,
                LocalDate.of(2018, 10, 10), LocalDate.of(2018, 10, 18)), is(0));

    }

    @Test
    public void shouldFindNumberOfTrainingsByOneEmployeeInPeriodOfTimeWithoutCanceledTrainingTest() {

        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTOInTime
                (LocalDate.of(2018, 10, 1), LocalDate.of(2018, 10, 5)));
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTOInTime
                (LocalDate.of(2018, 10, 3), LocalDate.of(2018, 10, 7)));
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        trainingService.addStudentToTraining(trainingTO1, employeeTO);
        trainingService.addStudentToTraining(trainingTO2, employeeTO);

        // when
        cancelTraining(trainingTO1, trainingService);

        // then
        assertThat(employeeService.findNumberOfTrainingsByOneEmployeeInPeriodOfTime(employeeTO,
                LocalDate.of(2018, 10, 1), LocalDate.of(2018, 10, 6)), is(0));
        assertThat(employeeService.findNumberOfTrainingsByOneEmployeeInPeriodOfTime(employeeTO,
                LocalDate.of(2018, 10, 1), LocalDate.of(2018, 10, 8)), is(1));

    }

    @Test
    public void shouldFindTotalCostOfTrainingsByEmployeeTest() {

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
    public void shouldFindTotalCostOfTrainingsByEmployeeWithoutCanceledTrainingTest() {

        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithCost(2000));
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithCost(5000));
        TrainingTO trainingTO3 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithCost(7000));
        EmployeeTO employeeTO = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        trainingService.addStudentToTraining(trainingTO1, employeeTO);
        trainingService.addStudentToTraining(trainingTO2, employeeTO);
        trainingService.addStudentToTraining(trainingTO3, employeeTO);

        // when
        cancelTraining(trainingTO3, trainingService);

        // then
        assertThat(employeeService.findTotalcostOfTrainingsByEmployee(employeeTO), is(7000));

    }

    @Test
    public void shouldFindOneEmployeeWithLongestTimeOnTrainingsAsStudentsTest() {

        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithDuration(20d));
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithDuration(20d));
        trainingService.addTrainingTOToDatabase(buildTrainingTOWithDuration(20d));
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
    public void shouldFindTwoEmployeesWithLongestTimeOnTrainingsAsStudentsTest() {

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

    @Test
    public void shouldFindOnlyOneEmployeeWithLongestTimeOnTrainingsAsStudentsCauseCanceledTest() {

        // given
        TrainingTO trainingTO1 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithDuration(30d));
        TrainingTO trainingTO2 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithDuration(20d));
        TrainingTO trainingTO3 = trainingService.addTrainingTOToDatabase(buildTrainingTOWithDuration(20d));
        EmployeeTO employeeTO1 = employeeService.addEmployeeToDatabase(buildEmployeeTO());
        EmployeeTO employeeTO2 = employeeService.addEmployeeToDatabase(buildEmployeeTO());

        // when
        trainingService.addStudentToTraining(trainingTO1, employeeTO2);
        trainingService.addStudentToTraining(trainingTO2, employeeTO2);
        trainingService.addStudentToTraining(trainingTO1, employeeTO1);
        trainingService.addStudentToTraining(trainingTO3, employeeTO1);

        cancelTraining(trainingTO3, trainingService);

        // then
        List<EmployeeTO> employeeTOList = employeeService.findEmployeesWithLongestTimeOnTrainingsAsStudents();
        assertThat(employeeTOList.size(), is(1));
        assertThat(employeeTOList.iterator().next().getId(), is(employeeTO2.getId()));

    }
}
