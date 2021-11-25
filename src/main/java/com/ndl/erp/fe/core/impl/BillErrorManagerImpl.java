package com.ndl.erp.fe.core.impl;

import com.ndl.erp.fe.core.BillErrorManager;

import com.ndl.erp.domain.ErrorProcess;
import com.ndl.erp.repository.ErrorProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillErrorManagerImpl implements BillErrorManager<ErrorProcess> {


    @Autowired
    private ErrorProcessRepository errorProcessRepository;

    @Override
    public void logError(ErrorProcess e) {
        // errorProcessRepository.addAErrorProcess(e);

    }
}
