package com.ndl.erp.services;

import com.ndl.erp.constants.CostCenterConstants;
import com.ndl.erp.domain.*;
import com.ndl.erp.repository.*;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Deprecated
@Component
public class InvestoryManagementService {

    @Autowired
    private BodegaIngresoRepository bodegaIngresoRepository;

    @Autowired
    private BodegaRepository bodegaRepository;

    @Autowired
    private InventarioItemRepository inventarioItemRepository;

    @Autowired
    private BillPayRepository billPayRepository;

    @Autowired
    private BitacoraBodegaIngresoRepository bitacoraBodegaIngresoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BodegaSalidaRepository bodegaSalidaRepository;

    @Autowired
    private PurchaseOrderClientDetailRepository purchaseOrderClientDetailRepository;

    @Autowired
    private BitacoraBodegaSalidaRepository bitacoraBodegaSalidaRepository;


    @Transactional
    public boolean manageStockIn(List<BillPayDetail> bpds, boolean isNew) {
        if (bpds==null || bpds.size()<1) {
            return true;
        }

        List<BodegaIngreso> bi = null;
        if (!isNew) {
            bi = this.bodegaIngresoRepository.getByBillPayId(bpds.get(0).getBillPay().getId());
        }

        Bodega bodega = this.getCurrentBodega();
        InventarioItem ii;
        for (BillPayDetail c: bpds) {
            if (isProduct(c)) {
                BodegaIngreso bIn = this.bodegaIngresoRepository.getBodegaIngreso(c.getBillPay().getId(),c.getId(), "CXP");
                if (isNew ||
                        bIn==null
                ) {
                    ii = addInventoryItem(c, bodega);
                } else {
                    manageUpdateItemsAndBodegaIn(c, bIn, bodega);

                }
                bi = removeFromOriginalList(bIn,bi);
                
            }
        }
        if (bi!=null && bi.size() >0) {
            removeBodegaIn(bi);
        }
        return true;


    }

    private void removeBodegaIn(List<BodegaIngreso> bil) {
        for (BodegaIngreso bi: bil) {
            this.addBitacora(bi, bi.getInventarioItem().getCodigoCabys(),CostCenterConstants.INVENTARIO_BITCORA_DELETE);//

            bi.setStatus("Borrado");
            this.bodegaIngresoRepository.save(bi);
            InventarioItem ii =this.inventarioItemRepository.getById(bi.getInventarioItem().getId());
//            InventarioItem ii =this.inventarioItemRepository.getById(bi.getInventarioItem().getId());
            if (ii!=null) {
                updateInventoryItem(bi.getQuantity()*-1, ii);
            }
        }
    }

    private List<BodegaIngreso>  removeFromOriginalList(BodegaIngreso bIn, List<BodegaIngreso> bi) {
        if (bIn==null || bi==null || bi.size()<1) {
            return bi;
        }
        Integer idx = -1;
        for (BodegaIngreso b: bi) {
           idx ++; 
            if (b.getId()==bIn.getId()) {
                bi.remove(b);
//                bi.remove(idx);
                break;
            }
        }
        return bi;

    }

    private void manageUpdateItemsAndBodegaIn(BillPayDetail c, BodegaIngreso bIn, Bodega bodega) {
        if (bIn.getInventarioItem().getCodigoCabys().equals(c.getCodigoCabys())) {
            updateBodegaInAndInventoryItem(c, bIn);
        } else {

            InventarioItem ii1 =this.inventarioItemRepository.getById(bIn.getInventarioItem().getId());
            updateInventoryItem((-1)* bIn.getQuantity(),ii1);

            InventarioItem ii = null;
//            InventarioItem ii =this.inventarioItemRepository.getById(c.getParentCabysId());
            if (ii!=null) {
                updateInventoryItem(c.getQuantity(), ii);
            } else {
                InventarioItem ii2 = addNewInventoryItem(c, bodega);
                bIn.setInventarioItem(ii2);
            }
            bIn = updateBodegaIngreso(c, bIn);
            this.addBitacora(bIn, c.getCodigoCabys(), CostCenterConstants.INVENTARIO_BITCORA_UPDATE);
        }
    }

    private void updateBodegaInAndInventoryItem(BillPayDetail c, BodegaIngreso bIn) {

        InventarioItem ii =this.inventarioItemRepository.getById(bIn.getInventarioItem().getId());
//        InventarioItem ii =this.inventarioItemRepository.getById(c.getParentCabysId());
        Double f = c.getQuantity() - bIn.getQuantity();
        bIn = updateBodegaIngreso(c, bIn);
        this.updateInventoryItem(f , ii);
        this.addBitacora(bIn, c.getCodigoCabys(), CostCenterConstants.INVENTARIO_BITCORA_UPDATE);
    }

