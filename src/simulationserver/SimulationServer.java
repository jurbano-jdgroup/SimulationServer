
package simulationserver;

import simulationserver.maths.*;

/**
 *
 * @author Ernesto
 */
public class SimulationServer {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RuntimeConfiguration config = new RuntimeConfiguration();
        
        if(!config.parseArgs(args, false)) {
            config.printUSage();
            System.exit(0);
        }
        
        config.dumpOptions();
        
        MainWorker worker = new MainWorker(config);
        worker.setExecutionStep(1000);
        worker.start();
        
        /*
        BaseMatrix num = new BaseMatrix(3,1);
        num.fill(0.0);
        num.set(2, 0, 4.0);
        //num.set(1, 0, 2.1);
        
        BaseMatrix den = new BaseMatrix(3,1);
        den.set(0, 0, 1.0);
        den.set(1, 0, 1.4);
        den.set(2, 0, 4.0);
        
        simulationserver.control.System system = null;
        
        try {
            system = new simulationserver.control.System(num, den);
        } catch (Exception ex) {
            System.out.println("Error creando el sistema: "+ex.getMessage());
            System.exit(-1);
        }
        
        ((BaseMatrix)system.getA()).dump();
        ((BaseMatrix)system.getB()).dump();
        ((BaseMatrix)system.getC()).dump();
        ((BaseMatrix)system.getD()).dump();
        
        MainWorker worker = new MainWorker();
        worker.server.setPort(10080);
        worker.server.setBacklog(5);
        worker.server.setModel(system);
        worker.setExecutionStep(1000);
        worker.start();*/
    }
}