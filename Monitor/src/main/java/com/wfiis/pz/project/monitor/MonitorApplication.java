package com.wfiis.pz.project.monitor;

import java.io.IOException;

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

@SpringBootApplication(scanBasePackages="com.wfiis")
public class MonitorApplication {
	
	@Autowired
	private Environment env;
	
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		
		JSONObject json = new JSONObject();
		try {
			json.put("monitor-id", env.getProperty("MONITORID"));
			json.put("api-endpoint", env.getProperty("API_ENDPOINT")+env.getProperty("MONITORID")+"/");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}    

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
		    HttpPost request = new HttpPost(env.getProperty("API_GATEWAY_URL"));
		    StringEntity params = new StringEntity(json.toString());
			request.addHeader("content-type", "application/json");
			request.addHeader("access-token", "1234");
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
