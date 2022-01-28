
package simulationserver.system.control.per2022I;

import simulationserver.system.control.SecondOrderSystem;

/**
 *
 * @author Julio
 */
public class P7ControlVelocidad extends  SecondOrderSystem{
    
    public P7ControlVelocidad() {
        this.s_a = 1.0;
        this.s_b = 1.0;
        this.s_c = 0.2;
        this.s_d = 1.0;
        this.minDeadZoneValue = -1.0;
        this.maxDeadZoneValue = 1.0;
        this.minInputValue = -10.0;
        this.maxInputValue = 10.0;
        this.delay_time = 3.0;
    }

    @Override
    public String getName() {
        return "Control de velocidad";
    }   
}