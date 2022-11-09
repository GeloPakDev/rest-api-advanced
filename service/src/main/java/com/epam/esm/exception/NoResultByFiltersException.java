package com.epam.esm.exception;

public class NoResultByFiltersException extends RuntimeException {

    public NoResultByFiltersException() {
    }

    public NoResultByFiltersException(String messageCode) {
        super(messageCode);
    }

    public NoResultByFiltersException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public NoResultByFiltersException(Throwable cause) {
        super(cause);
    }

}