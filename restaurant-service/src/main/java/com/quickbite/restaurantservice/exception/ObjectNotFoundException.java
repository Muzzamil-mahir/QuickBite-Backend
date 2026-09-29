package com.quickbite.restaurantservice.exception;

public class ObjectNotFoundException extends  RuntimeException{
    public ObjectNotFoundException(String msg){
        super(msg);
    }
}
