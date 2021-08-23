
package simulationserver.system.control;

/**
 *
 * @author Ernesto
 */
public class Planta3 extends simulationserver.system.control.SecondOrderSystem {
    
    // time delay: 3s
    public Planta3() {
        super();
        this.s_a=0.0;
        this.s_k=-3.0;
        this.s_b=1.0;
        this.s_c=0.6;
        this.s_d=1.0;
    }
    
    @Override
    public String getName() {
        return "Planta 3";
    }
}
