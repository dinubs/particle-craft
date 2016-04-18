package com.example.examplemod;

import org.json.JSONObject;

import com.goebl.david.Webb;

public class ParticleAPI implements Runnable {
	String apiUrlPrefix = "https://api.particle.io/v1/devices";
	String token = "2518a87b92b142c4fb9e22fef9c102d4b8505ed8";
	String fn = "";
	String arg = "";
	
	protected ParticleAPI() {}
	
	protected ParticleAPI(String fn, String arg) {
		this.fn = fn;
		this.arg = arg;
	}
	
	public void setDigitalPin(String pinToSet, String value) {
		(new Thread(new ParticleAPI("digitalPin", "set," + pinToSet + "," + value))).start();
	}
	
	public void run() {
    	Webb webb = Webb.create();
    	JSONObject result = webb
    	        .post(this.buildUrl(this.fn))
    	        .param("arg", this.arg)
    	        .param("access_token", this.token)
    	        .ensureSuccess()
    	        .asJsonObject()
    	        .getBody();
    }
	
	private String buildUrl(String fn) {
		return this.apiUrlPrefix + "/34002a000f47343432313031/" + fn;
	}
}
