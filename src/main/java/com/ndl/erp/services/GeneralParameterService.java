package com.ndl.erp.services;

import com.ndl.erp.domain.*;
import com.ndl.erp.exceptions.NotFoundException;
import com.ndl.erp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Component
public class GeneralParameterService {

    public static final String TYPE_INTEGER = "INTEGER";

    public static final String POP_KEY = "POP_PRO";

    public static final String POC_KEY= "POC_CLI";
    public static final String INVOICE_NUM= "INVOICE_NUM";

    //Parametros para consecutivos cotizaciones
    public static final String COTIZACION_PARAM_MES_CONSECUTIVO   = "COT_CONS_MES";
    public static final String COTIZACION_PARAM_KEY = "COT_NUMBER";
    public static final String COTIZACION_PARAM_PREFIX = "COT_PREFIX";

    //Parametros para consecutivos requisiciones
    public static final String REQUISICION_PARAM_MES_CONSECUTIVO   = "REQ_CONS_MES";
    public static final String REQUISICION_PARAM_KEY = "REQ_NUMBER";
    public static final String REQUISICION_PARAM_PREFIX = "REQ_PREFIX";

    //Parametros para consecutivos nota de credito
    public static final String NOTA_CREDITO_PARAM_MES_CONSECUTIVO   = "NCR_CONS_MES";
    public static final String NOTA_CREDITO_PARAM_KEY = "NCR_NUMBER";
    public static final String NOTA_CREDITO_PARAM_PREFIX = "NCR_PREFIX";

    //Parametros para consecutivos devolucion
    public static final String DEVOLUCION_PARAM_MES_CONSECUTIVO   = "DEV_CONS_MES";
    public static final String DEVOLUCION_PARAM_KEY = "DEV_NUMBER";
    public static final String DEVOLUCION_PARAM_PREFIX = "DEV_PREFIX";

    //Parametros para consecutivos traslados entre bodegas
    public static final String TRASLADO_PARAM_MES_CONSECUTIVO   = "TRSL_CSC_MES";
    public static final String TRASLADO_PARAM_KEY = "TRSL_NUMBER";
    public static final String TRASLADO_PARAM_PREFIX = "TRSL_PREFIX";


    //parametros info empresa
    public static final String INFO_EMPRESA_PARAM_CODE = "INGP_INF";
    public static final String INFO_EMPRESA_PARAM_NAME = "NAME";


    //Parametros para consecutivos nota de credito por bill pay
    public static final String BILL_PAY_NC_PARAM_MES_CONSECUTIVO   = "BPNC_CON_MES";
    public static final String BILL_PAY_NC_PARAM_KEY = "BPNC_NUMBER";
    public static final String BILL_PAY_NC_PARAM_PREFIX = "BPNC_PREFIX";


    @Autowired
    private GeneralParameterRepository generalParameterRepository;

    @Autowired
    private BillPayNotaCreditoRepository billPayNotaCreditoRepository;

    @Autowired
    private PurchaseOrderClientRepository purchaseOrderClientRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceNotaCreditoRepository invoiceNotaCreditoRepository;

    @Autowired
    private PurchaseOrderProviderRepository purchaseOrderProviderRepository;

    public synchronized GeneralParameter getByCode(String code) {
        GeneralParameter gp = generalParameterRepository.findByCode(code);
        return gp;
    }

    public synchronized List<GeneralParameter> getByCodes(String code) {
        return  generalParameterRepository.findByCodes(code);
    }

    public synchronized GeneralParameter get(Integer id) {
        GeneralParameter gp = generalParameterRepository.findById(id);
        return gp;
    }


    public synchronized Integer getNextDocumentNumber(String code) {
        GeneralParameter gp = generalParameterRepository.findByCode(code);

        Integer ret = 0;
        if (TYPE_INTEGER.equalsIgnoreCase(gp.getType())) {
            ret = gp.getIntVal() + 1;
            gp.setIntVal(ret);
            this.generalParameterRepository.save(gp);
        }
        return ret;
    }

