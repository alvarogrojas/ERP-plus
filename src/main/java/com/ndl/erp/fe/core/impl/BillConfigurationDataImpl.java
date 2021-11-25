package com.ndl.erp.fe.core.impl;

import com.ndl.erp.fe.core.BillConfigurationData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillConfigurationDataImpl implements BillConfigurationData {

    @Value( "${bill.usuario}" )
    private String usuario;
    @Value( "${bill.password}" )
    private String password ;
    @Value( "${bill.pin}" )
    private String pin ;
    @Value( "${bill.base.path}" )
    private String basePath;

    @Value( "${bill.idp.uri}" )
    private String idpUri;

    @Value( "${bill.api.uri}" )
    private String apiUri;

    @Value( "${bill.client.id}" )
    private String idpClientId;

    @Value( "${bill.certificate.file.name}" )
    private String certificateFile;

    @Value( "${bill.tenantId}" )
    private Integer tenantId;

    @Value( "${bill.pais.code}" )
    private String paisCode;

    @Value( "${bill.clients.path}" )
    private String billClientPath;



    public String getBasedPath() {
        return basePath;
    }
    public String getIdpUri() {
        return idpUri;
    }
    public String getIdpClientId() {
        return idpClientId;
    }
    public String getApiUri() {
        return apiUri;
    }
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }



    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setIdpUri(String idpUri) {
        this.idpUri = idpUri;
    }

    public void setApiUri(String apiUri) {
        this.apiUri = apiUri;
    }

    public void setIdpClientId(String idpClientId) {
        this.idpClientId = idpClientId;
    }

    public void setCertificateFile(String certificateFile) {
        this.certificateFile = certificateFile;
    }

    public String getClave() {
        return password;
    }
    public String getPin() {
        return pin;
    }

    public String getCertificateFileName() {return this.certificateFile;}

    @Override
    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String getPaisCode() {
        return paisCode;
    }

    public void setPaisCode(String paisCode) {
        this.paisCode = paisCode;
    }

    public String getBillClientPath() {
        return billClientPath;
    }

    public void setBillClientPath(String billClientPath) {
        this.billClientPath = billClientPath;
    }
}
