
package simulationserver.system.control;

import simulationserver.system.RTAbstractModel;

/**
 *
 * @author Ernesto
 */
public class ThirdOrderSystem extends RTAbstractModel {
    // initial condition vars
    public double x10;
    public double x20;
    public double x30;
    // state space vars
    protected double x1;
    protected double x2;
    protected double x3;
    
    // constructor
    public ThirdOrderSystem () {
        super();
        // init step size
        step_size = 0.0;
        // init vars
        x10=0.0;
        x20=0.0;
        x30=0.0;
        // init state space vars
        x1=0.0;
        x2=0.0;
        x3=0.0;
    }

    @Override
    public String getName() {
        return "Third Order System";
    }

    @Override
    public void makeStep() {
        // update the time
        ++t_iter;
        this.setCurrentTime(((double)t_iter)*step_size);
        // calculate the value of the states
        final double inputValue = this.getCurrentInputValue();
        x1 = x1 + (-3.0*x1 + -1.0*x2 + inputValue)*step_size;
        x2 = x2 + (2.0*x1)*step_size;
        x3 = x3 + (0.5*x2)*step_size;
        // update the output
        this.setCurrentOutputValue(x3);
    }
}