    @Transactional
    public synchronized void generateNextPOOrderNumber(PurchaseOrderProvider pop) {
        GeneralParameter gp = this.generalParameterRepository.findByCode("POP_RPO_MES");
        //Integer mesActualGP = new Integer( this.generalParameterRepository.getValByCode("POP_RPO_MES"));
        Integer mesActualGP = new Integer(gp.getVal());
        Integer orderNumber = 1;
        Calendar calc = Calendar.getInstance();
        calc.setTime(pop.getDate());
        Integer mounth  = calc.get(Calendar.MONTH) + 1;
        if ( !mesActualGP.equals(mounth)) {
            gp.setVal(mounth.toString());
            this.generalParameterRepository.save(gp);
            GeneralParameter gp1 = this.generalParameterRepository.findByCode(POP_KEY);
            gp1.setIntVal(1);
            orderNumber = 1;
            this.generalParameterRepository.save(gp1);

        }else{
            orderNumber = this.getNextDocumentNumber(POP_KEY);
        }
        pop.setOrderNumber(pop.getOrderNumber() + this.getNum3Chars(orderNumber));
    }

    //Consecutivo cotizacion
    @Transactional
    public synchronized void generateNextCotizacionNumber(Cotizacion cotizacion) {
        GeneralParameter gp = this.generalParameterRepository.findByCode(COTIZACION_PARAM_MES_CONSECUTIVO );
        GeneralParameter gpPrefix = this.generalParameterRepository.findByCode(COTIZACION_PARAM_PREFIX);
        Integer mesActualGP = new Integer(gp.getVal());
        Integer orderNumber = 1;
        Calendar calc = Calendar.getInstance();
        calc.setTime(cotizacion.getFechaIngreso());
        Integer mounth  = calc.get(Calendar.MONTH) + 1;
        if ( !mesActualGP.equals(mounth)) {
            gp.setVal(mounth.toString());
            this.generalParameterRepository.save(gp);
            GeneralParameter gp1 = this.generalParameterRepository.findByCode(COTIZACION_PARAM_KEY);
            gp1.setIntVal(1);
            orderNumber = 1;
            this.generalParameterRepository.save(gp1);

        }else{
            orderNumber = this.getNextDocumentNumber(COTIZACION_PARAM_KEY);
        }
        cotizacion.setCotizacionNumber(this.getYear2Digit() + this.getMonth());
        cotizacion.setCotizacionNumber(gpPrefix.getVal()+ cotizacion.getCotizacionNumber() + this.getNum4Chars(orderNumber));
    }


    //Consecutivo requisicion
    @Transactional
    public synchronized void generateNextRequisicionNumber(RequisicionBodega requisicion) {
        GeneralParameter gp = this.generalParameterRepository.findByCode(REQUISICION_PARAM_MES_CONSECUTIVO);
        GeneralParameter gpPrefix = this.generalParameterRepository.findByCode(REQUISICION_PARAM_PREFIX);
        Integer mesActualGP = new Integer(gp.getVal());
        Integer orderNumber = 1;
        Calendar calc = Calendar.getInstance();
        calc.setTime(requisicion.getFechaSolicitada());
        Integer mounth  = calc.get(Calendar.MONTH) + 1;
        if ( !mesActualGP.equals(mounth)) {
            gp.setVal(mounth.toString());
            this.generalParameterRepository.save(gp);
            GeneralParameter gp1 = this.generalParameterRepository.findByCode(REQUISICION_PARAM_KEY);
            gp1.setIntVal(1);
            orderNumber = 1;
            this.generalParameterRepository.save(gp1);

        }else{
            orderNumber = this.getNextDocumentNumber(REQUISICION_PARAM_KEY);
        }
        requisicion.setConsecutivo(this.getYear() + this.getMonth());
        requisicion.setConsecutivo(gpPrefix.getVal()+ requisicion.getConsecutivo() + this.getNum4Chars(orderNumber));
    }

