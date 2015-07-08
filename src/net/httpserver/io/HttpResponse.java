package net.httpserver.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

import net.httpserver.util.StatusCode;

public class HttpResponse {

	public StatusCode statusCode;
	public String body;
	public HashMap<String, String> headers = new HashMap<>();
	public boolean needsWrite = true;
	public final BufferedWriter out;
	public final Socket socket;

	public void end(String body){
		this.body = body;
		headers.put("Content-Length", body.length() + "");
		headers.put("Content-Type", "text/html");
		statusCode = StatusCode.OK;
	}
	
	public void write(String text) throws IOException{
		out.write(text);
	}
	
	public boolean flush() throws IOException{
		try{
			out.flush();
			needsWrite = false;
		} catch(SocketException e){
			return false;
		}
		return true;
	}
	
	public void setHeader(String key, String value){
		headers.put(key, value);
	}
	
	public HttpResponse(BufferedWriter out, Socket s) {
		this.out = out;
		socket = s;
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
