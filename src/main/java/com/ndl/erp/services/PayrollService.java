package com.ndl.erp.services;

import com.ndl.erp.constants.CostCenterConstants;
import com.ndl.erp.domain.*;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.dto.*;
import com.ndl.erp.repository.*;
import com.ndl.erp.util.DateUtil;
import com.ndl.erp.util.RefundDevolution;
import com.ndl.erp.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Component
@Transactional
public class PayrollService {

    private final transient Logger log = LoggerFactory.getLogger(PayrollService.class);

    @Autowired
    private PayRollRepository repository;

    @Autowired
    private PayRollDetailRepository detailRepository;

    @Autowired
    private LaborCostRepository laborCostRepository;


    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private PayRollCollaboratorRefundDevolutionDetailRepository payRollCollaboratorRefundDevolutionDetailRepository;


    @Autowired
    private DeductionsRefundsDetailsRepository deductionsRefundsDetailsRepository;

    @Autowired
    private PayRollCollaboratorDetailRepository payRollCollaboratorDetailRepository;

    @Autowired
    private PayRollCollaboratorDeductionDetailRepository payRollCollaboratorDeductionDetailRepository;


    public PayRollDTO get(Integer id) {
        PayRollDTO d = new PayRollDTO();

        if (id==null) {
            PayRoll p = new PayRoll();

            d.setCurrent(p);
            return d;
        }
        Optional<PayRoll> c = repository.findById(id);
        if (c==null || c.get()==null) {
            throw new RuntimeException("No se encontro el id de la planilla");
        }
        d.setCurrent(c.get());

        return d;
    }

