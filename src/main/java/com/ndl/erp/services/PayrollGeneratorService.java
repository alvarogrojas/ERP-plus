package com.ndl.erp.services;

import com.ndl.erp.domain.*;

import com.ndl.erp.dto.PayRollDTO;
import com.ndl.erp.dto.Result;
import com.ndl.erp.repository.*;
import com.ndl.erp.util.CalculatorPayRoll;
import com.ndl.erp.util.DateUtil;
import com.ndl.erp.util.RefundDevolution;
import com.ndl.erp.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;


@Component
@Transactional
public class PayrollGeneratorService {

    private static final String REINTEGRO = "REINTEGRO";
    private static final String KEY_DEVOLUTIONS = "devoluciones";
    private static final String KEY_REFUNDS = "reintegros";

    private final transient Logger log = LoggerFactory.getLogger(PayrollGeneratorService.class);

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private LaborCostService laborCostDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeductionService deductionService;

    @Autowired
    private PayRollRepository payRollRepository;

    @Autowired
    private CollaboratorService collaboratorService;

    @Autowired
    private DeductionsRefundsService deductionsRefundsService;

    @Autowired
    private DeductionsDetailsRepository deductionsDetailsRepository;

    @Autowired
    private TaxesService taxesService;

    @Autowired
    private PayRollDetailRepository payRollDetailRepository;

    @Autowired
    private PayRollCollaboratorDetailRepository payRollCollaboratorDetailRepository;

    @Autowired
    private PayRollCollaboratorDeductionDetailRepository payRollCollaboratorDeductionDetailRepository;

    @Autowired
    private PayRollCollaboratorRefundDevolutionDetailRepository payRollCollaboratorRefundDevolutionDetailRepository;

    @Autowired
    private PayrollService payrollService;


