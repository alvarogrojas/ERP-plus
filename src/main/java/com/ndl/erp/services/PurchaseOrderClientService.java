package com.ndl.erp.services;

import com.ndl.erp.domain.*;
import com.ndl.erp.dto.*;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.exceptions.NotFoundException;
import com.ndl.erp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ndl.erp.constants.CostCenterConstants.PARAMETER_DEFAULT_COST_CENTER_TRASLADO;
import static com.ndl.erp.constants.DescuentosConstants.DESCUENTO_TIPO_GLOBAL;
import static com.ndl.erp.constants.InvoiceConstants.INVOICE_STATUS_EDICION;
import static com.ndl.erp.constants.PurchaseOrderClientConstants.PURCHASE_ORDER_CLIENT_STATUS_EDICION;
import static com.ndl.erp.constants.PurchaseOrderClientConstants.TIPO_DETALLE_PRODUCTO_PURCHASE_ORDER_CLIENT;
import static com.ndl.erp.util.StringHelper.IMP_VENTA_DEFAULT;

@Component
@Transactional
public class PurchaseOrderClientService {

    @Autowired
    private PurchaseOrderClientRepository purchaseOrderClientRepository;

    @Autowired
    private CentroCostosRepository centroCostosRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TaxesIvaRepository taxesIvaRepository;

    @Autowired
    GeneralParameterService generalParameterService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private PurchaseOrderClientDetailRepository purchaseOrderClientDetailRepository;

    @Autowired
    private InventarioItemRepository inventoryItemsRepository;

    @Autowired
    private ProductCabysRepository productCabysRepository;

    @Autowired
    private ServiceCabysRepository serviceCabysRepository;

    @Autowired
    private  ExhangeRateRepository exhangeRateRepository;

    @Autowired
    private CentroCostosService centroCostosService;

    @Autowired
    private DescuentosRepository descuentosRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private GeneralParameterRepository generalParameterRepository;

    @Autowired
    private TaxesIvaRepository taxIvaRepository;


    public PurchaseOrderClientDTO getPurchaseOrderClient(Integer id) {
        PurchaseOrderClientDTO d = this.getPurchaseOrderClient();
        Optional<PurchaseOrderClient> c = purchaseOrderClientRepository.findById(id);
        if (c==null) {
            return d;
        } else {
            d.setCurrent(c.get());
        }
        //initDescuentos(d);
        this.setDescuentosCache(d);

        initCabys(d);
        return d;
    }

    private boolean isBarcodeInCache(List<DescuentosCacheDTO> cache, String barcode) {
        if (cache.size()==0) {
            return false;
        }

        for (DescuentosCacheDTO d: cache
        ) {
            if (d.getBarcode().equals(barcode)) {
                return true;
            }
        }
        return false;
    }

    private void setDescuentosCache(PurchaseOrderClientDTO r) {
        if (r.getCurrent().getStatus().equals(PURCHASE_ORDER_CLIENT_STATUS_EDICION)) {

            List<DescuentosCacheDTO> cache = new ArrayList<>();
            List<Descuentos> descuentos = this.descuentosRepository.getDescuentosActivosByTipo(DESCUENTO_TIPO_GLOBAL);
            for(PurchaseOrderClientDetail id: r.getCurrent().getDetails()) {
                if(id.getInventario()!=null &&
                        !isBarcodeInCache(cache, id.getInventario().getBarcode())) {
                    DescuentosDTO d = this.productoService.getDescuentosDTO(id.getInventario().getProducto().getId());
                    //r.setDescuentos(d.getDescuentos());
                    if (d.getDescuentos()!=null && d.getDescuentos().size()<=1) { // 1 = DEFAULT SIN DESCUENTO MEJOR AGREGO TODOS
                        d.setDescuentos(descuentos);
                    }
                    cache.add(new DescuentosCacheDTO(id.getInventario().getBarcode(),d.getDescuentos()));
                }
            }
            r.setDescuentosCache(cache);
        }
    }

