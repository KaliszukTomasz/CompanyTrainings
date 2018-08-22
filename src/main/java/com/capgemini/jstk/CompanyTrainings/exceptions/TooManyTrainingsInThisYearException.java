package com.capgemini.jstk.CompanyTrainings.exceptions;

public class TooManyTrainingsInThisYearException extends RuntimeException {

    public TooManyTrainingsInThisYearException(String message) {
        super(message);
    }
}
