package net.httpserver.io;

import java.util.HashMap;

import net.httpserver.util.StatusCode;

public class HttpResponse {

	public StatusCode statusCode;
	public String body;
	public HashMap<String, String> headers = new HashMap<>();

	public void end(String body){
		this.body = body;
		headers.put("Content-Length", body.length() + "");
		headers.put("Content-Type", "text/html");
		statusCode = StatusCode.OK;
	}
	
	@Override
	public String toString() {
		String out = "HTTP/1.1 " + statusCode.code() + statusCode.toString() + "\r\n";
		
		for(String key : headers.keySet()){
			out += key + ": " + headers.get(key) + "\r\n";
		}
		out += "\r\n";
		out += body;
		
		return out;
	}
}
