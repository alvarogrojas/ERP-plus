package com.ndl.erp.services;

import com.ndl.erp.constants.CostCenterConstants;
import com.ndl.erp.domain.*;
import com.ndl.erp.dto.*;
import com.ndl.erp.repository.*;
import com.ndl.erp.util.DateUtil;
import com.ndl.erp.util.StringHelper;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

//import org.springframework.beans.BeanUtils;

@Component
@Transactional
public class MonthlyClosureService {

    private final transient Logger log = LoggerFactory.getLogger(MonthlyClosureService.class);

    @Autowired
    private MonthlyClosureRepository repository;

    @Autowired
    private CurrencyService currencyService;

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

    @Autowired
    private BillCollectService billCollectService;

    @Autowired
    private GeneralParameterService generalService;

    @Autowired
    private BillPayService billPayService;

    @Autowired
    private RefundableService refundableService;

    @Autowired
    private KilometerService kilometerService;

    @Autowired
    private PayrollService payRollService;

    @Autowired
    private UserService userService;

    @Transactional
    public Result approveMonthlyClosure(Integer id) {
        Result r = null;
        try {
            GeneralParameter gp=this.generalService.getByCode(StringHelper.CUR_CLOSURE);
            Currency curr= this.currencyService.getCurrencyById(StringHelper.getValInt(gp.getVal()));

            MonthlyClosure mc = this.getById(id);
            mc.setChangeTypeBuy(curr.getValueBuy());
            mc.setChangeTypeSale(curr.getValueSale());
            mc.setStatus(StringHelper.STATUS_DATA_FROZEN);
            this.repository.save(mc);


            //Congelar los planillas
            List<Integer> ids = this.getMonthlyClosurePayRollIds(mc.getPayRolls());
                    //.getPayRollIdsByParent(id);
            Integer count=0;
            if(ids!=null && !ids.isEmpty())
                this.payRollService.updateStatusClosing(ids, StringHelper.STATUS_DATA_FROZEN);


            //this.getMonthlyClosureBillPayService().deleteByParent(params.getId());

            //Actualiza loes tados de las cxc y borra las referencias
            ids = this.getMonthlyClosureCxC(mc.getBillCollects());
            if (ids!=null && ids.size()>0) {
                this.billCollectService.updateStatusClosing(ids);
            }

            ids= this.getMonthlyClosureCxP(mc.getBillPays());
            if (ids!=null && ids.size()>0) {
                this.billPayService.updateStatusClosing(ids);
            }

            //Congelar los kms
            List<Integer> kmsIds = this.billPayService.getTypeIdsByType(ids, StringHelper.KMS);
            if(kmsIds!=null && !kmsIds.isEmpty())
                this.kilometerService.updateStatusByIds(kmsIds, StringHelper.STATUS_DATA_FROZEN);


            //Congelar los rem
            List<Integer> remIds = this.billPayService.getTypeIdsByType(ids, StringHelper.REM);
            if(remIds!=null && !remIds.isEmpty())
                this.refundableService.updateStatusByIds(remIds);

            r = new Result(Result.RESULT_CODE.GENERATE, "Se aprobó el cierre");
        }catch (Exception e){
            e.printStackTrace();
            r = new Result(Result.RESULT_CODE.ERROR, "Error al aprobar el cierre",e.getMessage());
        }
        return r;
    }

    public MonthlyClosure getById(Integer id) {

        Optional<MonthlyClosure> c = repository.findById(id);
        if (c==null || c.get()==null) {
            throw new RuntimeException("No se encontro el id del cierre");
        }


        return c.get();
    }

    public MonthlyClosureDTO get(Integer id) {
        MonthlyClosureDTO d = new MonthlyClosureDTO();

        if (id==null || id==0) {
            MonthlyClosure p = new MonthlyClosure();
            String s = DateUtil.getNameMonthYearCr();
            if (s!=null) {
                p.setName(s);
            }



            Calendar cal = Calendar.getInstance();
           // cal.setTime(lastPayroll.getEnd());
            cal.set(Calendar.DATE, 1);

            p.setStart(new java.sql.Date(cal.getTime().getTime()));
            Integer lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            //cal.add(Calendar.DATE, +13);
            cal.set(Calendar.DATE, lastDay);

            p.setEnd(new java.sql.Date(cal.getTime().getTime()));
            //return newP;


            d.setCurrent(p);
            return d;
        }
        Optional<MonthlyClosure> c = repository.findById(id);
        if (c==null || c.get()==null) {
            throw new RuntimeException("No se encontro el id del cierre");
        }
        d.setCurrent(c.get());

        return d;
    }