    //Consecutivo nota de credito por invoice
    @Transactional
    public synchronized Integer generateNextNotaCreditoNumber() {
        /*GeneralParameter gp = this.generalParameterRepository.findByCode(NOTA_CREDITO_PARAM_MES_CONSECUTIVO);
        GeneralParameter gpPrefix = this.generalParameterRepository.findByCode(NOTA_CREDITO_PARAM_PREFIX);
        Integer mesActualGP = new Integer(gp.getVal());
        Integer orderNumber = 1;
        Calendar calc = Calendar.getInstance();
        calc.setTime(invoiceNotaCredito.getDate());
        Integer mounth  = calc.get(Calendar.MONTH) + 1;
        if ( !mesActualGP.equals(mounth)) {
            gp.setVal(mounth.toString());
            this.generalParameterRepository.save(gp);
            GeneralParameter gp1 = this.generalParameterRepository.findByCode(NOTA_CREDITO_PARAM_KEY);
            gp1.setIntVal(1);
            orderNumber = 1;
            this.generalParameterRepository.save(gp1);

        }else{
            orderNumber = this.getNextDocumentNumber(NOTA_CREDITO_PARAM_KEY);
        }
        invoiceNotaCredito.setConsecutivo(this.getYear() + this.getMonth());
        invoiceNotaCredito.setConsecutivo(gpPrefix.getVal()+ invoiceNotaCredito.getConsecutivo() + this.getNum4Chars(orderNumber));
        */

        GeneralParameter gp = this.generalParameterRepository.findByCode(this.NOTA_CREDITO_PARAM_KEY);
        Integer ret = 0;
        if (TYPE_INTEGER.equalsIgnoreCase(gp.getType())) {
            ret = gp.getIntVal() + 1;

            Integer maxConsecutive = this.invoiceNotaCreditoRepository.getMaxConsecutive();
            if (maxConsecutive > gp.getIntVal()) {
                ret = maxConsecutive + 1;
            }

            gp.setIntVal(ret);
            this.generalParameterRepository.save(gp);
        }
        return ret;
    }


    //Consecutivo nota de credito por bill pay
    @Transactional
    public synchronized Integer generateNextBillPayNotaCreditoNumber() {

        GeneralParameter gp = this.generalParameterRepository.findByCode(this.BILL_PAY_NC_PARAM_KEY);
        Integer ret = 0;
        if (TYPE_INTEGER.equalsIgnoreCase(gp.getType())) {
            ret = gp.getIntVal() + 1;

            Integer maxConsecutive = this.billPayNotaCreditoRepository.getMaxConsecutive();

            if (maxConsecutive!=null && maxConsecutive > gp.getIntVal()) {
                ret = maxConsecutive + 1;
            } else if (maxConsecutive==null) {
                ret = 1;
            }

            gp.setIntVal(ret);
            this.generalParameterRepository.save(gp);
        }
        return ret;
    }


    //Consecutivo de devolucion
    @Transactional
    public synchronized void generateNextDevolucionNumber(Devolucion devolucion) {
        GeneralParameter gp = this.generalParameterRepository.findByCode(DEVOLUCION_PARAM_MES_CONSECUTIVO);
        GeneralParameter gpPrefix = this.generalParameterRepository.findByCode(DEVOLUCION_PARAM_PREFIX);
        Integer mesActualGP = new Integer(gp.getVal());
        Integer orderNumber = 1;
        Calendar calc = Calendar.getInstance();
        calc.setTime(devolucion.getFechaDevolucion());
        Integer mounth  = calc.get(Calendar.MONTH) + 1;
        if ( !mesActualGP.equals(mounth)) {
            gp.setVal(mounth.toString());
            this.generalParameterRepository.save(gp);
            GeneralParameter gp1 = this.generalParameterRepository.findByCode(DEVOLUCION_PARAM_KEY);
            gp1.setIntVal(1);
            orderNumber = 1;
            this.generalParameterRepository.save(gp1);

        }else{
            orderNumber = this.getNextDocumentNumber(DEVOLUCION_PARAM_KEY);
        }
        devolucion.setConsecutivo(this.getYear() + this.getMonth());
        devolucion.setConsecutivo(gpPrefix.getVal()+ devolucion.getConsecutivo() + this.getNum4Chars(orderNumber));
    }


