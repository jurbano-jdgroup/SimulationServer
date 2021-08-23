
package simulationserver.system.control;

import simulationserver.system.RTAbstractModel;

/**
 *
 * @author Ernesto
 */
public class FirstOrderSystem extends RTAbstractModel {

    // system model vars
    // system of the form
    // y(s)/u(s) = k*(a*s + 1)/(b*s + c)
    // b must be different of zero
    public double s_k;
    public double s_a;
    public double s_b;
    public double s_c;
    public double s_t;
    // state
    protected double x;
    // initial condition
    public double x0;
    
    // initial values for the model
    // s_k=1, s_a=0, s_b=1, s_c=1
    public FirstOrderSystem () {
        super();
        // init vars
        s_k = 1.0;
        s_a = 0.0;
        s_b = 1.0;
        s_c = 1.0;
        s_t = 0.0;
        // set the step size
        step_size = 1e-2;
        // init states
        x0 = 0.0;
        x = x0;
    }
    
    @Override
    public synchronized void makeStep() {
        // update the simulation time
        ++t_iter;
        this.setCurrentTime(((double)t_iter) * step_size);
        // check s_b, must be different of zero
        if(s_b == 0.0) {
            this.setCurrentOutputValue(Double.NaN);
            return;
        }
        // System.out.println("Input: "+current_input_value);
        final double inputValue = this.getCurrentInputValue();
        x = x + (-1.0*(s_c/s_b)*x + (1.0/s_b)*inputValue)*step_size;
        this.setCurrentOutputValue(s_k*(1.0 - (s_a*s_c/s_b))*x + s_k*(s_a/s_b)*inputValue);
    }

    @Override
    public String getName() {
        return "First Order System";
    }
}
