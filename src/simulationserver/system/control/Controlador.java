
package simulationserver.experimental;

/**
 *
 * @author Garcia, Ochoa
 */
public class Controlador extends simulationserver.RTAbstractModel {

    public double sat_max=100.0, sat_min=-100.0;
    public double referencia, disturbio = 0;
    double variableManipulada; // salida para el lego
    double variableProceso ; // dato del sensor
    // b= constante de peso... ajusta cambios en la referencia valor entre 0 y 1... lo define el usuario... los puede dejar en 1
    // c=constante de peso c=0 cuando son procesos... y c=valor cuando son servomecanismos... lo mete el usuario...los puede dejar en 1
    // variables del controlador 
    //bias va entre 0 y 100%
    public double Kp=1.0, Ti=0.0, Td=0.0, Ts=1e-2, Tt=0.0, N=20.0;
    public double c = 1.0, b = 1.0, bias_inicial = 0.0;
    double b1=0.0, b2=0.0, Ar=0.0, a1=0.0, up=0.0, ud=0.0, ui=0.0, u=0.0, V=0.0;
    double cvold=0.0, pvold=0.0, uiold=0.0, udold=0.0, spold=0.0;
    int salida;
    // boolean flags
    private boolean manual = false;
    private boolean inverse = false;
    //PID 2
    double error_old=0, error=0, P_error=0, I_error=0, D_error, motor_actual=0;
    // default constructor
    public Controlador(){
        super();        
    }
    // set manual
    public void setManual () {
        this.manual = true;
    }
    // set inverse
    public void setInverse () {
        this.inverse = true;
    }
    // set non inverse
    public void setNonInverse () {
        this.inverse = false;
    }
    // set auto
    public void setAuto () {
        this.manual = false;
    }
    // set setpoint value
    public synchronized void setSetpoint (double setpoint) {
        this.referencia = setpoint;
    }
    // set output value if manul
    public void setOutput (double output) {
        if (this.manual) {
            this.setCurrentOutputValue(output);
        }
    }
    // calculate the next output
    public double PID(double variableProceso, double referencia){
        this.variableProceso=variableProceso;
        // update integral discharge time
        if (Td!=0){
            Tt = Math.sqrt(Td * Ti);			
        }else{
            Tt = Math.sqrt(Ti);
        }
        // aux vars
        b1=Td==0?0.0:Td/(Td+N*Ts);
        b2=Td==0?0.0:Kp*Td*N/(Td+N*Ts);
        Ar=Tt==0?0.0:Ts/Tt;
        a1=Ti==0?0.0:Kp*Ts/Ti;
        // update proportional and derivative actions
        up=Kp*(b*this.referencia-this.variableProceso);
        ud=b1*udold+b2*c*(this.referencia-spold)-b2*(this.variableProceso-pvold);
        // update actuator output
        V=up+uiold+ud+this.disturbio+bias_inicial;
        //saturador
        if (V<sat_min){
            u=sat_min; // V entrada al saturador
        }else if (V>sat_max) {
            u=sat_max; // u salida del saturador
        }else {
            u=V;
        }
        // check if inverse
        if (this.inverse) {
            u = -1.0*u;
        }
        // update integral action        
        ui=uiold+a1*(this.referencia-this.variableProceso)+Ar*(u-V);
        // debug
        //System.out.println("Error: "+(this.referencia-variableProceso));
        //System.out.println("up: "+up+", ui: "+ui+", ud: "+ud+", u: "+u);
        // Actualiza estados del controlador
        cvold = V;
        pvold = this.variableProceso;
        uiold = ui;
        udold = ud;
        spold = this.referencia;
        // do return
        return u;
    }
    // pid2
    public double PID2(double variableProceso, double disturbio, double referencia){
        double salida_s;
        this.variableProceso=variableProceso-90;
        this.referencia=referencia-90;
        error_old=error;
        error= this.referencia-this.variableProceso;
        P_error=error;
        I_error+=error_old;
        D_error=error-error_old;
        motor_actual=Kp*P_error + Ti*I_error+Td*D_error;
        if(motor_actual>sat_max)
            salida_s=sat_max;
        else if(motor_actual<sat_min)
            salida_s=sat_min;
        else
            salida_s=motor_actual;

        return salida_s;
    }

    @Override
    public void makeStep() {
        // update the time
        ++t_iter;        
        this.setCurrentTime(((double)t_iter) * step_size);
        // check if manual or auto
        final double inputValue = this.getCurrentInputValue();
        final double outputValue = PID(inputValue, this.referencia);
        // debug
        //System.out.println("input: "+inputValue+", output: "+outputValue);
        // check if manual
        if (!this.manual) {
            this.setCurrentOutputValue(outputValue);
        }
    }

    @Override
    public String getName() {
        return "PID Controller";
    }
}
