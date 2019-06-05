package com.wfiis.pz.project.monitor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

/**
 * 
 * @author Mateusz Papież
 *
 */
@SpringBootApplication(scanBasePackages="com.wfiis")
public class MonitorApplication {
	
	@Autowired
	private Environment env;
	
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		
		JSONObject reply = new JSONObject();
		
		try {

			String httpurl = env.getProperty("AUTH_SERVICE_URL")+"login";

			URL url = new URL(httpurl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			
			JSONObject body = new JSONObject();
			body.put("username",env.getProperty("username"));
			body.put("password",env.getProperty("pass"));
			
			

			OutputStream os = con.getOutputStream();
			BufferedWriter writer = new BufferedWriter(
			        new OutputStreamWriter(os, "UTF-8"));
			writer.write(body.toString());
			writer.flush();
			writer.close();
			os.close();
			
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
				  sb.append(output);
				}
				
				
				reply = new JSONObject(sb.toString());
				//return sb.toString();
			}else {
				System.out.println("Brak odpowiedzi na url /login");
			}

		} catch (Exception e) {
			System.out.println("Błąd wysyłu danych na url /login");
			e.printStackTrace();
		}
		
		JSONObject json = new JSONObject();
		try {
			json.put("monitor-id", env.getProperty("MONITORID"));
			json.put("api-endpoint", env.getProperty("API_ENDPOINT")+env.getProperty("API_VERSION"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}    

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
		    HttpPost request = new HttpPost(env.getProperty("API_GATEWAY_URL"));
		    StringEntity params = new StringEntity(json.toString());
			request.addHeader("content-type", "application/json");
			request.addHeader("access-token", reply.getString("access-token"));
		    request.setEntity(params);
		    httpClient.execute(request);
		// handle response here...
		} catch (Exception ex) {
		    // handle exception here
		} finally {
		    try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MonitorApplication.class);
		application.run( args);
	}

}
