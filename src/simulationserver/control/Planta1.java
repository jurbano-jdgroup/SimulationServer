
package simulationserver.control;

/**
 *
 * @author Ernesto
 */
public class Planta1 extends simulationserver.SecondOrderSystem {
    
    // time delay: 20s
    public Planta1() {
        super();
        this.s_a = 0.0;
        this.s_k = 1.5;
        this.s_b = 240.0;
        this.s_c = 38.0;
        this.s_d = 1.0;
    }
    
    @Override
    public String getName() {
        return "Planta 1";
    }
}
