
package simulationserver.services.simulation;

import simulationserver.Service;
import simulationserver.RuntimeConfiguration;

/**
 *
 * @author Ernesto
 */
public class SimulationServer implements Service {

    final RuntimeConfiguration config;
    
    public SimulationServer (RuntimeConfiguration config) {
        this.config = config;
    }
    
    @Override
    public void runService() throws Exception {
        MainWorker worker = new MainWorker(this.config);
        worker.setExecutionStep(1000);
        worker.start();
    }
}