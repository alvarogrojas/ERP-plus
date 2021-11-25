package com.ndl.erp.fe.core;

public interface BillErrorManager<T> {

    void logError(T e);
}
