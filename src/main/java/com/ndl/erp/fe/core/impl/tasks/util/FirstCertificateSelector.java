package com.ndl.erp.fe.core.impl.tasks.util;

import xades4j.providers.impl.KeyStoreKeyingDataProvider;

import java.security.cert.X509Certificate;
import java.util.List;

public class FirstCertificateSelector implements KeyStoreKeyingDataProvider.SigningCertSelector {

    //@Override
    public X509Certificate selectCertificate(
            List<X509Certificate> availableCertificates) {
        return availableCertificates.get(0);
    }
}