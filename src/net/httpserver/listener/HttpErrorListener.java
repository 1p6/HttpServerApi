package net.httpserver.listener;

public interface HttpErrorListener {

	public boolean onHttpError(Exception e);
}
