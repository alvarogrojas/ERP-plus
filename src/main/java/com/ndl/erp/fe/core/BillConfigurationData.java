package com.ndl.erp.fe.core;

public interface BillConfigurationData {

    public String getBasedPath();
    public String getIdpUri();
    public String getIdpClientId();
    public String getApiUri();
    public String getUsuario();
    public String getClave();
    public String getPin();
    public String getCertificateFileName();
    public Integer getTenantId();

    String getPaisCode();
}
