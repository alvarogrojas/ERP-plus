package com.ndl.erp.services;
//
//import com.ndl.erp.fe.core.BillUserSystem;


import com.ndl.erp.fe.core.OptionSet;
import com.ndl.erp.domain.User;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.fee.FacturaElectronicaExportacion;
import com.ndl.erp.fe.helpers.BillHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BillConfigService {

    public static final String INPUT_FILE = "input";

    public static final String OUTPUT_FILE = "output";
    
    private static final String PREFIX_FILE = "signed";
    public static final String PASSWORD = "password";
    public static final String CERTIFICATE = "certificate";

//    private String certificateFileName = "310165487908.p12";

    @Autowired
    public BillConfigData billConfigData;

    @Autowired
    private UserServiceImpl userService;


//    @Autowired
//    private BillUserSystem billUserSystem;

    private SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

    //private String dirSystemPath;

    private String basePath;

    public BillConfigService() {
        //dirSystemPath = billConfigData.getBasedPath();
    }

    public String getDirSystemPath() {
        return billConfigData.getBasedPath();
    }

//    public void setDirSystemPath(String dirSystemPath) {
//        this.dirSystemPath = dirSystemPath;
//    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void initBasePath(String consecutivo) {
        //System.out.println("---> initBasePath consecutivo " + consecutivo);
        this.basePath = sdf.format(new Date()) + File.separator + consecutivo + File.separator;
        //System.out.println("---> initBasePath BASE PATH " + this.basePath);
        String path = getCompletePath();
        //System.out.println("---> initBasePath  PATH " + path);
        File directory = new File(path);
        if (! directory.exists()){
            boolean created = directory.mkdirs();
            //System.out.println("---> initBasePath  CREADO " + created);

        }
    }

    public void initBasePath(Integer consecutivo, String tipo) {
        this.basePath = sdf.format(new Date())+ File.separator + tipo + File.separator + consecutivo + File.separator;
        //System.out.println("---> initBasePath BASE PATH " + this.basePath);
        String path = getCompletePath();
        //System.out.println("--> initBasePath complete path " + this.basePath);
        File directory = new File(path);
        if (! directory.exists()){
            //System.out.println("--> initBasePath DIR NO EXISTE " + this.basePath);
            directory.mkdirs();

        }
    }

    public String getCompletePath() {
        if (this.basePath!=null) {
            return billConfigData.getBasedPath() + this.basePath;
        } else {
            return billConfigData.getBasedPath();
        }
    }

    public OptionSet initOptionsValues(String fileName) {
        
        OptionSet options = new OptionSet();
        
        options.setValue(INPUT_FILE,getCompletePath() + fileName);

        options.setValue(OUTPUT_FILE,getCompletePath() + PREFIX_FILE + fileName);

        options.setValue(PASSWORD,billConfigData.getPin());

        options.setValue(CERTIFICATE, billConfigData.getBasedPath() + billConfigData.getCertificateFileName());

        return options;
    }

    public OptionSet initOptionsValues(String fileName, OptionSet options) {

        options.setValue(INPUT_FILE,getCompletePath() + fileName);

        options.setValue(OUTPUT_FILE,getCompletePath() + PREFIX_FILE + fileName);

        return options;
    }

    public String getCertificateFileName() {

        return billConfigData.getCertificateFileName();
    }

//    public void setCertificateFileName(String certificateFileName) {
//        this.billConfigData. = certificateFileName;
//    }

    public String getPin() {
        return billConfigData.getPin();
    }

//    public void setPin(String pin) {
//        this.pin = pin;
//    }

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
        return getCompletePath() + this.PREFIX_FILE + fe.getClave() + ".xml";
    }


    public String getSignedXmlFileAndFullPath(String clave) {
        return getCompletePath() + this.PREFIX_FILE + clave + ".xml";
    }

    public Integer getCurrentUser() {
        //return this.billUserSystem.getCurrentLoggedUser();
        try {
            User u = this.userService.getCurrentLoggedUser();
            if (u!=null)
                return  new Long(u.getId()).intValue();
            else
                return 0;
        } catch(RuntimeException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();

        }
        return 0;
    }

    public String getPdfFileAndFullPath(FacturaElectronica fe) {
        return getCompletePath() + fe.getClave() + ".pdf";
    }

    public String getPdfFileAndFullPath(FacturaElectronicaExportacion fe) {
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

    public String getRespuestaHaciendaPath(String clave) {
        return getCompletePath() + BillHelper.PREFIX_MENSAJE_HACIENDA_FILE + clave + ".xml";

    }

    public String getPdfFileAndFullPath(String clave, Integer invoiceNumber) {
        if (clave!=null && clave!="") {
            return getCompletePath() + clave + ".pdf";
        } else {
            return getCompletePath() + invoiceNumber + ".pdf";

        }

    }

    public String getPdfFileAndFullPath( Integer invoiceNumber) {
//        if (clave!=null && clave!="") {
//            return getCompletePath() + clave + ".pdf";
//        } else {
            return getCompletePath() + invoiceNumber + ".pdf";

//        }

    }

    public String getBasePdfFile( Integer invoiceNumber) {
//        if (clave!=null && clave!="") {
//            return getCompletePath() + clave + ".pdf";
//        } else {
            return billConfigData.getBasedPath() + invoiceNumber + ".pdf";

//        }

    }
}
