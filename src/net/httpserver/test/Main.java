package net.httpserver.test;

import net.httpserver.HttpServer;
import net.httpserver.io.HttpRequest;
import net.httpserver.io.HttpResponse;
import net.httpserver.listener.HttpRequestListener;
import net.httpserver.util.StatusCode;

public class Main implements HttpRequestListener {

	public static void main(String[] args) {
		HttpServer server = new HttpServer();

		server.addHttpRequestListener(new Main());

		server.listen();
	}

	@Override
	public boolean onHttpRequest(HttpRequest req, HttpResponse res) {
		try{
			if(req.query.containsKey("test")){
				res.statusCode = StatusCode.OK;
				res.setHeader("Content-Type", "text/html");
				res.setHeader("Transfer-Encoding", "chunked");
				res.body = "";
				res.write(res.toString());
				res.flush();
				
				while(true){
					String content = "<h1>" + req.getQuery("test") + "</h1>";
					res.write(Integer.toString(content.length(), 16) + "\r\n");
					res.write(content + "\r\n");
					if(!res.flush())
						break;
					Thread.sleep(2000);
				}
				
				System.out.println("Closed.");
			
				return true;
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
}
