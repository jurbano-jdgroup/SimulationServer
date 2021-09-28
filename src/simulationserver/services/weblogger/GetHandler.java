
package simulationserver.services.weblogger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import com.sun.net.httpserver.Headers;
import java.util.List;

/**
 *
 * @author Ernesto
 */
public class GetHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        final String path = exchange.getRequestURI().getPath();
        System.out.println("New request to: "+path);
        
        if (path.compareToIgnoreCase("/")==0) {
            this.handleLogin(exchange);
            return;
        } else if (path.compareToIgnoreCase("/sistemas")==0) {
            this.handleSystems(exchange);
            return;
        } else if (path.compareToIgnoreCase("/salir")==0) {
            this.handleLogout(exchange);
            return;
        }
        
        exchange.sendResponseHeaders(404, 0);
        exchange.getResponseBody().close();
    }
    
    protected String getCookie (HttpExchange t) {
        final Headers headers = t.getRequestHeaders();
        final List<String> cookies = headers.get("Cookie");
        
        if (cookies == null) {
            return null;
        }
        
        return cookies.get(0);
    }
    
    protected boolean checkCookie (HttpExchange t) {        
        final String cookie = this.getCookie(t);
        
        if (cookie == null) return false;
        
        return WebLoggerService.isCookieSet(cookie);
    }
    
    protected void handleLogout (HttpExchange t) throws IOException {
        
        if (!this.checkCookie(t)) {
            t.sendResponseHeaders(401, 0);
            t.getResponseBody().write((new String("Unauthorized")).getBytes());
            t.getResponseBody().close();
        }
        
        WebLoggerService.removeCookie(this.getCookie(t));
        
        t.getResponseHeaders().add("Location", "/");
        t.sendResponseHeaders(301, 0);
        t.getResponseBody().close();
    }
    
    protected void handleLogin (HttpExchange t) throws IOException {
        InputStream template = this.getClass().getClassLoader().getResourceAsStream("resources/index.html");
        
        if (template == null)
            throw new IOException("Template resource doesn't exist");
        
        t.sendResponseHeaders(200, 0);
        //t.getResponseBody().write(template.readAllBytes());
        t.getResponseBody().close();
    }
    
    protected void handleSystems (HttpExchange t) throws IOException {
        if (!this.checkCookie(t)) {
            t.sendResponseHeaders(401, 0);
            t.getResponseBody().write((new String("Unauthorized")).getBytes());
            t.getResponseBody().close();
            
            return;
        }
        
        InputStream template = this.getClass().getClassLoader().getResourceAsStream("resources/sistemas.html");
        
        if (template == null)
            throw new IOException("Template resource doesn't exist");
        
        t.sendResponseHeaders(200, 0);
        //t.getResponseBody().write(template.readAllBytes());
        t.getResponseBody().close();
    }
}