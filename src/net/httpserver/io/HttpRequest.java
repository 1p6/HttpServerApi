package net.httpserver.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class HttpRequest {

	public String path = null;
	public String method = null;
	public HashMap<String, String> headers = new HashMap<>();
	
	public String getHeader(String key){
		return headers.get(key);
	}
	
	public String toString(){
		String str = method + " " + path + "HTTP/1.1\r\n";
		
		for(Entry<String, String> e : headers.entrySet()){
			str += e.getKey() + ": " + e.getValue() + "\r\n";
		}
		
		str += "\r\n";
		
		return str;
	}
	
	public HttpRequest(BufferedReader in) throws IOException {
		boolean first = true;
		while(true){
			String line = in.readLine();
			if(line == null || line.isEmpty())
				break;
			if(first){
				first = false;
				String[] args = line.split(" ");
				method = args[0];
				path = args[1];
			} else {
				String[] pair = line.split(": ");
				headers.put(pair[0], pair[1]);
			}
		}
	}
}
