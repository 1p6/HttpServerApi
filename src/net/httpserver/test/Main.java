package net.httpserver.test;

import net.httpserver.HttpServer;
import net.httpserver.io.HttpRequest;
import net.httpserver.io.HttpResponse;
import net.httpserver.listener.HttpRequestListener;

public class Main implements HttpRequestListener {

	public static void main(String[] args){
		HttpServer server = new HttpServer();
		
		server.addHttpRequestListener(new Main());
		
		server.listen();
	}

	@Override
	public boolean onHttpRequest(HttpRequest req, HttpResponse res) {
		if(req.query.containsKey("test")){
			res.end("<h1>" + req.getQuery("test") + "</h1>");
			return true;
		}
		
		return false;
	}
}
