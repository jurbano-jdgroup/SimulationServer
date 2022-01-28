
package simulationserver.system.control.per2022I;

import simulationserver.system.control.SecondOrderSystem;

/**
 *
 * @author Julio
 */
public class P3GeneradoresSincronicos extends SecondOrderSystem {
    // y(s)/u(s) = k*(a*s + 1)/(b*s^2 + c*s + d)
    public P3GeneradoresSincronicos() {
        this.s_a = -1.0;
        this.s_b = 1.0;
        this.s_c = 2.5;
        this.s_d = 1.0;
        this.minDeadZoneValue = -1.0;
        this.maxDeadZoneValue = 1.0;
        this.minInputValue = -10.0;
        this.maxInputValue = 10.0;
    }
    
    @Override
    public String getName() {
        return "Generadores Sincrónicos";
    }
}