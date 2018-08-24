package com.capgemini.jstk.CompanyTrainings.types;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ExternalCoachTO {

    private Long version;
    private Long id;
    private String firstName;
    private String lastName;
    private String company;

    public ExternalCoachTO() {
    }

    public ExternalCoachTO(Long version, Long id, String firstName, String lastName, String company) {
        this.version = version;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
    }
}
