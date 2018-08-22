package com.capgemini.jstk.CompanyTrainings.domain;

import java.util.Set;

public class ExternalCoachEntityBuilder {
    private Long version;
    private Long id;
    private String firstName;
    private String lastName;
    private String company;
    private Set<TrainingEntity> trainingsAsExternalCoach;

    public ExternalCoachEntityBuilder setVersion(Long version) {
        this.version = version;
        return this;
    }

    public ExternalCoachEntityBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ExternalCoachEntityBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ExternalCoachEntityBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ExternalCoachEntityBuilder setCompany(String company) {
        this.company = company;
        return this;
    }

    public ExternalCoachEntityBuilder setTrainingsAsExternalCoach(Set<TrainingEntity> trainingsAsExternalCoach) {
        this.trainingsAsExternalCoach = trainingsAsExternalCoach;
        return this;
    }

    public ExternalCoachEntity buildExternalCoachEntity() {
        return new ExternalCoachEntity(version, id, firstName, lastName, company, trainingsAsExternalCoach);
    }
}