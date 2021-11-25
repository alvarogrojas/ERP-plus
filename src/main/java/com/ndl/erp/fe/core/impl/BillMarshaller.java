package com.ndl.erp.fe.core.impl;

import com.ndl.erp.fe.MensajeHacienda;
import com.ndl.erp.fe.MensajeReceptor;
import com.ndl.erp.fe.nce.NotaCreditoElectronica;
import com.ndl.erp.fe.v43.FacturaElectronica;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

@Service
public class BillMarshaller {

    public FacturaElectronica writeXMLFile(FacturaElectronica fe, String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(FacturaElectronica.class);
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

    public MensajeHacienda writeXMLFile(MensajeHacienda mr, String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(MensajeHacienda.class);
        Marshaller marshallerObj = jContext.createMarshaller();
        marshallerObj.marshal(mr,file);
        return mr;
    }


}