    public void updateBilledDetails(List<PurchaseOrderClientDetail> dtls) {

        for(PurchaseOrderClientDetail d:dtls){
            this.updateBilledAmounts(d,true);
        }
    }

    private void updateBilledAmounts(PurchaseOrderClientDetail pocd, boolean pluss ){
//        if(ivd.getPocdId()!=null) {
           Optional<PurchaseOrderClientDetail> o = this.purchaseOrderClientDetailRepository.findById(pocd.getId());
            if (o==null) {
                return;
            }
            PurchaseOrderClientDetail pocDetail = o.get();

//        System.out.println("--> Mto BILLED " + pocd.getMtoBilled());
            pocDetail.setMtoBilled(pocd.getMtoBilled());

//            System.out.println("--> Mto BILLED " + pocDetail.getMtoBilled());

//        System.out.println("--> POR BILLED " + pocd.getPorBilled());

        pocDetail.setPorBilled(pocd.getPorBilled());
//            System.out.println("--> POR BILLED " + pocDetail.getPorBilled());

//        System.out.println("--> TAX BILLED " + pocd.getTaxBilled());
        pocDetail.setTaxBilled(pocd.getTaxBilled());
//            System.out.println("--> TAX BILLED " + pocDetail.getTaxBilled());
//                pocd.setPorBilled((float)((pocd.getMtoBilled() * 100) / pocd.getSubTotal()));
//                pocd.setTaxBilled(pocd.getTaxBilled()  + ivd.getTax());
//            if(pluss){
//                pocd.setMtoBilled(pocd.getMtoBilled() + ivd.getSubTotal());
//                pocd.setPorBilled((float)((pocd.getMtoBilled() * 100) / pocd.getSubTotal()));
//                pocd.setTaxBilled(pocd.getTaxBilled()  + ivd.getTax());
//            }else{
//                pocd.setMtoBilled(pocd.getMtoBilled()  - ivd.getSubTotal());
//                pocd.setTaxBilled(pocd.getTaxBilled()  - ivd.getTax());
//                pocd.setPorBilled((float)((pocd.getMtoBilled() * 100) / pocd.getSubTotal()));
//            }
            this.purchaseOrderClientDetailRepository.saveAndFlush(pocDetail);
//        }
    }

    public PurchaseOrderClientsDTO getPurchaseOrderClients(String filter) {

        PurchaseOrderClientsDTO d = new PurchaseOrderClientsDTO();

         d.setPurchaseOrderClients(this.purchaseOrderClientRepository.findUsingFilter(filter));

        return d;

    }

