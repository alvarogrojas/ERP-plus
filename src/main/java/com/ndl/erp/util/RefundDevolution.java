package com.ndl.erp.util;

import com.ndl.erp.domain.DeductionsRefunds;
import com.ndl.erp.domain.DeductionsRefundsDetails;

public class RefundDevolution {

    private DeductionsRefunds deductionsRefunds;
    private DeductionsRefundsDetails deductionsRefundsDetails;

    public DeductionsRefunds getDeductionsRefunds() {
        return deductionsRefunds;
    }

    public void setDeductionsRefunds(DeductionsRefunds deductionsRefunds) {
        this.deductionsRefunds = deductionsRefunds;
    }

    public DeductionsRefundsDetails getDeductionsRefundsDetails() {
        return deductionsRefundsDetails;
    }

    public void setDeductionsRefundsDetails(DeductionsRefundsDetails deductionsRefundsDetails) {
        this.deductionsRefundsDetails = deductionsRefundsDetails;
    }
}
