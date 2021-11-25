package com.ndl.erp.exceptions;


public class NotAvailableItemsException  extends RuntimeException {
    public NotAvailableItemsException(String message){
        super(message);
    }
}
