package com.ndl.erp.dto;

//import com.google.api.client.util.ArrayMap;
import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.util.StringHelper;


import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BalanceCostCenterDTO implements Serializable{

    private static final long serialVersionUID = 1777000000000000035L;
    private static final int RENTABILIDAD_PORCENT = 100;

    private CostsCenterDTO costCenter;
    private Currency currency;


    private Double materialsReal=0d;         // Monto real en materiales
    private Double materialBudgets=0d;       // Materiales presupuestados
    private Double materialAvailable=0d;     // Materiales disponibles

//    private Map<String,Double> months = new ArrayMap<String,Double>();        // Presupuesto por meses
    private Map<String,Double> months = new HashMap<String,Double>();        // Presupuesto por meses

    private Double laborCostsReal=0d;// Mano de obra real
    private Double laborCostBuggets=0d;        // Mano de obra presupuestada
    private Double laborCostAvailable=0d;      // Mano de Obra Disponible

    private Double balance=0d;                 //BAlance
    private Double profitability=0d;           //Rentabilidad



    public BalanceCostCenterDTO(BalanceLaborCostDetailDTO blcd, Currency curRow, CostCenter costsCenter, Currency system) {
        this.setCostCenter(new CostsCenterDTO(costsCenter));
        this.setCurrency(system);
        this.setLaborCostBuggets(this.getCostCenter().getTotalBudgeted());
        this.setMaterialBudgets(this.getCostCenter().getTotalBudgetedMaterials());

        Double cbh = blcd.getCostHour();
        Double hs = cbh * blcd.getHoursSimple();
        Double hm = cbh * blcd.getHoursMedia() *  1.5;
        Double hd = cbh * blcd.getHoursDouble() * 2;
        Double total = hs + hm + hd;
        total = this.changeCurrency(total,curRow);

        this.setLaborCostsReal(this.getLaborCostsReal()  + total);

        this.setLaborCostAvailable(this.getLaborCostBuggets() - this.getLaborCostsReal());

    }


    public BalanceCostCenterDTO(BalanceBillPayDTO bp, Currency curRow, CostCenter costsCenter,Currency system) {
        this.setCostCenter(new CostsCenterDTO(costsCenter));
        this.setCurrency(system);
        this.setLaborCostBuggets(this.getCostCenter().getTotalBudgeted());
        this.setMaterialBudgets(costsCenter.getTotalBudgetedMaterials());

        Double total = bp.getTotal();
        total = this.changeCurrency(total,curRow);

        this.setMaterialsReal(total + this.getMaterialsReal());

        this.setMtoMonth(bp.getBillDate(),total,bp.getIdCostCenter());

        this.setMaterialAvailable(this.getMaterialBudgets() - this.getMaterialsReal());

    }


    public BalanceCostCenterDTO(BalanceKmDTO kmd, Currency curRow, CostCenter costsCenter,Currency system) {
        this.setCostCenter(new CostsCenterDTO(costsCenter));
        this.setCurrency(system);
        this.setLaborCostBuggets(this.getCostCenter().getTotalBudgeted());
        this.setMaterialBudgets(costsCenter.getTotalBudgetedMaterials());

        Double total = kmd.getSubTotal();
        total = this.changeCurrency(total,curRow);

        this.setMaterialsReal(total + this.getMaterialsReal());

        this.setMtoMonth(kmd.getDateKm(),total,kmd.getIdCostCenter());

        this.setMaterialAvailable(this.getMaterialBudgets() - this.getMaterialsReal());


    }

    public BalanceCostCenterDTO(BalanceRefundableDTO rfd, Currency curRow, CostCenter costsCenter,Currency system) {
        this.setCostCenter(new CostsCenterDTO(costsCenter));
        this.setCurrency(system);
        this.setLaborCostBuggets(this.getCostCenter().getTotalBudgeted());
        this.setMaterialBudgets(costsCenter.getTotalBudgetedMaterials());

        Double total = rfd.getSubTotal();
        total = this.changeCurrency(total,curRow);

        this.setMaterialsReal(total + this.getMaterialsReal());

        this.setMtoMonth(rfd.getDateRfd(),total,rfd.getIdCostCenter());

        this.setMaterialAvailable(this.getMaterialBudgets() - this.getMaterialsReal());

    }

    private void setMtoMonth(Date date, Double total, Integer idcc){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int m = calendar.get(Calendar.MONTH) + 1;
        int y = calendar.get(Calendar.YEAR);

        String key = StringHelper.getKeyMonths(m,y);
        if(!this.months.isEmpty() && this.months.containsKey(key)){
            Double mto = this.months.get(key);
            mto = mto + total;
            this.months.put(key,mto);
        }else{
            this.months.put(key, total);
        }
    }


    private Double changeCurrency(Double total, Currency curr){
        Double ret= total;
        if(!curr.getDefault()){
           ret = (ret  * this.getCurrency().getValueBuy())  / curr.getValueBuy();
        }
        return ret;
    }

    /**
     * METODOS a la medida segun necesidad
     */


    public void modifyLaborCostMtos(BalanceLaborCostDetailDTO blcd,Currency curRow) {
        Double cbh=blcd.getCostHour();
        Double hs = cbh * blcd.getHoursSimple();
        Double hm = cbh * blcd.getHoursMedia() *  1.5;
        Double hd = cbh * blcd.getHoursDouble() * 2;
        Double total = hs + hm + hd;

        total = this.changeCurrency(total,curRow);
        this.setLaborCostsReal(this.getLaborCostsReal()  + total);
        this.setLaborCostAvailable(this.getLaborCostBuggets() - this.getLaborCostsReal());

    }


    public void modifyBillPaysMtos(BalanceBillPayDTO bp,Currency currRow){
        Double total = bp.getTotal();
        total = this.changeCurrency(total,currRow);
        this.modifyMts(total,bp.getBillDate(),bp.getIdCostCenter());
    }

    public void modifyKmMtos(BalanceKmDTO kmd,Currency currRow){
        Double total = kmd.getSubTotal();
        total = this.changeCurrency(total,currRow);
        this.modifyMts(total,kmd.getDateBillPay(),kmd.getIdCostCenter());
    }

    public void modifyRefundableMtos(BalanceRefundableDTO rfd,Currency currRow){
        Double total = rfd.getSubTotal();
        total = this.changeCurrency(total,currRow);
        this.modifyMts(total,rfd.getDateBillPay(),rfd.getIdCostCenter());
    }


    private void modifyMts(Double total, Date fecha, Integer idCC){
        this.setMtoMonth(fecha,total,idCC);
        this.setMaterialsReal(total + this.getMaterialsReal());
        this.setMaterialAvailable(this.getMaterialBudgets() - this.getMaterialsReal());
    }

    private void calcRentabilidad(){
        Double balance = this.getBalance();
        Double mtoMaterialesPresupuestado = this.getMaterialBudgets();
        Double mtoManoObraPresupustado = this.getLaborCostBuggets();
        Double divisor = mtoManoObraPresupustado + mtoMaterialesPresupuestado;
        if (divisor != 0) {
            Double rentabilidad = (balance / (divisor)) * RENTABILIDAD_PORCENT;
            this.setProfitability(rentabilidad);
        } else {
            this.setProfitability(0d);
        }
    }

    public void calcBalanceAndRentabilidad(){
        this.setBalance(this.getLaborCostAvailable() + this.getMaterialAvailable());
        this.calcRentabilidad();
    }


    /**
     * Metodos auto generados
     * @return
     */

    public CostsCenterDTO getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostsCenterDTO costCenter) {
        this.costCenter = costCenter;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getMaterialsReal() {
        return materialsReal;
    }

    public void setMaterialsReal(Double materialsReal) {
        this.materialsReal = materialsReal;
    }

    public Double getMaterialBudgets() {
        return materialBudgets;
    }

    public void setMaterialBudgets(Double materialBudgets) {
        this.materialBudgets = materialBudgets;
    }

    public Double getMaterialAvailable() {
        return materialAvailable;
    }

    public void setMaterialAvailable(Double materialAvailable) {
        this.materialAvailable = materialAvailable;
    }

    public Map<String, Double> getMonths() {
        return months;
    }

    public void setMonths(Map<String, Double> months) {
        this.months = months;
    }

    public Double getLaborCostsReal() {
        return laborCostsReal;
    }

    public void setLaborCostsReal(Double laborCosts) {
        this.laborCostsReal = laborCosts;
    }


    public Double getLaborCostBuggets() {
        return laborCostBuggets;
    }

    public void setLaborCostBuggets(Double laborCostBuggets) {
        this.laborCostBuggets = laborCostBuggets;
    }

    public Double getLaborCostAvailable() {
        return laborCostAvailable;
    }

    public void setLaborCostAvailable(Double laborCostAvailable) {
        this.laborCostAvailable = laborCostAvailable;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getProfitability() {
        return profitability;
    }

    public void setProfitability(Double profitability) {
        if (profitability==null) {
            profitability = 0D;
        }
        this.profitability = profitability;
    }
}
