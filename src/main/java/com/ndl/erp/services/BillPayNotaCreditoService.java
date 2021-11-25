package com.ndl.erp.services;

import com.ndl.erp.domain.*;
import com.ndl.erp.dto.BillPayNotaCreditoDTO;
import com.ndl.erp.dto.BillPayNotaCreditosDTO;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.repository.*;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ndl.erp.constants.BillPayNotaCreditoConstants.*;


@Component
public class BillPayNotaCreditoService {


    @Autowired
    BillPayRepository billPayRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    BillPayNotaCreditoRepository billPayNotaCreditoRepository;

    @Autowired
    EconomicActivityRepository economicActivityRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    GeneralParameterService generalParameterService;

    @Autowired
    BillPayDetailRepository billPayDetailRepository;





    public BillPayNotaCreditoDTO BillPayNotaCredito(Integer id, Integer billPayId)throws Exception {

        BillPay billPay = new BillPay();
        if (billPayId!=null)
            billPay = this.billPayRepository.getOne(billPayId);

        if (billPay==null) {
            throw new GeneralInventoryException("La cuenta por pagar  no fue encontrada y no se pudo crear la nota de credito");
        }

        BillPayNotaCreditoDTO billPayNotaCreditoDTO = this.getBillPayNotaCreditoDTO(billPay);

        if (id == null) {
            billPayNotaCreditoDTO.setStatus(this.getStatusBillPayNotaCredito());
            billPayNotaCreditoDTO.getCurrent().setStatus(BILL_PAY_NOTA_CREDITO_EDICION);

            return billPayNotaCreditoDTO;
        }
        BillPayNotaCredito billPayNotaCredito = billPayNotaCreditoRepository.findBillPayNotaCreditoById(id);
        try {
            if (billPayNotaCredito==null ) {
                return null;
            }
        } catch (GeneralInventoryException e) {
            e.printStackTrace();
            return null;
        }

        billPayNotaCreditoDTO.setCurrent(billPayNotaCredito);
        return billPayNotaCreditoDTO;
    }



    public BillPayNotaCreditoDTO getBillPayNotaCreditoDTO(BillPay billPay) {
        if (billPay  == null){
            throw new GeneralInventoryException("No se encontró una cuenta por pagar para crear la nota de crédito");
        }

        BillPayNotaCreditoDTO ncDTO = new BillPayNotaCreditoDTO();
        ncDTO.setStatus(this.getStatusBillPayNotaCredito());
        ncDTO.setCurrencies(this.currencyRepository.findAllCurrency());
        ncDTO.setEconomicActivities(this.economicActivityRepository.findAll());
        return ncDTO;
    }

    public List<String> getStatusBillPayNotaCredito() {

        List<String> estados = new ArrayList<String>();
        estados.add(BILL_PAY_NOTA_CREDITO_EDICION);
        estados.add(BILL_PAY_NOTA_CREDITO_EMITIDA);

        return estados;
    }

