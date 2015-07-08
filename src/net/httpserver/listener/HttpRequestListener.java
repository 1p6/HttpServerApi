package net.httpserver.listener;

import net.httpserver.io.HttpRequest;
import net.httpserver.io.HttpResponse;

public interface HttpRequestListener {

	public boolean onHttpRequest(HttpRequest req, HttpResponse res);
}
