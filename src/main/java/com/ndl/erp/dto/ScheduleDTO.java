package com.ndl.erp.dto;

import com.ndl.erp.domain.Bank;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.util.StringHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ScheduleDTO implements Serializable {

    private static final long serialVersionUID = 1777000000000000052L;

    public static final String [] MONTHS =  {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio", "Agosto","Setiembre","Octubre","Noviembre", "Diciembre"};


    private List<ScheduleMonthsDTO> months = new ArrayList<>();
    private List<ScheduleWeekDTO> weeks = new ArrayList<>();


    private List<ScheduleGeneralWeeksDTO> fixedCosts = new ArrayList();
    private List<ScheduleGeneralWeeksDTO> variosWeeks = new ArrayList();
    private List<ScheduleGeneralWeeksDTO> billPayWeeks = new ArrayList();
    private List<ScheduleGeneralWeeksDTO> popWeeks = new ArrayList();


    private List<BankDTO> banks;  //Bancos
    private List<ScheduleInvoiceDTO> invoices; //Facturacion
    private List<ScheduleProjectionsDTO> projections; //Proyecciones
    private List<ScheduleVariosDTO> varios;
    private List<ScheduleFixedCostDTO> fixed_costs;
    private List<ScheduleBillPayWeekDTO> billPays;
    private List<SchedulePurchaseOrderProviderWeekDTO> purchaseOrderProviders;

    private ScheduleGeneralWeeksDTO bank;
    private ScheduleGeneralWeeksDTO controlInvoice;
    private ScheduleGeneralWeeksDTO masterProjection;
    private ScheduleGeneralWeeksDTO subtotal1;
    private ScheduleGeneralWeeksDTO subtotal2;
    private ScheduleGeneralWeeksDTO subtotal3;
    private ScheduleGeneralWeeksDTO subtotal4;
    private ScheduleGeneralWeeksDTO purchaseOrderTitle;
    private ScheduleGeneralWeeksDTO billPaysTitle;


    private List<String> weekIndex;
    private Currency defaultCurrency;

    public ScheduleDTO() {
        weekIndex = new ArrayList<>();
        this.purchaseOrderTitle = new ScheduleGeneralWeeksDTO("Órdenes de Compra");
        this.billPaysTitle = new ScheduleGeneralWeeksDTO("Cuentas por pagar");

    }

    public void createBankScheduleWeek() {
        this.bank = new ScheduleGeneralWeeksDTO("Bancos");
        ScheduleGeneralWeekDTO c;
        for (ScheduleWeekDTO w: this.weeks) {
            c = new ScheduleGeneralWeekDTO(w.getYear(),w.getMonth(), w.getWeek(), w.isLastWeek(), w.getBank());
//            c = new ScheduleGeneralWeekDTO(w);
            this.bank.getWeeks().add(c);
        }

    }

    public void createControlInvoiceScheduleWeek() {
        this.controlInvoice = new ScheduleGeneralWeeksDTO("Control de Facturas");
        ScheduleGeneralWeekDTO c;
        for (ScheduleWeekDTO w: this.weeks) {
            c = new ScheduleGeneralWeekDTO(w.getYear(),w.getMonth(), w.getWeek(), w.isLastWeek(), w.getAmountInvoice());
//            c = new ScheduleGeneralWeekDTO(w);
            this.controlInvoice.getWeeks().add(c);
        }

    }

    public void createSubtotal1ScheduleWeek() {
        this.subtotal1 = new ScheduleGeneralWeeksDTO("Sub Total 1");
        ScheduleGeneralWeekDTO c;
        for (ScheduleWeekDTO w: this.weeks) {
            c = new ScheduleGeneralWeekDTO(w.getYear(),w.getMonth(), w.getWeek(), w.isLastWeek(), w.getSubTotal1());
//            c = new ScheduleGeneralWeekDTO(w);
            this.subtotal1.getWeeks().add(c);
        }

    }

    public void createSubtotal2ScheduleWeek() {
        this.subtotal2 = new ScheduleGeneralWeeksDTO("Sub Total 2");
        ScheduleGeneralWeekDTO c;
        for (ScheduleWeekDTO w: this.weeks) {
            c = new ScheduleGeneralWeekDTO(w.getYear(),w.getMonth(), w.getWeek(), w.isLastWeek(), w.getSubTotal2());
//            c = new ScheduleGeneralWeekDTO(w);
            this.subtotal2.getWeeks().add(c);
        }

    }

    public void createSubtotal3ScheduleWeek() {
        this.subtotal3 = new ScheduleGeneralWeeksDTO("Sub Total 3");
        ScheduleGeneralWeekDTO c;
        for (ScheduleWeekDTO w: this.weeks) {
            c = new ScheduleGeneralWeekDTO(w.getYear(),w.getMonth(), w.getWeek(), w.isLastWeek(), w.getSubTotal3());
//            c = new ScheduleGeneralWeekDTO(w);
            this.subtotal3.getWeeks().add(c);
        }

    }

    public void createSubtotal4ScheduleWeek() {
        this.subtotal4 = new ScheduleGeneralWeeksDTO("Sub Total 4");
        ScheduleGeneralWeekDTO c;
        for (ScheduleWeekDTO w: this.weeks) {
            c = new ScheduleGeneralWeekDTO(w.getYear(),w.getMonth(), w.getWeek(), w.isLastWeek(), w.getSubTotal4());
//            c = new ScheduleGeneralWeekDTO(w);
            this.subtotal4.getWeeks().add(c);
        }

    }

    public void createProjectionMasterScheduleWeek() {
        this.masterProjection = new ScheduleGeneralWeeksDTO("Proyección del Master");
        ScheduleGeneralWeekDTO c;
        for (ScheduleWeekDTO w: this.weeks) {
            c = new ScheduleGeneralWeekDTO(w.getYear(),w.getMonth(), w.getWeek(), w.isLastWeek(), w.getAmountProjection());
            this.masterProjection.getWeeks().add(c);
        }
    }

    public void creadteScheduleWeeks() {
        this.createBankScheduleWeek();
        this.createControlInvoiceScheduleWeek();
        this.createProjectionMasterScheduleWeek();
        this.createSubtotal1ScheduleWeek();
        this.createSubtotal2ScheduleWeek();
        this.createSubtotal3ScheduleWeek();
        this.createSubtotal4ScheduleWeek();
    }



    public void genScheduleWeeks(Integer mount) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.MONTH,mount);
        today.set(Calendar.DAY_OF_MONTH, 1);
        Integer month =today.get(Calendar.MONTH);
        String monthName = MONTHS[month];
        Integer year = today.get(Calendar.YEAR);
        int max_days = today.getActualMaximum(Calendar.DAY_OF_MONTH);
        for(int i = 1 ; i <= max_days; i+=7){
            if(i==1) {
                today.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            }else{
                today.set(year,month,i);
            }
            Integer nWeek=today.get(Calendar.WEEK_OF_YEAR);
            if(!this.exist(nWeek)){
                this.weekIndex.add(nWeek.toString());
                ScheduleWeekDTO w = new ScheduleWeekDTO(monthName,month,year,nWeek);
                today.add(Calendar.DAY_OF_MONTH,i);
                if(i + 7 >= max_days)
                    w.setLastWeek(true);
                this.weeks.add(w);
            }

        }

    }

    public void populateMonths(){
        for(ScheduleWeekDTO w: this.weeks){
            if(!existMonthPlus(w.getMonth(),w.getYear())) {
                this.months.add(new ScheduleMonthsDTO(w));
            }
        }
    }

    public List<java.sql.Date> minDateMax(){
        List<java.sql.Date>  ret = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        ScheduleMonthsDTO sc = this.months.get(0);
        cal.set(sc.getYear(),sc.getNumber(),1);
        ret.add(new java.sql.Date(cal.getTime().getTime()));

        sc = this.months.get(this.months.size()-1);
        cal.set(sc.getYear(),sc.getNumber(),1);
        ret.add(new java.sql.Date(cal.getTime().getTime()));

        return ret;
    }

    private boolean existMonthPlus(Integer mounth,Integer year) {
        for(ScheduleMonthsDTO m : this.months){
            if(m.getYear().equals(year) && m.getNumber().equals(mounth)){
                m.plusWeek();
                return true;
            }
        }
        return false;
    }

    private boolean exist(Integer nWeek) {
        for(ScheduleWeekDTO w : this.weeks){
            if(w.getWeek().equals(nWeek))
                return true;
        }
        return false;
    }


    public void populateScheduleInvoice(){
        for(ScheduleInvoiceDTO inv : this.invoices){
            for(ScheduleWeekDTO w: this.weeks){
                if(w.getWeek().equals(inv.getWeek()))
                    w.plussInvoice(inv.getTotal());
            }
        }
    }
    public void populateScheduleProjection() {
        for(ScheduleProjectionsDTO proj : this.projections){
            for(ScheduleWeekDTO w: this.weeks){
                if(proj.getWeek().equals(w.getWeek()))
                    w.plussProjection(proj.getAmount());
            }
        }
    }

    private ScheduleGeneralWeeksDTO cloneWeeks(String title){
        ScheduleGeneralWeeksDTO ret = new ScheduleGeneralWeeksDTO(title);
        List<ScheduleGeneralWeekDTO> weeksFC = new ArrayList<>();
        for(ScheduleWeekDTO w:this.weeks){
            ScheduleGeneralWeekDTO temp = new ScheduleGeneralWeekDTO(w);
            weeksFC.add(temp);
        }
        ret.setWeeks(weeksFC);
        return ret;
    };

    public void populateFixedCost() {
        for(ScheduleFixedCostDTO fc: this.fixed_costs){
            Integer contador=1;
            ScheduleGeneralWeeksDTO temp = cloneWeeks(fc.getName());
            this.fixedCosts.add(temp);
            for(ScheduleGeneralWeekDTO w: temp.getWeeks()){
                this.setAmount(fc,w,contador,2);
                contador++;
            }

        }

    }

    public void populateVarios() {
        for(ScheduleVariosDTO var: this.varios){
            Integer contador=1;
            ScheduleGeneralWeeksDTO temp = cloneWeeks(var.getName());
            this.variosWeeks.add(temp);
            for(ScheduleGeneralWeekDTO w: temp.getWeeks()){
                this.setAmount(var,w,contador,4);
                contador++;
            }

        }

    }


    public void populateBillPay(){
        for(ScheduleBillPayWeekDTO bp:this.billPays){
            ScheduleGeneralWeeksDTO temp = cloneWeeks(bp.getProvider());
            temp.putAmmount(bp.getTotal(),bp.getWeek(), bp.getYear());
            this.plusTotals(bp.getTotal(),bp.getWeek(),bp.getYear(),3);
            this.billPayWeeks.add(temp);
        }
    }

    public void populatePurchaseOrderProviders() {
        for(SchedulePurchaseOrderProviderWeekDTO pop: this.purchaseOrderProviders){
            ScheduleGeneralWeeksDTO temp = cloneWeeks(pop.getProvider());
            temp.putAmmount(pop.getTotal(),pop.getWeek(), pop.getYear());
            this.plusTotals(pop.getTotal(),pop.getWeek(),pop.getYear(),3);
            this.popWeeks.add(temp);
        }
    }

    private void setAmount(ScheduleGastos fc,ScheduleGeneralWeekDTO w,int contador, int subt){
        if(fc.getPeriodicity().equalsIgnoreCase(StringHelper.PERIODICITY.WEEKLY)){ //cada semanal
            w.setAmount(fc.getAmount());
            if(fc.getAmount() > 0 )
                this.plusTotals(fc.getAmount(),w.getWeek(),w.getYear(),subt);
        }else if(fc.getPeriodicity().equalsIgnoreCase(StringHelper.PERIODICITY.BIWEEKLY)){ //cada 2 semanas
            if(w.getWeek() % 2 == 0){
                w.setAmount(fc.getAmount());
                if(fc.getAmount() > 0 )
                    this.plusTotals(fc.getAmount(),w.getWeek(),w.getYear(),subt);
            }
        }else if(fc.getPeriodicity().equalsIgnoreCase(StringHelper.PERIODICITY.MONTHLY)){ //cada mes
            if(w.isLastWeek()){
                w.setAmount(fc.getAmount());
                if(fc.getAmount() > 0 )
                    this.plusTotals(fc.getAmount(),w.getWeek(),w.getYear(),subt);
            }
        }else if(fc.getPeriodicity().equalsIgnoreCase(StringHelper.PERIODICITY.QUARTERLY)){//cada 3 meses
            if(this.isQuarterky(w)  && w.isLastWeek()){
                w.setAmount(fc.getAmount());
                if(fc.getAmount() > 0 )
                    this.plusTotals(fc.getAmount(),w.getWeek(),w.getYear(),subt);
            }
        }else if(fc.getPeriodicity().equalsIgnoreCase(StringHelper.PERIODICITY.BIANNUAL)){//cada 6 menses
            if((w.getMonth().intValue()  == 5 || w.getMonth().intValue()  == 11 )&& w.isLastWeek()){
                w.setAmount(fc.getAmount());
                if(fc.getAmount() > 0 )
                    this.plusTotals(fc.getAmount(),w.getWeek(),w.getYear(),subt);
            }
        }else if(fc.getPeriodicity().equalsIgnoreCase(StringHelper.PERIODICITY.ANNUAL)){//una vez al anno
            if(w.getMonth().intValue()  == 11 && w.isLastWeek()){
                w.setAmount(fc.getAmount());
                if(fc.getAmount() > 0 )
                    this.plusTotals(fc.getAmount(),w.getWeek(),w.getYear(),subt);
            }
        }
    }

    private boolean isQuarterky(ScheduleGeneralWeekDTO w){
        return (w.getMonth().intValue()  == 2 || w.getMonth().intValue() == 5 || w.getMonth().intValue()  == 8 || w.getMonth().intValue()  == 11);
    }


    private void plusTotals(Double amount, Integer nWeek, Integer nYear,int subt ){
        for( ScheduleWeekDTO w : this.weeks){
            if(w.getYear().equals(nYear) && w.getWeek().equals(nWeek)){
                if(subt == 2)
                    w.plusSubTotal2(amount);
                else if(subt == 3)
                    w.plusSubTotal3(amount);
                else if(subt == 4)
                    w.plusSubTotal4(amount);
            }
        }
    }

    public void calcSubTotalsAndBank() {
        int index=1;
        Double bankAnt = this.banks!=null && !this.banks.isEmpty() ? this.banks.get(0).getAmount():0d;
        Double s1Ant = 0d, s2Ant = 0d, s3Ant =0d, s4Ant=0d;
        for(ScheduleWeekDTO w: this.weeks){
            if(index++==1)
                w.setBank(bankAnt);
            else
                w.calcBank(s1Ant,s2Ant,s3Ant,s4Ant);
           w.calcSubtotal1();
           w.calcSubtotal2();
           w.calcSubtotal3();
           w.calcSubtotal4();
           s1Ant = w.getSubTotal1();
           s2Ant = w.getSubTotal2();
           s3Ant = w.getSubTotal3();
           s4Ant = w.getSubTotal4();
        }
    }



    public void allBanks(List<Bank> list){
        List<BankDTO> ret = new ArrayList<>();
        for(Bank b : list){
            ret.add(new BankDTO(b));
        }
        this.banks =  ret;
    };

    public List< ScheduleWeekDTO> getWeeks() {
        return weeks;
    }

    public void setWeeks(List< ScheduleWeekDTO> weeks) {
        this.weeks = weeks;
    }

    public List <ScheduleMonthsDTO> getMonths() {
        return months;
    }

    public void setMonths(List<ScheduleMonthsDTO> months) {
        this.months = months;
    }

    public List<BankDTO> getBanks() {
        return banks;
    }

    public void setBanks(List<BankDTO> banks) {
        this.banks = banks;
    }

    public List<ScheduleInvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<ScheduleInvoiceDTO> invoices) {
        this.invoices = invoices;
    }

    public void setProjections(List<ScheduleProjectionsDTO> projections) {
        this.projections = projections;
    }

    public List<ScheduleProjectionsDTO> allProjections() {
        return projections;
    }

    public void setVarios(List<ScheduleVariosDTO> varios) {
        this.varios = varios;
    }

    public List<ScheduleVariosDTO> allVarios() {
        return varios;
    }

    public List<ScheduleGeneralWeeksDTO> getFixedCosts() {
        return fixedCosts;
    }

    public void setFixedCosts(List<ScheduleGeneralWeeksDTO> fixedCosts) {
        this.fixedCosts = fixedCosts;
    }

    public void setFixed_costs(List<ScheduleFixedCostDTO> fixed_costs) {
        this.fixed_costs = fixed_costs;
    }

    public List<ScheduleFixedCostDTO> allFixedCosts() {
        return fixed_costs;
    }

    public List<ScheduleGeneralWeeksDTO> getVariosWeeks() {
        return variosWeeks;
    }

    public void setVariosWeeks(List<ScheduleGeneralWeeksDTO> variosWeeks) {
        this.variosWeeks = variosWeeks;
    }


    public void setBillPays(List<ScheduleBillPayWeekDTO> billPays) {
        this.billPays = billPays;
    }

    public List<ScheduleBillPayWeekDTO> allBillPays() {
        return billPays;
    }

    public void setPurchaseOrderProviders(List<SchedulePurchaseOrderProviderWeekDTO> purchaseOrderProviders) {
        this.purchaseOrderProviders = purchaseOrderProviders;
    }

    public List<SchedulePurchaseOrderProviderWeekDTO> allPurchaseOrderProviders() {
        return purchaseOrderProviders;
    }

    public List<ScheduleGeneralWeeksDTO> getPopWeeks() {
        return popWeeks;
    }

    public void setPopWeeks(List<ScheduleGeneralWeeksDTO> popWeeks) {
        this.popWeeks = popWeeks;
    }

    public List<ScheduleGeneralWeeksDTO> getBillPayWeeks() {
        return billPayWeeks;
    }

    public void setBillPayWeeks(List<ScheduleGeneralWeeksDTO> billPayWeeks) {
        this.billPayWeeks = billPayWeeks;
    }

    /*




    //Sub total


    private List<PurchaseOrderClientDTO> pocs; //Ordenes de compras para clientes, cuentas por pagar.
    //Sub total;

    private List<PurchaseOrderProviderDTO> pops; //Ordenes de compras para proveedores, cuentas por pagar.
    //Sub total;
*/


    public static void main(String[] args) {
        for(int i=1; i< 25; i++){
            if(i%2 == 0)
                System.out.printf("Bi Semanal :%d\n" ,i);
            if(i%4 == 0)
                System.out.printf("Mensual :%d\n" , i);
            if(i%12 == 0)
                System.out.printf("Mensual :%d\n" , i);
            if(i%12 == 0)
                System.out.printf("Mensual :%d\n" , i);

        }
    }

    public List<String> getWeekIndex() {
        return weekIndex;
    }

    public void setWeekIndex(List<String> weekIndex) {
        this.weekIndex = weekIndex;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public ScheduleGeneralWeeksDTO getBank() {
        return bank;
    }

    public void setBank(ScheduleGeneralWeeksDTO bank) {
        this.bank = bank;
    }

    public ScheduleGeneralWeeksDTO getControlInvoice() {
        return controlInvoice;
    }

    public void setControlInvoice(ScheduleGeneralWeeksDTO controlInvoice) {
        this.controlInvoice = controlInvoice;
    }

    public ScheduleGeneralWeeksDTO getMasterProjection() {
        return masterProjection;
    }

    public void setMasterProjection(ScheduleGeneralWeeksDTO masterProjection) {
        this.masterProjection = masterProjection;
    }

    public ScheduleGeneralWeeksDTO getSubtotal1() {
        return subtotal1;
    }

    public void setSubtotal1(ScheduleGeneralWeeksDTO subtotal1) {
        this.subtotal1 = subtotal1;
    }

    public ScheduleGeneralWeeksDTO getSubtotal2() {
        return subtotal2;
    }

    public void setSubtotal2(ScheduleGeneralWeeksDTO subtotal2) {
        this.subtotal2 = subtotal2;
    }

    public ScheduleGeneralWeeksDTO getSubtotal3() {
        return subtotal3;
    }

    public void setSubtotal3(ScheduleGeneralWeeksDTO subtotal3) {
        this.subtotal3 = subtotal3;
    }

    public ScheduleGeneralWeeksDTO getSubtotal4() {
        return subtotal4;
    }

    public void setSubtotal4(ScheduleGeneralWeeksDTO subtotal4) {
        this.subtotal4 = subtotal4;
    }


    public ScheduleGeneralWeeksDTO getPurchaseOrderTitle() {
        return purchaseOrderTitle;
    }

    public void setPurchaseOrderTitle(ScheduleGeneralWeeksDTO purchaseOrderTitle) {
        this.purchaseOrderTitle = purchaseOrderTitle;
    }

    public ScheduleGeneralWeeksDTO getBillPaysTitle() {
        return billPaysTitle;
    }

    public void setBillPaysTitle(ScheduleGeneralWeeksDTO billPaysTitle) {
        this.billPaysTitle = billPaysTitle;
    }
}
