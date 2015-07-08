package net.httpserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import net.httpserver.io.HttpRequest;
import net.httpserver.io.HttpResponse;
import net.httpserver.listener.HttpErrorListener;
import net.httpserver.listener.HttpRequestListener;
import net.httpserver.util.StatusCode;

public class HttpServer {

	private volatile ArrayList<HttpRequestListener> reqListeners = new ArrayList<>();
	private volatile ArrayList<HttpErrorListener> errListeners = new ArrayList<>();

	public void addHttpRequestListener(HttpRequestListener listener) {
		reqListeners.add(listener);
	}

	public void addHttpErrorListener(HttpErrorListener listener) {
		errListeners.add(listener);
	}

	public void listen() {
		listen(80);
	}

	public void listen(int port) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			while (true) {
				Socket socket = server.accept();

				SocketThread t = new SocketThread(socket);
				t.start();
			}
		} catch (Exception e) {
			error(e);
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (Exception e) {
					error(e);
				}
			}
		}
	}

	private void error(Exception e) {
		boolean handeled = false;
		
		for(HttpErrorListener listener : errListeners){
			handeled = listener.onHttpError(e);
			if(handeled)
				break;
		}
		if(!handeled){
			e.printStackTrace();
		}
	}

	private class SocketThread extends Thread {

		private volatile Socket socket;

		public SocketThread(Socket s) {
			socket = s;
		}

		@Override
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream()));
				
				HttpRequest req = new HttpRequest(in);
				HttpResponse res = new HttpResponse();
				
				if(req.path == null)
					throw new NullPointerException();
				
				boolean handeled = false;
				for(HttpRequestListener listener : reqListeners){
					handeled = listener.onHttpRequest(req, res);
					if(handeled)
						break;
				}
				if(!handeled){
					res.end("<h1>404: Not Found</h1>");
					res.statusCode = StatusCode.Not_Found;
				}
				
				out.write(res.toString());
				out.flush();
			} catch (Exception e) {
				error(e);
			} finally {
				try {
					socket.close();
				} catch (Exception e) {
					error(e);
				}
			}
		}
	}
}
