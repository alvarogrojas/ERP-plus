package com.ndl.erp.fe.core;

import com.ndl.erp.services.UserServiceImpl;
import com.ndl.erp.fe.v43.FacturaElectronica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BillConfigurationService {


    @Autowired
    public BillConfigurationData billConfigData;


//    @Autowired
//    private BillUserSystem billUserSystem;

    private SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

    private String basePath;

    @Autowired
    private UserServiceImpl userService;

    public BillConfigurationService() {
        //dirSystemPath = billConfigData.getBasedPath();
    }

    public String getDirSystemPath() {
        return billConfigData.getBasedPath();
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void initBasePath(String consecutivo) {
        this.basePath = sdf.format(new Date()) + File.separator + consecutivo + File.separator;

        String path = getCompletePath();
        File directory = new File(path);
        if (! directory.exists()){
            directory.mkdirs();

        }
    }

    public void initBasePath(Integer consecutivo, String tipo) {
        this.basePath = sdf.format(new Date())+ File.separator + tipo + File.separator + consecutivo + File.separator;

        String path = getCompletePath();
        File directory = new File(path);
        if (! directory.exists()){
            directory.mkdirs();

        }
    }

    public String getCompletePath() {
        return billConfigData.getBasedPath() + this.basePath;
    }

    public OptionSet initOptionsValues(String fileName) {
        
        OptionSet options = new OptionSet();
        
        options.setValue(OptionSet.INPUT_FILE,getCompletePath() + fileName);

        options.setValue(OptionSet.OUTPUT_FILE,getCompletePath() + OptionSet.PREFIX_FILE + fileName);

        options.setValue(OptionSet.PASSWORD,billConfigData.getPin());

        options.setValue(OptionSet.CERTIFICATE, billConfigData.getBasedPath() + billConfigData.getCertificateFileName());

        return options;
    }

    public OptionSet initOptionsValues(String fileName, String namespace, String baseNameStructure,String accessToken, Integer processId) {

        OptionSet options = new OptionSet();

        options.setValue(OptionSet.INPUT_FILE,getCompletePath() + fileName);

        options.setValue(OptionSet.OUTPUT_FILE,getCompletePath() + OptionSet.PREFIX_FILE + fileName);

        options.setValue(OptionSet.PASSWORD,billConfigData.getPin());

        options.setValue(OptionSet.CERTIFICATE, billConfigData.getBasedPath() + billConfigData.getCertificateFileName());

        options.setValue(OptionSet.NAMESPACE, namespace);

        options.setValue(OptionSet.BASE_NAME, namespace);
        options.setValue(OptionSet.ACCESS_TOKEN, accessToken);
        options.setValue(OptionSet.PROCESS_ID, processId.toString());

        return options;
    }

    public OptionSet initOptionsValues(String fileName, OptionSet options) {

        options.setValue(OptionSet.INPUT_FILE,getCompletePath() + fileName);

        options.setValue(OptionSet.OUTPUT_FILE,getCompletePath() + OptionSet.PREFIX_FILE + fileName);

        return options;
    }

    public String getCertificateFileName() {
        return billConfigData.getCertificateFileName();
    }

    public String getPin() {
        return billConfigData.getPin();
    }

    public String getUsuario() {
        return billConfigData.getUsuario();
    }

    public String getPassword() {
        return billConfigData.getClave();
    }

    public String getXmlFileAndFullPath(FacturaElectronica fe) {
        return getCompletePath() + fe.getClave() + ".xml";
    }


    public String getSignedXmlFileAndFullPath(FacturaElectronica fe) {
        return getCompletePath() + OptionSet.PREFIX_FILE + fe.getClave() + ".xml";
    }


    public String getSignedXmlFileAndFullPath(String clave) {
        return getCompletePath() + OptionSet.PREFIX_FILE + clave + ".xml";
    }

    public Integer getCurrentUser() {
        //return this.billUserSystem.getCurrentLoggedUser();
        Long id = this.userService.getCurrentLoggedUser().getId();
        return id.intValue();
        //return ((Us) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    public String getPdfFileAndFullPath(FacturaElectronica fe) {
        return getCompletePath() + fe.getClave() + ".pdf";
    }

    public String getPdfFileAndFullPath(String clave) {
        return getCompletePath() + clave + ".pdf";
    }

    public String getIdpClientId() {
        return this.billConfigData.getIdpClientId();
    }

    public String getBasedPath() {
        return  this.billConfigData.getBasedPath();
    }
    public String getIdpUri() {
        return this.billConfigData.getIdpUri();
    }

    public String getApiUri() {
        return billConfigData.getApiUri();
    }

    public Integer getTenantId() {
        return this.billConfigData.getTenantId();
    }

    public String getPaisCode() {
        return this.billConfigData.getPaisCode();
    }
}