    public PayrollsDTO getPayrolls(Integer collaboratorId,
                                            Date startDate, Date endDate,
                                            Integer pageNumber,
                                            Integer pageSize, String sortDirection,
                                            String sortField) {

        PayrollsDTO d = new PayrollsDTO();


        if (collaboratorId!=null && collaboratorId!=0 ) {
            d.setPage(this.repository.getFilterPageableByCollaboratorIdAndDates(
                   collaboratorId, startDate, endDate, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCollaboratorIdAndDates(collaboratorId,
                    startDate, endDate));

        } else if (collaboratorId==null || collaboratorId==0) {
            d.setPage(this.repository.getFilterPageableByDates(
                    startDate, endDate, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByDates(startDate, endDate));
//            d.setTotalKilometers(this.repository.getSumKmByDates(startDate, endDate, consecutive, currencyId, state));
//            d.setTotalAmount(this.repository.getSumTotalByDates(startDate, endDate, consecutive, currencyId, state));
        }

        d.setCollaborators(this.collaboratorRepository.findByStatus(CostCenterConstants.COLLABORATOR_ACTIVE));

        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public Map getDataForNomina(Integer idPayRoll, Integer idCollaborator) {
        Map <String,Object> data = new HashMap<String,Object>();

       Optional<PayRoll> op = this.repository.findById(idPayRoll);
       if (op==null) {
           throw new RuntimeException("Not found payroll " + idPayRoll);
       }
        PayRoll p = op.get();
        data.put("payRoll",p);
        List<PayRollDetail> d = this.detailRepository.getPayRollDetailPayRollIdAndCollaboratorId(idCollaborator, idPayRoll);
        if (d==null) {
            throw new RuntimeException("No found detail");
        }
        data.put("payRollDetail", d.get(0));

        List<PayRollCollaboratorDeductionDetail> l = this.payRollCollaboratorDeductionDetailRepository.
                getPayRollCollaboratorDeductionDetail
                        (idCollaborator, idPayRoll);
        if (l!=null)
            data.put("prc-dd", l);
        else
            data.put("prc-dd", new ArrayList<>());


        List<PayRollCollaboratorRefundDevolutionDetail> dd = this.payRollCollaboratorRefundDevolutionDetailRepository.
                getByIdPayroolAndCollaboratorId(
                idPayRoll, idCollaborator);


        data.put("prc-d",
                this.payRollCollaboratorDetailRepository.
                        getPayRollDetailPayRollIdAndCollaboratorId(idCollaborator, idPayRoll));

        Integer max=0;
        if(dd!=null && dd.size()>0){
            for(PayRollCollaboratorRefundDevolutionDetail d1 : dd){
                Integer id =   d1.getIdDevolRefundDetail();
                Integer idDeductionRefund = this.deductionsRefundsDetailsRepository.getIdDeductionRefund(id);
                max = this.deductionsRefundsDetailsRepository.getMaxId(idDeductionRefund);
                d1.setMaxCuota(max);
            }

        }

        data.put("prc-rdd", dd);

        return data;
    }


    public NominaDetailDTO getPayRollGetDetail(Integer idPayRoll, Integer idCollaborator) {

        Optional<PayRoll> o = this.repository.findById(idPayRoll);
        PayRoll payRoll = o.get();

        PayRollDetail payRollDetail = null;
        List<PayRollDetail> d = this.detailRepository.getPayRollDetailPayRollIdAndCollaboratorId(idCollaborator, idPayRoll);
        if (d!=null) {
            payRollDetail = d.get(0);
        }

        List<PayRollCollaboratorDeductionDetail> prcdd = this.payRollCollaboratorDeductionDetailRepository.
                getPayRollCollaboratorDeductionDetail
                        (idCollaborator, idPayRoll);

        List<PayRollCollaboratorRefundDevolutionDetail> refundsDevols = this.payRollCollaboratorRefundDevolutionDetailRepository.
                getByIdPayroolAndCollaboratorId(
                        idPayRoll, idCollaborator);


        NominaDetailDTO result =  new NominaDetailDTO();
        List<PayRollCollaboratorDetail> payRollCollaboratorDetails = this.payRollCollaboratorDetailRepository.
                getPayRollDetailPayRollIdAndCollaboratorId(idCollaborator, idPayRoll);


         this.cratePRCDDTO(payRollCollaboratorDetails, result);
        this.crateDRCDDDTO(prcdd, result);
        this.crateDRRDDTO(refundsDevols, result);

        result.setPayRoll(payRoll);
        result.setPayRollDetail(payRollDetail);
        result.setCollaborator(payRollDetail.getCollaborator());

        return result;
    }

    private List<PayRollCollaboratorDetailDTO> cratePRCDDTO(List<PayRollCollaboratorDetail> details, NominaDetailDTO result) {
        List<PayRollCollaboratorDetailDTO> ret = new ArrayList<PayRollCollaboratorDetailDTO>();
        if (details != null && details.size() > 0) {
            for (PayRollCollaboratorDetail detail : details) {
                PayRollCollaboratorDetailDTO temp = new PayRollCollaboratorDetailDTO(detail);
                ret.add(temp);
            }

        }
        result.setPayRollCollaboratorDetail(ret);
        return ret;
    }

    private List<PayRollCollaboratorRefundDevolutionDetailDTO> crateDRRDDTO(
            List<PayRollCollaboratorRefundDevolutionDetail> details, NominaDetailDTO result
            ) {
        List<PayRollCollaboratorRefundDevolutionDetailDTO> ret = new ArrayList<PayRollCollaboratorRefundDevolutionDetailDTO>();
        Double total = 0d;
        if (details != null && details.size() > 0) {
            for (PayRollCollaboratorRefundDevolutionDetail detail : details) {
                PayRollCollaboratorRefundDevolutionDetailDTO temp = new PayRollCollaboratorRefundDevolutionDetailDTO(detail);
                ret.add(temp);
                total += temp.getMount();
            }
        }
        result.setRefundsDevols(ret);
        result.setTotalRefundsDevols(total);
        return ret;
    }


    private List<PayRollCollaboratorDeductionDetailDTO> crateDRCDDDTO(List<PayRollCollaboratorDeductionDetail> details
            , NominaDetailDTO result) {
        List<PayRollCollaboratorDeductionDetailDTO> ret = new ArrayList<PayRollCollaboratorDeductionDetailDTO>();
        Double total = 0d;
        if (details != null && details.size() > 0) {
            for (PayRollCollaboratorDeductionDetail detail : details) {
                PayRollCollaboratorDeductionDetailDTO temp = new PayRollCollaboratorDeductionDetailDTO(detail);
                ret.add(temp);
                total += temp.getMount();
            }
        }
        result.setDeductios(ret);
        result.setTotalDeductions(total);
        return ret;
    }

    public Result approvePayRoll(Integer id) {
        PayRoll p;
        Optional<PayRoll> op = repository.findById(id);
        if (op!=null) {
            p = op.get();
        } else {
            throw new RuntimeException("No found " + id);
        }

//        List<PayRollDetail>  payRollDetails = this.getPayRollDetailService().getPayRollDetailList(payRoll.getId());
        Integer idPayRoll=null,idCollaborator=null;
        for (PayRollDetail prd: p.getDetails() ) {
            idPayRoll = prd.getPayRoll().getId(); // Id de la planilla.
            idCollaborator = prd.getCollaborator().getId(); // Id del colaborador.

            List<PayRollCollaboratorRefundDevolutionDetail> payRollCollaboratorRefundDevolutionDetails =
                    this.payRollCollaboratorRefundDevolutionDetailRepository.getByIdPayroolAndCollaboratorId(idPayRoll,idCollaborator);
            for (PayRollCollaboratorRefundDevolutionDetail prcdrd:  payRollCollaboratorRefundDevolutionDetails) {
                Optional<DeductionsRefundsDetails> o = this.deductionsRefundsDetailsRepository.findById(prcdrd.getIdDevolRefundDetail());
                if (o!=null) {
                    DeductionsRefundsDetails drd = o.get();
                    drd.setStatus(StringHelper.STATUS_DATA_APLICADO);
                    this.deductionsRefundsDetailsRepository.save(drd);
                }
            }

        }

        // Actualizar la planilla
        p.setStatus(StringHelper.STATUS_DATA_APROB);
        this.repository.save(p);

        // Obtener las horas laboradas y se congelan
        List <LaborCost> laborCosts = this.laborCostRepository.getLaborCostByStarAndEnd(p.getStart(),p.getEnd());
        for(LaborCost lc:laborCosts){
            lc.setStatus(StringHelper.STATUS_DATA_FROZEN);
            this.laborCostRepository.save(lc);
        }
        return new Result(Result.RESULT_CODE.OK,"Planilla aprobada");
    }

    public PayRollDTO get() {
        PayRollDTO d = new PayRollDTO();
        d.setCollaborators(this.collaboratorRepository.findByStatus(CostCenterConstants.COLLABORATOR_ACTIVE));

        return d;
    }



    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {
        return PageRequest.of(pageNumber, pageSize);
    }

    public void deletePlanilla(PayRoll pr) {

        Integer idPayRoll = pr.getId();

        List<PayRollCollaboratorDeductionDetail> deductions = this.payRollCollaboratorDeductionDetailRepository.
                getPayRollCollaboratorDeductionDetail
                        (idPayRoll);

        List<PayRollCollaboratorRefundDevolutionDetail> refundsDevols = this.payRollCollaboratorRefundDevolutionDetailRepository.
                getByIdPayroolAndCollaboratorId(
                        idPayRoll);


        NominaDetailDTO result =  new NominaDetailDTO();
        List<PayRollCollaboratorDetail> payRollCollaboratorDetails = this.payRollCollaboratorDetailRepository.
                getPayRollDetailPayRollIdAndCollaboratorId( idPayRoll);

        log.info("INICIANDO deletePlanilla 2 :: deletePayRollDetailByIdPayRoll ");
//        this.payRollDetailRepository.deleteById(id);

        for (PayRollDetail d: pr.getDetails()) {
            this.detailRepository.delete(d);
        }

        log.info("INICIANDO deletePlanilla 3 :: deletePayRollCollaboratorDeductionDetailByPayRoll ");
        for (PayRollCollaboratorDeductionDetail d: deductions) {
            this.payRollCollaboratorDeductionDetailRepository.delete(d);
        }

//        log.info("INICIANDO deletePlanilla 3 :: deletePayRollCollaboratorDeductionDetailByPayRoll ");
//        this.getPayRollCollaboratorDeductionDetailService().deletePayRollCollaboratorDeductionDetailByPayRoll(id);

        log.info("INICIANDO deletePlanilla 4:: deletePayRollCollaboratorRefundDevolutionDetailByPayRoll ");
        for (PayRollCollaboratorRefundDevolutionDetail d: refundsDevols) {
            this.payRollCollaboratorRefundDevolutionDetailRepository.delete(d);
        }


        log.info("INICIANDO deletePlanilla 5:: deletePayRollCollaboratorDetailByPayRoll ");
        for (PayRollCollaboratorDetail d: payRollCollaboratorDetails) {
            this.payRollCollaboratorDetailRepository.delete(d);
        }



        log.info("INICIANDO deletePlanilla 6:: deletePayRoll ");
        this.repository.delete(pr);

        log.info(" deletePlanilla 7:: Fin ");

    }

    public PayRollDTO getNewPayroll() {
        PayRollDTO d = new PayRollDTO();
        PayRoll p = this.repository.getLastPayroll();


        PayRoll r = new PayRoll();

        this.initNewPayrollDates(r, p);
        d.setCurrent(r);
        return d;

    }

    public PayRollDTO getByDates(Date startDate, Date endDate, Integer id) {
        PayRollDTO d = new PayRollDTO();

        if (startDate==null || endDate==null) {
            PayRoll p = new PayRoll();

            d.setCurrent(p);
            return d;
        }
        List<PayRoll> c = repository.getByDates(startDate, endDate);
        if (c==null || c.size()<=0) {
            if (id==null)
                throw new RuntimeException("No se encontro planilla entre las fechas " + startDate + " " + endDate);
            Optional<PayRoll> o = this.repository.findById(id);
            if (o==null) {
                throw new RuntimeException("No se encontro planilla con id  " + id );
            }
            d.setCurrent(o.get());
        } else {
            d.setCurrent(c.get(0));
        }

        return d;
    }

    public List<PayRoll> getPayRollsByDates(Date startDate, Date endDate, String status, boolean inClosure) {
//        List<PayRoll> c = repository.getByDatesBetween(startDate, endDate, status, isClosure);
        List<PayRoll> c1 = repository.getByDatesBetween1(startDate, endDate, status, inClosure);
        return c1;
    }

    public List<PayRollDetail> getPayRollDetailList(Integer id) {

            return this.repository.getPayRollDetailByParent(id);
    }

    public PayRoll getPayRollById(Integer id) {

        Optional<PayRoll> c = repository.findById(id);
        if (c==null) {
            throw new RuntimeException("No found PayRoll " + id);
        }
        return c.get();
    }

    public PayRoll save(PayRoll c) {
        if (c.getId()==null) {
            c.setCreateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));

        }

        c.setUpdateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        return this.repository.save(c);
    }

    private PayRoll initNewPayrollDates(PayRoll newP, PayRoll lastPayroll) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(lastPayroll.getEnd());
        cal.add(Calendar.DATE, +1);
        newP.setStart(new java.sql.Date(cal.getTime().getTime()));
        cal.add(Calendar.DATE, +13);
//        cal.add(Calendar.WEEK_OF_YEAR, +2);
        newP.setEnd(new java.sql.Date(cal.getTime().getTime()));
        return newP;
    }

    public Result delete(Integer id) {
        PayRoll pr = this.getPayRollById(id);
        Result result;

        if (pr != null && pr.getStatus().equals(StringHelper.STATUS_DATA_GEN)) {
            try {
                this.deletePlanilla(pr);
                result = new Result(Result.RESULT_CODE.DELETE, "Se elimino la planilla");
            }
          catch (Exception e) {
            result = new Result(Result.RESULT_CODE.ERROR, "Error al tratar de borrar los datos actuales");
            result.setStackTrace(e.getMessage());
            e.printStackTrace();
        }

    } else {
        result = new Result(Result.RESULT_CODE.ERROR, "No se permiten eliminar planillas en el estado : " + pr.getStatus());
    }



        return result;
    }

    @Transactional
    public void updatePayRollsOutOfClosure(List<Integer> ids) {
        this.repository.updatePayRollsByIds(ids);
    }

    public void updateStatusClosing(List<Integer> ids, String statusDataFrozen) {
        this.repository.updatePayRollsToFrozenByIds(ids);
    }


}
