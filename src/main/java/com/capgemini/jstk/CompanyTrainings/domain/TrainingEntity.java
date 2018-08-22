package com.capgemini.jstk.CompanyTrainings.domain;


import com.capgemini.jstk.CompanyTrainings.enums.TrainingCharacter;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Trainings")
@Getter
@Setter
public class TrainingEntity extends AbstractEntity {

    @Column(nullable = false)
    private String trainingName;
    @Enumerated
    @Column(nullable = false)
    private TrainingType trainingType;
    @Column(nullable = false)
    private Double duration;
    @Enumerated
    @Column(nullable = false)
    private TrainingCharacter trainingCharacter;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private Date endDate;
    @Column(nullable = false)
    private Integer costPerStudent;
    @Column(nullable = false)
    private String tags;

    @ManyToMany(mappedBy = "trainingsAsCoach")
    Set<EmployeeEntity> employeesAsCoaches = new HashSet<>();
    @ManyToMany(mappedBy = "trainingsAsStudent")
    Set<EmployeeEntity> employeesAsStudents = new HashSet<>();
    @ManyToMany(mappedBy = "trainingsAsExternalCoach")
    Set<ExternalCoachEntity> externalCoaches = new HashSet<>();

    public TrainingEntity() {
    }

    public TrainingEntity(Long version, Long id, String trainingName, TrainingType trainingType, Double duration, TrainingCharacter trainingCharacter, Date startDate, Date endDate, Integer costPerStudent, String tags, Set<EmployeeEntity> employeesAsCoaches, Set<EmployeeEntity> employeesAsStudents, Set<ExternalCoachEntity> externalCoaches) {
        super(version, id);
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.duration = duration;
        this.trainingCharacter = trainingCharacter;
        this.startDate = startDate;
        this.endDate = endDate;
        this.costPerStudent = costPerStudent;
        this.tags = tags;
        this.employeesAsCoaches = employeesAsCoaches;
        this.employeesAsStudents = employeesAsStudents;
        this.externalCoaches = externalCoaches;
    }
}
