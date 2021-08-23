
package simulationserver.control;

import simulationserver.SecondOrderSystem;

/**
 *
 * @author Ernesto
 */
public class FreqResPlant extends SecondOrderSystem {

    public FreqResPlant() {
        super();
        this.s_a = 0.0;
        this.s_k = 10.0;
        this.s_b = 2.5;
        this.s_c = 5.5;
        this.s_d = 1;
    }
    
    @Override
    public String getName() {
        return "Planta para respuesta en frecuencia";
    }
}