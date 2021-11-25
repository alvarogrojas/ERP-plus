package com.ndl.erp.services;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.GeneralParameter;
import com.ndl.erp.domain.User;
import com.ndl.erp.dto.CentroCostoDTO;
import com.ndl.erp.dto.CentroCostosDTO;
//import com.ndl.erp.dto.ClientDTO;
import com.ndl.erp.dto.CostCenterListNoPODTO;
import com.ndl.erp.dto.CostCenterNoPODTO;
import com.ndl.erp.exceptions.NotFoundException;
import com.ndl.erp.repository.*;
import com.ndl.erp.util.DateUtil;
import com.ndl.erp.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//import java.util.ArrayList;
import java.util.*;

import static com.ndl.erp.constants.CostCenterConstants.PARAMETER_DEFAULT_COST_CENTER_SELLING;
import static com.ndl.erp.constants.ParseCargaArchivoConstants.PARAMETER_IMPORT_BODEGA;

@Component
@Transactional
public class CentroCostosService {

    @Autowired
    private CodeGeneratorService codeGeneratorService;

    @Autowired
    private CentroCostosRepository centroCostosRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ContactClientRepository contactClientRepository;

    @Autowired
    private CostCenterStateRepository costCenterStateRepository;

    @Autowired
    private CostCenterTypeRepository costCenterTypeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GeneralParameterService generalParameterService;

    public CentroCostoDTO getCostCenter(Integer id) {
        CentroCostoDTO d = this.getCostCenter();
        Optional<CostCenter> c = centroCostosRepository.findById(id);
        if (c==null) {
            return null;
        }
        d.setCurrent(c.get());
        if (d.getCurrent().getClient()!=null)
            d.setContactClients(this.contactClientRepository.findByClient(d.getCurrent().
                    getClient().getClientId()));

        return d;
    }

    public CentroCostosDTO getCentroCostos(String filter) {

        CentroCostosDTO d = new CentroCostosDTO();

         d.setCentroCostos(this.centroCostosRepository.findUsingFilter(filter));
         d.getCentroCostos().add(createDefaultCC());
        return d;

    }

    private CostCenter createDefaultCC() {
        CostCenter costCenter = new CostCenter();
        costCenter.setCode("        ");
        costCenter.setName("No Centros Costos Seleccionado");
        costCenter.setId(0);
        costCenter.setDescription("");
        return costCenter;
    }

    public CentroCostosDTO getCentroCostos(String filter, Integer pageNumber,
                                           Integer pageSize,
                                           String sortDirection,
                                           String sortField) {

        CentroCostosDTO d = new CentroCostosDTO();

//        List<CostCenterNoPODTO> l = this.centroCostosRepository.getProyectoWithNoPO();

        d.setCostCentersPage(this.centroCostosRepository.findUsingFilterPageable(filter, createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.centroCostosRepository.countAllByFilter(filter));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }
        return d;

    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")?Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public CentroCostoDTO getCostCenter() {
        List<String> tipos = this.costCenterTypeRepository.findAllType();

        List<Client> clients = clientRepository.findClientsActive();

        List<String> estados = this.costCenterStateRepository.findAllStates();

        ArrayList purchaseOrderOptions = new ArrayList();
        purchaseOrderOptions.add("NO");
        purchaseOrderOptions.add("SI");


        CentroCostoDTO d = new CentroCostoDTO();
        d.setClientes(clients);
        d.setEstados(estados);
        d.setTypes(tipos);
        d.setPurchaseOrderOptions(purchaseOrderOptions);
        return d;
    }

    public CostCenter save(CostCenter costsCenter) {

        if(costsCenter.getTotalBudgeted()==null)
            costsCenter.setTotalBudgeted(0d);
        if(costsCenter.getTotalBudgetedMaterials()==null)
            costsCenter.setTotalBudgetedMaterials(0d);

        if(
                costsCenter.getType().equals(StringHelper.DEFAULT_COSTS_CENTER_TYPE)
                && (costsCenter.getId()==null || costsCenter.getId()==0))
        {
            costsCenter.setCode(codeGeneratorService.generateCode(costsCenter.getType()));
        }
        costsCenter.setCreatedDate(DateUtil.getCurrentCalendar());
        costsCenter.setLastUpdatedDate(DateUtil.getCurrentCalendar());
        User u = userService.getCurrentLoggedUser();
        costsCenter.setLastUpdatedById(u.getId().intValue());
        costsCenter.setCreatedById(u.getId().intValue());
        return this.centroCostosRepository.save(costsCenter);
    }

    public CostCenterListNoPODTO getProjectsNoOC() {
        CostCenterListNoPODTO r = new CostCenterListNoPODTO();
        Set<CostCenterNoPODTO> result = new LinkedHashSet<>();

        List<CostCenterNoPODTO> result1 =this.centroCostosRepository.getProyectoWithNoPO();

        List<CostCenterNoPODTO> result2 = this.centroCostosRepository.getProyectsCxP();
        List<CostCenterNoPODTO> result3 = this.centroCostosRepository.getProyectsKilometers();
        List<CostCenterNoPODTO> result4 = this.centroCostosRepository.getProyectsRefundables();
//        List<ProyectsDTO> result4 = this.costsCenterRepository.getProyectsRefundables();
//
////        Set<ProyectsDTO> r = new LinkedHashSet<>();
//        for (ProyectsDTO c: result1) {
//            addData(c, result);
//
//        }
//        for (ProyectsDTO c: result2) {
//            addData(c, result);
//
//        }
//        for (ProyectsDTO c: result3) {
//            addData(c, result);
//
//        }
//        for (ProyectsDTO c: result4) {
//            addData(c, result);
//
//        }
        for (CostCenterNoPODTO c: result1) {
            addData(c, result);

        }
        for (CostCenterNoPODTO c: result2) {
            addData(c, result);

        }
        for (CostCenterNoPODTO c: result3) {
            addData(c, result);

        }
        for (CostCenterNoPODTO c: result4) {
            addData(c, result);

        }

        r.setList(result);

        return r;
    }

    private void addData(CostCenterNoPODTO c, Set<CostCenterNoPODTO> r) {
        if (!r.contains(c)) {
            r.add(c);
        } else {
            //sumAmountToProject();
            for (CostCenterNoPODTO c1: r) {
                if (c1.equals(c) ) {
                    c1.setTotal(c1.getTotal() + c.getTotal());
                }
            }
        }
    }


    public CostCenter getDefaultCostCenterForSelling() {

        GeneralParameter parameterBodega = generalParameterService.getByCode(PARAMETER_DEFAULT_COST_CENTER_SELLING);
        if (parameterBodega==null) {
            throw new NotFoundException("No hay un Centro de Costos creador para realizar las ventas. " +
                    "El administrador  del sistema debe configurar el ERP, agregando un centro de costos, " +
                    "de lo contrario el sistema no podrá realizar ventas");
        }
        CostCenter defaultCc = this.centroCostosRepository.getById(parameterBodega.getIntVal());
        return defaultCc;
    }
}
