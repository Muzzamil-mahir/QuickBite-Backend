package com.quickbite.restaurantservice.exception;

public class MediaStorageException extends RuntimeException{
    public MediaStorageException(String msg, Throwable cause){
        super(msg,cause);
    }
}
