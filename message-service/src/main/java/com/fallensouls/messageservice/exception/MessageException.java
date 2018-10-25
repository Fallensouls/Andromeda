package com.fallensouls.messageservice.exception;

public class MessageException extends Exception {
//    private int code;
    private String message;

    public MessageException(String message){
        super(message);
//        this.code = code;
    }

//    public int getCode(){
//        return this.code;
//    }
}