    public BillPayNotaCreditoDTO getBillPayeNotaCredito(Integer id) {

        BillPayNotaCredito billPayNotaCredito  = billPayNotaCreditoRepository.findBillPayNotaCreditoById(id);
        BillPay  billPay  = this.billPayRepository.getOne(billPayNotaCredito.getBillPay().getId());
        BillPayNotaCreditoDTO billPayNotaCreditoDTO = this.getBillPayNotaCreditoDTO(billPay);

        if (billPayNotaCredito == null) {
            return billPayNotaCreditoDTO;
        } else {
            billPayNotaCreditoDTO.setCurrent(billPayNotaCredito);
        }
        return billPayNotaCreditoDTO;
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized void procesarDetalleBillPayNotaCredito(BillPayNotaCredito billPayNotaCredito) throws Exception{

        if (billPayNotaCredito.getDetails() == null) {
            throw new GeneralInventoryException("La nota de crédito de proveedor no se puede procesar vacía");
        }

            for (BillPayNotaCreditoDetail detail : billPayNotaCredito.getDetails()) {
                if (detail.getBillPayDetail() == null) {
                    throw new GeneralInventoryException("La nota de crédito de proveedor tiene un detalle sin referencia a la CxC");
                }

                    BillPayDetail d = this.billPayDetailRepository.getBillPayDetailByBillPayAndId(billPayNotaCredito.getBillPay().getId(), detail.getBillPayDetail().getId());
                    if (d  == null) {
                        throw new GeneralInventoryException("Error detalle CxC proveedor: Referencia a CxC no fué encontrada");
                    }

                    d.setCreditNoteNumber(billPayNotaCredito.getConsecutivo());
                    d.setCreditNoteMto(detail.getTotal());
                    d.setTotal(d.getTotal() - detail.getTotal());
                    d.setUpdateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
                    this.billPayDetailRepository.save(d);

            }

    }



    @Transactional(rollbackFor = {Exception.class})
    public synchronized BillPayNotaCredito save(BillPayNotaCredito billPayNotaCredito) throws Exception {

        BillPayNotaCredito oldBillPay = null;



        if (billPayNotaCredito.getId()==null) {

            setAuditoriaCreacionBillPayNotaCredito(billPayNotaCredito);
            billPayNotaCredito.setNumber(this.generalParameterService.generateNextBillPayNotaCreditoNumber());

        } else {
            oldBillPay = this.billPayNotaCreditoRepository.findBillPayNotaCreditoById(billPayNotaCredito.getId());
            setAuditoriaModificacionBillPayNotaCredito(billPayNotaCredito);
        }

        if (oldBillPay!= null && oldBillPay.getStatus().equals(BILL_PAY_NOTA_CREDITO_EDICION) &&  billPayNotaCredito.getStatus().equals(BILL_PAY_NOTA_CREDITO_EMITIDA)){
            this.procesarDetalleBillPayNotaCredito(billPayNotaCredito);
        }

        BillPayNotaCredito bpnc =  this.billPayNotaCreditoRepository.save(billPayNotaCredito);
        if (billPayNotaCredito.getBillPay()!=null) {
            this.billPayRepository.save(billPayNotaCredito.getBillPay());
        }

        return bpnc;
    }

    public void setAuditoriaModificacionBillPayNotaCredito(BillPayNotaCredito billPayNotaCredito) throws Exception{

        billPayNotaCredito.setUpdateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));

    }

    public void setAuditoriaCreacionBillPayNotaCredito(BillPayNotaCredito billPayNotaCredito) throws Exception{

        User u = this.userService.getCurrentLoggedUser();
        if (u==null) {
            throw new GeneralInventoryException("User is not logged");
        }
        billPayNotaCredito.setUpdateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        billPayNotaCredito.setCreateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        billPayNotaCredito.setUserId(u.getId().intValue());

    }

    public BillPayNotaCreditosDTO getInvoiceNotaCreditoList(java.util.Date startFecha, java.util.Date endFecha, String status, Integer pageNumber,
                                                                Integer pageSize, String sortDirection,
                                                                String sortField) {

        BillPayNotaCreditosDTO billPayNotaCreditosDTO = new BillPayNotaCreditosDTO();

        billPayNotaCreditosDTO.setPage(this.billPayNotaCreditoRepository.getPageableBillPayNotaCreditoByDateAndStatus(startFecha,
                endFecha,
                status,
                createPageable(pageNumber,
                        pageSize,
                        sortDirection,
                        sortField)));

        billPayNotaCreditosDTO.setTotal(this.billPayNotaCreditoRepository.countAllBillPayNotaCreditoByDateAndStatus(startFecha, endFecha,status));
        if (billPayNotaCreditosDTO.getTotal()>0) {
            billPayNotaCreditosDTO.setPagesTotal(billPayNotaCreditosDTO.getTotal() /pageSize);
        } else {
            billPayNotaCreditosDTO.setPagesTotal(0);
        }

        billPayNotaCreditosDTO.setStatus(this.getStatusBillPayNotaCredito());

        return billPayNotaCreditosDTO;

    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    @Transactional(rollbackFor = {Exception.class})
    public BillPayNotaCredito updateStatus(Integer id, String status)throws  Exception{

        BillPayNotaCredito billPayNotaCredito = this.billPayNotaCreditoRepository.findBillPayNotaCreditoById(id);

        if (billPayNotaCredito == null) {
            throw new GeneralInventoryException("Nota de crédito de CxC no encontrada!");
        }

        String oldStatus = billPayNotaCredito.getStatus();

        User user = userService.getCurrentLoggedUser();
        billPayNotaCredito.setUserId(user.getId().intValue());
        billPayNotaCredito.setStatus(status);
        billPayNotaCredito.setUpdateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));



        if (oldStatus!= null && oldStatus.equals(BILL_PAY_NOTA_CREDITO_EDICION) &&  billPayNotaCredito.getStatus().equals(BILL_PAY_NOTA_CREDITO_EMITIDA)){
            this.procesarDetalleBillPayNotaCredito(billPayNotaCredito);
        }

        return this.billPayNotaCreditoRepository.save(billPayNotaCredito);


    }


}
