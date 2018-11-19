package com.fallensouls.messageservice.exception;

public class RequestNotValidException extends Exception {
    private String message;

    public RequestNotValidException(String message){
        super(message);
    }

}
