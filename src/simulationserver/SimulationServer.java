
package simulationserver;

import simulationserver.services.simulation.MainWorker;

/**
 *
 * @author Ernesto
 */
public class SimulationServer {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Request request = new Request();
        Controller controller = new Controller();
        Service service;
        
        try{
            request.buildRequest(args);
            service = controller.getService(request);
            service.runService();
        }catch(Exception ex) {
            System.out.println("Error parsing commands: "+ex.getMessage());
            System.exit(0);
        }
    }
}