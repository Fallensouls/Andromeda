package com.fallensouls.userservice.exception;

public class UserNotFoundException extends Exception {
//    private String message;

    public UserNotFoundException(String message){
        super(message);
    }
}
