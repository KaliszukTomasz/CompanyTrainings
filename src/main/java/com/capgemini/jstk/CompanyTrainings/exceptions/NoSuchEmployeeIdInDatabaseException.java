package com.capgemini.jstk.CompanyTrainings.exceptions;

public class NoSuchEmployeeIdInDatabaseException extends RuntimeException{
    public NoSuchEmployeeIdInDatabaseException(String message) {
        super(message);
    }
}