    private void addBitacora(BodegaIngreso bi, String codigoCabys, String type) {
        BitacoraBodegaIngreso bitacoraBodegaIngreso = new BitacoraBodegaIngreso();
        bitacoraBodegaIngreso.setBodegaIngreso(bi);
        bitacoraBodegaIngreso.setDate(new Date(DateUtil.getCurrentTime()));
        bitacoraBodegaIngreso.setPrice(bi.getPrice());
        bitacoraBodegaIngreso.setQuantity(bi.getQuantity());
        bitacoraBodegaIngreso.setCodigoCabys(codigoCabys);
        bitacoraBodegaIngreso.setType(type);
        bitacoraBodegaIngreso.setUser(this.userService.getCurrentLoggedUser());
        this.bitacoraBodegaIngresoRepository.save(bitacoraBodegaIngreso);
    }

    private BodegaIngreso updateBodegaIngreso(BillPayDetail c, BodegaIngreso bIn) {
        bIn.setPrice(c.getPrice());
        bIn.setQuantity(c.getQuantity());
        bIn.setUpdateAt(new Date(DateUtil.getCurrentTime()));
        bIn.setUser(this.userService.getCurrentLoggedUser());
        bIn.setStatus(c.getStatus());
        bIn.setType(CostCenterConstants.BILL_PAY_TYPE_IN);

        bIn = this.bodegaIngresoRepository.save(bIn);
        return bIn;
    }

    private Bodega getCurrentBodega() {
        Optional<Bodega> bodega = bodegaRepository.findById(1);
        if (bodega!=null) {
            return bodega.get();

        }
        return null;
    }


    private InventarioItem addInventoryItem(BillPayDetail bpd, Bodega b) {
//        InventarioItem ii = this.inventarioItemRepository.getByProductId(bpd.getParentCabysId(), bpd.getBodega().getId());
        InventarioItem ii = this.inventarioItemRepository.getByCodigoIngproAndBodegaId(bpd.getCodigoIngpro(), bpd.getInventario().getBodega().getId());
//       InventarioItem ii = null;
        if (ii==null) {
            System.out.println("No encontrado la instancia de " + bpd.getParentCabysId());
        }
//        InventarioItem ii = this.inventarioItemRepository.getByCodigoCabys(bpd.getCodigoCabys());
        if (ii==null) {
            ii = this.addNewInventoryItem(bpd, b);

        } else {
            ii = updateInventoryItem(bpd, ii);


        }
        BodegaIngreso bIn = this.addBodegaIngreso(bpd,ii);
        this.addBitacora(bIn, bpd.getCodigoCabys(), CostCenterConstants.INVENTARIO_BITCORA_ADD);
        return ii;
    }

    private BodegaIngreso addBodegaIngreso(BillPayDetail c, InventarioItem ii) {
        BodegaIngreso bIn = new BodegaIngreso();
        bIn.setBillId(c.getBillPay().getId());
        bIn.setBillDetailId(c.getId());
        bIn.setInventarioItem(ii);

        bIn.setPrice(c.getPrice());
        bIn.setQuantity(c.getQuantity());
        bIn.setUpdateAt(new Date(DateUtil.getCurrentTime()));
        bIn.setCreateAt(new Date(DateUtil.getCurrentTime()));
        bIn.setUser(this.userService.getCurrentLoggedUser());
        bIn.setStatus(c.getStatus());
        bIn.setType(CostCenterConstants.BILL_PAY_TYPE_IN);

        bIn = this.bodegaIngresoRepository.save(bIn);
        return bIn;
    }

    private InventarioItem addNewInventoryItem(BillPayDetail bpd, Bodega bodega) {
        InventarioItem ii = new InventarioItem();
        ii.setStockIngpro(bpd.getQuantity());
        if (bpd.getInventario().getBodega()==null) {
            ii.setBodega(bodega);
        } else {
            ii.setBodega(bpd.getInventario().getBodega());
        }

        // Agregar Bodega.
        ii.setCantidadCotizacion(0);
        ii.setDescripcion(bpd.getDetail());

        ii.setProductId(bpd.getParentCabysId());

        ii.setCatalogo("");
        ii.setCodigoCabys(bpd.getCodigoCabys());
        ii.setCodigoProveedor("");

        ii.setCurrency(bpd.getBillPay().getCurrency());
        ii.setEntrega("");
        ii.setMargenGanancia(0d);
        ii.setMultiplicadorIngpro(1.3d);

        ii.setProductId(bpd.getParentCabysId());

        ii.setPrecioLista(bpd.getPrice());

        ii.setSimboloDescuento("AC");
//        ii.set(bpd.getParentCabysId());
        ii = this.inventarioItemRepository.save(ii);

        return ii;
    }

