package com.quickbite.restaurantservice.exception;

public class InvalidMediaException extends RuntimeException{
    public InvalidMediaException(String msg){
        super(msg);
    }
}
