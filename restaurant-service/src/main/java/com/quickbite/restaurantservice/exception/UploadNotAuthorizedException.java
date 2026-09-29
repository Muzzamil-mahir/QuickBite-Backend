package com.quickbite.restaurantservice.exception;

public class UploadNotAuthorizedException extends RuntimeException{
    public UploadNotAuthorizedException(String msg){
        super(msg);
    }
}