    public MonthlyClosuresDTO getClosures(
                                            Integer pageNumber,
                                            Integer pageSize, String sortDirection,
                                            String sortField) {

        MonthlyClosuresDTO d = new MonthlyClosuresDTO();

        d.setPage(this.repository.getPageable(createPageable(pageNumber,pageSize,sortDirection,sortField)));
//        d.setPage(this.repository.getPageable(createPageable(pageNumber,pageSize,sortDirection,sortField)));
        d.setTotal(this.repository.countAll());


        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public AnalysisDTO getPayRolls(MonthlyClosure closure, Currency curDef,  Currency curPayRoll, AnalysisDTO ret) {


        List<MonthlyClosurePayRoll> monthlyClosurePayRolls = closure.getPayRolls();
        List<Integer> ids = this.getPayRollId(monthlyClosurePayRolls);

        if(StringHelper.STATUS_DATA_FROZEN.equals(closure.getStatus())){
            curPayRoll.setValueSale(closure.getChangeTypeSale());
            curPayRoll.setValueBuy(closure.getChangeTypeBuy());
        }

        List<PayRollDTO> prDTOS = new ArrayList<PayRollDTO>();
        if(ids!=null && ids.size() > 0) {
            for (Integer idx : ids) {
                prDTOS.add(new PayRollDTO(curPayRoll, this.payRollService.getPayRollDetailList(idx)));
            }
            Double crudSalaryTotal = 0d;
            Double devolucionesTotal  = 0d;
            Double ccssTotal  = 0d;
            Double netSalaryTotal  = 0d;

            for (PayRollDTO pr : prDTOS) {

                pr.changeDefaultCurrency(curDef);
                ccssTotal =  pr.getCcssTotal() + ccssTotal;
                devolucionesTotal =  pr.getDevolutionTotal() + devolucionesTotal;
                crudSalaryTotal =  pr.getCrudSalaryTotal() + crudSalaryTotal;
                netSalaryTotal =  pr.getNetSalaryTotal() + netSalaryTotal;


            }
            ret.setCcssTotal(ccssTotal);
            ret.setCrudSalaryTotal(crudSalaryTotal);
            ret.setNetSalaryTotal(netSalaryTotal);
            ret.setDevolucionesTotal(devolucionesTotal);

        }
        ret.setPayRollDTOS(prDTOS);

        return ret;
    }

    public AnalysisDTO getClosureAnalysis(Integer closureId) {
        //    public AnalysisDTO getCxC (Integer closureId) {
        Currency curDef = this.currencyService.getSystemCurrency();

        GeneralParameter gp = this.generalService.getByCode(StringHelper.CUR_DEF_PAY_ROLL);
        Currency curPayRoll = this.currencyService.getCurrencyById(StringHelper.getValInt(gp.getVal()));

        Optional<MonthlyClosure> oc = this.repository.findById(closureId);
        if (oc==null || oc.get()==null) {
            throw new RuntimeException("No found closure " + closureId);
        }

        MonthlyClosure c = oc.get();
        Currency cFrozen = this.getCurrencyOfMonthlyClosure(c);


        AnalysisDTO ret=new AnalysisDTO(c);

        ret.setSystemCurrency(curDef);

        this.getPayRolls(c, curDef, curPayRoll, ret);

        ret = this.getCxC(c, curDef, ret);


        ret = getCxP(c, curDef, cFrozen, ret);

        return ret;
    }

    public AnalysisDTO getCxP(MonthlyClosure closure, Currency systemCurrency,  Currency currencyFrozen, AnalysisDTO ret) {



        List<MonthlyClosureBillPay> monthlyClosureBillPays = closure.getBillPays();

        log.debug(" Cantidad de cxp {} ", monthlyClosureBillPays!=null ? monthlyClosureBillPays.size() : 0);
        log.debug(" >> Fin Obteniendo  Listado de cuentas por pagar ::  ");

        log.debug(" >> Init Obteniendo  ids  de los cxp asignados al cierre  ");
        List<Integer> bpIds= this.getBillPayId(monthlyClosureBillPays);
        log.debug(" >> Fin Obteniendo  ids  de los cxp asignados al cierre  ");

        List<BillPay> billPays = new ArrayList<BillPay>();

        log.info(" >> Init Lista de cxp ");
        if(bpIds!=null && bpIds.size() > 0)
            billPays = this.billPayService.getByIds(bpIds);
        log.info(" >> Fin Lista de cxp ");



        Currency rowCurrency = null;



        //todo: Codigo Nuevo ::::
        List<Integer> bpsId = new ArrayList<Integer>();
        List<Integer> remsId = new ArrayList<Integer>();
        List<Integer> kmsId = new ArrayList<Integer>();
        for(BillPay bp:billPays){
            if(bp.getType().equals(StringHelper.CXP)) {
                bpsId.add(bp.getId());
            }else if(bp.getType().equals(StringHelper.REM)){
                remsId.add(bp.getTypeId());
            }else if(bp.getType().equals(StringHelper.KMS)){
                kmsId.add(bp.getTypeId());
            }
        }
        log.debug(" \t Init BillPayDetail {} catidad {}",StringHelper.CXP, bpsId.size());
        List<BillPayDetail> bpsAll = bpsId !=null && bpsId.size() > 0 ? this.billPayService.getAllDetailsByBillPayIds(bpsId): new ArrayList<BillPayDetail>();
        log.debug(" \t Fin BillPayDetail {}",StringHelper.CXP );

        log.debug(" \t Init RefundableDetail {} catidad {}",StringHelper.REM, remsId.size());
        List<RefundableDetail> rdsAll =  remsId !=null && remsId.size() > 0 ? this.refundableService.getByRefundableDetailsByIds(remsId): new ArrayList<RefundableDetail>();
        log.debug(" \t ids de reembolsables {} y cantidad: {}", this.getIds(remsId), rdsAll.size());
        log.debug(" \t Fin RefundableDetail {}",StringHelper.REM );

        log.debug(" \t Init KilometerDetail {} catidad {}",StringHelper.KMS, kmsId.size());
        List<KilometerDetail> kmdsAll = kmsId !=null && kmsId.size() > 0 ? this.kilometerService.getByKilometerIds(kmsId) : new ArrayList<KilometerDetail>();
        log.debug(" \t ids de reembolsables : {} y cantidad: {} ",this.getIds(kmsId), kmdsAll.size());
        log.debug(" \t Fin KilometerDetail {}",StringHelper.KMS );

        log.debug(" \t Inico del ciclo:  {} ",StringHelper.CXP );
        double totalCostsCenter = 0d;
        double totalCxP = 0d;
        AnalysisCostCenterDTO cc;
        AnalysisBillPayDTO bp1;
        for(BillPay bp:billPays){
            rowCurrency =  this.getCurrency(currencyFrozen,bp.getCurrency());
            if(bp.getType().equals(StringHelper.CXP)) {
                for(BillPayDetail bpd:bpsAll){
                    if(bpd.getBillPay().getId().equals(bp.getId())){
                        cc = new AnalysisCostCenterDTO(bpd,rowCurrency,systemCurrency);
                        bp1 = new AnalysisBillPayDTO(bp,bpd,rowCurrency,systemCurrency);
                        ret.addCostCenter(cc);
                        ret.addBillPay(bp1);
                        totalCostsCenter = totalCostsCenter + cc.getTotal();
                        totalCxP = totalCxP + bp1.getTotal();
                    }
                }
            }else if(bp.getType().equals(StringHelper.REM)){
                for(RefundableDetail rd:rdsAll) {
                    if(rd.getRefundable().getId().intValue()  == bp.getTypeId().intValue()){
                        cc = new AnalysisCostCenterDTO(rd, rowCurrency, systemCurrency);
                        bp1 = new AnalysisBillPayDTO(bp,rd,rowCurrency,systemCurrency);
                        ret.addCostCenter(cc);
                        ret.addBillPay(bp1);
                        totalCostsCenter = totalCostsCenter + cc.getTotal();
                        totalCxP = totalCxP + bp1.getTotal();
                    }
                }

            }else if(bp.getType().equals(StringHelper.KMS)){
                for(KilometerDetail kmd:kmdsAll){
                    if(kmd.getKilometer().getId().intValue() == bp.getTypeId().intValue()) {
                        cc = new AnalysisCostCenterDTO(kmd, rowCurrency, systemCurrency);
                        bp1 = new AnalysisBillPayDTO(bp, kmd, rowCurrency, systemCurrency);
                        ret.addCostCenter(cc);
                        ret.addBillPay(bp1);
                        totalCostsCenter = totalCostsCenter + cc.getTotal();
                        totalCxP = totalCxP + bp1.getTotal();
                    }
                }
            }
        }
        ret.setTotalBillPay(totalCxP);
        ret.setTotalCC(totalCostsCenter);
        log.debug(" \t Fin del ciclo {}",StringHelper.CXP );
        return ret;
    }

    public AnalysisDTO getCxC(MonthlyClosure c, Currency curDef, AnalysisDTO ret) {
        List<MonthlyClosureBillCollect>   monthlyClosureBillCollects = c.getBillCollects();

        List<Integer> bpIds=this.getBillCollectsId(monthlyClosureBillCollects);

        List<BillCollect> billCollects = new ArrayList<>();
        if(bpIds!=null && bpIds.size() > 0){
            billCollects = this.billCollectService.getByIds(bpIds);
        }
        List<Integer> idsBC = new ArrayList<>();
        for(BillCollect bc:billCollects){
            idsBC.add(bc.getId());
        }
        List<BillCollectDetail> bcds = this.billCollectService.getAllDetailsByBillCollectIds(idsBC);

        for(BillCollect bc:billCollects){

            for(BillCollectDetail bcd:bcds){
                if(bcd.getBillCollect().getId().intValue() == bc.getId()){
                    ret.addBillCollect(new AnalysisBillCollectDTO(bc,bcd,curDef));
                    //ret.addCostCenter(new AnalysisCostCenterDTO(bcd,curDef,bc.getCurrency())); //todo :: Revisar si esto es necesario
                }

            }
        }

        return ret;
    }

    private List<Integer> getBillCollectsId(List<MonthlyClosureBillCollect> mcbps){
        List<Integer> ids = new ArrayList<Integer>();
        for(MonthlyClosureBillCollect pr:mcbps){
            ids.add(pr.getBillCollect().getId());
        }
        return ids;
    }

    private List<Integer> getBillPayId(List<MonthlyClosureBillPay> mcbps){
        List<Integer> ids = new ArrayList<Integer>();
        for(MonthlyClosureBillPay pr:mcbps){
            ids.add(pr.getBillPay().getId());
        }
        return ids;
    }

    private List<Integer> getPayRollId(List<MonthlyClosurePayRoll> mcprs){
        List<Integer> ids = new ArrayList<Integer>();
        for(MonthlyClosurePayRoll pr:mcprs){
            ids.add(pr.getPayRoll().getId());
        }
        return ids;
    }

    private Currency getCurrencyOfMonthlyClosure(MonthlyClosure mc){
        Currency ret = null;
        if(StringHelper.STATUS_DATA_FROZEN.equals(mc.getStatus())) {
            GeneralParameter gp = this.generalService.getByCode(StringHelper.CUR_CLOSURE);
            ret = this.currencyService.getCurrencyById(StringHelper.getValInt(gp.getVal()));
            ret.setValueBuy(mc.getChangeTypeBuy());
            ret.setValueSale(mc.getChangeTypeSale());
        }
        return  ret;

    }

    private Currency getCurrency(Currency frozen, Currency current){
        Currency ret = frozen;
        if(frozen == null || current.getDefault()){
            ret= current;
        }
        return ret;
    }


    public PayRollDTO get() {
        PayRollDTO d = new PayRollDTO();
        d.setCollaborators(this.collaboratorRepository.findByStatus(CostCenterConstants.COLLABORATOR_ACTIVE));

        return d;
    }


    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {
        //return PageRequest.of(pageNumber, pageSize);
        //return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC);
        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
//        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, (byField!=null:byField?"id"));
    }


    private String getIds(List<Integer> ids) {
        StringBuffer str = new StringBuffer();
        for(Integer id: ids) {
            str.append(id).append(" , ");
        }
        return  str.toString();
    }

    public MonthlyClosure save(MonthlyClosure c) {
        if (c==null) {
            return null;
        }
        User u = this.userService.getCurrentLoggedUser();
        c.setIdUser(u.getId().intValue());
        c.setUpdateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        if (c.getStatus()==null || c.getStatus().equals("")) {
            c.setStatus("Ingresado");
        }
       c =  this.repository.save(c);
       return c;
    }

    public MonthlyClosure update(MonthlyClosure c) {

        User u = this.userService.getCurrentLoggedUser();
       Optional<MonthlyClosure> co =  this.repository.findById(c.getId());
       MonthlyClosure c1 = co.get();
        c1.setIdUser(u.getId().intValue());
        c1.setUpdateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
//        System.out.println("PAY ROLLS "+ c.getPayRolls().size());
        this.copyMonthlyClosurePayRoll(c.getPayRolls(),c1);
        this.copyMonthlyClosureBillPay(c.getBillPays(),c1);
        this.copyMonthlyClosureBillCollect(c.getBillCollects(),c1);

       c =  this.repository.save(c1);
       return c;
    }

    private void copyMonthlyClosurePayRoll(List<MonthlyClosurePayRoll> detallesDTO, MonthlyClosure f) {
        MonthlyClosurePayRoll dto1;
        List<MonthlyClosurePayRoll> toUpdateList = new ArrayList<>();
        List<MonthlyClosurePayRoll> toRemoveList = new ArrayList<>();
        for (MonthlyClosurePayRoll dt : f.getPayRolls()) {
            if (!detallesDTO.contains(dt)) {
                toRemoveList.add(dt);
            } else {
                toUpdateList.add(dt);
            }
        }
        for (MonthlyClosurePayRoll dt : toRemoveList) {
            f.getPayRolls().remove(dt);

        }
        for (MonthlyClosurePayRoll dto : detallesDTO) {

            if (!f.getPayRolls().contains(dto)) {
                MonthlyClosurePayRoll fdt = new MonthlyClosurePayRoll();
                try {
                    BeanUtils.copyProperties(fdt, dto);
                    f.getPayRolls().add(fdt);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void copyMonthlyClosureBillCollect(List<MonthlyClosureBillCollect> detallesDTO, MonthlyClosure f) {
        MonthlyClosureBillCollect dto1;
        List<MonthlyClosureBillCollect> toUpdateList = new ArrayList<>();
        List<MonthlyClosureBillCollect> toRemoveList = new ArrayList<>();
        for (MonthlyClosureBillCollect dt : f.getBillCollects()) {
            if (!detallesDTO.contains(dt)) {
                toRemoveList.add(dt);
            } else {
                toUpdateList.add(dt);
            }
        }
        for (MonthlyClosureBillCollect dt : toRemoveList) {
            f.getBillCollects().remove(dt);

        }
        for (MonthlyClosureBillCollect dto : detallesDTO) {

            if (!f.getBillCollects().contains(dto)) {
                MonthlyClosureBillCollect fdt = new MonthlyClosureBillCollect();
                try {
                    BeanUtils.copyProperties(fdt, dto);
                    f.getBillCollects().add(fdt);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void copyMonthlyClosureBillPay(List<MonthlyClosureBillPay> detallesDTO, MonthlyClosure f) {
        MonthlyClosureBillPay dto1;
        List<MonthlyClosureBillPay> toUpdateList = new ArrayList<>();
        List<MonthlyClosureBillPay> toRemoveList = new ArrayList<>();
        for (MonthlyClosureBillPay dt : f.getBillPays()) {
            if (!detallesDTO.contains(dt)) {
                toRemoveList.add(dt);
            } else {
                toUpdateList.add(dt);
            }
        }
        for (MonthlyClosureBillPay dt : toRemoveList) {
            f.getBillPays().remove(dt);

        }
        for (MonthlyClosureBillPay dto : detallesDTO) {

            if (!f.getBillPays().contains(dto)) {
                MonthlyClosureBillPay fdt = new MonthlyClosureBillPay();
                try {
                    BeanUtils.copyProperties(fdt, dto);
                    f.getBillPays().add(fdt);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public MonthlyClosureGenDTO getMonthlyClosureGenData(Integer id) {
        MonthlyClosureGenDTO res = new MonthlyClosureGenDTO();
        Optional<MonthlyClosure> op = this.repository.findById(id);
        if (op==null) {
            throw new RuntimeException("No found closure " + id);
        }
        MonthlyClosure monthlyClosure = op.get();
        boolean isFrozen = StringHelper.STATUS_DATA_FROZEN.equals(monthlyClosure.getStatus());

       res.setBillCollects(getBillCollects(monthlyClosure, isFrozen));
       res.setBillPays(getBillPays(monthlyClosure, isFrozen));
       res.setPayRollDTOS(getPayRolls(monthlyClosure, isFrozen));
       res.setCurrent(monthlyClosure);
       return res;
    }

    private List<BillCollectDTO> getBillCollects(MonthlyClosure monthlyClosure, boolean isFrozen) {
        List<MonthlyClosureBillCollect> monthlyClosureBillCollects = monthlyClosure.getBillCollects();
        List<BillCollect> billCollects = new ArrayList<>();
        if(!isFrozen) {
            billCollects = this.billCollectService.getByDates(monthlyClosure.getStart(), monthlyClosure.getEnd(), false);
        }
        List<BillCollectDTO> billCollectDTOS = this.getBillCollectDto(billCollects, monthlyClosureBillCollects);

        return billCollectDTOS;
    }

    private List<BillPayDTO> getBillPays(MonthlyClosure monthlyClosure, boolean isFrozen) {
        List<MonthlyClosureBillPay> bills = monthlyClosure.getBillPays();
        List<BillPay> billPays = new ArrayList<>();
        if(!isFrozen) {
            billPays = this.billPayService.getByDates(monthlyClosure.getStart(), monthlyClosure.getEnd(), false);
        }
        List<BillPayDTO> billPayDTO = this.getBillPayDto(billPays, bills);

        return billPayDTO;
    }

    private List<PayRollDTO> getPayRolls(MonthlyClosure monthlyClosure, boolean isFrozen) {
        List<MonthlyClosurePayRoll> bills = monthlyClosure.getPayRolls();
        List<PayRoll> payRolls = new ArrayList<>();
        if(!isFrozen) {
            payRolls = this.payRollService.getPayRollsByDates(monthlyClosure.getStart(), monthlyClosure.getEnd(), StringHelper.STATUS_DATA_APROB, false);
        }
        List<PayRollDTO> billPayDTO = this.getPayRoll(payRolls, bills);

        return billPayDTO;
    }

    private List<PayRollDTO> getPayRoll(List<PayRoll> prs, List<MonthlyClosurePayRoll> mcprs) {
        List<PayRollDTO> ret = new ArrayList<PayRollDTO>();
        PayRollDTO temp;
        for (MonthlyClosurePayRoll mcpr : mcprs) {
            temp = new PayRollDTO(mcpr.getPayRoll());
            temp.setInThisClosure("TRUE");
            ret.add(temp);
        }
        for (PayRoll pr : prs) {
            temp = new PayRollDTO(pr);
            ret.add(temp);
        }
        return ret;
    }

    private List<BillPayDTO> getBillPayDto(List<BillPay> bps, List<MonthlyClosureBillPay> mcbps) {
        List<BillPayDTO> ret = new ArrayList<BillPayDTO>();
        BillPayDTO temp;
        if (mcbps != null){
            for (MonthlyClosureBillPay mcbp : mcbps) {
                temp = new BillPayDTO(mcbp.getBillPay());

                temp.setInThisClosure("TRUE");
                ret.add(temp);
            }
        }
        if(bps!=null){
            for (BillPay bp : bps) {
                temp = new BillPayDTO(bp);
                ret.add(temp);
            }
        }
        return ret;
    }

    private List<BillCollectDTO> getBillCollectDto(List<BillCollect> bcs, List<MonthlyClosureBillCollect> mcbcs) {
        List<BillCollectDTO> ret = new ArrayList<BillCollectDTO>();
        BillCollectDTO temp;

        if(mcbcs!=null){
            for (MonthlyClosureBillCollect mcbc : mcbcs) {
                temp = new BillCollectDTO(mcbc.getBillCollect());
                temp.setInThisClosure("TRUE");
                ret.add(temp);
            }
        }
        if(bcs!=null){
            for (BillCollect bc : bcs) {
                temp = new BillCollectDTO(bc);
                ret.add(temp);
            }
        }
        return ret;

    }

    public MonthlyClosureGenDTO update(MonthlyClosureGenDTO c) throws Exception {

        this.update(c.getCurrent());
        saveBillCollects(c.getUpdateBillCollects());
        saveBillPays(c.getUpdateBillPays());
        savePayRolls(c.getUpdatePayRolls());
        return c;
    }

    private void savePayRolls(List<PayRoll> updatePayRolls) {
        if (updatePayRolls==null || updatePayRolls.size()==0) {
            return;
        }
        PayRoll current;
        for (PayRoll c: updatePayRolls) {
            current =  this.payRollService.getPayRollById(c.getId());
            current.setInClosing(c.getInClosing());
            this.payRollService.save(current);
        }
    }

    public void saveBillPays(List<BillPay> updateBillPays) throws Exception {
        if (updateBillPays==null || updateBillPays.size()==0) {
            return;
        }
        BillPay current;
        for (BillPay c: updateBillPays) {
                current =  this.billPayService.getBillPayById(c.getId());
            current.setInClosing(c.getInClosing());
            this.billPayService.save(current);
        }
    }

    private void saveBillCollects(List<BillCollect> updateBillCollects) {
        if (updateBillCollects==null || updateBillCollects.size()==0) {
            return;
        }
        BillCollect current;
        for (BillCollect c: updateBillCollects) {
            current =  this.billCollectService.getBillPayById(c.getId());
            current.setInClosing(c.getInClosing());
            this.billCollectService.save(current);
        }
    }

    public Result delete(MonthlyClosure mc) {
        this.repository.delete(mc);

        Result r = null;

        try {

            r = this.deleteChildren(mc);
            if (r!=null && r.getCode()==Result.RESULT_CODE.DELETE) {
                this.repository.delete(mc);

            } else {
//                System.out.println("" + r.getMesage());
            }

            //r = new Result(Result.RESULT_CODE.DELETE, "Se eliminó el cierre");
        } catch (Exception e) {
            r = new Result(Result.RESULT_CODE.ERROR, "Error al borrar: " + e.getMessage());
        }
        return r;
    }

    private Result deleteChildren(MonthlyClosure mc) {
        Result result = null;
        try {

            //Actualiza los estados de las planillas y borra las referencias
//            List<Integer> ids= this.getMonthlyClosurePayRollService().getPayRollIdsByParent(params.getId());
            List<Integer> ids= this.getMonthlyClosurePayRollIds(mc.getPayRolls());
            if (ids!=null && ids.size()>0) {
                this.payRollService.updatePayRollsOutOfClosure(ids);
            }
            //this.getMonthlyClosurePayRollService().deleteByParent(params.getId());


            //Actualiza los estados de las cxp y borra las referencias
            ids= this.getMonthlyClosureCxP(mc.getBillPays());
            if (ids!=null && ids.size()>0) {
                this.billPayService.updateBillPaysOutOfClosure(ids);
            }
            //this.getMonthlyClosureBillPayService().deleteByParent(params.getId());

            //Actualiza loes tados de las cxc y borra las referencias
            ids = this.getMonthlyClosureCxC(mc.getBillCollects());
            if (ids!=null && ids.size()>0) {
                this.billCollectService.updateBillCollectOutOfClosure(ids);
            }
            //this.getMonthlyClosureBillCollectService().deleteByParent(params.getId());

            //Borra el cierre
//            this.getMonthlyClosureService().delete(params.getId());
            result = new Result(Result.RESULT_CODE.DELETE, "Se eliminó el cierre");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.RESULT_CODE.ERROR, "Error al borrar: " + e.getMessage());
        }
        return result;
    }

    private List<Integer> getMonthlyClosurePayRollIds(List<MonthlyClosurePayRoll> payRolls) {

        List<Integer> ids = new ArrayList<>();
        for (MonthlyClosurePayRoll pr: payRolls) {
            ids.add(pr.getPayRoll().getId());
        }
        return ids;
    }

    private List<Integer> getMonthlyClosureCxC(List<MonthlyClosureBillCollect> bc) {

        List<Integer> ids = new ArrayList<>();
        for (MonthlyClosureBillCollect b: bc) {
            ids.add(b.getBillCollect().getId());
        }
        return ids;
    }

    private List<Integer> getMonthlyClosureCxP(List<MonthlyClosureBillPay> bp) {

        List<Integer> ids = new ArrayList<>();
        for (MonthlyClosureBillPay b: bp) {
            ids.add(b.getBillPay().getId());
        }
        return ids;
    }

    public MonthlyClosureGenDTO getMonthlyClosureBillData(java.util.Date start, java.util.Date end, Integer id, String type) {

        MonthlyClosureGenDTO res = new MonthlyClosureGenDTO();
        Optional<MonthlyClosure> op = this.repository.findById(id);
        if (op==null) {
            throw new RuntimeException("No found closure " + id);
        }
        MonthlyClosure monthlyClosure = op.get();
        boolean isFrozen = StringHelper.STATUS_DATA_FROZEN.equals(monthlyClosure.getStatus());

       // res.setBillCollects(getBillCollects(monthlyClosure, isFrozen));
        if (type!=null && type.equals("CXP")) {
            res.setBillPays(getBillPaysPendings(monthlyClosure, start, end));
        } else if (type!=null && type.equals("CXC")) {
            res.setBillCollects(getBillCollectPendings(monthlyClosure, start, end));
        } else {
            res.setBillPays(getBillPaysPendings(monthlyClosure, start, end));
            res.setBillCollects(getBillCollectPendings(monthlyClosure, start, end));
        }
      //  res.setPayRollDTOS(getPayRolls(monthlyClosure, isFrozen));
        res.setCurrent(monthlyClosure);
        return res;
    }

    private List<BillPayDTO> getBillPaysPendings(MonthlyClosure monthlyClosure, java.util.Date start, java.util.Date end) {
        List<MonthlyClosureBillPay> bills = monthlyClosure.getBillPays();
        List<BillPay> billPays = new ArrayList<>();

        billPays = this.billPayService.getByDates(start, end, false);

        List<BillPayDTO> billPayDTO = this.getBillPayPendingDto(billPays);

        return billPayDTO;
    }

    private List<BillPayDTO> getBillPayPendingDto(List<BillPay> bps) {
        List<BillPayDTO> ret = new ArrayList<BillPayDTO>();
        BillPayDTO temp;

        if(bps!=null){
            for (BillPay bp : bps) {
                temp = new BillPayDTO(bp);
                ret.add(temp);
            }
        }
        return ret;
    }

    private List<BillCollectDTO> getBillCollectPendings(MonthlyClosure monthlyClosure, java.util.Date start, java.util.Date end) {
        List<MonthlyClosureBillPay> bills = monthlyClosure.getBillPays();
        List<BillCollect> bcs = new ArrayList<>();

        bcs = this.billCollectService.getByDates(DateUtil.convertDateToSqlDate(start), DateUtil.convertDateToSqlDate(end), false);

        List<BillCollectDTO> billPayDTO = this.getBillCollectPendingDto(bcs);

        return billPayDTO;
    }

    private List<BillCollectDTO> getBillCollectPendingDto(List<BillCollect> bps) {
        List<BillCollectDTO> ret = new ArrayList<BillCollectDTO>();
        BillCollectDTO temp;

        if(bps!=null){
            for (BillCollect bp : bps) {
                temp = new BillCollectDTO(bp);
                ret.add(temp);
            }
        }
        return ret;
    }
}
