
package simulationserver.control;

/**
 *
 * @author Ernesto
 */
public class Planta2 extends simulationserver.SecondOrderSystem {
    
    public Planta2() {
        super();
        this.s_k=1.0;
        this.s_a=-1.0;
        this.s_b=1.0;
        this.s_c=2.5;
        this.s_d=1.0;
    }
    
    @Override
    public String getName() {
        return "Planta 2";
    }
}
