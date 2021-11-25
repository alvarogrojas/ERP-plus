package com.ndl.erp.repository;


import com.ndl.erp.dto.CostCenterNoPODTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.jpa.repository.Query;
import com.ndl.erp.domain.CostCenter;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CentroCostosRepository extends PagingAndSortingRepository<CostCenter, Integer>{

//	CostCenter findById(Integer id);

    @Query(value = "select c from CostCenter c where c.state = ?1 or c.state=?2")
    List<CostCenter> getByStates(String state, String state2);

    CostCenter getById(Integer id);

    @Query(value = "select c from CostCenter c where c.state = ?1")
    List<CostCenter> getByState(String state);


    @Query(value = "SELECT " +
            "new " +
            "com.ndl.erp.dto.CostCenterNoPODTO(cc.id, cc.code, cc.type, cc.name,  cc.totalBudgeted, cc.totalBudgetedMaterials, " +
            "cc.client.clientId, cc.client.name, cnt.name, lcd.currency.id, lcd.currency.simbol, " +
            "(sum(lcd.hoursSimple) * max(lcd.costHour)) + (sum(lcd.hoursDouble) * max(lcd.costHour) * 2) + (sum(lcd.hoursMedia) * max(lcd.costHour) * 1.5)) " +
            " from CostCenter cc " +
            "INNER JOIN LaborCostDetail lcd ON cc.id = lcd.costCenter.id and lcd.costCenter.id not in " +
            "(select pod.costCenter.id from PurchaseOrderClientDetail pod GROUP BY pod.costCenter.id) " +
            "INNER JOIN Currency cur on lcd.currency.id = cur.id " +
            " INNER JOIN Client c ON cc.client.clientId = c.clientId " +
            " INNER JOIN ContactClient cnt ON cc.contact.id = cnt.id " +
            "WHERE  cc.type = 'Proyecto' and cc.inPurchaseOrder = 0 " +
            "GROUP BY cc.id, cur.id "
    )
    List<CostCenterNoPODTO> getProyectoWithNoPO();

    @Query(value = "SELECT " +
            "new " +
            "com.ndl.erp.dto.CostCenterNoPODTO(" +
            "cc.id, " +
            "cc.code, " +
            "cc.type, " +
            "cc.name,  " +
            "cc.totalBudgeted, " +
            "cc.totalBudgetedMaterials, " +
            "cc.client.clientId, " +
            "cc.client.name, " +
            "cnt.name, " +
            "cur.id, " +
            "cur.simbol, " +
            "sum(bp.total)) " +
            " from CostCenter cc " +
            "INNER JOIN BillPayDetail lcd ON cc.id = lcd.costCenter.id and lcd.costCenter.id not in " +
                "(select pod.costCenter.id from PurchaseOrderClientDetail pod GROUP BY pod.costCenter.id) " +
            "INNER JOIN BillPay bp on bp.id = lcd.billPay.id " +
            "INNER JOIN Currency cur on bp.currency.id = cur.id " +
            " INNER JOIN Client c ON cc.client.clientId = c.clientId " +
            " INNER JOIN ContactClient cnt ON cc.contact.id = cnt.id " +
            "WHERE  cc.type = 'Proyecto' and cc.inPurchaseOrder = 0 " +
            "GROUP BY cc.id, cur.id "
    )
    List<CostCenterNoPODTO> getProyectsCxP();

    @Query(value = "SELECT " +
            "new " +
            "com.ndl.erp.dto.CostCenterNoPODTO(" +
            "cc.id, " +
            "cc.code, " +
            "cc.type, " +
            "cc.name,  " +
            "cc.totalBudgeted, " +
            "cc.totalBudgetedMaterials, " +
            "cc.client.clientId, " +
            "cc.client.name, " +
            "cnt.name, " +
            "cur.id, " +
            "cur.simbol, " +
            "sum(bp.total)) " +
            " from CostCenter cc " +
            "INNER JOIN KilometerDetail lcd ON cc.id = lcd.costCenter.id and lcd.costCenter.id not in " +
            "(select pod.costCenter.id from PurchaseOrderClientDetail pod GROUP BY pod.costCenter.id) " +
            "INNER JOIN Kilometer bp on bp.id = lcd.kilometer.id " +
            "INNER JOIN Currency cur on bp.currency.id = cur.id " +
            " INNER JOIN Client c ON cc.client.clientId = c.clientId " +
            " INNER JOIN ContactClient cnt ON cc.contact.id = cnt.id " +
            "WHERE  cc.type = 'Proyecto' and cc.inPurchaseOrder = 0 " +
            "GROUP BY cc.id, cur.id, bp.id "
    )
    List<CostCenterNoPODTO> getProyectsKilometers();

    @Query(value = "SELECT " +
            "new " +
            "com.ndl.erp.dto.CostCenterNoPODTO(" +
            "cc.id, " +
            "cc.code, " +
            "cc.type, " +
            "cc.name,  " +
            "cc.totalBudgeted, " +
            "cc.totalBudgetedMaterials, " +
            "cc.client.clientId, " +
            "cc.client.name, " +
            "cnt.name, " +
            "cur.id, " +
            "cur.simbol, " +
            "sum(bp.total)) " +
            " from CostCenter cc " +
            "INNER JOIN RefundableDetail lcd ON cc.id = lcd.costCenter.id and lcd.costCenter.id not in " +
            "(select pod.costCenter.id from PurchaseOrderClientDetail pod GROUP BY pod.costCenter.id) " +
            "INNER JOIN Refundable bp on bp.id = lcd.refundable.id " +
            "INNER JOIN Currency cur on bp.currency.id = cur.id " +
            " INNER JOIN Client c ON cc.client.clientId = c.clientId " +
            " INNER JOIN ContactClient cnt ON cc.contact.id = cnt.id " +
            "WHERE  cc.type = 'Proyecto' and cc.inPurchaseOrder = 0 " +
            "GROUP BY cc.id, cur.id, bp.id "
    )
    List<CostCenterNoPODTO> getProyectsRefundables();




//    @Query(value = "SELECT new com.ndl.erp.dto.CostCenterNoPODTO(cc.id, cc.code, cc.type, cc.name,  cc.totalBudgeted, cc.totalBudgetedMaterials, " +
//            "cc.client.clientId, cc.client.name, null, lcd.currency.id, lcd.currency.simbol, " +
//        "(sum(lcd.hoursSimple) * max(lcd.costHour)) + (sum(lcd.hoursDouble) * max(lcd.costHour) * 2) + (sum(lcd.hoursMedia) * max(lcd.costHour) * 1.5)) " +
//        " from CostCenter cc " +
//        "INNER JOIN LaborCostDetail lcd ON cc.id = lcd.costsCenter.id and lcd.costsCenter.id not in (select pod.costCenter.id from PurchaseOrderClientDetail pod GROUP BY pod.costCenter.id) " +
//        "INNER JOIN Currency cur on lcd.currency.id = cur.id " +
//        " INNER JOIN Client c ON cc.client.clientId = c.clientId " +
//        "WHERE  cc.type = 'Proyecto' and cc.inPurchaseOrder = 0 " +
//        "GROUP BY cc.id, cur.id "
//)
//    List<CostCenterNoPODTO> getProyectoWithNoPOCxP(); // ADD BILL PAY
//
//    @Query(value = "SELECT new com.ndl.erp.dto.CostCenterNoPODTO(cc.id, cc.code, cc.type, cc.name,  cc.totalBudgeted, cc.totalBudgetedMaterials, " +
//            "cc.client.clientId, cc.client.name, null, lcd.currency.id, lcd.currency.simbol, " +
//        "(sum(lcd.hoursSimple) * max(lcd.costHour)) + (sum(lcd.hoursDouble) * max(lcd.costHour) * 2) + (sum(lcd.hoursMedia) * max(lcd.costHour) * 1.5)) " +
//        " from CostCenter cc " +
//        "INNER JOIN LaborCostDetail lcd ON cc.id = lcd.costsCenter.id and lcd.costsCenter.id not in (select pod.costCenter.id from PurchaseOrderClientDetail pod GROUP BY pod.costCenter.id) " +
//        "INNER JOIN Currency cur on lcd.currency.id = cur.id " +
//        " INNER JOIN Client c ON cc.client.clientId = c.clientId " +
//        "WHERE  cc.type = 'Proyecto' and cc.inPurchaseOrder = 0 " +
//        "GROUP BY cc.id, cur.id "
//)
//    List<CostCenterNoPODTO> getProyectoWithNoPOKilometers(); // KILOMETER
//
//
//    @Query(value = "SELECT new com.ndl.erp.dto.CostCenterNoPODTO(cc.id, cc.code, cc.type, cc.name,  cc.totalBudgeted, cc.totalBudgetedMaterials, " +
//            "cc.client.clientId, cc.client.name, null, lcd.currency.id, lcd.currency.simbol, " +
//        "(sum(lcd.hoursSimple) * max(lcd.costHour)) + (sum(lcd.hoursDouble) * max(lcd.costHour) * 2) + (sum(lcd.hoursMedia) * max(lcd.costHour) * 1.5)) " +
//        " from CostCenter cc " +
//        "INNER JOIN LaborCostDetail lcd ON cc.id = lcd.costsCenter.id and lcd.costsCenter.id not in (select pod.costCenter.id from PurchaseOrderClientDetail pod GROUP BY pod.costCenter.id) " +
//        "INNER JOIN Currency cur on lcd.currency.id = cur.id " +
//        " INNER JOIN Client c ON cc.client.clientId = c.clientId " +
//        "WHERE  cc.type = 'Proyecto' and cc.inPurchaseOrder = 0 " +
//        "GROUP BY cc.id, cur.id "
//)
//    List<CostCenterNoPODTO> getProyectoWithNoPORefundables(); // Refundables

	@Query(value = "select c from CostCenter c where c.name like %?1% or c.code like %?1% or c.type like %?1% or c.state like %?1% or c.client.name like %?1%")
    List<CostCenter> findUsingFilter(String filter);

	@Query(value = "select c from CostCenter c where c.name like %?1% or" +
            " c.code like %?1% or c.type like %?1% or c.state like %?1% " +
            "or c.client.name like %?1% " +
            "order by c.createdDate desc ")
    Page<CostCenter> findUsingFilterPageable(String filter,
                                             PageRequest pageable);

    @Query(value = "select count(c.id) from CostCenter c where c.name like %?1% or" +
            " c.code like %?1% or c.type like %?1% or c.state like %?1% " +
            "or c.client.name like %?1% " +
            "order by c.createdDate desc ")
    public Integer countAllByFilter(String filter);


    @Query("SELECT max(c.id) FROM CostCenter c where c.type=?1")
    Integer getMaxId(String type);


}
