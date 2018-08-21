package com.capgemini.jstk.CompanyTrainings.types;

import com.capgemini.jstk.CompanyTrainings.enums.TrainingCharacter;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class TrainingTO {
    private Long version;
    private Long id;
    private String trainingName;
    private TrainingType trainingType;
    private Double duration;
    private TrainingCharacter trainingCharacter;
    private Date startDate;
    private Date endDate;
    private Integer costPerStudent;
    private String tags;

    public TrainingTO() {
    }

    public TrainingTO(Long version, Long id, String trainingName, TrainingType trainingType, Double duration, TrainingCharacter trainingCharacter, Date startDate, Date endDate, Integer costPerStudent, String tags) {
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
    }
}
