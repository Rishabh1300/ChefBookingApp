package com.Rishabh.Order_Service.Exceptions;

public class    ChefNotAvailableException extends RuntimeException {
    public ChefNotAvailableException(String message) {
        super(message);
    }
}
