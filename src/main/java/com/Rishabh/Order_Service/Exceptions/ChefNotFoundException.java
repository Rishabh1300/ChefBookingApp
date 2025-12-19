package com.Rishabh.Order_Service.Exceptions;

public class ChefNotFoundException extends RuntimeException{

    public ChefNotFoundException(String message){
        super(message);
    }
}
