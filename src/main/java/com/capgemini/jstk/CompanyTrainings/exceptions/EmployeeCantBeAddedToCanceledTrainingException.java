package com.capgemini.jstk.CompanyTrainings.exceptions;

public class EmployeeCantBeAddedToCanceledTrainingException extends RuntimeException {

    public EmployeeCantBeAddedToCanceledTrainingException(String message) {
        super(message);
    }
}
