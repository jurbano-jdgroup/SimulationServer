
package simulationserver;

/**
 *
 * @author Ernesto
 */
public class SecondOrderSystem extends RTAbstractModel {

    // system model vars
    // transfer function of the form
    // y(s)/u(s) = k*(a*s + 1)/(b*s^2 + c*s + d)
    // b must be different of zero
    public double s_k;
    public double s_a;
    public double s_b;
    public double s_c;
    public double s_d;
    public double s_t;
    // initial conditions
    public double x10;
    public double x20;
    // states
    protected double x1;
    protected double x2;
    
    // initial values for the model
    // s_k=1, s_a=0, s_b=1, s_c=1.4, s_d=1
    public SecondOrderSystem () {
        super();
        // init vars
        s_k = 1.0;
        s_a = 0.0;
        s_b = 1.0;
        s_c = 1.4;
        s_d = 1.0;
        s_t = 0.0;
        // set the step size
        this.step_size = 1e-2;
        // set initial conditions
        x10 = 0.0;
        x20 = 0.0;
        x1 = x10;
        x2 = x20;
    }
    
    @Override
    public void makeStep() {
        // update the time
        ++t_iter;
        this.setCurrentTime(((double)t_iter)*step_size);
        // check s_b, must be different of zero
        if (s_b == 0.0){
            this.setCurrentOutputValue(Double.NaN);

            return;
        }
        // calculate the value of the states
        final double inputValue = this.getCurrentInputValue();
        x1 = x1 + (x2)*step_size;
        x2 = x2 + (-1.0*(s_d/s_b)*x1 - 1.0*(s_c/s_b)*x2 + (1.0/s_b)*inputValue)*step_size;
        // update the output
        this.setCurrentOutputValue(s_k*(x1 + s_a*x2));
    }

    @Override
    public String getName() {
        return "Second Order System";
    }
}
