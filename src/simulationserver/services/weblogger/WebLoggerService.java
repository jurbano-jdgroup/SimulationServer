
package simulationserver.services.weblogger;

import simulationserver.RuntimeConfiguration;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import simulationserver.Service;
import java.util.Map;

/**
 *
 * @author Ernesto
 */
public class WebLoggerService implements Service {
    private final RuntimeConfiguration config;
    
    private HttpServer server;
    private InetSocketAddress address;
    
    private static final Map<String, Boolean> cookiesMap = new java.util.HashMap<>();

    public WebLoggerService(RuntimeConfiguration config) {
        this.config = config;
    }
    
    public static String addCookie () {
        String id = java.util.UUID.randomUUID().toString();
        
        WebLoggerService.cookiesMap.put(id, Boolean.TRUE);
        return id;
    }
    
    public static boolean isCookieSet (String cookie) {
        return WebLoggerService.cookiesMap.containsKey(cookie);
    }
    
    public static void removeCookie (String cookie) {
        WebLoggerService.cookiesMap.remove(cookie);
    }
    
    @Override
    public void runService() throws Exception {
        int port = 10080;
        
        final Map<String, Object> map = this.config.getParamsMap();
        
        if (map.containsKey("port")) {
            int definedPort = (Integer) map.get("port");
            
            if (definedPort > 0) {
                port = definedPort;
            }
        }
        
        GetHandler getHandler = new GetHandler();
        PostHandler postHandler = new PostHandler();
        
        this.address = new InetSocketAddress("localhost", port);
        this.server = HttpServer.create(address, 1);
        this.server.createContext("/index.js", new ResourceHandler("/index.js"));
        this.server.createContext("/", getHandler);
        
        this.server.createContext("/sistemas", getHandler);
        this.server.createContext("/salir", getHandler);
        this.server.createContext("/login", postHandler);
        this.server.createContext("/updates", postHandler);
        this.server.setExecutor(null);
        
        System.out.println("Port set to: "+port);
        this.server.start();
    }
}