package com.ndl.erp.services;

import com.ndl.erp.constants.CostCenterConstants;
import com.ndl.erp.domain.Currency;
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

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.*;

//import org.springframework.beans.BeanUtils;

@Component
public class BalanceService {

    private final transient Logger log = LoggerFactory.getLogger(BalanceService.class);

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

//    List<String> months;
//
//    private HashMap<Integer,BalanceCostCenterDTO> balance;

    @Autowired
    private LaborCostService laborCostService;


    public BalanceDTO generate(Date start, Date end) {
        List<String> months = new ArrayList<>();

        HashMap<Integer,BalanceCostCenterDTO> balance = null;
        BalanceDTO result = new BalanceDTO();

        generateMonthsByDates(start, end, months);

        Currency currencySystem = this.currencyService.getSystemCurrency();
        List<MonthlyClosure> closures = this.repository.getByDates(start, end);

        balance = getLaborCost(closures, currencySystem, balance);


        balance = getBillPays(currencySystem,start,end, balance);

        balance = getKms(currencySystem,start,end, balance);

        balance = getRefundables(currencySystem,start,end, balance);

        balance = calcBalanceAndRentabilidad(balance);

        result.setBalance(balance);
        result.setMonths(months);
        result.setSystemCurrency(currencySystem);
        return result;
    }

    public HashMap<Integer,BalanceCostCenterDTO> calcBalanceAndRentabilidad(HashMap<Integer,BalanceCostCenterDTO> balance){
        if(balance!=null && !balance.isEmpty()){
            for( BalanceCostCenterDTO cc:balance.values()){

                cc.calcBalanceAndRentabilidad();
            }
        }
        return balance;
    }

    public void generateMonthsByDates(Date ini, Date end, List<String> months){
//        try {

            Calendar cini = Calendar.getInstance();
            cini.setTime(ini);

            Calendar cend = Calendar.getInstance();
            cend.setTime(end);
            int mini = cini.get(Calendar.MONTH) + 1;
            int aini = cini.get(Calendar.YEAR);

            int mfin = cend.get(Calendar.MONTH) + 1;
            int afin = cend.get(Calendar.YEAR);
            int annos = afin - aini + 1;
            for(int i=1;i<=annos; i++){
                if(i == 1 && mini > 0 && annos >= 1){
                    this.addMothbyYear(mini,annos == 1 ? mfin:12,aini++,true, months);
                }else if(i < annos){
                    this.addMothbyYear(1, 12,aini++,true, months);
                }else if(i==annos){
                    this.addMothbyYear(1, mfin,aini++,true, months);
                }
            }


    }

    private HashMap<Integer,BalanceCostCenterDTO> getRefundables(Currency curSystem,Date startDate,Date endDate, HashMap<Integer,BalanceCostCenterDTO> balance) {
        List<BalanceRefundableDTO> rDetails=this.refundableService.getBalanceRefundableDTO(startDate, endDate);
        Currency curr = null;
        for(BalanceRefundableDTO rfd: rDetails){

            BalanceCostCenterDTO cc = getCostCenter(rfd.getIdCostCenter(), balance);

            if(cc!=null){
                curr=this.getCurrency(curr,rfd.getIdCurrency(),rfd.getStatusMC(),rfd.getBuyMC(),rfd.getSaleMC());
                cc.modifyRefundableMtos(rfd,curr);
            }else{
                curr=this.getCurrency(rfd.getIdCurrency(),rfd.getStatusMC(),rfd.getBuyMC(),rfd.getSaleMC());
                CostCenter costsCenter = rfd.getCostCenter();
                cc = new BalanceCostCenterDTO(rfd,curr,costsCenter, curSystem);

                balance = addBalanceCostCenter(cc, balance);
            }
        }
        return balance;
    }

    private HashMap<Integer,BalanceCostCenterDTO> getKms(Currency curSystem,Date startDate,Date endDate, HashMap<Integer,BalanceCostCenterDTO> balance) {
        List<BalanceKmDTO> kmds= this.kilometerService.getBalanceKmDTO(startDate, endDate);
        Currency curr = null;
        for(BalanceKmDTO kmd: kmds){

            BalanceCostCenterDTO cc = getCostCenter(kmd.getIdCostCenter(), balance);

            if(cc!=null){
                curr=this.getCurrency(curr,kmd.getIdCurrency(),kmd.getStatusMC(),kmd.getBuyMC(),kmd.getSaleMC());
                cc.modifyKmMtos(kmd,curr);
            }else{
                curr  =this.getCurrency(kmd.getIdCurrency(),kmd.getStatusMC(),kmd.getBuyMC(),kmd.getSaleMC());
                CostCenter costsCenter = kmd.getCostCenter();
                cc = new BalanceCostCenterDTO(kmd,curr,costsCenter, curSystem);

                balance = addBalanceCostCenter(cc, balance);
            }
        }
        return balance;
    }

