# HttpServerApi
A java api that hosts http servers.

## Examples

Display a static string:
```java
import net.httpserver.HttpServer;
import net.httpserver.io.HttpRequest;
import net.httpserver.io.HttpResponse;
import net.httpserver.listener.HttpRequestListener;

public class Main implements HttpRequestListener {

  public static void main(String[] args){
    //Creates the server.
    HttpServer server = new HttpServer();

    //Adds this listener.
    server.addHttpRequestListener(new Main());

    //Listens on localhost:80
    server.listen();
  }

  @Override
  public boolean onHttpRequest(HttpRequest req, HttpResponse res){
    res.end("<h1>Hello</h1>");
    
    //Returns whether this listener handeled the request.
    //If false, the request is passed onto the next listener if there is one.
    return true;
  }
}
```
