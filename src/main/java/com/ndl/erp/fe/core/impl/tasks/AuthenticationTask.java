package com.ndl.erp.fe.core.impl.tasks;

import com.ndl.erp.fe.core.BillErrorManager;
import com.ndl.erp.fe.core.BillTask;

import com.ndl.erp.domain.ErrorProcess;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class AuthenticationTask extends BillTask<Integer, AuthResult> {

    public AuthenticationTask() {}

    public AuthenticationTask(BillErrorManager e) {
        this.em = e;
    }

    @Override
    public AuthResult executeTask(Integer processId) {
        AuthResult result = new AuthResult();
        this.processId = processId;

        try {

            //Variable para obtener la respuesta.
            JSONObject resp = null;

            //URL
            URL url = new URL(billConfigurationService.getIdpUri() + "/token");

            //Parametros
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("grant_type", "password");
            params.put("username", billConfigurationService.getUsuario());
            params.put("password", billConfigurationService.getPassword());
            params.put("client_id", billConfigurationService.getIdpClientId());


            //Parse parameter to StringBuilder
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }


            //Abro la conexion del url
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Se indica que va a obtener una respuesta y otros parametros
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //Se hace la consulta al servidor.
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(postData.toString());
            writer.flush();
            writer.close();



            //Lectura de la respuesta, se leen las lineas retornadas por el servicio.
            int respCode = conn.getResponseCode(); // New items get NOT_FOUND on PUT
            if (respCode == HttpURLConnection.HTTP_OK || respCode == HttpURLConnection.HTTP_NOT_FOUND) {
                StringBuffer response = new StringBuffer();
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                resp = new JSONObject(response.toString());
                reader.close();
            }

            //Se retorna lo que es necesario.
            if( resp !=null ){
                result.setAccessToken((String) resp.get("access_token"));
                result.setRefreshToken((String) resp.get("refresh_token"));
            }

            conn.disconnect();

        } catch (RuntimeException e) {
            ErrorProcess errorProcess = manageError(e.getMessage());
            result.setMessage(e.getMessage());
            result.setResult(false);

        } catch (Exception e) {
            ErrorProcess errorProcess = manageError(e.getMessage());
            result.setMessage(e.getMessage());
            result.setResult(false);

        } finally {
            this.saveTareaEjecutada(result);
        }
        return result;

    }


/*
    public AuthResult executeTask(Integer processId) {
        AuthResult result = new AuthResult();
        this.processId = processId;
//        if (!state) {
//            manageError("There is an error in the process");
//            result.setMessage("There is an error in the process");
//            result.setResult(false);
//        }

        try {

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(billConfigurationService.getIdpUri() + "/token");
            Form form = new Form();
            form.param("grant_type", "password")
                    .param("username", billConfigurationService.getUsuario())
                    .param("password", billConfigurationService.getPassword())
                    .param("client_id", billConfigurationService.getIdpClientId());

            Response response = target.request().post(Entity.form(form));


            String accessTokenStr = response.readEntity(String.class);

            JSONObject jsonObj = new JSONObject(accessTokenStr);

            result.setAccessToken((String) jsonObj.get("access_token"));

            result.setRefreshToken((String) jsonObj.get("refresh_token"));

        } catch (RuntimeException e) {
            ErrorProcess errorProcess = manageError(e.getMessage());
            result.setMessage(e.getMessage());
            result.setResult(false);

        } catch (Exception e) {
            ErrorProcess errorProcess = manageError(e.getMessage());
            result.setMessage(e.getMessage());
            result.setResult(false);

        } finally {
            this.saveTareaEjecutada(result);
        }
        return result;

    }
*/

    @Override
    public String getTaskCode() {
        return "AUTHENTICATOR";
    }

}
