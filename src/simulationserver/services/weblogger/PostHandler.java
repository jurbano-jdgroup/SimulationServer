
package simulationserver.services.weblogger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

/**
 *
 * @author Ernesto
 */
public class PostHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        final String path = exchange.getRequestURI().getPath();
        
        if (path.compareToIgnoreCase("/login")==0) {
            this.handleLogin(exchange);
            return;
        } else if (path.compareToIgnoreCase("/updates")==0) {
            this.handleSystemUpdates(exchange);
            return;
        }
        
        exchange.sendResponseHeaders(404, 0);
        exchange.getResponseBody().close();
    }
    
    protected void handleLogin(HttpExchange t) throws IOException {
        final String data = new String(t.getRequestBody().readAllBytes());
        final String nameRegex = "user=admin";
        final String passRegex = "password=hT6vNm4ssDu";
        
        if (!(data.contains(passRegex) && data.contains(nameRegex))) {
            //System.out.println("No string match on "+data);
            t.sendResponseHeaders(401, 0);
            final String message = "Auth error";
            t.getResponseBody().write(message.getBytes());
            t.getResponseBody().close();
            
            return;
        }

        t.getResponseHeaders().add("Set-Cookie", WebLoggerService.addCookie());
        t.getResponseHeaders().add("Location", "/sistemas");
        t.sendResponseHeaders(301, 0);
        t.getResponseBody().close();
    }
    
    protected void handleSystemUpdates(HttpExchange t) throws IOException {
        throw new IOException("Updates not implemented yet");
    }
}