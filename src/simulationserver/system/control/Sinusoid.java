
package simulationserver.system.control;

import simulationserver.system.RTAbstractModel;

/**
 *
 * @author Ernesto
 */
public class Sinusoid extends RTAbstractModel {

    public double A = 10.0;
    public double freq = 1.0;
    public double omega = 0.0;
    public double offset = 0.0;

    public Sinusoid() {
        super();
        this.step_size = 1e-2;
    }
    
    @Override
    public void makeStep() {
        this.current_output_value = A*Math.sin(2.0*Math.PI*freq*((double)this.t_iter)*this.step_size + omega) + offset;
        this.current_time = ((double)this.t_iter) * this.step_size;
        ++this.t_iter;
    }

    @Override
    public String getName() {
        return "Sinusoid Signal";
    }
}
