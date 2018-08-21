package com.capgemini.jstk.CompanyTrainings.types.builders;

import com.capgemini.jstk.CompanyTrainings.types.ExternalCoachTO;

public class ExternalCoachTOBuilder {
    private Long version;
    private Long id;
    private String firstName;
    private String lastName;
    private String company;

    public ExternalCoachTOBuilder setVersion(Long version) {
        this.version = version;
        return this;
    }

    public ExternalCoachTOBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ExternalCoachTOBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ExternalCoachTOBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ExternalCoachTOBuilder setCompany(String company) {
        this.company = company;
        return this;
    }

    public ExternalCoachTO buildExternalCoachTO() {
        return new ExternalCoachTO(version, id, firstName, lastName, company);
    }
}