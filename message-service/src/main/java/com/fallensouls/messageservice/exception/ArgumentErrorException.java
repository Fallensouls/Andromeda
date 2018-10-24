package com.fallensouls.messageservice.exception;

public class ArgumentErrorException extends Exception {
//    private int code;
    private String message;

    public ArgumentErrorException(String message){
        super(message);
//        this.code = code;
    }

//    public int getCode(){
//        return this.code;
//    }
}
