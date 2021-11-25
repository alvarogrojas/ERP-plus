package com.ndl.erp.services;


import com.ndl.erp.domain.FeeVehiculeFuel;

import com.ndl.erp.dto.FeeVehiculeFuelDTO;
import com.ndl.erp.dto.FeeVehiculeFuelsDTO;
import com.ndl.erp.repository.FeeVehiculeFuelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FeeVehiculeFuelService {

    @Autowired
    private FeeVehiculeFuelRepository feeVehiculeFuelRepository;


    public FeeVehiculeFuel getFeeVehiculeFuel(
            Integer vehiculeId,
            Integer fuelId,
            Integer old
    ) {
        List<FeeVehiculeFuel> r = feeVehiculeFuelRepository.getFeeVehiculeFuel(vehiculeId, fuelId, old);
        if (r!=null && r.size()>0) {
            return r.get(0);
        } else {
            r = feeVehiculeFuelRepository.getFeeVehiculeFuel(vehiculeId, fuelId);
            if (r!=null && r.size()>0) {
                return r.get(0);
            }
        }
        return null;
    }

    public FeeVehiculeFuelDTO getFeeVehiculeFuel(Integer id) {
        FeeVehiculeFuelDTO d = this.getFeeVehiculeFuel();
        Optional<FeeVehiculeFuel> c = feeVehiculeFuelRepository.findById(id);
        if (c==null) {
            return d;
        }
        d.setCurrent(c.get());
        return d;
    }

    public FeeVehiculeFuelsDTO getFeeVehiculeFuels(String filter, Integer pageNumber,
                                                   Integer pageSize, String sortDirection,
                                                   String sortField) {

        FeeVehiculeFuelsDTO d = new FeeVehiculeFuelsDTO();

        d.setPage(this.feeVehiculeFuelRepository.findUsingFilterPageable(filter,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.feeVehiculeFuelRepository.countAllByFilter(filter));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public FeeVehiculeFuelDTO getFeeVehiculeFuel() {

        FeeVehiculeFuelDTO d = new FeeVehiculeFuelDTO();

        return d;
    }

    public FeeVehiculeFuel save(FeeVehiculeFuel c) {

        return this.feeVehiculeFuelRepository.save(c);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }


}
