
package simulationserver.system.control;

/**
 *
 * @author Ernesto
 */
public class CaracsPlant extends simulationserver.system.control.FirstOrderSystem {
    
    public CaracsPlant() {
        super();
        this.s_k = 1.2;
        this.s_b = 3.2;
    }
    
    @Override
    public String getName() {
        return "Caracteristicas de primer orden";
    }
}
