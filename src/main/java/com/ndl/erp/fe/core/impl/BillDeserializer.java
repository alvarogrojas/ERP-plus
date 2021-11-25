package com.ndl.erp.fe.core.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ndl.erp.fe.core.BillConfigurationService;
import com.ndl.erp.fe.v43.FacturaElectronica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class BillDeserializer {

    @Autowired
    private BillConfigurationService billConfigurationService;

    private XmlMapper xmlMapper;

    public BillDeserializer() {
        xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);

        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public FacturaElectronica deserialize(String xmlFullPath) {
        FacturaElectronica fe = new FacturaElectronica();
        try {
            File file = new File(xmlFullPath);
            String xml = inputStreamToString(new FileInputStream(file));
            fe = xmlMapper.readValue(xml, FacturaElectronica.class);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return fe;
    }

    public String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }


}