    private InventarioItem updateInventoryItem(BillPayDetail bpd, InventarioItem ii) {
        Double q = getValidNumber(ii.getStockIngpro()) + bpd.getQuantity();
        ii.setStockIngpro(q);
        this.inventarioItemRepository.save(ii);
        return ii;
    }

    private Double getValidNumber(Double stockIngpro) {
        if (stockIngpro==null) {
            return 0d;
        }
        return stockIngpro;
    }

    private InventarioItem updateInventoryItem(Double qFaltante, InventarioItem ii) {
        if (ii==null) {
            return null;
        }
        Double q = getValidNumber(ii.getStockIngpro())  + qFaltante;
        ii.setStockIngpro(q);

        this.inventarioItemRepository.save(ii);
        return ii;
    }

    private boolean isBillPayDetailIn(BillPayDetail c) {

        BodegaIngreso bi = this.bodegaIngresoRepository.getBodegaIngreso(c.getBillPay().getId(),c.getId(), "CXP");
        return bi==null?false:true;
    }

    private boolean isProduct(BillPayDetail c) {
        return c.getType()!=null && c.getType().equals(CostCenterConstants.BILL_PAY_PRODUCT_LINE)?true:false;
    }

    private boolean isProduct(InvoiceDetail c) {
        return c.getType()!=null && c.getType().equals(CostCenterConstants.BILL_PAY_PRODUCT_LINE)?true:false;
    }


    @Transactional
    public boolean manageStockOut(Set<InvoiceDetail> d, boolean isNew, Integer id) {
        if (d==null || d.size()<1) {
            return true;
        }

        List<BodegaSalida> bi = null;
        if (!isNew) {
            bi = this.bodegaSalidaRepository.getByInvoiceId(id);
        }

        Bodega bodega = this.getCurrentBodega();
        InventarioItem ii;
        for (InvoiceDetail c: d) {
            if (isProduct(c)) {
                BodegaSalida bIn = this.bodegaSalidaRepository.getBodegaSalida(c.getInvoice().getId(),c.getId());
                if (isNew ||
                        bIn==null
                ) {
                    ii = addInventoryItem(c, bodega);
                } else {
                    manageUpdateItemsAndBodegaOut(c, bIn, bodega);

                }
                bi = removeFromOriginalList(bIn,bi);
            }
        }
        if (bi!=null && bi.size() >0) {
            removeBodegaOut(bi);
        }
        return true;


    }

    private InventarioItem addInventoryItem(InvoiceDetail bpd, Bodega b) {
        if (bpd==null || bpd.getPocdId()==null) {
            return null;
        }
        PurchaseOrderClientDetail pocd = this.purchaseOrderClientDetailRepository.getById(bpd.getPocdId());
        if (pocd==null || (pocd.getInventarioItemId()==null)) {
            return null;
        }
//        InventarioItem ii = this.inventarioItemRepository.getByCodigoIngproAndBodegaId(pocd.getInventarioItem().getCodigoIngpro(), bpd.getBodega().getId());
        InventarioItem ii = this.inventarioItemRepository.getById(pocd.getInventarioItemId());
//        InventarioItem ii = pocd.getInventarioItem();
//       InventarioItem ii = null;
        if (ii==null) {
            System.out.println("No encontrado la instancia de " + bpd.getParentCabysId());
            return null;
        }
//        InventarioItem ii = this.inventarioItemRepository.getByCodigoCabys(bpd.getCodigoCabys());
        if (ii==null) {
            //ii = this.addNewInventoryItem(bpd, b);

        } else {
            ii = updateInventoryItem(bpd, ii);


        }
        BodegaSalida bOut = this.addBodegaSalida(bpd,ii);
        this.addBitacoraSalida(bOut, bpd.getCodigoCabys(), CostCenterConstants.INVENTARIO_BITCORA_OUT);
        return ii;
    }

    private InventarioItem updateInventoryItem(InvoiceDetail bpd, InventarioItem ii) {
        Double q = getValidNumber(ii.getStockIngpro()) - bpd.getQuantity();
        ii.setStockIngpro(q);
        this.inventarioItemRepository.save(ii);
        return ii;
    }

    private BodegaSalida addBodegaSalida(InvoiceDetail c, InventarioItem ii) {
        BodegaSalida bOut = new BodegaSalida();
//        bIn.setBillId(c.getBillPay().getId());
//        bIn.setBillDetailId(c.getId());
        bOut.setInventarioItem(ii);

        bOut.setPrice(c.getPrice());
        bOut.setQuantity(c.getQuantity());
        bOut.setUpdateAt(new Date(DateUtil.getCurrentTime()));
        bOut.setCreateAt(new Date(DateUtil.getCurrentTime()));
        bOut.setUser(this.userService.getCurrentLoggedUser());
        bOut.setStatus("INGRESADO");
        bOut.setInvoiceId(c.getInvoice().getId());
        bOut.setInvoiceDetailId(c.getId());
        bOut.setType(CostCenterConstants.BILL_PAY_TYPE_IN);

        bOut = this.bodegaSalidaRepository.save(bOut);
        return bOut;
    }

