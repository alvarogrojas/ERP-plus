package com.ndl.erp.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndl.erp.fe.core.OptionSet;
import com.ndl.erp.fe.core.impl.tasks.util.DirectPasswordProvider;
import com.ndl.erp.fe.core.impl.tasks.util.FirstCertificateSelector;

import com.ndl.erp.domain.Recepcion;
import com.ndl.erp.dto.ResultFe;
import com.ndl.erp.util.ComprobanteElectronico;
import com.ndl.erp.util.ComprobanteElectronicoCR;
import com.ndl.erp.util.ObligadoTributario;
import com.ndl.erp.util.XmlHelper;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.mh.MensajeHacienda;
import com.ndl.erp.fe.v43.mr.MensajeReceptor;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;
import com.ndl.erp.fe.helpers.BillHelper;
//import org.apache.commons.io.FileUtils;
//import org.apache.http.Header;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.HttpClient;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.message.BufferedHeader;
//import org.apache.http.util.EntityUtils;


import org.apache.commons.io.FileUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import xades4j.production.Enveloped;
import xades4j.production.XadesEpesSigningProfile;
import xades4j.production.XadesSigner;
import xades4j.production.XadesSigningProfile;
import xades4j.properties.ObjectIdentifier;
import xades4j.properties.SignaturePolicyBase;
import xades4j.properties.SignaturePolicyIdentifierProperty;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.SignaturePolicyInfoProvider;
import xades4j.providers.impl.FileSystemKeyStoreKeyingDataProvider;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

//import com.ndl.erp.fe.core.impl.tasks.util.DirectPasswordProvider;
//import com.ndl.erp.fe.core.impl.tasks.util.FirstCertificateSelector;


@Service
public class BillEmitter {

    private String accessToken;
    private String refreshToken;

    @Autowired
    private BillConfigService billConfigService;

    @Autowired
    private FacturaElectronicaUnmarshaller facturaElectronicaUnmarshaller;

    private Logger logger = Logger.getLogger("BillEmitter");




    @Autowired
    private FacturaElectronicaMarshaller facturaElectronicaMarshaller;
    private String messageError;

