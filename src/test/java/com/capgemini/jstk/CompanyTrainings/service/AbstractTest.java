package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.enums.*;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;
import com.capgemini.jstk.CompanyTrainings.types.builders.EmployeeTOBuilder;
import com.capgemini.jstk.CompanyTrainings.types.builders.TrainingTOBuilder;

import java.time.LocalDate;

public class AbstractTest {

    protected EmployeeTO buildEmployeeTO() {
        return new EmployeeTOBuilder()
                .setId(1L)
                .setGrade(Grade.FIRST)
                .setEmployeePosition(EmployeePosition.DEALER)
                .setFirstName("Jan")
                .setLastName("Kowalski")
                .buildEmployeeTO();
    }

    protected TrainingTO buildTrainingTO() {
        return new TrainingTOBuilder()
                .setId(1L)
                .setTrainingType(TrainingType.MANAGEMENT)
                .setTrainingName("StarterKit")
                .setTrainingCharacter(TrainingCharacter.EXTERNAL)
                .setTags("Java, Spring")
                .setStartDate(LocalDate.of(2018, 12, 4))
                .setEndDate(LocalDate.of(2018, 12, 4))
                .setDuration(4d)
                .setCostPerStudent(2000)
                .buildTrainingTO();

    }

    protected TrainingTO buildCanceledTrainingTO() {
        return new TrainingTOBuilder()
                .setId(1L)
                .setTrainingType(TrainingType.MANAGEMENT)
                .setTrainingName("StarterKit")
                .setTrainingCharacter(TrainingCharacter.EXTERNAL)
                .setTags("Java, Spring")
                .setStartDate(LocalDate.of(2018, 12, 4))
                .setEndDate(LocalDate.of(2018, 12, 4))
                .setDuration(4d)
                .setCostPerStudent(2000)
                .setTrainingStatus(TrainingStatus.CANCELED)
                .buildTrainingTO();

    }

    protected TrainingTO buildTrainingTOInTime(LocalDate startTime, LocalDate endTime) {
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

    protected TrainingTO buildTrainingTOWithDuration(Double duration) {
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

    protected TrainingTO buildTrainingTOWithCost(Integer cost) {
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


    protected TrainingTO build20KCostTrainingTO() {
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


    protected TrainingTO buildTrainingTOWithTitleAndType(String title, TrainingType trainingType) {
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

    protected TrainingTO buildTrainingTOWithDate(LocalDate startDate, LocalDate endDate) {
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

    protected TrainingTO cancelTraining(TrainingTO trainingTO, TrainingService trainingService) {
        trainingTO.setTrainingStatus(TrainingStatus.CANCELED);
        return trainingService.updateTrainingInDatabase(trainingTO);
    }
}