    private void addBitacoraSalida(BodegaSalida bi, String codigoCabys, String type) {
        BitacoraBodegaSalida bitacoraBodega = new BitacoraBodegaSalida();
        bitacoraBodega.setBodegaSalida(bi);
        bitacoraBodega.setDate(new Date(DateUtil.getCurrentTime()));
        bitacoraBodega.setPrice(bi.getPrice());
        bitacoraBodega.setQuantity(bi.getQuantity());
//        bitacoraBodega.setCodigoCabys(codigoCabys);
        bitacoraBodega.setType(type);
        bitacoraBodega.setUser(this.userService.getCurrentLoggedUser());
        this.bitacoraBodegaSalidaRepository.save(bitacoraBodega);
    }

    private void manageUpdateItemsAndBodegaOut(InvoiceDetail c, BodegaSalida bOut, Bodega bodega) {
        if (bOut.getInventarioItem().getCodigoCabys().equals(c.getCodigoCabys())) {
            updateBodegaOutAndInventoryItem(c, bOut);
        } else {

//            PurchaseOrderClientDetail pocd = this.purchaseOrderClientDetailRepository.getById(c.getPocdId());
//            if (pocd==null) {
//                return ;
//            }
//            InventarioItem ii1 = pocd.getInventarioItem();
//            updateInventoryItem((-1)* bOut.getQuantity(),ii1);
//
////            InventarioItem ii = null;
//            InventarioItem ii =this.inventarioItemRepository.getById(c.getParentCabysId());
//            if (ii!=null) {
//                updateInventoryItem(c.getQuantity(), ii);
//            } else {
////                InventarioItem ii2 = addNewInventoryItem(c, bodega);
////
////                bOut.setInventarioItem(ii2);
//            }
//            bOut = updateBodegaSalida(c, bOut);
//            this.addBitacoraSalida(bOut, c.getCodigoCabys(), CostCenterConstants.INVENTARIO_BITCORA_UPDATE);
        }
    }

    private void updateBodegaOutAndInventoryItem(InvoiceDetail c, BodegaSalida bOut) {

        PurchaseOrderClientDetail pocd = this.purchaseOrderClientDetailRepository.getById(c.getPocdId());
        if (pocd==null || pocd.getInventarioItemId()==null) {
            return ;
        }
//        InventarioItem ii = pocd.getInventarioItem();
        InventarioItem ii = this.inventarioItemRepository.getById(pocd.getInventarioItemId());

//        InventarioItem ii =this.inventarioItemRepository.getById(bIn.getInventarioItem().getId());
//        InventarioItem ii =this.inventarioItemRepository.getById(c.getParentCabysId());
        Double f = bOut.getQuantity() - c.getQuantity() ;
        bOut = updateBodegaSalida(c, bOut);
        this.updateInventoryItem(f , ii);
        this.addBitacoraSalida(bOut, c.getCodigoCabys(), CostCenterConstants.INVENTARIO_BITCORA_UPDATE);
    }

    private BodegaSalida updateBodegaSalida(InvoiceDetail c, BodegaSalida bOut) {
        bOut.setPrice(c.getPrice());
        bOut.setQuantity(c.getQuantity());
        bOut.setUpdateAt(new Date(DateUtil.getCurrentTime()));
        bOut.setUser(this.userService.getCurrentLoggedUser());
//        bOut.setStatus(c.getStatus());
        bOut.setType(CostCenterConstants.BILL_PAY_TYPE_IN);

        bOut = this.bodegaSalidaRepository.save(bOut);
        return bOut;
    }

    private List<BodegaSalida>  removeFromOriginalList(BodegaSalida bIn, List<BodegaSalida> bi) {
        if (bIn==null || bi==null || bi.size()<1) {
            return bi;
        }
        Integer idx = -1;
        for (BodegaSalida b: bi) {
            idx ++;
            if (b.getId()==bIn.getId()) {
                bi.remove(b);
//                bi.remove(idx);
                break;
            }
        }
        return bi;

    }

    private void removeBodegaOut(List<BodegaSalida> bil) {
        for (BodegaSalida bi: bil) {
            this.addBitacoraSalida(bi, bi.getInventarioItem().getCodigoCabys(),CostCenterConstants.INVENTARIO_BITCORA_DELETE);//

            bi.setStatus("Borrado");
            this.bodegaSalidaRepository.save(bi);
            InventarioItem ii =this.inventarioItemRepository.getById(bi.getInventarioItem().getId());
//
            if (ii!=null) {
                updateInventoryItem(bi.getQuantity()*-1, ii);
            }
        }
    }


}
