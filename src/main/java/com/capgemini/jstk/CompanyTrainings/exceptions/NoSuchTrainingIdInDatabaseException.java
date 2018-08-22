package com.capgemini.jstk.CompanyTrainings.exceptions;

public class NoSuchTrainingIdInDatabaseException extends RuntimeException {
    public NoSuchTrainingIdInDatabaseException(String message) {
        super(message);
    }
}
