package com.capgemini.jstk.CompanyTrainings.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "External_coaches")
@Getter
@Setter
public class ExternalCoachEntity extends AbstractEntity {

    @Column(nullable = false, length = 30)
    private String firstName;
    @Column(nullable = false, length = 30)
    private String lastName;
    @Column(nullable = false, length = 30)
    private String company;
    @ManyToMany
    @JoinTable(name = "TraininigExternalCoach", joinColumns = {
            @JoinColumn(name = "EXTERNALCOACH_ID", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "TRAINING_ID", nullable = false, updatable = false)})
    private Set<TrainingEntity> trainingsAsExternalCoach = new HashSet<>();

    public ExternalCoachEntity() {

    }

    public ExternalCoachEntity(Long version, Long id, String firstName, String lastName, String company, Set<TrainingEntity> trainingsAsExternalCoach) {
        super(version, id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.trainingsAsExternalCoach = trainingsAsExternalCoach;
    }


    public void addTrainingToTrainingsAsExternalCoach(TrainingEntity trainingEntity) {
        trainingsAsExternalCoach.add(trainingEntity);
        trainingEntity.getExternalCoaches().add(this);
    }

    public TrainingEntity removeTrainingFromTrainingsAsExternalCoach(TrainingEntity trainingEntity) {
        trainingsAsExternalCoach.remove(trainingEntity);
        trainingEntity.getExternalCoaches().remove(this);
        return trainingEntity;
    }
}
