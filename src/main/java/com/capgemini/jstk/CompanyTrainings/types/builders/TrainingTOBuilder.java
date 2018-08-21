package com.capgemini.jstk.CompanyTrainings.types.builders;

import com.capgemini.jstk.CompanyTrainings.enums.TrainingCharacter;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingType;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;

import java.util.Date;

public class TrainingTOBuilder {
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

    public TrainingTOBuilder setVersion(Long version) {
        this.version = version;
        return this;
    }

    public TrainingTOBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public TrainingTOBuilder setTrainingName(String trainingName) {
        this.trainingName = trainingName;
        return this;
    }

    public TrainingTOBuilder setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
        return this;
    }

    public TrainingTOBuilder setDuration(Double duration) {
        this.duration = duration;
        return this;
    }

    public TrainingTOBuilder setTrainingCharacter(TrainingCharacter trainingCharacter) {
        this.trainingCharacter = trainingCharacter;
        return this;
    }

    public TrainingTOBuilder setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public TrainingTOBuilder setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public TrainingTOBuilder setCostPerStudent(Integer costPerStudent) {
        this.costPerStudent = costPerStudent;
        return this;
    }

    public TrainingTOBuilder setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public TrainingTO buildTrainingTO() {
        return new TrainingTO(version, id, trainingName, trainingType, duration, trainingCharacter, startDate, endDate, costPerStudent, tags);
    }
}