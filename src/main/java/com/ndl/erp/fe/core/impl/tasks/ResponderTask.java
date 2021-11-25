package com.ndl.erp.fe.core.impl.tasks;

import com.ndl.erp.fe.core.BillConfigurationService;
import com.ndl.erp.fe.core.BillErrorManager;
import com.ndl.erp.fe.core.BillTask;
import com.ndl.erp.fe.core.impl.BillMarshaller;
import com.ndl.erp.fe.core.impl.BillUnmarshaller;
import com.ndl.erp.fe.core.impl.GlobalManager;
import com.ndl.erp.fe.core.impl.ResultBase;
import com.ndl.erp.fe.core.impl.tasks.util.ResponderWrapper;
import com.ndl.erp.fe.MensajeHacienda;
import com.ndl.erp.fe.helpers.BillHelper;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

@Component

public class ResponderTask extends BillTask<ResponderWrapper, ResultBase> {

    @Autowired
    private BillErrorManager billErrorManager;

    @Autowired
    private BillConfigurationService billConfigurationService;

    @Autowired
    private BillMarshaller facturaElectronicaMarshaller;

    @Autowired
    private BillUnmarshaller facturaElectronicaUnmarshaller;

    ResponderTask() {
    }

    ResponderTask(BillErrorManager bm) {

    }

    @Autowired
    private GlobalManager globalManager;


    @Override
    public ResultBase executeTask(ResponderWrapper params) {
        this.processId = params.getProcessId();

        ResultBase result = new ResultBase();
        ResultBase vr = new ResultBase();
        Integer trying = 0;
        Boolean validada = false;
        try {
            while (trying < 5 && !validada) {
                try {
                    Thread.sleep(3000);
                    vr = getComprobante(params.getClave(), params.getAccessToken());
                    if (vr == null || vr.getResult() == null) {
                        validada = false;
                    } else {
                        validada = vr.getResult();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    vr = new ResultBase();
                    vr.setResult(false);
                    vr.setMessage("EXCEPTION " + e.getMessage());
                    manageError("Excepcion durante el envio: " + e.getMessage());
                }

                trying++;
            }

        }  catch (Exception e) {
            e.printStackTrace();
            vr.setResult(false);
            manageError("Excepcion durante el envio: " + e.getMessage());
        } finally {
            this.saveTareaEjecutada(vr);
        }
//        System.out.println(" RESPUESTA de get comprobante " + validada);
        return vr;


    }

    public ResultBase getComprobante(String clave, String accessToken) throws JAXBException {
        ResultBase result = new ResultBase();
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(billConfigurationService.getApiUri() + "recepcion" + "/" + clave);
        Invocation.Builder request = target.request(MediaType.APPLICATION_JSON);

        // Se deberá brindar una cabecera (header) "Authorization" con el valor del access token obtenido anteriormente.
        request.header("Authorization", "Bearer " + accessToken);

        Response response = request.get();
        // Response response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        Base64 base64 = new Base64();
        String encodedString;
        if (response.getStatus()==200 || response.getStatus()==202) {


            String responseStr = response.readEntity(String.class);
            JSONObject jsonObj = new JSONObject(responseStr);
            String estado = (String) jsonObj.get("ind-estado");
            if (!esEstadoAceptadoRechazado(estado)) {
                result.setResult(false);
                result.setMessage("Pendiente de obtener mensaje de respuesta de hacienda ");
                manageError("Pendiente de obtener mensaje de respuesta de hacienda " + estado);
                return result;
            }
//            System.out.println(" ESTADO = " + estado);
            String respuestaNoCodificada = "";
            try {
                respuestaNoCodificada = (String) jsonObj.get("respuesta-xml");
            } catch (Exception e) {
//                System.out.println(e.getMessage());
                result.setResult(false);
                result.setMessage("getComprobante EXCEPTION: " + e.getMessage());
                manageError("getComprobante EXCEPTION: " + e.getMessage());
                return result;
            }
            if (respuestaNoCodificada==null || respuestaNoCodificada.equals("")) {
                result.setResult(false);
                result.setMessage("RESPUESTA VACIA ESTADO= " + estado);
                manageError("RESPUESTA VACIA ESTADO= " + estado);

                return result;
            }
            base64 = new Base64();
            encodedString = new String(base64.decode(respuestaNoCodificada.getBytes()));
//            System.out.println(encodedString);
            MensajeHacienda m = facturaElectronicaUnmarshaller.createMensajeHacienda(encodedString);
            String resultadoFile = billConfigurationService.getCompletePath() + BillHelper.PREFIX_MENSAJE_HACIENDA_FILE + clave + ".xml";
            facturaElectronicaMarshaller.writeXMLFile(m, resultadoFile);
            result.setMessage("getComprobante ESTADO: " + estado + " MENSAJE " + m.getMensaje());
            //result.setResult();
//            System.out.println("ESTADO=" + estado + " respuestad " + encodedString);
            //System.out.println(response);
            //result = 1;
        } else if (response.getStatus()>=400 && response.getStatus()<=499) {
            String xErrorCause = response.getHeaderString("X-Error-Cause");
//            System.out.println(xErrorCause);

        } else {
            result.setMessage(" RETORNO NO ESPERADO " + response.getStatus()!=null?String.valueOf(response.getStatus()):"");
            result.setResult(false);
        }
        return result;
    }

    private boolean esEstadoAceptadoRechazado(String estado) {
        if (estado!=null && (estado.equals("aceptado") || estado.equals("rechazado"))) {
            return true;
        }
        return false;
    }

    @Override
    public String getTaskCode() {
        return "RESPONDER";
    }

}
