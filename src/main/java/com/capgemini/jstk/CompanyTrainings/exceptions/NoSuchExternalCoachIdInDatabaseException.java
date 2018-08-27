package com.capgemini.jstk.CompanyTrainings.exceptions;

public class NoSuchExternalCoachIdInDatabaseException extends RuntimeException{
    public NoSuchExternalCoachIdInDatabaseException(String message) {
        super(message);
    }
}
