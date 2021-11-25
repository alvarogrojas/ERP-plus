package com.ndl.erp.fe.core.impl;

import com.ndl.erp.fe.MensajeHacienda;
import com.ndl.erp.fe.MensajeReceptor;
import com.ndl.erp.fe.SignatureType;
import com.ndl.erp.fe.v43.FacturaElectronica;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;

@Service
public class BillUnmarshaller {

    public FacturaElectronica readXMLFile(String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(FacturaElectronica.class);
        Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
        FacturaElectronica fe = (FacturaElectronica) unmarshallerObj.unmarshal(file);
        return fe;
    }

    public SignatureType readSignedTypeXMLFile(String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(com.ndl.erp.fe.SignatureType.class);
        Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
        SignatureType fe = (SignatureType) unmarshallerObj.unmarshal(file);
        return fe;
    }

    public MensajeReceptor readMensajeReceptorXMLFile(String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(com.ndl.erp.fe.MensajeReceptor.class);
        Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
        MensajeReceptor fe = (MensajeReceptor) unmarshallerObj.unmarshal(file);
        return fe;
    }

    public MensajeHacienda createMensajeHacienda(String data) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(MensajeHacienda.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        MensajeHacienda mensajeHacienda = (MensajeHacienda) unmarshaller.unmarshal(new StringReader(data));
        return mensajeHacienda;
    }
}

