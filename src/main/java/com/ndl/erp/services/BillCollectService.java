package com.ndl.erp.services;

import com.ndl.erp.domain.BillCollect;
import com.ndl.erp.domain.BillCollectDetail;

import com.ndl.erp.domain.BillPay;
import com.ndl.erp.repository.BillCollectRepository;

import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@Transactional
public class BillCollectService {

    @Autowired
    private BillCollectRepository billCollectRepository;

    @Autowired
    private UserService userDetailsService;

    public List<BillCollect> getByIds(List<Integer> ids) {
        return this.billCollectRepository.findByIdIn(ids);
    }

    public BillCollect save(BillCollect c) {

        if (c.getId()==null) {
            c.setCreateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            c.setUserId(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
        }

        c.setUpdateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        return this.billCollectRepository.save(c);
    }

    public List<BillCollectDetail> getAllDetailsByBillCollectIds(List<Integer> idsBC) {
        if (idsBC!=null && idsBC.size()>0) {
            return this.billCollectRepository.getDetailsByIds(idsBC);
        }
        return new ArrayList<>();
    }

    public List<BillCollect> getByDates(Date start, Date end, Boolean inClosure) {
        return this.billCollectRepository.getByBillDates(start, end, inClosure);
    }

    public BillCollect getBillPayById(Integer id) {

        Optional<BillCollect> c = billCollectRepository.findById(id);
        if (c==null) {
            throw new RuntimeException("No found BillCollect " + id);
        }
        return c.get();
    }


    @Transactional
    public void updateBillCollectOutOfClosure(List<Integer> ids) {
        this.billCollectRepository.updateBillCollectByIds(ids);
    }

    @Transactional
    public void updateStatusClosing(List<Integer> ids) {
        this.billCollectRepository.updateBillCollectToFrozenByIds(ids);
    }
}
