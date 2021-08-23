
package simulationserver;

import java.util.Map;

/**
 *
 * @author Ernesto
 */
public class Controller {
    
    public Service getService(Request request) throws Exception {
        Map<String, Object> map = request.getConfig().getParamsMap();
        
        if (map.isEmpty())
            throw new Exception("No commands");
        
        final String service = (String) map.get("service");
        
        if (service == null) {
            final Service ret = this.inferService(map, request);
            return ret;
        }
        
        if (service.compareToIgnoreCase("simulation")==0) {
            return this.getSimulationService(map, request);
        } else if (service.compareToIgnoreCase("weblogger")==0) {
            return this.getWebLoggerService(map, request);
        } else if (service.compareToIgnoreCase("plotter")==0) {
            return this.getPlotterService(map, request);
        }
        
        throw new Exception("Error getting the service for the specified commands");
    }
    
    protected Service getPlotterService(Map<String, Object> map, Request request) throws Exception {
        return new simulationserver.services.plotter.PlotterService();
    }
    
    protected Service getWebLoggerService(Map<String, Object> map, Request request) throws Exception {
        return new simulationserver.services.weblogger.WebLoggerService(request.getConfig());
    }
    
    protected Service getSimulationService(Map<String, Object> map, Request request) throws Exception {
        return new simulationserver.services.simulation.SimulationServer(request.getConfig());
    }
    
    protected Service inferService (Map<String, Object> map, Request request) throws Exception {
        if (map.containsKey("system")) {
            return new simulationserver.services.simulation.SimulationServer(request.getConfig());
        }
        
        throw new Exception("Service could not be inferred");
    }
}