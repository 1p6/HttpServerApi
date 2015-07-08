package net.httpserver.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import net.httpserver.io.HttpRequest;
import net.httpserver.io.HttpResponse;

public class FileServer implements HttpRequestListener {

	private volatile File home;
	
	public FileServer(String path) {
		this(new File(path));
	}
	
	public FileServer(File home) throws IllegalArgumentException {
		if(!home.exists() || !home.isDirectory())
			throw new IllegalArgumentException();
		
		this.home = home.getAbsoluteFile();
	}
	
	@Override
	public boolean onHttpRequest(HttpRequest req, HttpResponse res) {
		File f = new File(home.getAbsolutePath(), req.path.substring(1));
		
		if(f.isDirectory())
			f = new File(f, "index.html");
		if(!f.exists()){
			f = new File(home.getAbsolutePath(), "404.html");
			if(!f.exists()){
				return false;
			}
		}
		
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			String file = "";
			
			while(true){
				String line = in.readLine();
				if(line == null)
					break;
				file += line + "\n";
			}
			
			in.close();
			
			res.end(file);
		} catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
}