    //Consecutivo traslado
    @Transactional
    public synchronized void generateNextTrasladoNumber(Traslado traslado) {
        GeneralParameter gp = this.generalParameterRepository.findByCode(TRASLADO_PARAM_MES_CONSECUTIVO);
        GeneralParameter gpPrefix = this.generalParameterRepository.findByCode(TRASLADO_PARAM_PREFIX);
        Integer mesActualGP = new Integer(gp.getVal());
        Integer docNumber = 1;
        Calendar calc = Calendar.getInstance();
        calc.setTime(traslado.getFechaTraslado());
        Integer month  = calc.get(Calendar.MONTH) + 1;
        if ( !mesActualGP.equals(month)) {
            gp.setVal(month.toString());
            this.generalParameterRepository.save(gp);
            GeneralParameter gp1 = this.generalParameterRepository.findByCode(TRASLADO_PARAM_KEY);
            gp1.setIntVal(1);
            docNumber = 1;
            this.generalParameterRepository.save(gp1);

        }else{
            docNumber = this.getNextDocumentNumber(TRASLADO_PARAM_KEY);
        }
        traslado.setConsecutivo(this.getYear() + this.getMonth());
        traslado.setConsecutivo(gpPrefix.getVal()+ traslado.getConsecutivo() + this.getNum4Chars(docNumber));
    }

    @Transactional
    public synchronized Integer getNextConsecutivePurchaseOrderClient() {
        GeneralParameter gp = this.generalParameterRepository.findByCode(this.POC_KEY);
        Integer ret = 0;
        if (TYPE_INTEGER.equalsIgnoreCase(gp.getType())) {
            ret = gp.getIntVal() + 1;
//            System.out.println("--> getNextConsecutivePurchaseOrderClient " + gp.getIntVal());
            Integer maxConsecutive = this.purchaseOrderClientRepository.getMaxConsecutive();
            if (maxConsecutive > gp.getIntVal()) {
                ret = maxConsecutive + 1;
//                System.out.println("--> getNextConsecutivePurchaseOrderClient usa max + 1" );
            }

            gp.setIntVal(ret);
            this.generalParameterRepository.save(gp);
        }
        return ret;
    }

    @Transactional
    public synchronized Integer getNextInvoiceNumber() {
        GeneralParameter gp = this.generalParameterRepository.findByCode(this.INVOICE_NUM);
        Integer ret = 0;
        if (TYPE_INTEGER.equalsIgnoreCase(gp.getType())) {
            ret = gp.getIntVal() + 1;

            Integer maxConsecutive = this.invoiceRepository.getMaxConsecutive();
            if (maxConsecutive > gp.getIntVal()) {
                ret = maxConsecutive + 1;
//                System.out.println("-->    getNextInvoiceNumber usa max + 1" );
            }
            gp.setIntVal(ret);
            this.generalParameterRepository.save(gp);
        }
        return ret;
    }



    public String getNum3Chars(Integer num){
        return num <= 9 ? "00" + num : num > 9 && num < 100 ? "0" + num : num.toString();
    }

    public String getNum4Chars(Integer num){
        return num <= 9 ? "000" + num : num > 9 && num < 100 ? "00" + num :  num >=100  && num < 1000 ? "0" + num : num.toString();
    }

    public String getYear() {
        Calendar calc = Calendar.getInstance();
        return Integer.toString(calc.get(Calendar.YEAR));
    }

    public String getYear2Digit(){
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String formattedYear = df.format(Calendar.getInstance().getTime());
        return formattedYear;
    }

    public String getMonth() {
        Calendar calc = Calendar.getInstance();
        Integer m = calc.get(Calendar.MONTH) + 1;
        return m<10?"0" + m :Integer.toString(m);
    }

    public GeneralParameter getGeneralParameterByNameAndCode(String code, String name) throws Exception{

        GeneralParameter g = this.generalParameterRepository.findByCodeAndName(code, name);

        if (g == null) {
            throw new NotFoundException("El parametro con el codigo/nombre = " + code + " / " + name + " no fue encontrado");
        }
        return g;
    }


}
