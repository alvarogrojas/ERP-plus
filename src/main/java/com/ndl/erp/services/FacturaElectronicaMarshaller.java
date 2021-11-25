package com.ndl.erp.services;


import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.fee.FacturaElectronicaExportacion;
import com.ndl.erp.fe.v43.mh.MensajeHacienda;
import com.ndl.erp.fe.v43.mr.MensajeReceptor;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;
import com.ndl.erp.fe.v43.te.TiqueteElectronico;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

@Service
public class FacturaElectronicaMarshaller {

    public FacturaElectronica writeXMLFile(FacturaElectronica fe, String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(FacturaElectronica.class);
        Marshaller marshallerObj = jContext.createMarshaller();
        marshallerObj.marshal(fe,file);
        return fe;
    }

    public FacturaElectronicaExportacion writeXMLFile(FacturaElectronicaExportacion fe, String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(FacturaElectronicaExportacion.class);
        Marshaller marshallerObj = jContext.createMarshaller();
        marshallerObj.marshal(fe,file);
        return fe;
    }

    public MensajeReceptor writeXMLFile(MensajeReceptor mr, String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(MensajeReceptor.class);
        Marshaller marshallerObj = jContext.createMarshaller();
        marshallerObj.marshal(mr,file);
        return mr;
    }

    public NotaCreditoElectronica writeXMLFile(NotaCreditoElectronica mr, String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(NotaCreditoElectronica.class);
        Marshaller marshallerObj = jContext.createMarshaller();
        marshallerObj.marshal(mr,file);
        return mr;
    }

    public TiqueteElectronico writeXMLFile(TiqueteElectronico te, String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(FacturaElectronica.class);
        Marshaller marshallerObj = jContext.createMarshaller();
        marshallerObj.marshal(te,file);
        return te;
    }

    public MensajeHacienda writeXMLFile(MensajeHacienda mr, String fileName) throws JAXBException {
        Marshaller marshallerObj = null;
        try {
            File file = new File(fileName);
            JAXBContext jContext = JAXBContext.newInstance(MensajeHacienda.class);
            marshallerObj = jContext.createMarshaller();
            marshallerObj.marshal(mr, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mr;
    }


}

