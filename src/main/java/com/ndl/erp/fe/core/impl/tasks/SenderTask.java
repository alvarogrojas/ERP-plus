package com.ndl.erp.fe.core.impl.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndl.erp.fe.core.BillConfigurationService;
import com.ndl.erp.fe.core.BillErrorManager;
import com.ndl.erp.fe.core.BillTask;
import com.ndl.erp.fe.core.OptionSet;
import com.ndl.erp.fe.core.impl.GlobalManager;
import com.ndl.erp.fe.core.impl.ResultBase;
import com.ndl.erp.fe.core.impl.tasks.util.ComprobanteElectronico;
import com.ndl.erp.fe.core.impl.tasks.util.ObligadoTributario;
import com.ndl.erp.fe.core.impl.tasks.util.XmlHelper;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SenderTask extends BillTask<OptionSet, ResultBase> {

    @Autowired
    private BillErrorManager billErrorManager;

    @Autowired
    private BillConfigurationService billConfigurationService;

    SenderTask() {
    }

    SenderTask(BillErrorManager bm) {

    }

    @Autowired
    private GlobalManager globalManager;


    @Override
    public ResultBase executeTask(OptionSet options) {
        this.processId = Integer.parseInt(options.valueOf(OptionSet.PROCESS_ID).toString());

        ResultBase result = new ResultBase();
        try {
            String baseXMLCE = options.valueOf(OptionSet.BASE_NAME);
            XPath xPath = XPathFactory.newInstance().newXPath();
            File file = new File(options.valueOf(OptionSet.OUTPUT_FILE).toString());
            byte[] bytes = FileUtils.readFileToString(file, "UTF-8").getBytes("UTF-8");
            String base64 = Base64.encodeBase64String(bytes);
            ComprobanteElectronico comprobanteElectronico = new ComprobanteElectronico();
            comprobanteElectronico.setComprobanteXml(base64);

            Document xml = XmlHelper.getDocument(options.valueOf(OptionSet.OUTPUT_FILE).toString());
            NodeList nodes = (NodeList) xPath.evaluate(baseXMLCE + "/Clave", xml.getDocumentElement(), XPathConstants.NODESET);
            comprobanteElectronico.setClave(nodes.item(0).getTextContent());
            nodes = (NodeList) xPath.evaluate(baseXMLCE + "/FechaEmision", xml.getDocumentElement(), XPathConstants.NODESET);
            //comprobanteElectronico.setFecha(nodes.item(0).getTextContent());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            comprobanteElectronico.setFecha(format.format(new Date()));

            ObligadoTributario receptor = new ObligadoTributario();
            ObligadoTributario emisor = new ObligadoTributario();
            nodes = (NodeList) xPath.evaluate(baseXMLCE + "/Emisor/Identificacion/Tipo", xml.getDocumentElement(), XPathConstants.NODESET);
            emisor.setTipoIdentificacion(nodes.item(0).getTextContent());
            nodes = (NodeList) xPath.evaluate(baseXMLCE + "/Emisor/Identificacion/Numero", xml.getDocumentElement(), XPathConstants.NODESET);
            emisor.setNumeroIdentificacion(nodes.item(0).getTextContent());

            nodes = (NodeList) xPath.evaluate(baseXMLCE + "/Receptor/Identificacion/Tipo", xml.getDocumentElement(), XPathConstants.NODESET);
            receptor.setTipoIdentificacion(nodes.item(0).getTextContent());
            nodes = (NodeList) xPath.evaluate(baseXMLCE + "/Receptor/Identificacion/Numero", xml.getDocumentElement(), XPathConstants.NODESET);
            receptor.setNumeroIdentificacion(nodes.item(0).getTextContent());

            comprobanteElectronico.setReceptor(receptor);
            comprobanteElectronico.setEmisor(emisor);

            //String token = getToken(username, password);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(billConfigurationService.getApiUri() + "recepcion");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(comprobanteElectronico);
//            System.out.println(json);
            StringEntity params = new StringEntity(json);
            request.addHeader("content-type", "application/javascript");
            request.addHeader("Authorization", "bearer " + OptionSet.ACCESS_TOKEN);
            request.setEntity(params);
            org.apache.http.HttpResponse response = httpClient.execute(request);
            org.apache.http.HttpEntity entity = response.getEntity();
            //System.out.println("Response code: " + response.getStatusLine().getStatusCode());
            //printHeaders(response.getAllHeaders());
            String responseString = EntityUtils.toString(entity, "UTF-8");
//            System.out.println(responseString);

            if (response.getStatusLine().getStatusCode()==200 || response.getStatusLine().getStatusCode()==202) {
                //result.setResult(1);
                result.setMessage("Se envio Exitosamente  " + comprobanteElectronico.getClave() + ": " + responseString );
            } else if (response.getStatusLine().getStatusCode()>=400 || response.getStatusLine().getStatusCode()<500) {
                //result = 0;
                result.setResult(false);
                manageError("Se produjo un error de envio  " + comprobanteElectronico.getClave() + ": " + responseString );

            } else {
                result.setResult(false);
                manageError("Se produjo un error al enviar  " + comprobanteElectronico.getClave() + ":" + responseString);

            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            manageError("Excepcion durante el envio: " + e.getMessage());
        } finally {
            this.saveTareaEjecutada(result);
        }
        return result;
    }

    @Override
    public String getTaskCode() {
        return "SENDER";
    }

}
