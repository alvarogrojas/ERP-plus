package com.ndl.erp.exceptions;


public class UserInvalidPasswordException  extends RuntimeException {
    public UserInvalidPasswordException(String message){
        super(message);
    }
}