    public boolean authenticate() {
        Boolean result = false;
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(billConfigService.getIdpUri() + "/token");
            Form form = new Form();
            form.param("grant_type", "password")
                    .param("username", billConfigService.getUsuario())
                    .param("password", billConfigService.getPassword())
                    .param("client_id", billConfigService.getIdpClientId());
            Response response = target.request().post(Entity.form(form));


            String accessTokenStr = response.readEntity(String.class);
            JSONObject jsonObj = new JSONObject(accessTokenStr);
            accessToken = (String) jsonObj.get("access_token");
            refreshToken = (String) jsonObj.get("refresh_token");
            result =  true;
        } catch(Exception e) {
            e.printStackTrace();
            logger.log(Level.ALL,e.getMessage());
            messageError = e.getMessage();

            result = false;
        }
        return result;
    }



    public void refreshToken() throws JSONException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(billConfigService.getIdpUri() + "/token");
        Form form = new Form();
        form.param("grant_type", "refresh_token")
                .param("refresh_token", this.refreshToken)
                .param("client_id", billConfigService.getIdpClientId());
        Response response = target.request().post(Entity.form(form));


        String accessTokenStr = response.readEntity(String.class);
        JSONObject jsonObj = new JSONObject(accessTokenStr);
        accessToken = (String) jsonObj.get("access_token");
        refreshToken = (String) jsonObj.get("refresh_token");
    }

    public Boolean sign(OptionSet options, String namespace) {
        KeyingDataProvider kp;
        Boolean result = false;
        try {
            SignaturePolicyInfoProvider policyInfoProvider = new SignaturePolicyInfoProvider() {
                public SignaturePolicyBase getSignaturePolicy() {
                    return new SignaturePolicyIdentifierProperty(
                            new ObjectIdentifier(namespace),
                            new ByteArrayInputStream("https://www.hacienda.go.cr/ATV/ComprobanteElectronico/docs/esquemas/2016/v4.3/ResolucionComprobantesElectronicosDGT-R-48-2016_4.3.pdf".getBytes()));
                }
            };


            kp = new FileSystemKeyStoreKeyingDataProvider(
                    "pkcs12",
                    options.valueOf(BillConfigService.CERTIFICATE).toString(),
                    new FirstCertificateSelector(),
                    new DirectPasswordProvider(options.valueOf(BillConfigService.PASSWORD).toString()),
                    new DirectPasswordProvider(options.valueOf(BillConfigService.PASSWORD).toString()),
                    false);


            XadesSigningProfile p = new XadesEpesSigningProfile(kp, policyInfoProvider);

            // open file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document doc1 = builder.parse(new File(options.valueOf(BillConfigService.INPUT_FILE).toString()));
            Element elemToSign = doc1.getDocumentElement();

            XadesSigner signer = p.newSigner();

            new Enveloped(signer).sign(elemToSign);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StreamResult output = new StreamResult(new File(options.valueOf(BillConfigService.OUTPUT_FILE).toString()));
            Source input = new DOMSource(doc1);

            transformer.transform(input, output);
            result = true;

        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            messageError = e.getMessage();
            e.printStackTrace();
            result = false;

        }
        return result;
    }

    public ResultFe sendConfirmacionRechazo(OptionSet options, String baseXMLCE, MensajeReceptor mr) {
        ResultFe result = new ResultFe();
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            File file = new File(options.valueOf(BillConfigService.OUTPUT_FILE).toString());
            byte[] bytes = FileUtils.readFileToString(file, "UTF-8").getBytes("UTF-8");
            String base64 = Base64.getEncoder().encodeToString(bytes);

            ComprobanteElectronicoCR comprobanteElectronico = new ComprobanteElectronicoCR();
            comprobanteElectronico.setComprobanteXml(base64);

            Document xml = XmlHelper.getDocument(options.valueOf(BillConfigService.OUTPUT_FILE).toString());
            NodeList nodes = (NodeList) xPath.evaluate(baseXMLCE + "/Clave", xml.getDocumentElement(), XPathConstants.NODESET);
            comprobanteElectronico.setClave(nodes.item(0).getTextContent());
            nodes = (NodeList) xPath.evaluate(baseXMLCE + "/FechaEmisionDoc", xml.getDocumentElement(), XPathConstants.NODESET);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            comprobanteElectronico.setFecha(format.format(new Date()));

            ObligadoTributario receptor = new ObligadoTributario();
            ObligadoTributario emisor = new ObligadoTributario();
            emisor.setTipoIdentificacion(mr.getEmisor().getTipoIdentificacion());
//            emisor.setTipoIdentificacion("02");
            nodes = (NodeList) xPath.evaluate(baseXMLCE + "/NumeroCedulaEmisor", xml.getDocumentElement(), XPathConstants.NODESET);
            emisor.setNumeroIdentificacion(nodes.item(0).getTextContent());

            receptor.setTipoIdentificacion(mr.getReceptor().getTipoIdentificacion());
//            receptor.setTipoIdentificacion("02");
            nodes = (NodeList) xPath.evaluate(baseXMLCE + "/NumeroCedulaReceptor", xml.getDocumentElement(), XPathConstants.NODESET);
            receptor.setNumeroIdentificacion(nodes.item(0).getTextContent());
            comprobanteElectronico.setReceptor(receptor);
            comprobanteElectronico.setEmisor(emisor);
            comprobanteElectronico.setConsecutivoReceptor(mr.getNumeroConsecutivoComprobante());

//            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpClient httpClient =getHttpClient();
            HttpPost request = new HttpPost(billConfigService.getApiUri() + "recepcion");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(comprobanteElectronico);
            //System.out.println(json);
            StringEntity params = new StringEntity(json);
            request.addHeader("content-type", "application/javascript");
            request.addHeader("Authorization", "bearer " + this.accessToken);
            request.setEntity(params);
            org.apache.http.HttpResponse response = httpClient.execute(request);
            org.apache.http.HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
//            System.out.println(responseString);

            if (response.getStatusLine().getStatusCode()==200 || response.getStatusLine().getStatusCode()==202) {
                result.setResult(1);
                result.setResultStr("Se envio Exitosamente  " + comprobanteElectronico.getClave() + ": " + responseString );
                Header[] hs = response.getAllHeaders();
                String location = null;
                location = getHeaderValue(hs, "Location");
                if (location!=null) {
                    result.setLocation(location);
                }
            } else if (response.getStatusLine().getStatusCode()>=400 && response.getStatusLine().getStatusCode()<500) {
                //result = 0;
                result.setResult(0);
                //String xErrorCause = response.getHeaderString("X-Error-Cause");
                Header[] hs = response.getAllHeaders();
                String location = null;
                String xErrorCause = getHeaderValue(hs, "X-Error-Cause");
//                System.out.println(xErrorCause);
                result.setResultStr("Se produjo un error de envio  " + comprobanteElectronico.getClave() + ": " + xErrorCause );

            } else {
                result.setResult(-3);
                result.setResultStr("No se obtuvo respuesta de Hacienda al enviar  " + comprobanteElectronico.getClave() + ": " + responseString );


            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.ALL, e.getMessage());
            this.messageError = e.getMessage();
            result.setResult(-2);
            result.setResultStr( e.getMessage());
        }

        return result;
    }

    public ResultFe send(OptionSet options, String baseXMLCE, Integer esInter) {
        ResultFe result = new ResultFe();
        HttpClient httpClient = null;
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            File file = new File(options.valueOf(BillConfigService.OUTPUT_FILE).toString());
            byte[] bytes = FileUtils.readFileToString(file, "UTF-8").getBytes("UTF-8");
            String base64 = Base64.getEncoder().encodeToString(bytes);
            ComprobanteElectronico comprobanteElectronico = new ComprobanteElectronico();
            comprobanteElectronico.setComprobanteXml(base64);

            Document xml = XmlHelper.getDocument(options.valueOf(BillConfigService.OUTPUT_FILE).toString());
            NodeList nodes = (NodeList) xPath.evaluate(baseXMLCE + "/Clave", xml.getDocumentElement(), XPathConstants.NODESET);
            comprobanteElectronico.setClave(nodes.item(0).getTextContent());
            nodes = (NodeList) xPath.evaluate(baseXMLCE + "/FechaEmision", xml.getDocumentElement(), XPathConstants.NODESET);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            comprobanteElectronico.setFecha(format.format(new Date()));

            ObligadoTributario receptor = new ObligadoTributario();
            ObligadoTributario emisor = new ObligadoTributario();
            nodes = (NodeList) xPath.evaluate(baseXMLCE + "/Emisor/Identificacion/Tipo", xml.getDocumentElement(), XPathConstants.NODESET);
            emisor.setTipoIdentificacion(nodes.item(0).getTextContent());
            nodes = (NodeList) xPath.evaluate(baseXMLCE + "/Emisor/Identificacion/Numero", xml.getDocumentElement(), XPathConstants.NODESET);
            emisor.setNumeroIdentificacion(nodes.item(0).getTextContent());


            if (!baseXMLCE.equals(BillHelper.FACTURA_ELECTRONICA_EXPORTACION_BASE_XML) &&
                    baseXMLCE.equals(BillHelper.TIQUETE_ELECTRONICO_BASE_XML)) {
                nodes = (NodeList) xPath.evaluate(baseXMLCE + "/Receptor/Identificacion/Tipo", xml.getDocumentElement(), XPathConstants.NODESET);
                receptor.setTipoIdentificacion(nodes.item(0).getTextContent());
                nodes = (NodeList) xPath.evaluate(baseXMLCE + "/Receptor/Identificacion/Numero", xml.getDocumentElement(), XPathConstants.NODESET);
                receptor.setNumeroIdentificacion(nodes.item(0).getTextContent());
                comprobanteElectronico.setReceptor(receptor);
            }


            comprobanteElectronico.setEmisor(emisor);
            httpClient = getHttpClient();
            HttpPost request = new HttpPost(billConfigService.getApiUri() + "recepcion");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(comprobanteElectronico);
            StringEntity params = new StringEntity(json);
            request.addHeader("content-type", "application/javascript");
            request.addHeader("Authorization", "bearer " + this.accessToken);
            request.setEntity(params);
            org.apache.http.HttpResponse response = httpClient.execute(request);
            Thread.sleep(1000);// WAITING FOR RESPONSE
            org.apache.http.HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            //System.out.println(responseString);

            if (response.getStatusLine().getStatusCode()==200 || response.getStatusLine().getStatusCode()==202) {
                result.setResult(1);
                result.setResultStr("Se envio Exitosamente  " + comprobanteElectronico.getClave() + ": " + responseString );
                Header[] hs = response.getAllHeaders();
                String location = null;
                location = getHeaderValue(hs, "Location");
                if (location!=null) {
                    result.setLocation(location);
                }

            } else if (response.getStatusLine().getStatusCode()>=400 && response.getStatusLine().getStatusCode()<500) {
                //result = 0;
                result.setResult(0);
                //String xErrorCause = response.getHeaderString("X-Error-Cause");
                Header[] hs = response.getAllHeaders();
                String location = null;
                String xErrorCause = getHeaderValue(hs, "X-Error-Cause");
//                System.out.println(xErrorCause);
                result.setResultStr("Se produjo un error de envio  " + comprobanteElectronico.getClave() + ": " + xErrorCause );

            } else {
                result.setResult(-3);
                result.setResultStr("No se obtuvo respuesta de Hacienda al enviar  " + comprobanteElectronico.getClave() + ": " + responseString );
                //result.setResultStr("Se produjo un error al enviar  " + comprobanteElectronico.getClave() + ":" + responseString);

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.ALL, e.getMessage());
            this.messageError = e.getMessage();
            result.setResult(-2);
            result.setResultStr( e.getMessage());
        } finally {

            if (httpClient!=null) {
                httpClient.getConnectionManager().shutdown();
            }
        }
        return result;
    }

    private String getHeaderValue(Header[] hs, String header) {
        String h = "";
        for (int i=0;i < hs.length ;i++) {
            if (hs[i].getName().toLowerCase().equals(header.toLowerCase())) {
                h = new String(((BufferedHeader) hs[i]).getBuffer().buffer());
                Integer idx = h.indexOf(header + ": ");
                if (idx==0) {
//                    idx = idx + "Location: ".length();
                    idx = idx + (header + ": ").length();
                }
                h = h.substring(idx);

                break;
            }
        }
        return h;
    }

    public Integer sendBill(OptionSet options, FacturaElectronica factura) {
        Recepcion recepcion = new Recepcion();

        recepcion.setClave(factura.getClave());
        recepcion.setEmisor(factura.getEmisor1());
        recepcion.setReceptor(factura.getReceptor1());
        recepcion.setFecha(factura.getFechaEmisionStr());
        recepcion.setComprobanteXml(getSignedXMLEncode(options));

        return this.sendBill(recepcion);
    }

    public Integer sendBill(Recepcion recepcion) {
        Integer result = 0;
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(billConfigService.getApiUri() + "recepcion");
        Invocation.Builder request = target.request();

        // Se deber?? brindar una cabecera (header) "Authorization" con el valor del access token obtenido anteriormente.
        request.header("Authorization", "Bearer " + this.accessToken);

        // Se env??a un POST. con los datos del documento que deseamos registrar, observe que colocamos como
        // atributo el objeto que configuramos en el apartado de 'Preparaci??n'
        Response response = request.post(Entity.json(recepcion));

        switch (response.getStatus()) {
            case 202:
                result = 1;
                break;
            case 400:

                String xErrorCause = response.getHeaderString("X-Error-Cause");
                //System.out.println(xErrorCause);
                break;
        }
        return result;
    }

    public void desconectar() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(billConfigService.getIdpUri() + "/logout");

        Response response = target.request().header("Authorization", "Bearer " + accessToken)
                .post(Entity.form(new Form("refresh_token", this.refreshToken)));
    }

    public String getSignedXMLEncode(OptionSet options) {
        StringBuilder sb = new StringBuilder();
        try {
            //BufferedReader br = new BufferedReader(new FileReader("/home/alvaro//development/projects/facturaDigital/demo/gs-maven/src/main/java/com/neodigitallogic/result.xml"));
            BufferedReader br = new BufferedReader(new FileReader(options.valueOf(BillConfigService.OUTPUT_FILE)));

            String sCurrentLine = null;
            sCurrentLine = br.readLine();
            while (sCurrentLine != null) {
                sb.append(sCurrentLine);
                sCurrentLine = br.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       // Base64 base64 = new Base64();
        //String encodedString = new String(base64.encode(sb.toString().getBytes()));
        String encodedString = new String(Base64.getEncoder().encode(sb.toString().getBytes()));
        return encodedString;

    }


    public Integer validate(FacturaElectronica fe) {
        Integer result = 0;
        Client client = ClientBuilder.newClient();
        // En ??ste caso, clave corresponde a la clave del documento a validar
        return validateDE(fe.getClave(), result, client);
    }

    public Integer validate(MensajeReceptor fe) {
        Integer result = 0;
        Client client = ClientBuilder.newClient();
        // En ??ste caso, clave corresponde a la clave del documento a validar
        return validateDE(fe.getClave(), result, client);
    }

    public Integer validate(NotaCreditoElectronica fe) {
        Integer result = 0;
        Client client = ClientBuilder.newClient();
        // En ??ste caso, clave corresponde a la clave del documento a validar
        return validateDE(fe.getClave(), result, client);
    }

    private Integer validateDE(String clave, Integer result, Client client) {
        WebTarget target = client.target(billConfigService.getApiUri() + "recepcion/" + clave);
        Invocation.Builder request = target.request();

        // Se debe brindar un header "Authorization" con el valor del access token obtenido anteriormente.
        request.header("Authorization", "Bearer " + this.accessToken);

        // Se env??a un GET. para tomar el estado
        Response response = request.get();

        switch (response.getStatus()) {
            case 200:
                result = 1;
//                System.out.println("BIEN VALIDADA");
                break;
            case 404:
                // Se presenta si no se localiza la clave brindada
//                System.out.println("La clave no esta registrada");
                break;
        }
        return result;
    }

    public Integer sendElectronicDocument(OptionSet options, FacturaElectronica factura) {
        Recepcion recepcion = new Recepcion();

        recepcion.setClave(factura.getClave());
        recepcion.setEmisor(factura.getEmisor1());
        recepcion.setReceptor(factura.getReceptor1());
        recepcion.setFecha(factura.getFechaEmisionStr());
        recepcion.setComprobanteXml(getSignedXMLEncode(options));


        return this.sendElectronicDocument(recepcion);
    }

    public Integer sendElectronicDocument(Recepcion recepcion) {

        Integer result = 0;
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(billConfigService.getApiUri() + "recepcion");
        Invocation.Builder request = target.request();

        // Se deber?? brindar una cabecera (header) "Authorization" con el valor del access token obtenido anteriormente.
        request.header("Authorization", "Bearer " + this.accessToken);

        // Se env??a un POST. con los datos del documento que deseamos registrar, observe que colocamos como
        // atributo el objeto que configuramos en el apartado de 'Preparaci??n'
        Response response = request.post(Entity.json(recepcion));

        switch (response.getStatus()) {
            case 202:
                result = 1;
                break;
            case 400:

                String xErrorCause = response.getHeaderString("X-Error-Cause");
//                System.out.println(xErrorCause);
                break;
        }
        return result;
    }

    public ResultFe sendElectronicDocument(MensajeReceptor mensajeReceptor, OptionSet options) throws JAXBException {
        ResultFe result = new ResultFe();
        Recepcion recepcion = new Recepcion();
        recepcion.setConsecutivoReceptor(mensajeReceptor.getNumeroConsecutivoReceptor());
        recepcion.setEmisor(mensajeReceptor.getEmisor());
        recepcion.setReceptor(mensajeReceptor.getReceptor());
        recepcion.setClave(mensajeReceptor.getClave());

        recepcion.setFecha(mensajeReceptor.getFechaEmisionDoc().toString());
        recepcion.setComprobanteXml(getSignedXMLEncode(options));

        //Integer result = 0;
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(billConfigService.getApiUri() + "recepcion");
        Invocation.Builder request = target.request();

        // Se deber?? brindar una cabecera (header) "Authorization" con el valor del access token obtenido anteriormente.
        request.header("Authorization", "Bearer " + this.accessToken);

        // Se env??a un POST. con los datos del documento que deseamos registrar, observe que colocamos como
        // atributo el objeto que configuramos en el apartado de 'Preparaci??n'
        Response response = request.post(Entity.json(recepcion));

        switch (response.getStatus()) {
            case 202:
                result.setResult(1);
                result.setResultStr("Comprobante enviado correctamente");
                break;
            case 400:
                result.setResult(0);

                String xErrorCause = response.getHeaderString("X-Error-Cause");
//                System.out.println(xErrorCause);
                result.setResultStr(xErrorCause);
                break;
        }
        return result;
    }

    public ResultFe sendElectronicDocument(NotaCreditoElectronica nce, OptionSet options) throws JAXBException {
        ResultFe result = new ResultFe();
        Recepcion recepcion = new Recepcion();
//        recepcion.setEmisor(nce.getEmisor1());
//        recepcion.setReceptor(nce.getReceptor1());
        recepcion.setClave(nce.getClave());

        recepcion.setFecha(nce.getFechaEmision().toString());
        recepcion.setComprobanteXml(getSignedXMLEncode(options));

        //Integer result = 0;
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(billConfigService.getApiUri() + "recepcion");
        Invocation.Builder request = target.request();

        // Se deber?? brindar una cabecera (header) "Authorization" con el valor del access token obtenido anteriormente.
        request.header("Authorization", "Bearer " + this.accessToken);

        // Se env??a un POST. con los datos del documento que deseamos registrar, observe que colocamos como
        // atributo el objeto que configuramos en el apartado de 'Preparaci??n'
        Response response = request.post(Entity.json(recepcion));

        switch (response.getStatus()) {
            case 202:
                result.setResult(1);
                result.setResultStr("Comprobante enviado correctamente");
                break;
            case 400:
                result.setResult(0);

                String xErrorCause = response.getHeaderString("X-Error-Cause");
//                System.out.println(xErrorCause);
                result.setResultStr(xErrorCause);
                break;
        }
        return result;
    }

    public ResultFe getComprobante(String clave) throws JAXBException {
        ResultFe result = new ResultFe();
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(billConfigService.getApiUri() + "recepcion" + "/" + clave);
            Invocation.Builder request = target.request(MediaType.APPLICATION_JSON);

            // Se deber?? brindar una cabecera (header) "Authorization" con el valor del access token obtenido anteriormente.
            request.header("Authorization", "Bearer " + this.accessToken);

            Response response = request.get();
            // Response response = target.request(MediaType.APPLICATION_JSON).get(String.class);
            // Base64 base64 = new Base64();
            String encodedString;
            if (response.getStatus() == 200 || response.getStatus() == 202) {


                String responseStr = response.readEntity(String.class);
                JSONObject jsonObj = new JSONObject(responseStr);
                String estado = (String) jsonObj.get("ind-estado");
                if (!esEstadoAceptadoRechazado(estado)) {
                    result.setResult(-1);
                    //result.set
//                    System.out.println(" RESPUESTA NO ES ACEPTADO NI RECHAZADO " + estado);

                    result.setResultStr("PENDIENTE_RESPUESTA DE HACIENDA " + estado);

                    return result;
                }
                else {
                    result.setEstado(estado);
                }
//                System.out.println(" ESTADO = " + estado);
                String respuestaNoCodificada = "";
                try {
                    respuestaNoCodificada = (String) jsonObj.get("respuesta-xml");
                } catch (Exception e) {
//                    System.out.println(e.getMessage());
                    result.setResult(-1);
                    result.setResultStr("EXCEPTION: " + e.getMessage());
                    return result;
                }
                if (respuestaNoCodificada == null || respuestaNoCodificada.equals("")) {
                    result.setResult(-1);
                    result.setResultStr("RESPUESTA VACIA ESTADO= " + estado);
                    return result;
                }
                // base64 = new Base64();
                encodedString = new String(Base64.getDecoder().decode(respuestaNoCodificada.getBytes()));
                //            encodedString = new String(base64.decode(respuestaNoCodificada.getBytes()));
                logger.info(" RESPUESTA DE HACIENDA " + estado + " " + encodedString);

                MensajeHacienda m = facturaElectronicaUnmarshaller.createMensajeHacienda(encodedString);
                String resultadoFile = billConfigService.getCompletePath() + BillHelper.PREFIX_MENSAJE_HACIENDA_FILE + clave + ".xml";

                facturaElectronicaMarshaller.writeXMLFile(m, resultadoFile);
                result.setResultStr(" MENSAJE HACIENDA: " + m.getDetalleMensaje());
                result.setResult(m.getMensaje().intValue());
//                System.out.println("ESTADO=" + estado + " respuesta " + encodedString);
            } else if (response.getStatus() == 400) {
                String xErrorCause = response.getHeaderString("X-Error-Cause");
//                System.out.println(xErrorCause);
                if (xErrorCause!=null) {
                    result.setResultStr(xErrorCause);
                    result.setResult(-2);
                }
                if (response.getStatus()==400) {
                    result.setResult(-99);
                    if (result.getEstado()==null || result.getEstado().equals("")) {
                        result.setEstado("no_recibido");
                    }
                }

            } else if (response.getStatus() >= 401 && response.getStatus() <= 499) {
                String xErrorCause = response.getHeaderString("X-Error-Cause");
//                System.out.println(xErrorCause);
                if (xErrorCause!=null) {
                    result.setResultStr(xErrorCause);
                    result.setResult(-2);
                }
                if (response.getStatus()==400) {
                    result.setResult(-99);
                    if (result.getEstado()==null || result.getEstado().equals("")) {
                        result.setEstado("no_recibido");
                    }
                }



            } else {
                result.setResultStr(" RETORNO NO ESPERADO " + response.getStatus() != null ? String.valueOf(response.getStatus()) : "");
                result.setResult(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultStr(" ERROR NO ESPERADO " + e != null && e.getMessage()!=null ? String.valueOf(e.getMessage()) : "Hubo un error obteniendo la respuesta desde hacienda");
            result.setResult(-1);

        }
        return result;
    }

    public ResultFe getLocateResponse(String locate, String clave) throws JAXBException, JSONException {
        ResultFe result = new ResultFe();
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(locate);
        Invocation.Builder request = target.request(MediaType.APPLICATION_JSON);

        // Se deber?? brindar una cabecera (header) "Authorization" con el valor del access token obtenido anteriormente.
        request.header("Authorization", "Bearer " + this.accessToken);

        Response response = request.get();
        // Response response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        //Base64 base64 = new Base64();
        String encodedString;
        if (response.getStatus()==200 || response.getStatus()==202) {


            String responseStr = response.readEntity(String.class);
            JSONObject jsonObj = new JSONObject(responseStr);
            String estado = (String) jsonObj.get("ind-estado");
            if (!esEstadoAceptadoRechazado(estado)) {
                result.setResult(-1);
                result.setResultStr("PENDIENTE RESPUESTA DE HACIENDA ");
                result.setEstado(BillHelper.RESPUESTA_NO_ACEPTADO_NO_RECHAZADO);
                return result;
            }
//            System.out.println(" ESTADO = " + estado);
            String respuestaNoCodificada = "";
            result.setEstado(estado);
            try {
                respuestaNoCodificada = (String) jsonObj.get("respuesta-xml");

                if (respuestaNoCodificada == null || respuestaNoCodificada.equals("")) {
                    result.setResult(-1);
                    result.setResultStr("RESPUESTA VACIA ESTADO= " + estado);

                    return result;
                }
                // = new Base64();
                encodedString = new String(Base64.getDecoder().decode(respuestaNoCodificada.getBytes()));
                logger.info("getLocateResponse RESPUESTA DE HACIENDA " + estado + " " + encodedString);
                //System.out.println(encodedString);
                MensajeHacienda m = facturaElectronicaUnmarshaller.createMensajeHacienda(encodedString);
                String resultadoFile = billConfigService.getCompletePath() + BillHelper.PREFIX_MENSAJE_HACIENDA_FILE + clave + ".xml";
                facturaElectronicaMarshaller.writeXMLFile(m, resultadoFile);
                result.setResultStr("getComprobante ESTADO: " + estado + " MENSAJE " + m.getMensaje() + " | " + m.getDetalleMensaje());
                result.setMensaje(m.getDetalleMensaje());
                result.setResult(m.getMensaje().intValue());
                // System.out.println("ESTADO=" + estado + " respuestad " + encodedString);
            } catch (Exception e) {
//                System.out.println(e.getMessage());
                result.setResult(-1);
                result.setResultStr("getComprobante EXCEPTION: " + e.getMessage());

                return result;
            }
            //System.out.println(response);
            //result = 1;
        } else if (response.getStatus()>=400 && response.getStatus()<=499) {
            String xErrorCause = response.getHeaderString("X-Error-Cause");
//            System.out.println(xErrorCause);
            result.setResult(-1);
            result.setResultStr(xErrorCause);

        } else {
            result.setResultStr(" RETORNO NO ESPERADO " + response.getStatus()!=null?String.valueOf(response.getStatus()):"");
            result.setResult(-1);
        }
        return result;
    }

    private boolean esEstadoAceptadoRechazado(String estado) {
        if (estado!=null && (estado.equals("aceptado") || estado.equals("rechazado"))) {
            return true;
        }
        return false;
    }

    public String getMessageError() {
        return messageError;
    }

    public HttpClient getHttpClient() {
        int timeout = 5*60; // seconds (5 minutes)
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();
        HttpClient httpClient =
                HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        return httpClient;
    }
}
