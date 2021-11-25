package com.ndl.erp.services;

import com.ndl.erp.domain.*;
import com.ndl.erp.dto.InvoiceNotaCreditoDTO;
import com.ndl.erp.dto.InvoiceNotaCreditoListDTO;
import com.ndl.erp.dto.ResultFe;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.fe.core.OptionSet;
import com.ndl.erp.fe.helpers.BillHelper;
import com.ndl.erp.fe.mappers.InvoiceNotaCreditoV43Mapper;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;
import com.ndl.erp.repository.BillSenderDetailRepository;
import com.ndl.erp.repository.ClientRepository;
import com.ndl.erp.repository.InvoiceNotaCreditoRepository;
import com.ndl.erp.repository.InvoiceRepository;
import com.ndl.erp.util.DateUtil;
import com.ndl.erp.views.NoteCeditViewBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.datatype.DatatypeConfigurationException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.ndl.erp.constants.InvoiceConstants.INVOICE_PARAMETER_DATOS_EMPRESA;
import static com.ndl.erp.constants.InvoiceConstants.INVOICE_PARAMETER_NOMBRE_EMPRESA;
import static com.ndl.erp.constants.InvoiceNotaCreditoConstants.*;

@Component
public class InvoiceNotaCreditoService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private FacturaElectronicaService facturaElectronicaService;


    @Autowired
    UserServiceImpl userService;

    @Autowired
    private BillEmitter billEmitter;

    @Autowired
    GeneralParameterService generalParameterService;

    @Autowired
    private GlobalDataManager globalDataManager;

    @Autowired
    private BillSenderDetailRepository billSenderDetailRepository;

    @Autowired
    private BillTaskService billTaskService;

    @Autowired
    private BillConfigService billConfigService;

    @Autowired
    private FacturaElectronicaMarshaller fem;


    @Autowired
    InvoiceNotaCreditoRepository invoiceNotaCreditoRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    private UserServiceImpl userDetailsService;


    public InvoiceNotaCreditoDTO invoiceNotaCredito(Integer id, Integer invoiceId)throws Exception {

        Invoice invoice = new Invoice();
        if (invoiceId!=null)
            invoice = this.invoiceRepository.getById(invoiceId);

        if (invoice==null) {
            throw new GeneralInventoryException("La factura no fue encontrada y no se puede crear una nota de credito con los datos de la factura que no existe");
        }


        InvoiceNotaCreditoDTO ncDTO = this.getInvoiceNotaCreditoDTO(invoice);

        if (id == null) {
            ncDTO.setStatus(this.getEstadosInvoiceNotaCredito());
            ncDTO.getCurrent().setStatus(INVOICE_NOTA_CREDITO_STATUS_EDICION);
//            ncDTO.setCurrent(this.invoiceRepository.getNotaCreditoFromInvoice(invoiceId));

            return ncDTO;
        }
        InvoiceNotaCredito c = invoiceNotaCreditoRepository.findByInvoiceNotaCreditoById(id);
        try {
            if (c==null ) {
                return null;
            }
        } catch (GeneralInventoryException e) {
            e.printStackTrace();
            return null;
        }

        ncDTO.setCurrent(c);
        return ncDTO;
    }


    public List<String> getEstadosHacienda() {

        List<String> haciendaEstados = new ArrayList<String>();
        haciendaEstados.add(INVOICE_NOTA_CREDITO_PENDIENTE_HACIENDA);
        haciendaEstados.add(INVOICE_NOTA_CREDITO_RECHAZADO_HACIENDA);
        haciendaEstados.add(INVOICE_NOTA_CREDITO_NO_ENVIADO_HACIENDA);
        haciendaEstados.add(INVOICE_NOTA_CREDITO_ACEPTADO_HACIENDA);
        return haciendaEstados;
    }


    public List<String> getEstadosInvoiceNotaCredito() {

        List<String> invoiceNotaCreditoEstadosList = new ArrayList<String>();
        invoiceNotaCreditoEstadosList.add(INVOICE_NOTA_CREDITO_STATUS_EMITIDA);
        invoiceNotaCreditoEstadosList.add(INVOICE_NOTA_CREDITO_STATUS_ANULADA);
        invoiceNotaCreditoEstadosList.add(INVOICE_NOTA_CREDITO_STATUS_PENDIENTE);
        // invoiceNotaCreditoEstadosList.add(INVOICE_NOTA_CREDITO_STATUS_EDICION);

        return invoiceNotaCreditoEstadosList;
    }



    public InvoiceNotaCreditoDTO getInvoiceNotaCreditoDTO(Invoice invoice) {
        if (invoice  == null){
            throw new GeneralInventoryException("No se encontró una factura para crear la nota de crédito");
        }

//        if (invoice.getDetails()  == null){
//            throw new GeneralInventoryException("La factura no tiene detalle para crear la nota de crédito");
//        }

        InvoiceNotaCreditoDTO ncDTO = new InvoiceNotaCreditoDTO();
        ncDTO.setStatus(this.getEstadosInvoiceNotaCredito());
        ncDTO.setEstadoHacienda(this.getEstadosHacienda());
        return ncDTO;
    }

    public InvoiceNotaCreditoDTO getInvoiceNotaCredito(Integer id) {

        InvoiceNotaCredito invoiceNotaCredito  = invoiceNotaCreditoRepository.findByInvoiceNotaCreditoById(id);
        Invoice invoice  = this.invoiceRepository.getById(invoiceNotaCredito.getInvoice().getId());
        InvoiceNotaCreditoDTO invoiceNotaCreditoDTO = this.getInvoiceNotaCreditoDTO(invoice);

        if (invoiceNotaCredito == null) {
            return invoiceNotaCreditoDTO;
        } else {
            invoiceNotaCreditoDTO.setCurrent(invoiceNotaCredito);
        }
        return invoiceNotaCreditoDTO;
    }

    public ResultFe sendNotaCredito(Integer id) {
        OptionSet options;

        InvoiceNotaCredito notaCredito = null;
        ResultFe rfe = new ResultFe();


        try {
            notaCredito = this.invoiceNotaCreditoRepository.findByInvoiceNotaCreditoById(id);

        } catch (Exception e) {
//            System.out.println(e.getMessage());
        }



        boolean ret = false;
        try {

            GeneralParameter gp = this.generalParameterService.get(4);
            List<GeneralParameter> emisor = this.generalParameterService.getByCodes("INGP_INF");
            InvoiceNotaCreditoV43Mapper ivm = new InvoiceNotaCreditoV43Mapper(emisor, gp);

//            if (notaCredito == null) {
//                notaCredito = createCreditNoteDB(iv);
//            }

            NotaCreditoElectronica fe = ivm.mapFacturaElectronica(notaCredito);
            fe.setNumeroConsecutivoFe(globalDataManager.getNotaCreditoElectronicaNext());
            fe.setNumeroConsecutivo(fe.getNumeroConsecutivoFe().toString());

            BillHelper.generarClave(fe);
            notaCredito.setClave(fe.getClave());
            notaCredito.setConsecutivo(fe.getNumeroConsecutivo());
            notaCredito.setEstadoHacienda(BillHelper.ENVIANDO);

            Optional<BillSenderDetail> obs = billSenderDetailRepository.findById(notaCredito.getInvoice().getBillSenderId());
            BillSenderDetail bsd = obs.get();
            fe.getInformacionReferencia().get(0).setNumero(bsd.getConsecutivo());

            BillSenderDetail pefd = this.billTaskService.createProcesoEmisionFacturaDetalle(id, BillHelper.TIPO_NOTA_CREDITO_FE);
            billConfigService.initBasePath(fe.getNumeroConsecutivo());

            String fileName = getXmlFileName(fe.getClave());
            this.fem.writeXMLFile(fe, billConfigService.getCompletePath() + fileName);
            options = billConfigService.initOptionsValues(fileName);
            pefd.setClave(fe.getClave());
            pefd.setStatus(BillHelper.NO_ENVIADA);
            pefd.setConsecutivo(fe.getNumeroConsecutivo());
            pefd.setPath(billConfigService.getCompletePath());
            if (!firmarFactura(options, BillHelper.NOTA_CREDITO_NAMESPACE_V43, fe.getId(), BillHelper.TIPO_NOTA_CREDITO_FE, pefd)) {
                rfe.setResultStr("Hubo un error al firmar la factura");
                rfe.setResult(-1);
                return rfe;
            }

            String pdfFile = this.billConfigService.getPdfFileAndFullPath(fe.getClave());
            this.saveCreditNote(fe.getId(), fe.getNumeroConsecutivo(), fe.getClave(), pdfFile);

            rfe = facturaElectronicaService.emitNotaCreditoElectronica(fe, notaCredito.getId(), fe.getCorreo(), fe.getReceptor().getNombre());
            this.invoiceNotaCreditoRepository.save(notaCredito);

            if (rfe != null && rfe.getResult() == 1 || rfe.getResult() == -1) {
                notaCredito.getInvoice().setStatus("Anulada");
                update(notaCredito.getInvoice());
            }
            ret = rfe.getResult() == 1;
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            rfe.setResult(0);
            if (e.getMessage() != null && !e.getMessage().equals(""))
                rfe.setResultStr(e.getMessage());
            else {
                rfe.setResultStr("Error Durante el envio");

            }
        } catch (Exception e) {
            e.printStackTrace();
            rfe.setResult(0);
            if (e.getMessage() != null && !e.getMessage().equals(""))
                rfe.setResultStr(e.getMessage());
            else {
                rfe.setResultStr("Error Durante el envio");

            }
        }

        notaCredito.getInvoice().setHacienda(ret);


        return rfe;
    }

    public void update(Invoice invoice) {
        invoice.setUpdateAt(new java.sql.Date(System.currentTimeMillis()));
        this.invoiceRepository.save(invoice);
    }

    public Boolean saveCreditNote(Integer id, String consecutivo, String clave, String path) throws Exception {
        GeneralParameter gp = this.generalParameterService.getByCode("EMPRESA_INFO");
        Optional<InvoiceNotaCredito> o = this.invoiceNotaCreditoRepository.findById(id);
        InvoiceNotaCredito invoiceNotaCredito = o.get();

        GeneralParameter leyenda = this.generalParameterService.getByCode("EINV_LEYENDA");
        GeneralParameter nombreEmpresaParameter = this.generalParameterService.getGeneralParameterByNameAndCode(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_NOMBRE_EMPRESA);
        Map<String, Object> e = new HashMap<String, Object>();
        e.put("leyenda", leyenda.getDescription() != null ? leyenda.getDescription() : "");
        if (clave != null)
            e.put("clave", clave);
        else {
            e.put("clave", "");

        }
        if (consecutivo != null) {

            e.put("consecutive", consecutivo);
        } else {
            e.put("consecutive", "");

        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("gp", gp);
        data.put("noteCredit", invoiceNotaCredito);
        data.put("ebill", e);
        data.put("nombreEmpresa", nombreEmpresaParameter);
        data.put("empInfo", nombreEmpresaParameter);
        try {
            NoteCeditViewBuilder pvb = new NoteCeditViewBuilder();
            pvb.getPdf(data, path);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return true;
    }

    private boolean firmarFactura(OptionSet options, String facturaNamespaceV42, Integer id, String tipo, BillSenderDetail pefd) {
        if (!billEmitter.sign(options, facturaNamespaceV42)) {
            this.billTaskService.agregarError("FIRMA", billEmitter.getMessageError(), id);
            this.billTaskService.createProcesoEmisionDetalle(id, new java.util.Date(), pefd, this.billConfigService.getCurrentUser(), 0, 0, 0, billEmitter.getMessageError(), "FIRMA", tipo);

            return false;
        }
        return true;
    }

    public String getXmlFileName(String clave) {
        String fileName = null;
        fileName = clave + ".xml";
        String fullFileName = billConfigService.getCompletePath() + fileName;
        return fileName;
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized InvoiceNotaCredito save(InvoiceNotaCredito invoiceNotaCredito) throws Exception {

        if (invoiceNotaCredito.getId()==null) {

            setAuditoriaCreacionInvoiceNotaCredito(invoiceNotaCredito);
            invoiceNotaCredito.setNumber(this.generalParameterService.generateNextNotaCreditoNumber());

        } else {

            setAuditoriaModificacionInvoiceNotaCredito(invoiceNotaCredito);

        }

        InvoiceNotaCredito r =  this.invoiceNotaCreditoRepository.save(invoiceNotaCredito);
        if (invoiceNotaCredito.getInvoice()!=null) {
            this.invoiceRepository.save(invoiceNotaCredito.getInvoice());
        }

        return r;
    }



    public void setAuditoriaCreacionInvoiceNotaCredito(InvoiceNotaCredito invoiceNotaCredito) throws Exception{

        User u = this.userService.getCurrentLoggedUser();
        if (u==null) {
            throw new GeneralInventoryException("User is not logged");
        }
        invoiceNotaCredito.setUpdateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        invoiceNotaCredito.setCreateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        invoiceNotaCredito.setUser(u);

    }


    public InvoiceNotaCreditoListDTO getInvoiceNotaCreditoList(java.util.Date startFecha, java.util.Date endFecha, String status, Integer pageNumber,
                                                               Integer pageSize, String sortDirection,
                                                               String sortField) {

        InvoiceNotaCreditoListDTO invoiceNotaCreditoListDTO = new InvoiceNotaCreditoListDTO();

        invoiceNotaCreditoListDTO.setPage(this.invoiceNotaCreditoRepository.getPageableInvoiceNotaCreditoByDateAndStatus(startFecha,
                endFecha,
                status,
                createPageable(pageNumber,
                        pageSize,
                        sortDirection,
                        sortField)));

        invoiceNotaCreditoListDTO.setTotal(this.invoiceNotaCreditoRepository.countAllInvoiceNotaCreditoByDateAndStatus(startFecha, endFecha,status));
        if (invoiceNotaCreditoListDTO.getTotal()>0) {
            invoiceNotaCreditoListDTO.setPagesTotal(invoiceNotaCreditoListDTO.getTotal() /pageSize);
        } else {
            invoiceNotaCreditoListDTO.setPagesTotal(0);
        }

        invoiceNotaCreditoListDTO.setStatus(getEstadosInvoiceNotaCredito());

        return invoiceNotaCreditoListDTO;

    }

    public void setAuditoriaModificacionInvoiceNotaCredito(InvoiceNotaCredito invoiceNotaCredito) throws Exception{

        invoiceNotaCredito.setUpdateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));

    }

    public void setAuditoriaModificacionInvoiceNotaCreditoDetail(InvoiceNotaCreditoDetail invoiceNotaCreditoDetail) throws Exception{

        invoiceNotaCreditoDetail.setUpdateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));

    }

    public void setAuditoriaCreacionInvoiceNotaCreditoDetail(InvoiceNotaCreditoDetail invoiceNotaCreditoDetail) throws Exception{

        invoiceNotaCreditoDetail.setUpdateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        invoiceNotaCreditoDetail.setCreateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        invoiceNotaCreditoDetail.setUserId(this.userService.getCurrentLoggedUser().getId().intValue());
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    @Transactional
    public InvoiceNotaCredito updateStatus(Integer id, String status) {
        Optional<InvoiceNotaCredito> o = this.invoiceNotaCreditoRepository.findById(id);
        if (o == null) {
            throw new GeneralInventoryException("Not found");
        }
        InvoiceNotaCredito c = o.get();
        User user = userDetailsService.getCurrentLoggedUser();
        c.setUser(user);

        c.setStatus(status);



//        this.addBillCollectByInvoiceData(c, user.getId().intValue());


        c.setUpdateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        c = this.invoiceNotaCreditoRepository.save(c);


        return c;

    }

    public Resource loadFileAsResource(Integer id, Path filePath) throws Exception {
        try {

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + id);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + id, ex);
        }
    }

    public Path getFacturaBasePdfPath(Integer id) throws Exception {
        Optional<InvoiceNotaCredito> invoiceOpt = this.invoiceNotaCreditoRepository.findById(id);
        String pdfFile = null;
        Path fileStorageLocation = Paths.get(this.billConfigService.getBasedPath());
        Path filePath = null;
        if (invoiceOpt == null) {
            return null;
        }
        InvoiceNotaCredito factura = invoiceOpt.get();
        pdfFile = this.billConfigService.getBasePdfFile(factura.getNumber());
//       pdfFile = this.billConfigService.getPdfFileAndFullPath(factura.getNumber());

        saveCreditNote(factura.getId(), factura.getConsecutivo(), factura.getClave(), pdfFile);
        filePath = fileStorageLocation.resolve(factura.getNumber() + ".pdf").normalize();

        return filePath;


    }
}
