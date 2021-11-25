package com.ndl.erp.services;


import com.ndl.erp.fe.SignatureType;
import com.ndl.erp.fe.v43.CodigoMonedaType;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.IdentificacionType;
import com.ndl.erp.fe.v43.fee.FacturaElectronicaExportacion;
import com.ndl.erp.fe.v43.mh.MensajeHacienda;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FacturaElectronicaUnmarshaller {

    private Logger logger = Logger.getLogger("FacturaElectronicaUnmarshaller");


    public FacturaElectronica readXMLFile(String fileName) throws JAXBException {


        FacturaElectronica fe = null;
        File file = new File(fileName);
        try {

            JAXBContext jContext = JAXBContext.newInstance(FacturaElectronica.class);
            Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
            fe = (FacturaElectronica) unmarshallerObj.unmarshal(file);
            return fe;
        } catch (JAXBException e){
            fe = manageUnmarshalFE42(file);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            throw e;
        }
        if (fe.getResumenFactura().getCodigoTipoMoneda()==null) {
            CodigoMonedaType c = new CodigoMonedaType();
            c.setCodigoMoneda("CRC");
            fe.getResumenFactura().setCodigoTipoMoneda(c);
        }
        return fe;
    }

    public FacturaElectronicaExportacion readXMLFileFEE(String fileName) throws JAXBException {


        FacturaElectronicaExportacion fe = null;
        File file = new File(fileName);
        try {

            JAXBContext jContext = JAXBContext.newInstance(FacturaElectronicaExportacion.class);
            Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
            fe = (FacturaElectronicaExportacion) unmarshallerObj.unmarshal(file);
            return fe;
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            throw e;
        }
//        if (fe.getResumenFactura().getCodigoTipoMoneda()==null) {
//            com.ndl.erp.fe.v43.fee.CodigoMonedaType c = new com.ndl.erp.fe.v43.fee.CodigoMonedaType();
//            c.setCodigoMoneda("CRC");
//            fe.getResumenFactura().setCodigoTipoMoneda(c);
//        }
       // return fe;
    }

    public NotaCreditoElectronica readXMLNCFile(String fileName) throws JAXBException {


        NotaCreditoElectronica fe = null;
        File file = new File(fileName);
        try {

            JAXBContext jContext = JAXBContext.newInstance(NotaCreditoElectronica.class);
            Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
            fe = (NotaCreditoElectronica) unmarshallerObj.unmarshal(file);
            return fe;
        } catch (JAXBException e){
            fe = manageUnmarshalNC42(file);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            throw e;
        }
        if (fe.getResumenFactura().getCodigoTipoMoneda()==null) {
            com.ndl.erp.fe.v43.nc.CodigoMonedaType c = new com.ndl.erp.fe.v43.nc.CodigoMonedaType();
            c.setCodigoMoneda("CRC");
            fe.getResumenFactura().setCodigoTipoMoneda(c);
        }
        return fe;
    }

    public SignatureType readSignedTypeXMLFile(String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(com.ndl.erp.fe.SignatureType.class);
        Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
        SignatureType fe = (SignatureType) unmarshallerObj.unmarshal(file);
        return fe;
    }

    public com.ndl.erp.fe.v43.mr.MensajeReceptor readMensajeReceptorXMLFile(String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(com.ndl.erp.fe.v43.mr.MensajeReceptor.class);
        Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
        com.ndl.erp.fe.v43.mr.MensajeReceptor fe = (com.ndl.erp.fe.v43.mr.MensajeReceptor) unmarshallerObj.unmarshal(file);
        return fe;
    }

    public MensajeHacienda readMensajeHaciendaXMLFile(String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(com.ndl.erp.fe.MensajeHacienda.class);
        Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
        MensajeHacienda fe = (MensajeHacienda) unmarshallerObj.unmarshal(file);
        return fe;
    }

    public MensajeHacienda createMensajeHacienda(String data) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(MensajeHacienda.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        MensajeHacienda mensajeHacienda = (MensajeHacienda) unmarshaller.unmarshal(new StringReader(data));
        return mensajeHacienda;
    }

    private FacturaElectronica manageUnmarshalFE42(File file) throws JAXBException {
        FacturaElectronica fe;
        JAXBContext jaxbContext = JAXBContext.newInstance(com.ndl.erp.fe.MensajeHacienda.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        com.ndl.erp.fe.FacturaElectronica facturaElectronica = (com.ndl.erp.fe.FacturaElectronica) unmarshaller.unmarshal(file);
        fe = new FacturaElectronica();
        //BeanUtils.copyProperties(facturaElectronica, fe);
        fe.setNumeroConsecutivo(facturaElectronica.getNumeroConsecutivo());
        fe.setClave(facturaElectronica.getClave());
        fe.setCondicionVenta(facturaElectronica.getCondicionVenta());
        fe.setPlazoCredito(facturaElectronica.getPlazoCredito());
        fe.setFechaEmision(facturaElectronica.getFechaEmision());


        if (facturaElectronica.getEmisor()!=null) {
            fe.setEmisor(new com.ndl.erp.fe.v43.EmisorType());
            fe.getEmisor().setCorreoElectronico(facturaElectronica.getEmisor().getCorreoElectronico());
            fe.getEmisor().setNombre(facturaElectronica.getEmisor().getNombre());
            fe.getEmisor().setIdentificacion(new IdentificacionType());
            fe.getEmisor().getIdentificacion().setNumero(facturaElectronica.getEmisor().getIdentificacion().getNumero());
            fe.getEmisor().getIdentificacion().setTipo(facturaElectronica.getEmisor().getIdentificacion().getTipo());

//            BeanUtils.copyProperties(facturaElectronica.getEmisor(), fe.getEmisor());
            fe.getEmisor().setIdentificacion(new IdentificacionType());
//            BeanUtils.copyProperties(facturaElectronica.getEmisor().getIdentificacion(), fe.getEmisor().getIdentificacion());

        }
        if (facturaElectronica.getReceptor()!=null) {
            fe.setReceptor(new com.ndl.erp.fe.v43.ReceptorType());
            //BeanUtils.copyProperties(facturaElectronica.getReceptor(), fe.getReceptor());
            fe.getReceptor().setIdentificacion(new IdentificacionType());
            //BeanUtils.copyProperties(facturaElectronica.getReceptor().getIdentificacion(), fe.getReceptor().getIdentificacion());

            fe.getReceptor().setCorreoElectronico(facturaElectronica.getReceptor().getCorreoElectronico());
            fe.getReceptor().setNombre(facturaElectronica.getReceptor().getNombre());
            fe.getReceptor().setCorreoElectronico(facturaElectronica.getReceptor().getCorreoElectronico());
            fe.getReceptor().setIdentificacion(new IdentificacionType());
            fe.getReceptor().getIdentificacion().setNumero(facturaElectronica.getReceptor().getIdentificacion().getNumero());
            fe.getReceptor().getIdentificacion().setTipo(facturaElectronica.getReceptor().getIdentificacion().getTipo());
        }
        if (facturaElectronica.getDetalleServicio()!=null) {
            fe.setDetalleServicio(new FacturaElectronica.DetalleServicio());

            if (facturaElectronica.getDetalleServicio().getLineaDetalle() != null) {
                FacturaElectronica.DetalleServicio.LineaDetalle ld1;
                for (com.ndl.erp.fe.FacturaElectronica.DetalleServicio.LineaDetalle ld : facturaElectronica.getDetalleServicio().getLineaDetalle()) {
                    ld1 = new FacturaElectronica.DetalleServicio.LineaDetalle();
//                    BeanUtils.copyProperties(ld, ld1);

                    ld1.setCantidad(ld.getCantidad());
                    ld1.setDetalle(ld.getDetalle());
                    ld1.setMontoTotal(ld.getMontoTotal());
                    ld1.setPrecioUnitario(ld.getPrecioUnitario());
                    ld1.setNumeroLinea(ld.getNumeroLinea());
                    ld1.setSubTotal(ld.getSubTotal());
//                    ld1.setCodigo(ld.getCodigo());
                    ld1.setUnidadMedida(ld.getUnidadMedida());

                    fe.getDetalleServicio().getLineaDetalle().add(ld1);
                }
            }
        }
        if (facturaElectronica.getResumenFactura()!=null) {
            fe.setResumenFactura(new FacturaElectronica.ResumenFactura());
           // BeanUtils.copyProperties(facturaElectronica.getResumenFactura(), fe.getResumenFactura());
            fe.getResumenFactura().setTotalServExentos(facturaElectronica.getResumenFactura().getTotalServExentos());
            fe.getResumenFactura().setTotalComprobante(facturaElectronica.getResumenFactura().getTotalComprobante());
            fe.getResumenFactura().setTotalDescuentos(facturaElectronica.getResumenFactura().getTotalDescuentos());
            fe.getResumenFactura().setTotalExento(facturaElectronica.getResumenFactura().getTotalExento());
            fe.getResumenFactura().setTotalGravado(facturaElectronica.getResumenFactura().getTotalGravado());
            fe.getResumenFactura().setTotalImpuesto(facturaElectronica.getResumenFactura().getTotalImpuesto());
            fe.getResumenFactura().setTotalMercanciasExentas(facturaElectronica.getResumenFactura().getTotalMercanciasExentas());
            fe.getResumenFactura().setTotalMercanciasGravadas(facturaElectronica.getResumenFactura().getTotalMercanciasGravadas());
            fe.getResumenFactura().setTotalVentaNeta(facturaElectronica.getResumenFactura().getTotalVentaNeta());
            fe.getResumenFactura().setTotalVenta(facturaElectronica.getResumenFactura().getTotalVenta());


            fe.getResumenFactura().setCodigoTipoMoneda(new CodigoMonedaType());
            if (facturaElectronica.getResumenFactura().getCodigoMoneda()!=null) {
                fe.getResumenFactura().getCodigoTipoMoneda().setCodigoMoneda(facturaElectronica.getResumenFactura().getCodigoMoneda());
            } else {
                fe.getResumenFactura().getCodigoTipoMoneda().setCodigoMoneda("CRC");
            }
        }
        return fe;
    }

    private NotaCreditoElectronica manageUnmarshalNC42(File file) throws JAXBException {
        NotaCreditoElectronica fe;
        JAXBContext jaxbContext = JAXBContext.newInstance(com.ndl.erp.fe.nce.NotaCreditoElectronica.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        com.ndl.erp.fe.nce.NotaCreditoElectronica facturaElectronica = (com.ndl.erp.fe.nce.NotaCreditoElectronica) unmarshaller.unmarshal(file);
        fe = new NotaCreditoElectronica();
        //BeanUtils.copyProperties(facturaElectronica, fe);
        fe.setNumeroConsecutivo(facturaElectronica.getNumeroConsecutivo());
        fe.setClave(facturaElectronica.getClave());
        fe.setCondicionVenta(facturaElectronica.getCondicionVenta());
        fe.setPlazoCredito(facturaElectronica.getPlazoCredito());
        fe.setFechaEmision(facturaElectronica.getFechaEmision());


        if (facturaElectronica.getEmisor()!=null) {
            fe.setEmisor(new com.ndl.erp.fe.v43.nc.EmisorType());
            fe.getEmisor().setCorreoElectronico(facturaElectronica.getEmisor().getCorreoElectronico());
            fe.getEmisor().setNombre(facturaElectronica.getEmisor().getNombre());
            fe.getEmisor().setIdentificacion(new com.ndl.erp.fe.v43.nc.IdentificacionType());
            fe.getEmisor().getIdentificacion().setNumero(facturaElectronica.getEmisor().getIdentificacion().getNumero());
            fe.getEmisor().getIdentificacion().setTipo(facturaElectronica.getEmisor().getIdentificacion().getTipo());

//            BeanUtils.copyProperties(facturaElectronica.getEmisor(), fe.getEmisor());
            fe.getEmisor().setIdentificacion(new com.ndl.erp.fe.v43.nc.IdentificacionType());
//            BeanUtils.copyProperties(facturaElectronica.getEmisor().getIdentificacion(), fe.getEmisor().getIdentificacion());

        }
        if (facturaElectronica.getReceptor()!=null) {
            fe.setReceptor(new com.ndl.erp.fe.v43.nc.ReceptorType());
            //BeanUtils.copyProperties(facturaElectronica.getReceptor(), fe.getReceptor());
            fe.getReceptor().setIdentificacion(new com.ndl.erp.fe.v43.nc.IdentificacionType());
            //BeanUtils.copyProperties(facturaElectronica.getReceptor().getIdentificacion(), fe.getReceptor().getIdentificacion());

            fe.getReceptor().setCorreoElectronico(facturaElectronica.getReceptor().getCorreoElectronico());
            fe.getReceptor().setNombre(facturaElectronica.getReceptor().getNombre());
            fe.getReceptor().setCorreoElectronico(facturaElectronica.getReceptor().getCorreoElectronico());
            fe.getReceptor().setIdentificacion(new com.ndl.erp.fe.v43.nc.IdentificacionType());
            fe.getReceptor().getIdentificacion().setNumero(facturaElectronica.getReceptor().getIdentificacion().getNumero());
            fe.getReceptor().getIdentificacion().setTipo(facturaElectronica.getReceptor().getIdentificacion().getTipo());
        }
        if (facturaElectronica.getDetalleServicio()!=null) {
            fe.setDetalleServicio(new NotaCreditoElectronica.DetalleServicio());

            if (facturaElectronica.getDetalleServicio().getLineaDetalle() != null) {
                NotaCreditoElectronica.DetalleServicio.LineaDetalle ld1;
                for (com.ndl.erp.fe.nce.NotaCreditoElectronica.DetalleServicio.LineaDetalle ld : facturaElectronica.getDetalleServicio().getLineaDetalle()) {
                    ld1 = new NotaCreditoElectronica.DetalleServicio.LineaDetalle();
//                    BeanUtils.copyProperties(ld, ld1);

                    ld1.setCantidad(ld.getCantidad());
                    ld1.setDetalle(ld.getDetalle());
                    ld1.setMontoTotal(ld.getMontoTotal());
                    ld1.setPrecioUnitario(ld.getPrecioUnitario());
                    ld1.setNumeroLinea(ld.getNumeroLinea());
                    ld1.setSubTotal(ld.getSubTotal());
//                    ld1.setCodigo(ld.getCodigo());
                    ld1.setUnidadMedida(ld.getUnidadMedida());

                    fe.getDetalleServicio().getLineaDetalle().add(ld1);
                }
            }
        }
        if (facturaElectronica.getResumenFactura()!=null) {
            fe.setResumenFactura(new NotaCreditoElectronica.ResumenFactura());
           // BeanUtils.copyProperties(facturaElectronica.getResumenFactura(), fe.getResumenFactura());
            fe.getResumenFactura().setTotalServExentos(facturaElectronica.getResumenFactura().getTotalServExentos());
            fe.getResumenFactura().setTotalComprobante(facturaElectronica.getResumenFactura().getTotalComprobante());
            fe.getResumenFactura().setTotalDescuentos(facturaElectronica.getResumenFactura().getTotalDescuentos());
            fe.getResumenFactura().setTotalExento(facturaElectronica.getResumenFactura().getTotalExento());
            fe.getResumenFactura().setTotalGravado(facturaElectronica.getResumenFactura().getTotalGravado());
            fe.getResumenFactura().setTotalImpuesto(facturaElectronica.getResumenFactura().getTotalImpuesto());
            fe.getResumenFactura().setTotalMercanciasExentas(facturaElectronica.getResumenFactura().getTotalMercanciasExentas());
            fe.getResumenFactura().setTotalMercanciasGravadas(facturaElectronica.getResumenFactura().getTotalMercanciasGravadas());
            fe.getResumenFactura().setTotalVentaNeta(facturaElectronica.getResumenFactura().getTotalVentaNeta());
            fe.getResumenFactura().setTotalVenta(facturaElectronica.getResumenFactura().getTotalVenta());


            fe.getResumenFactura().setCodigoTipoMoneda(new com.ndl.erp.fe.v43.nc.CodigoMonedaType());
            if (facturaElectronica.getResumenFactura().getCodigoMoneda()!=null) {
                fe.getResumenFactura().getCodigoTipoMoneda().setCodigoMoneda(facturaElectronica.getResumenFactura().getCodigoMoneda());
            } else {
                fe.getResumenFactura().getCodigoTipoMoneda().setCodigoMoneda("CRC");
            }
        }
        return fe;
    }
}

