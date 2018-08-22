package com.capgemini.jstk.CompanyTrainings.domain;

import com.capgemini.jstk.CompanyTrainings.enums.TrainingCharacter;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingType;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TrainingEntityBuilder {
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
    private Set<EmployeeEntity> employeesAsCoaches = new HashSet<>();
    private Set<EmployeeEntity> employeesAsStudents = new HashSet<>();
    private Set<ExternalCoachEntity> externalCoaches = new HashSet<>();

    public TrainingEntityBuilder setVersion(Long version) {
        this.version = version;
        return this;
    }

    public TrainingEntityBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public TrainingEntityBuilder setTrainingName(String trainingName) {
        this.trainingName = trainingName;
        return this;
    }

    public TrainingEntityBuilder setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
        return this;
    }

    public TrainingEntityBuilder setDuration(Double duration) {
        this.duration = duration;
        return this;
    }

    public TrainingEntityBuilder setTrainingCharacter(TrainingCharacter trainingCharacter) {
        this.trainingCharacter = trainingCharacter;
        return this;
    }

    public TrainingEntityBuilder setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public TrainingEntityBuilder setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public TrainingEntityBuilder setCostPerStudent(Integer costPerStudent) {
        this.costPerStudent = costPerStudent;
        return this;
    }

    public TrainingEntityBuilder setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public TrainingEntityBuilder setEmployeesAsCoaches(Set<EmployeeEntity> employeesAsCoaches) {
        this.employeesAsCoaches = employeesAsCoaches;
        return this;
    }

    public TrainingEntityBuilder setEmployeesAsStudents(Set<EmployeeEntity> employeesAsStudents) {
        this.employeesAsStudents = employeesAsStudents;
        return this;
    }

    public TrainingEntityBuilder setExternalCoaches(Set<ExternalCoachEntity> externalCoaches) {
        this.externalCoaches = externalCoaches;
        return this;
    }

    public TrainingEntity buildTrainingEntity() {
        return new TrainingEntity(version, id, trainingName, trainingType, duration, trainingCharacter, startDate, endDate, costPerStudent, tags, employeesAsCoaches, employeesAsStudents, externalCoaches);
    }
}