    private HashMap<Integer,BalanceCostCenterDTO> getBillPays(Currency curSystem,Date startDate,Date endDate, HashMap<Integer,BalanceCostCenterDTO> balance) {
        List<BalanceBillPayDTO> bpds=this.billPayService.getBalanceBillPayDTO(startDate, endDate);
        Currency curr = null;
        for(BalanceBillPayDTO bp: bpds){
            BalanceCostCenterDTO cc = getCostCenter(bp.getIdCostCenter(), balance);

            if(cc!=null){

                curr= getCurrency(curr,bp.getIdCurrency(),bp.getStatusMC(),bp.getBuyMC(),bp.getSaleMC());
                cc.modifyBillPaysMtos(bp,curr);
            }else{

                curr = this.getCurrency(bp.getIdCurrency(),bp.getStatusMC(),bp.getBuyMC(),bp.getSaleMC());
                CostCenter costsCenter = bp.getCostCenter();
                cc = new BalanceCostCenterDTO(bp,curr,costsCenter, curSystem);

//                addBalanceCostCenter(cc, balance);
                balance = addBalanceCostCenter(cc, balance);
            }
        }
        return balance;
    }

    private Currency getCurrency(Currency actual, Integer idCurrencyRow,String status, Double buy, Double sale){
        Currency ret = actual;
        if(actual==null || !idCurrencyRow.equals(actual.getId())){
            ret=  this.currencyService.getCurrencyById(idCurrencyRow);
        }
        if(StringHelper.STATUS_DATA_FROZEN.equals(status) && !ret.getDefault()){
            ret.setValueBuy(buy);
            ret.setValueSale(sale);
        }
        return ret;
    }

    private Currency getCurrency(Integer id,String status, Double buy, Double sale){
        Currency curr = this.currencyService.getCurrencyById(id);
        curr  =this.getCurrency(curr,curr.getId(),status,buy,sale);
        return curr;
    }

    private HashMap<Integer, BalanceCostCenterDTO> getLaborCost(List<MonthlyClosure> closures, Currency system, HashMap balance) {
        balance = new HashMap<Integer, BalanceCostCenterDTO>();
        for (MonthlyClosure mc : closures) {
//            List<MonthlyClosurePayRoll> monthlyClosurePayRolls = this.getMonthlyClosurePayRollService().getAllByIdMonthlyClosure(mc.getId());
            List<MonthlyClosurePayRoll> monthlyClosurePayRolls = mc.getPayRolls();
            List<Integer> ids = this.getPayRollId(monthlyClosurePayRolls);

            if(ids!=null && !ids.isEmpty()){
                List<BalanceLaborCostDetailDTO> blcds = this.laborCostService.getBalanceLaborCostDetailDTO(ids);
                Currency curr = null;
                for(BalanceLaborCostDetailDTO blcd: blcds){
                    BalanceCostCenterDTO cc = getCostCenter(blcd.getCostCenterId(), balance);

                    if(cc!=null){
                        if(curr==null || !blcd.getCurrencyId().equals(curr.getId())){
                            curr = this.currencyService.getCurrencyById(blcd.getCurrencyId());
                        }
                        cc.modifyLaborCostMtos(blcd,curr);
                    }else{

                        curr = this.currencyService.getCurrencyById(blcd.getCurrencyId());
                        //CostsCenter costsCenter = this.getCostsCenterService().getCostsCenter(blcd.getId_cost_center());
                        cc = new BalanceCostCenterDTO(blcd,curr,blcd.getCostCenter(), system);

                        balance = addBalanceCostCenter(cc, balance);
                    }
                }
            }
        }
        return balance;
    }

    public HashMap<Integer, BalanceCostCenterDTO>  addBalanceCostCenter(BalanceCostCenterDTO cc, HashMap<Integer, BalanceCostCenterDTO> balance) {
        if(balance==null)
            balance = new HashMap<>();
        balance.put(cc.getCostCenter().getId(),cc);
        return balance;
    }

    public BalanceCostCenterDTO getCostCenter(Integer id, HashMap<Integer, BalanceCostCenterDTO> balance){
        BalanceCostCenterDTO  ret= null;
        if(balance!=null && !balance.isEmpty() && balance.containsKey(id)){
            ret= balance.get(id) ;
        }
        return ret;
    }

    public void addMonth(String month, List<String> months){
//        if(months==null) {
//            months = new ArrayList<>();
//        }
        months.add(month);
    }

    private List<String> addMothbyYear(int mini,int mfin, int year, boolean all, List<String> months){
//        List<String> months = new ArrayList<>();;
        for(int i=mini; i<=mfin; i++){
            this.addMonth(StringHelper.getKeyMonths(i,year), months);
        }
        return months;
    }

    /************************************************************/

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
//        Currency curDef = this.getCurrencyService().getSystemCurrency();
//        GeneralParameter gp = this.generalService.getByCode(StringHelper.CUR_DEF_PAY_ROLL);
//        Currency curPayRoll = this.currencyService.getCurrencyById(StringHelper.getValInt(gp.getVal()));

        //Datos de las planillas.

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
        User u = this.userService.getCurrentLoggedUser();
        c.setIdUser(u.getId().intValue());
        c.setUpdateDate(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
       c =  this.repository.save(c);
       return c;
    }

    public MonthlyClosure update(MonthlyClosure c) {

        User u = this.userService.getCurrentLoggedUser();
       Optional<MonthlyClosure> co =  this.repository.findById(c.getId());
       MonthlyClosure c1 = co.get();
        c1.setIdUser(u.getId().intValue());
        c1.setUpdateDate(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
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

    private void saveBillPays(List<BillPay> updateBillPays) throws Exception {
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
}
