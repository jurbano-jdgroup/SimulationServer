
package simulationserver.system.control;

/**
 *
 * @author Ernesto
 */
public class Planta4 extends simulationserver.system.control.SecondOrderSystem {
    
    // time delay: 3s
    public Planta4() {
        super();
        this.s_k=1.0;
        this.s_a=1.0;
        this.s_b=1.0;
        this.s_c=0.2;
        this.s_d=1.0;
    }
    
    @Override
    public String getName() {
        return "Planta 4";
    }
}
