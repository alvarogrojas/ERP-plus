package com.ndl.erp.fe.core.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ndl.erp.fe.core.BillConfigurationService;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.mr.MensajeReceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class BillSerializer {

    @Autowired
    private BillConfigurationService billConfigurationService;

    private XmlMapper xmlMapper;

    public BillSerializer() {
        xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);

        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public String serialize(FacturaElectronica fe) {
        String fileName = null;
        try {
            fileName = fe.getClave() + ".xml";
            String fullFileName = billConfigurationService.getCompletePath() + fileName;
            xmlMapper.writeValue(new File(fullFileName), fe);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return fileName;
    }

    public String serialize(MensajeReceptor fe) {
        String fileName = null;
        try {
            fileName = fe.getClave() + ".xml";
            String fullFileName = billConfigurationService.getCompletePath() + fileName;
            xmlMapper.writeValue(new File(fullFileName), fe);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return fileName;
    }


}