    public PurchaseOrderClientsDTO getPurchaseOrderClients(String filter, Integer pageNumber,
                                           Integer pageSize, String sortDirection,
                                 String sortField) {

        PurchaseOrderClientsDTO d = new PurchaseOrderClientsDTO();

        d.setPurchaseOrderClientsPage(this.purchaseOrderClientRepository.getFilterPageable(filter,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));


        d.setTotal(this.purchaseOrderClientRepository.countAllByFilter(filter));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public PurchaseOrderClientDTO getPurchaseOrderClient() {
        List<String> estados = new ArrayList<>();
        estados.add("Edicion");
        estados.add("Ingresado");
        estados.add("Ejecucion");
        estados.add("Concluida");
        estados.add("Anulado");

        List<String> estadosMp = new ArrayList<>();
        estadosMp.add("Activo");
        estadosMp.add("Inactivo");


        PurchaseOrderClientDTO d = new PurchaseOrderClientDTO();

        d.setEstadosOc(estados);
        d.setEstadosPm(estadosMp);
        d.setCurrencies(this.currencyRepository.findAll());
//        d.setCostCenters(this.centroCostosRepository.getByStates(CostCenterConstants.ACTIVE, CostCenterConstants.QUOTE));
        d.setCostCenters(new ArrayList<>());
        d.setClients(this.clientRepository.findClientsActive());
        d.setTaxes(this.taxesIvaRepository.findAll());

        d.setDefaultIva(getDefaultIva());

        d.setInventarioItems(this.inventoryItemsRepository.findAll());
        d.setServices(this.serviceCabysRepository.findAll());
        d.setExchangeRates(this.exhangeRateRepository.getActivoExchangeRates());

        //String prefix = "IPOC" + this.generalParameterService.getYear() + this.generalParameterService.getMonth();
        PurchaseOrderClient pop = new PurchaseOrderClient();
        //pop.setOrderNumber(prefix);
        //this.generalParameterService.generateNextPOOrderNumber(pop);
        d.setCurrent(pop);

        d.setDescuentos(this.descuentosRepository.getDescuentosActivosByTipo(DESCUENTO_TIPO_GLOBAL));


        d.setDefaultCostCenter(this.getDefaultCostCenterForSelling());
        return d;
    }

    private TaxesIva getDefaultIva() {
        List<GeneralParameter> gp = this.generalParameterRepository.getByCode(IMP_VENTA_DEFAULT);
        if (gp==null || gp.size()==0) {
            return null;
        }
        GeneralParameter defaultGp = gp.get(0);
        try {
            Double val = Double.parseDouble(defaultGp.getVal());
            List<TaxesIva> taxes = taxIvaRepository.getTaxes();
            if (taxes==null) {
                return null;
            }
            TaxesIva foundTax = null;
            for (TaxesIva tiva: taxes) {
                if (tiva.getTaxPorcent()!=null && tiva.getTaxPorcent().doubleValue()==val.doubleValue()) {
                    foundTax = tiva;
                    break;
                }
            }
            return foundTax;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CostCenter getDefaultCostCenterForSelling(){

        CostCenter defaultCc = null;
        try {
            defaultCc = this.centroCostosService.getDefaultCostCenterForSelling();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultCc;
    }

    public void revisarCabysFaltantes(PurchaseOrderClient p) throws Exception{
        if (p!= null){
            if (p.getDetails()!= null) {
                for (PurchaseOrderClientDetail d : p.getDetails()){
                    if (d.getType().equals(TIPO_DETALLE_PRODUCTO_PURCHASE_ORDER_CLIENT)) {
                        if (d.getCodigoCabys() == null ||  d.getCodigoCabys().equals("")) {
                            throw new GeneralInventoryException("Por favor revise que todos los productos tengan un cabys asignado");
                        }
                    }
                }
            }
        }
    }

    public PurchaseOrderClient save(PurchaseOrderClient poc) throws Exception{

        if (poc.getId()==null ) {
           Integer consecutive = this.generalParameterService.getNextConsecutivePurchaseOrderClient();
           poc.setConsecutive(consecutive);
           poc.setCreateAt(new Date(System.currentTimeMillis()));
        }
        poc.setUpdateAt(new Date(System.currentTimeMillis()));
        //this.revisarCabysFaltantes(poc);
        PurchaseOrderClient r = this.purchaseOrderClientRepository.save(poc);

        return poc;
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    private void initCabys(PurchaseOrderClientDTO d) {

        List<Integer> products = this.purchaseOrderClientDetailRepository.getProductoDetail(d.getCurrent().getId());
//        List<Integer> products = this.purchaseOrderClientDetailRepository.getProductoDetail(d.getCurrent().getId());
        List<Integer> services =  this.purchaseOrderClientDetailRepository.getServiceDetail(d.getCurrent().getId());

        if (services!=null && services.size()>0) {
            List<ServiceCabys> l1 = this.serviceCabysRepository.findByIdIn(services);
            d.setServiceCabys(l1);
        }

        if (products!=null && products.size()>0) {
            List<InventarioItem> l2 = this.inventoryItemsRepository.findByIdIn(products);


            d.setItems(l2);
        }

    }

    private void initDescuentos(PurchaseOrderClientDTO r) {
//        if (r.)
    }

    public PurchaseOrderClient chageStatus(Integer id, String status) throws Exception{

        PurchaseOrderClient poc = this.purchaseOrderClientRepository.getById(id);
        if (poc==null) {
            throw new GeneralInventoryException("No se encontro el id de la orden de compra");
        }

        poc.setStatus(status);
        poc = this.save(poc);
        return poc;
    }
}
