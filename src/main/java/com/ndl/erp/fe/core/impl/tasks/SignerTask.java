package com.ndl.erp.fe.core.impl.tasks;

import com.ndl.erp.fe.core.BillConfigurationService;
import com.ndl.erp.fe.core.BillErrorManager;
import com.ndl.erp.fe.core.BillTask;
import com.ndl.erp.fe.core.OptionSet;
import com.ndl.erp.fe.core.impl.GlobalManager;
import com.ndl.erp.fe.core.impl.ResultBase;
import com.ndl.erp.fe.core.impl.tasks.util.DirectPasswordProvider;
import com.ndl.erp.fe.core.impl.tasks.util.FirstCertificateSelector;

import com.ndl.erp.domain.ErrorProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import xades4j.production.Enveloped;
import xades4j.production.XadesEpesSigningProfile;
import xades4j.production.XadesSigner;
import xades4j.production.XadesSigningProfile;
import xades4j.properties.SignaturePolicyBase;
import xades4j.properties.SignaturePolicyIdentifierProperty;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.SignaturePolicyInfoProvider;
import xades4j.providers.impl.FileSystemKeyStoreKeyingDataProvider;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.File;

@Component
public class SignerTask extends BillTask<OptionSet, ResultBase> {

    @Autowired
    private BillErrorManager billErrorManager;

    @Autowired
    private BillConfigurationService billConfigurationService;

    SignerTask() {
    }

    SignerTask(BillErrorManager bm) {

    }

    @Autowired
    private GlobalManager globalManager;


    @Override
    public ResultBase executeTask(OptionSet options) {
        this.processId = Integer.parseInt(options.valueOf(OptionSet.PROCESS_ID).toString());
        ResultBase result = new ResultBase();
        KeyingDataProvider kp;
        try {

            SignaturePolicyInfoProvider policyInfoProvider = new SignaturePolicyInfoProvider() {
                public SignaturePolicyBase getSignaturePolicy() {
                    return new SignaturePolicyIdentifierProperty(
                            new xades4j.properties.ObjectIdentifier(options.valueOf(OptionSet.NAMESPACE).toString()),
                            new ByteArrayInputStream("https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/Resolucion_Comprobantes_Electronicos_DGT-R-48-2016.pdf".getBytes())
                            );
                }
            };


            kp = new FileSystemKeyStoreKeyingDataProvider(
                    "pkcs12",
                    options.valueOf(OptionSet.CERTIFICATE).toString(),
                    new FirstCertificateSelector(),
                    new DirectPasswordProvider(options.valueOf(OptionSet.PASSWORD).toString()),
                    new DirectPasswordProvider(options.valueOf(OptionSet.PASSWORD).toString()),
                    false);


            XadesSigningProfile p = new XadesEpesSigningProfile(kp, policyInfoProvider);

            // open file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document doc1 = builder.parse(new File(options.valueOf(OptionSet.INPUT_FILE).toString()));
            Element elemToSign = doc1.getDocumentElement();

            XadesSigner signer = p.newSigner();

            new Enveloped(signer).sign(elemToSign);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StreamResult output = new StreamResult(new File(options.valueOf(OptionSet.OUTPUT_FILE).toString()));
            Source input = new DOMSource(doc1);

            transformer.transform(input, output);

        } catch (Exception e) {

            ErrorProcess errorProcess = manageError(e.getMessage());
            result.setMessage(e.getMessage());
            result.setResult(false);
        } finally {
           this.saveTareaEjecutada(result);
        }
        return result;
    }

    @Override
    public String getTaskCode() {
        return "SIGNER";
    }

}
