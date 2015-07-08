package net.httpserver.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class HttpRequest {

	public String path = null;
	public String method = null;
	public HashMap<String, String> headers = new HashMap<>();
	public HashMap<String, String> query = new HashMap<>();

	public String getHeader(String key) {
		return headers.get(key);
	}
	
	public String getQuery(String key) {
		return query.get(key);
	}

	public String toString() {
		String finalPath = path;

		if (query.size() > 0) {
			finalPath += "?";
			for (Entry<String, String> entry : query.entrySet()) {
				finalPath += entry.getKey() + "=" + entry.getValue() + "&";
			}
			finalPath = finalPath.substring(0, finalPath.length() - 1);
		}

		String str = method + " " + finalPath + "HTTP/1.1\r\n";

		for (Entry<String, String> e : headers.entrySet()) {
			str += e.getKey() + ": " + e.getValue() + "\r\n";
		}

		str += "\r\n";

		return str;
	}

	public HttpRequest(BufferedReader in) throws IOException {
		boolean first = true;
		while (true) {
			String line = in.readLine();
			if (line == null || line.isEmpty())
				break;
			if (first) {
				first = false;
				String[] args = line.split(" ");
				method = args[0];
				String[] things = args[1].split("\\Q?\\E");
				path = things[0];
				if (things.length == 2) {
					String[] queries = things[1].split("&");

					for (String s : queries) {
						String[] pair = s.split("=");
						query.put(pair[0], pair[1]);
					}
				}
			} else {
				String[] pair = line.split(": ");
				headers.put(pair[0], pair[1]);
			}
		}
	}
}