    public Result generatePayRoll(Date start, Date end) {

        Result result;

        try {
            log.info("INICIANDO generarPlanilla:: getLaborCostDetailRangeDate");
            //ORDENADOS POR COLLABORADOR, este orden es el que permite que el algoritmo funcione correctamente
            //@TODO: usar cache con id de colaboradores y no dependen del order by
            List<LaborCostDetail> laborCostDetails = this.laborCostDetailService.getLaborCostDetailOrderByCollaborator(start, end);
            log.debug("INICIANDO generarPlanilla 2");

            log.info("INICIANDO generarPlanilla");


            log.info("Obtener el usuario logeado al sistema");
            User user = this.userService.getCurrentLoggedUser();

            log.info("Creando el encabezado de la planilla:: addPayRoll");
            Long id = user.getId();
            PayRoll payRoll = this.addPayRoll(start, end, id.intValue());

            log.info("Iniciando el calculo de la planilla :: Invoke :: calcPayRoll ");
            List<CalculatorPayRoll> calculatorPayRolls = this.calcPayRoll(laborCostDetails, payRoll);

            log.info("Renererando:: addPayRollDetail 6 ");
            this.addPayRollDetail(calculatorPayRolls, payRoll, start, end, user);

            log.info("Renererando:: addCollaboratorDeduction 7 ");
            this.addCollaboratorDeduction(calculatorPayRolls, payRoll, start, end, user);

            log.info("Renererando:: addCollaboratorRefundDevolutionDetail 8 ");
            this.addCollaboratorRefundDevolutionDetail(calculatorPayRolls, payRoll, start, end, user);

            log.info("Renererando:: Fin 9 ");
            result = new Result(Result.RESULT_CODE.GENERATE, "Planilla generada correctamente");
//            PayRollDTO dto = new PayRollDTO(payRoll);
            PayRollDTO dto = this.payrollService.get(payRoll.getId());
            result.setResult(dto);
        } catch (Exception e) {
            result = new Result(Result.RESULT_CODE.ERROR, "Error al generar la planilla");
            result.setStackTrace(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private PayRoll addPayRoll(Date start, Date end, Integer idUser) {
        PayRoll payRoll = new PayRoll();
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();

        calendarStart.setTime(start);
        Integer weekStart = calendarStart.get(Calendar.WEEK_OF_YEAR);

        calendarEnd.setTime(end);
        Integer weekEnd = calendarEnd.get(Calendar.WEEK_OF_YEAR);
        if (weekEnd.equals(weekStart + 2))
            weekStart++;

        Integer year = calendarEnd.get(Calendar.YEAR);
        StringBuffer name = new StringBuffer("Semana ").
                append(weekStart).append(" y ").append(weekEnd).append(" del ").append(year);

        payRoll.setName(name.toString());
        payRoll.setUpdateDate(new Date(System.currentTimeMillis()));
        payRoll.setStatus(StringHelper.STATUS_DATA_GEN);
        payRoll.setCreateDate(new Date(System.currentTimeMillis()));
        payRoll.setIdUserGenerate(idUser);
        payRoll.setStart(start);
        payRoll.setEnd(end);

        PayRoll t = this.save(payRoll);
//        payRoll.setId(id);
//        return payRoll;
        return t;
    }

    private PayRoll save(PayRoll c) {
        c.setStatusClosing(StringHelper.STATUS_DATA_ING);
        c.setInClosing(false);
        c.setCreateDate(new Date(DateUtil.getCurrentTime()));
        return payRollRepository.save(c);
    }

    private List<CalculatorPayRoll> calcPayRoll(List<LaborCostDetail> laborCostDetails, PayRoll payRoll) {
        List<CalculatorPayRoll> ret = new ArrayList<CalculatorPayRoll>();

        Integer idCollaborator = null;
        Collaborator collaborator = null;
        CalculatorPayRoll calculatorPayRoll = null;

        log.info("For para recorrer los detalles de las horas de trabajo de cada colaborador. ");
        Deductions d = null;
        for (LaborCostDetail lcd : laborCostDetails) {
            Integer idCol = lcd.getCollaborator().getId();

            if (idCol != idCollaborator) {
                calculatorPayRoll = new CalculatorPayRoll();
                calculatorPayRoll.setPayRoll(payRoll);
                collaborator = this.collaboratorService.getCollaboratorBy(idCol);

                idCollaborator = collaborator.getId();
                calculatorPayRoll.setCollaborator(collaborator);

                log.info("Obtiene los devoluciones y reintegros de cada colaborador :" + idCol);
                Map rd = this.getRefundsAndDevolution(collaborator);
                calculatorPayRoll.setRefunds((List<RefundDevolution>) rd.get(KEY_REFUNDS));
                calculatorPayRoll.setDevolutions((List<RefundDevolution>) rd.get(KEY_DEVOLUTIONS));


                //TODO: Validar si requiere un stado
                log.info("Obtiene las deducciones del colaborador:" + idCol);
                List<DeductionsDetails> deductionDetails = this.deductionsDetailsRepository.
                        getDeductionsByCollaborator(collaborator.getId());

                log.info("Inicio del calculo de las deducciones.");
                for (DeductionsDetails dd : deductionDetails) {
                    if(d==null || !d.getId().equals(dd.getId()))
                        d = this.deductionService.getDeductionsById(dd.getDeduction().getId());
                    if(d!=null) {
                        if (d.getType().equals(StringHelper.CCSS)) {
                            calculatorPayRoll.setCcssDeduction(d);
                        } else {
                            calculatorPayRoll.addOthersDeduction(d);
                        }
                    }else {
                        log.info("No se encontro una deduccion para el colaborador:" + idCol);
                    }

                }
                calculatorPayRoll.addTaxes(this.taxesService.getTaxesList());

                ret.add(calculatorPayRoll);
            }
            calculatorPayRoll.addLaborCostDetails(lcd);

        }
        return ret;
    }

    private Map<String, List<RefundDevolution>> getRefundsAndDevolution(Collaborator collaborator) {
        HashMap<String, List<RefundDevolution>> ret = new HashMap<String, List<RefundDevolution>>();
        List<RefundDevolution> refunds = new ArrayList<RefundDevolution>();
        List<RefundDevolution> devolutions = new ArrayList<RefundDevolution>();


        ret.put(KEY_DEVOLUTIONS, devolutions);
        ret.put(KEY_REFUNDS, refunds);


        log.info("Obteniendo las devoluciones y reintegros del colaborador:" + collaborator.getName());
        List<DeductionsRefunds> deductionsRefunds = this.deductionsRefundsService.getDeductionsRefundsByCollaboratorIdList(collaborator.getId());
        for (DeductionsRefunds dr : deductionsRefunds) {
            if (!dr.getType().isEmpty() && dr.getType().equals(REINTEGRO)) {
                RefundDevolution refund = new RefundDevolution();
                refund.setDeductionsRefunds(dr);
                //refund.setDeductionsRefundsDetails(dr.getDetails());
                refund.setDeductionsRefundsDetails(this.deductionsRefundsService.getDeductionDetailByIdDeductionAndActive(dr.getId()));
                if (refund.getDeductionsRefundsDetails() !=null)
                    refunds.add(refund);
            } else {
                RefundDevolution devolution = new RefundDevolution();
                devolution.setDeductionsRefunds(dr);
                devolution.setDeductionsRefundsDetails(this.deductionsRefundsService.getDeductionDetailByIdDeductionAndActive(dr.getId()));
                if (devolution.getDeductionsRefundsDetails()!=null)
                    devolutions.add(devolution);
            }
        }

        log.info("Fin del proceso donde obtine las deducciones y reintegros:" + collaborator.getName());
        return ret;
    }

    private void addPayRollDetail(List<CalculatorPayRoll> calculatorPayRolls, PayRoll payRoll, Date start, Date end, User user) {
        List<Map<String,Object>> prds = new ArrayList<>();

        if (payRoll.getDetails()==null) {
            payRoll.setDetails(new HashSet<>());
        }

        Long id = user.getId();
        for (CalculatorPayRoll cpr : calculatorPayRolls) {
            cpr.calcNetSalry();
            PayRollDetail payRollDetail = new PayRollDetail();
            payRollDetail.setPayRoll(payRoll);
            payRollDetail.setCollaborator(cpr.getCollaborator());
            payRollDetail.setNetSalary(cpr.getNetSalary());
            payRollDetail.setDeducctions(cpr.getMtoOthersDeductions()); //CCS + RENTA + DEDUCTIONS Others
            payRollDetail.setDevolutions(cpr.getDevolution());
            payRollDetail.setRefunds(cpr.getRefund());
            payRollDetail.setStatus(StringHelper.STATUS_DATA_ING);
            payRollDetail.setCrudeSalary(cpr.getBruteSalary());
            payRollDetail.setIdUserRegister(id.intValue());
            payRollDetail.setStartDate(start);
            payRollDetail.setEndDate(end);
            payRollDetail.setCreateDate(new Date(System.currentTimeMillis()));
            payRollDetail.setUpdateDate(new Date(System.currentTimeMillis()));
            payRollDetail.setCcss(cpr.getCcss());
            payRollDetail.setTax1(cpr.getTax1());
            payRollDetail.setTax2(cpr.getTax2());
            payRollDetail.setTax3(cpr.getTax3());

            //Se agrega a las lista
            Map<String, Object> temp = new HashMap<>();
            temp.put("CalculatorPayRoll",cpr );
            temp.put("PayRollDetail", payRollDetail);
            payRoll.getDetails().add(payRollDetail);
            prds.add(temp);
        }

        this.addPayRollDetails(prds);

        List <PayRollCollaboratorDetail> prcds = new ArrayList<>();

        for(Map <String,Object> item: prds) {
            CalculatorPayRoll cpr = (CalculatorPayRoll) item.get("CalculatorPayRoll");
            PayRollDetail payRollDetail = (PayRollDetail) item.get("PayRollDetail");
            prcds.addAll(this.addPayRollCollaboratorDetail(cpr, payRollDetail));
        }
        this.addPayRollCollaboratorDetails(prcds);
    }

    public int addPayRollCollaboratorDetails(List<PayRollCollaboratorDetail> list) {
        log.debug("Iniciando PayRollCollaboratorDetailServiceImpl: addPayRollCollaboratorDetails");
        List<Integer> ids =  new ArrayList<>();
        for( PayRollCollaboratorDetail c: list){
            c.setCreateDate(new Date(DateUtil.getCurrentTime()));
            c = this.payRollCollaboratorDetailRepository.save(c);
            ids.add(c.getId());
        }
        log.debug("Finalizado PayRollCollaboratorDetailServiceImpl: addPayRollCollaboratorDetails");
        return ids.size();
    }

    private void addPayRollDetails(List<Map<String, Object>> prds) {

        log.debug("Iniciando PayRollDetailServiceImpl: addPayRollDetails");
        PayRollDetail t;
        for( Map item : prds) {
            PayRollDetail c = (PayRollDetail) item.get("PayRollDetail");
            c.setCreateDate(new Date(DateUtil.getCurrentTime()));
            t = this.payRollDetailRepository.save(c);
            c.setId(t.getId());
        }

    }

    private List <PayRollCollaboratorDetail>  addPayRollCollaboratorDetail(CalculatorPayRoll cpr, PayRollDetail payRollDetail) {
        List <PayRollCollaboratorDetail> ret = new ArrayList<>();

        PayRollCollaboratorDetail prcdHS1 = this.getHeaderPayRollCollaboratorDetail(cpr, payRollDetail);
        PayRollCollaboratorDetail prcdHS2 = this.getHeaderPayRollCollaboratorDetail(cpr, payRollDetail);

        PayRollCollaboratorDetail prcdHM1 = this.getHeaderPayRollCollaboratorDetail(cpr, payRollDetail);
        PayRollCollaboratorDetail prcdHM2 = this.getHeaderPayRollCollaboratorDetail(cpr, payRollDetail);

        PayRollCollaboratorDetail prcdHD1 = this.getHeaderPayRollCollaboratorDetail(cpr, payRollDetail);
        PayRollCollaboratorDetail prcdHD2 = this.getHeaderPayRollCollaboratorDetail(cpr, payRollDetail);

        prcdHS1.setType("HS");
        prcdHS2.setType("HS");

        prcdHM1.setType("HM");
        prcdHM2.setType("HM");

        prcdHD1.setType("HD");
        prcdHD2.setType("HD");

        prcdHS1.setIndice(1);
        prcdHS2.setIndice(2);

        prcdHM1.setIndice(1);
        prcdHM2.setIndice(2);

        prcdHD1.setIndice(1);
        prcdHD2.setIndice(2);


        for (LaborCostDetail lcd : cpr.getLaborCostDetails()) {
            this.setHours(payRollDetail.getStartDate(), lcd, prcdHS1, prcdHS2, prcdHM1, prcdHM2, prcdHD1, prcdHD2);
        }


        if(prcdHS1.getCollaborator().getTypePayroll().equals(StringHelper.EMP_CONFIANSA)) {
            prcdHS1.setCantidad(prcdHS1.getCollaborator().getCcssHoursWork() / 2); //StringHelper.EMP_CONFIANSA_CANTIDAD
            prcdHS2.setCantidad(prcdHS2.getCollaborator().getCcssHoursWork() / 2); //StringHelper.EMP_CONFIANSA_CANTIDAD
        }else{
            prcdHS1.refreshCantidad();
            prcdHS2.refreshCantidad();
        }

        prcdHM1.refreshCantidad();
        prcdHM2.refreshCantidad();

        prcdHD1.refreshCantidad();
        prcdHD2.refreshCantidad();

        prcdHS1.setTotal(prcdHS1.getCantidad() * prcdHS1.getPrice());
        prcdHS2.setTotal(prcdHS2.getCantidad() * prcdHS2.getPrice());

        prcdHM1.setTotal(prcdHM1.getCantidad() * prcdHM1.getPrice() * StringHelper.HM);
        prcdHM2.setTotal(prcdHM2.getCantidad() * prcdHM2.getPrice() * StringHelper.HM);

        prcdHD1.setTotal(prcdHD1.getCantidad() * prcdHD1.getPrice() * StringHelper.HD);
        prcdHD2.setTotal(prcdHD2.getCantidad() * prcdHD2.getPrice() * StringHelper.HD);


        ret.add(prcdHS1);
        ret.add(prcdHS2);

        ret.add(prcdHM1);
        ret.add(prcdHM2);

        ret.add(prcdHD1);
        ret.add(prcdHD2);
        return ret;

//        this.getPayRollCollaboratorDetailService().addPayRollCollaboratorDetail(prcdHS1);
//        this.getPayRollCollaboratorDetailService().addPayRollCollaboratorDetail(prcdHS2);

//        this.getPayRollCollaboratorDetailService().addPayRollCollaboratorDetail(prcdHM1);
//        this.getPayRollCollaboratorDetailService().addPayRollCollaboratorDetail(prcdHM2);

//        this.getPayRollCollaboratorDetailService().addPayRollCollaboratorDetail(prcdHD1);
//        this.getPayRollCollaboratorDetailService().addPayRollCollaboratorDetail(prcdHD2);
    }

    private PayRollCollaboratorDetail getHeaderPayRollCollaboratorDetail(CalculatorPayRoll cpr, PayRollDetail payRollDetail) {
        PayRollCollaboratorDetail payRollCollaboratorDetail = new PayRollCollaboratorDetail();
        payRollCollaboratorDetail.setPayRoll(cpr.getPayRoll());
        payRollCollaboratorDetail.setCollaborator(cpr.getCollaborator());
        payRollCollaboratorDetail.setPrice(cpr.getCollaborator().getRate());
        payRollCollaboratorDetail.setIdUserRegister(payRollDetail.getIdUserRegister());
        payRollCollaboratorDetail.setStartDate(payRollDetail.getStartDate());
        payRollCollaboratorDetail.setEndDate(payRollDetail.getEndDate());
        payRollCollaboratorDetail.setUpdateDate(new Date(System.currentTimeMillis()));
        payRollCollaboratorDetail.setCreateDate(new Date(System.currentTimeMillis()));
        return payRollCollaboratorDetail;
    }

    private void setHours(Date start, LaborCostDetail lcd, PayRollCollaboratorDetail prcdHS1, PayRollCollaboratorDetail prcdHS2, PayRollCollaboratorDetail prcdHM1, PayRollCollaboratorDetail prcdHM2, PayRollCollaboratorDetail prcdHD1, PayRollCollaboratorDetail prcdHD2) {

        long diff = lcd.getLaborDate().getTime() - start.getTime();
        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

        switch (diffDays) {
            case 0:
                prcdHS1.addDay1(lcd.getHoursSimple());
                prcdHM1.addDay1(lcd.getHoursMedia());
                prcdHD1.addDay1(lcd.getHoursDouble());
                break;

            case 1:
                prcdHS1.addDay2(lcd.getHoursSimple());
                prcdHM1.addDay2(lcd.getHoursMedia());
                prcdHD1.addDay2(lcd.getHoursDouble());
                break;
            case 2:
                prcdHS1.addDay3(lcd.getHoursSimple());
                prcdHM1.addDay3(lcd.getHoursMedia());
                prcdHD1.addDay3(lcd.getHoursDouble());
                break;
            case 3:
                prcdHS1.addDay4(lcd.getHoursSimple());
                prcdHM1.addDay4(lcd.getHoursMedia());
                prcdHD1.addDay4(lcd.getHoursDouble());
                break;
            case 4:
                prcdHS1.addDay5(lcd.getHoursSimple());
                prcdHM1.addDay5(lcd.getHoursMedia());
                prcdHD1.addDay5(lcd.getHoursDouble());
                break;
            case 5:
                prcdHS1.addDay6(lcd.getHoursSimple());
                prcdHM1.addDay6(lcd.getHoursMedia());
                prcdHD1.addDay6(lcd.getHoursDouble());
                break;
            case 6:
                prcdHS1.addDay7(lcd.getHoursSimple());
                prcdHM1.addDay7(lcd.getHoursMedia());
                prcdHD1.addDay7(lcd.getHoursDouble());
                break;
            case 7:
                prcdHS2.addDay1(lcd.getHoursSimple());
                prcdHM2.addDay1(lcd.getHoursMedia());
                prcdHD2.addDay1(lcd.getHoursDouble());
                break;

            case 8:
                prcdHS2.addDay2(lcd.getHoursSimple());
                prcdHM2.addDay2(lcd.getHoursMedia());
                prcdHD2.addDay2(lcd.getHoursDouble());
                break;
            case 9:
                prcdHS2.addDay3(lcd.getHoursSimple());
                prcdHM2.addDay3(lcd.getHoursMedia());
                prcdHD2.addDay3(lcd.getHoursDouble());
                break;
            case 10:
                prcdHS2.addDay4(lcd.getHoursSimple());
                prcdHM2.addDay4(lcd.getHoursMedia());
                prcdHD2.addDay4(lcd.getHoursDouble());
                break;
            case 11:
                prcdHS2.addDay5(lcd.getHoursSimple());
                prcdHM2.addDay5(lcd.getHoursMedia());
                prcdHD2.addDay5(lcd.getHoursDouble());
                break;
            case 12:
                prcdHS2.addDay6(lcd.getHoursSimple());
                prcdHM2.addDay6(lcd.getHoursMedia());
                prcdHD2.addDay6(lcd.getHoursDouble());
                break;
            case 13:
                prcdHS2.addDay7(lcd.getHoursSimple());
                prcdHM2.addDay7(lcd.getHoursMedia());
                prcdHD2.addDay7(lcd.getHoursDouble());
                break;

            default:
                break;
        }
    }

    private void addCollaboratorDeduction(List<CalculatorPayRoll> calculatorPayRolls, PayRoll payRoll, Date start, Date end, User user) {

        List <PayRollCollaboratorDeductionDetail> deductionsDetail = new ArrayList<>();

        for (CalculatorPayRoll cpr : calculatorPayRolls) {
            PayRollCollaboratorDeductionDetail prcdd;

            if(cpr.getCcssDeduction()!=null){
                deductionsDetail.add(this.addDeductionTaxes(cpr, payRoll, start, end, user, StringHelper.CCSS));
            }

            deductionsDetail.add(addDeductionTaxes(cpr,payRoll,start,end,user,StringHelper.TAX1));
            deductionsDetail.add(addDeductionTaxes(cpr,payRoll,start,end,user,StringHelper.TAX2));
            deductionsDetail.add(addDeductionTaxes(cpr,payRoll,start,end,user,StringHelper.TAX3));

            if(cpr.getOthersDeduction()!=null){
                for (Deductions ds : cpr.getOthersDeduction()) {
                    PayRollCollaboratorDeductionDetail add = new PayRollCollaboratorDeductionDetail();
                    add.setPayRoll(payRoll);
                    add.setDescription(ds.getName());
                    add.setCollaborator(cpr.getCollaborator());
                    add.setUpdateDate(new Date(System.currentTimeMillis()));
                    add.setCreateDate(new Date(System.currentTimeMillis()));
                    add.setPorcent(ds.getPercent());
                    add.setIdUserRegister(user.getId().intValue());
                    add.setStartDate(start);
                    add.setEndDate(end);
                    add.setMount((cpr.getHsTotal() * ds.getPercent()) / CalculatorPayRoll.PORCENT);
                    deductionsDetail.add(add);
                }
            }
        }
        this.addPayRollCollaboratorDeductionDetails(deductionsDetail);




    }

    private PayRollCollaboratorDeductionDetail addDeductionTaxes(CalculatorPayRoll cpr,PayRoll payRoll,Date start, Date end, User user,String type) {
        PayRollCollaboratorDeductionDetail prcdd = new PayRollCollaboratorDeductionDetail();

        prcdd.setPayRoll(payRoll);

        prcdd.setCollaborator(cpr.getCollaborator());
        prcdd.setUpdateDate(new Date(System.currentTimeMillis()));
        prcdd.setCreateDate(new Date(System.currentTimeMillis()));
        prcdd.setIdUserRegister(user.getId().intValue());
        prcdd.setStartDate(start);
        prcdd.setEndDate(end);

        if(StringHelper.CCSS.equals(type)) {
            prcdd.setDescription(cpr.getCcssDeduction().getName());
            Double porcentage = cpr.getCcssDeduction().getPercent();
            prcdd.setPorcent(porcentage);
            prcdd.setMount(cpr.getCcss());
        }else if(StringHelper.TAX1.equals(type)) {
            prcdd.setDescription(cpr.getRenta1().getName());
            prcdd.setPorcent(cpr.getRenta1().getPercent());
            prcdd.setMount(cpr.getTax1());
        }else if(StringHelper.TAX2.equals(type)) {
            prcdd.setDescription(cpr.getRenta2().getName());
            prcdd.setPorcent(cpr.getRenta2().getPercent());
            prcdd.setMount(cpr.getTax2());
        }else if(StringHelper.TAX3.equals(type)) {
            prcdd.setDescription(cpr.getRenta3().getName());
            prcdd.setPorcent(cpr.getRenta3().getPercent());
            prcdd.setMount(cpr.getTax3());
        }
        return prcdd;
    }

    public int addPayRollCollaboratorDeductionDetails(List<PayRollCollaboratorDeductionDetail> prcdds) {
        log.debug("Inicio de PayRollCollaboratorDeductionDetailServiceImpl: addPayRollCollaboratorDeductionDetails");
        for( PayRollCollaboratorDeductionDetail c : prcdds){
            c.setCreateDate(new Date(DateUtil.getCurrentTime()));
            payRollCollaboratorDeductionDetailRepository.save(c);
        }
        log.debug("Inicio de PayRollCollaboratorDeductionDetailServiceImpl: addPayRollCollaboratorDeductionDetails");
        return prcdds.size();
    }

    private void addCollaboratorRefundDevolutionDetail(List<CalculatorPayRoll> calculatorPayRolls, PayRoll payRoll, Date start, Date end, User user) {
        List<PayRollCollaboratorRefundDevolutionDetail> adds = new ArrayList<>();

        for (CalculatorPayRoll cpr : calculatorPayRolls) {
            log.info("Agregando deducciones 10 ");
            for (RefundDevolution rd : cpr.getDevolutions()) {
                if(rd.getDeductionsRefundsDetails()!=null)
                    adds.add(this.addRDAtomic(cpr,rd,payRoll,start,end,user));
            }

            log.info("Agregando RefundDevolution 10 ");
            for (RefundDevolution rd : cpr.getRefunds()) {
                if(rd.getDeductionsRefundsDetails()!=null)
                    adds.add(this.addRDAtomic(cpr,rd,payRoll,start,end,user));
            }

        }

        this.addPayRollCollaboratorRefundDevolutionDetails(adds);
    }

    private PayRollCollaboratorRefundDevolutionDetail addRDAtomic(CalculatorPayRoll cpr ,RefundDevolution rd,PayRoll payRoll, Date start, Date end, User user){
        PayRollCollaboratorRefundDevolutionDetail add = new PayRollCollaboratorRefundDevolutionDetail();
        add.setPayRoll(payRoll);
        add.setCollaborator(cpr.getCollaborator());
        add.setIdUserRegister(user.getId().intValue());
        add.setDescription(rd.getDeductionsRefunds().getName());
        add.setMount(rd.getDeductionsRefundsDetails().getFee());
        add.setType(rd.getDeductionsRefunds().getType());
        add.setStartDate(start);
        add.setEndDate(end);
        add.setEndDate(new Date(System.currentTimeMillis()));
        add.setCreateDate(new Date(System.currentTimeMillis()));
        add.setUpdateDate(new Date(System.currentTimeMillis()));
        add.setIdDevolRefundDetail(rd.getDeductionsRefundsDetails().getId());
        add.setCoutaNumber(rd.getDeductionsRefundsDetails().getIndice());
        return add;

    }

    public int addPayRollCollaboratorRefundDevolutionDetails(List<PayRollCollaboratorRefundDevolutionDetail> adds) {
        List<Integer> rets = new ArrayList<>();
        log.debug("Iniciando PayRollCollaboratorRefundDevolutionDetailServiceImpl: addPayRollCollaboratorRefundDevolutionDetails");
        for(PayRollCollaboratorRefundDevolutionDetail c: adds){
            c.setCreateDate(new Date(DateUtil.getCurrentTime()));
            c = this.payRollCollaboratorRefundDevolutionDetailRepository.save(c);
            rets.add(c.getId());
        }
        log.debug("End PayRollCollaboratorRefundDevolutionDetailServiceImpl: addPayRollCollaboratorRefundDevolutionDetails");
        return rets.size();
    }

    public Result regeneratePayRoll(Integer oldPayRollId, Date startDate, Date endDate) {
        this.deletePlanilla(oldPayRollId);
        return this.generatePayRoll(startDate,endDate );
    }

    private void deletePlanilla(Integer id) {
        log.debug("INICIANDO deletePlanilla 1 ");

        Optional<PayRoll> o = this.payRollRepository.findById(id);
        if (o==null || o.get()==null) {
            return;
        }
        PayRoll pr = o.get();
//        ResultAction result;
        if (pr != null && pr.getStatus().equals(StringHelper.STATUS_DATA_GEN)) {
            try {
                this.payrollService.deletePlanilla(pr);



            } catch (Exception e) {

                e.printStackTrace();
            }

        } else {
//            result = new ResultAction(ResultAction.RESULT_CODE.ERROR, "No se permiten eliminar planillas en el estado : " + pr.getStatus());
            throw new RuntimeException("No se permiten eliminar planillas en el estado : " + pr.getStatus());
        }

    }
}
