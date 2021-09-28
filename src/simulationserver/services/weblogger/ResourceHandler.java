
package simulationserver.services.weblogger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Ernesto
 */
public class ResourceHandler implements HttpHandler {
    private final java.util.HashMap<String, String> resourceMap;
    private final java.util.HashMap<String, String> resourceTypeMap;
    private final String resource;
    
    public ResourceHandler(final String resource) {
        this.resource = resource;
        
        this.resourceMap = new java.util.HashMap<>();
        this.resourceMap.put("/index.js", "index.js");
        this.resourceTypeMap = new java.util.HashMap<>();
        this.resourceTypeMap.put("/index.js", "application/javascript");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!this.resourceMap.containsKey(this.resource))
            throw new IOException("Resource doesn't exists");
        
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("resources/"+
                this.resourceMap.get(this.resource));
        
        exchange.getResponseHeaders().add("Content-Type", this.resourceTypeMap.get(this.resource));
        exchange.sendResponseHeaders(200, 0);
        //exchange.getResponseBody().write(in.readAllBytes());
        exchange.close();
    }
}