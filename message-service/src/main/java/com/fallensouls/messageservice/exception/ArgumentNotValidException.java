package com.fallensouls.messageservice.exception;

public class ArgumentNotValidException extends Exception {
//    private int code;
    private String message;

    public ArgumentNotValidException(String message){
        super(message);
//        this.code = code;
    }

//    public int getCode(){
//        return this.code;
//    }
}
