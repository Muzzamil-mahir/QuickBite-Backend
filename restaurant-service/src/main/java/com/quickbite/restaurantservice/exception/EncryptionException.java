package com.quickbite.restaurantservice.exception;

public class EncryptionException extends RuntimeException{
    public EncryptionException(String msg){
        super(msg);

    }
    public EncryptionException(String msg, Throwable cause){
        super(msg,cause);

    }
}
