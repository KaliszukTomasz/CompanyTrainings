package com.capgemini.jstk.CompanyTrainings.types;

import com.capgemini.jstk.CompanyTrainings.enums.TrainingCharacter;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingStatus;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class TrainingTO {
    private Long version;
    private Long id;
    private String trainingName;
    private TrainingType trainingType;
    private Double duration;
    private TrainingCharacter trainingCharacter;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer costPerStudent;
    private String tags;
    private TrainingStatus trainingStatus;

    public TrainingTO() {
    }

    public TrainingTO(Long version, Long id, String trainingName, TrainingType trainingType,
                      Double duration, TrainingCharacter trainingCharacter, LocalDate startDate,
                      LocalDate endDate, Integer costPerStudent, String tags, TrainingStatus trainingStatus) {
        this.version = version;
        this.id = id;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.duration = duration;
        this.trainingCharacter = trainingCharacter;
        this.startDate = startDate;
        this.endDate = endDate;
        this.costPerStudent = costPerStudent;
        this.tags = tags;
        this.trainingStatus = trainingStatus;
    }
}
