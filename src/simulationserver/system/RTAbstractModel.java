
package simulationserver.system;

/**
 *
 * @author Ernesto
 */

public abstract class RTAbstractModel implements RTModelInterface {
    /** initial input buffer size */
    public static int INITIAL_INPUT_BUFFER_SIZE = 4096;
    /* step size vars */
    protected double step_size;
    protected double max_step_size;
    protected double min_step_size;
    /* time vars */
    protected long t_iter;
    /**
     * delay time
    */
    protected double delay_time;
    /**
     * current simulation time, 0 at start
     */
    protected double current_time;
    /**
     * max time to simulate
     */
    protected double simulation_time;
    protected double min_simulation_time;
    protected double max_simulation_time;
    /* max and min output values */
    protected double maxOutputValue;
    protected double minOutputValue;
    /* max and min input values */
    protected double maxInputValue;
    protected double minInputValue;
    /* max and min dead zone values */
    protected double maxDeadZoneValue;
    protected double minDeadZoneValue;
    /* values vars */
    protected double current_output_value;
    protected double current_input_value;
    /** input buffer for delayed systems */
    protected java.util.Queue<Double> inputBuffer;
    /** Default constructor */
    public RTAbstractModel() {
        this.t_iter = 0;
        this.delay_time = 0.0;
        this.current_time = 0.0;
        this.simulation_time = 0.0;
        this.min_simulation_time = 0.0;
        this.max_simulation_time = Double.MAX_VALUE;
        
        this.step_size = 0.0;
        this.max_step_size = this.simulation_time / 2.0;
        this.min_step_size = 1e-6;
        
        this.maxOutputValue = Double.POSITIVE_INFINITY;
        this.minOutputValue = Double.NEGATIVE_INFINITY;
        
        this.maxInputValue = Double.POSITIVE_INFINITY;
        this.minInputValue = Double.NEGATIVE_INFINITY;
        
        this.maxDeadZoneValue = 0.0;
        this.minDeadZoneValue = 0.0;
        
        this.current_input_value = 0.0;
        this.current_output_value = 0.0;
        
        this.inputBuffer = new java.util.concurrent.ArrayBlockingQueue<>(RTAbstractModel.INITIAL_INPUT_BUFFER_SIZE);
    }

    @Override
    public synchronized double getCurrentTime() {
        return this.current_time;
    }

    @Override
    public synchronized void setCurrentTime(double currentTime) {
        this.current_time = currentTime;
    }
    
    @Override
    public double getDelayTime() {
        return this.delay_time;
    }
    
    @Override
    public void setDelayTime (double delayTime) {
        this.delay_time = delayTime;
    }

    @Override
    public double getCurrentOutputValue() {
        return this.current_output_value;
    }

    @Override
    public void stopSimulation() {
        this.t_iter = 0;
        this.current_time = 0.0;
        this.current_output_value = 0.0;
        this.current_input_value = 0.0;
    }

    @Override
    public void startSimulation() {
        this.stopSimulation();
    }

    @Override
    public double getMaxSimulationTime() {
        return this.max_simulation_time;
    }

    @Override
    public double getStepSize() {
        return this.step_size;
    }

    @Override
    public double getMaxStepSize() {
        return this.max_step_size;
    }

    @Override
    public double getMinStepSize() {
        return this.min_step_size;
    }

    @Override
    public void setCurrentOutputValue(double currentValue) {
        // check max and min values
        if (currentValue > this.getMaxOutputValue()) {
            this.current_output_value = this.getMaxOutputValue();
            return;
        } else if(currentValue < this.getMinOutputValue()) {
            this.current_output_value = this.getMinOutputValue();
            return;
        }
        
        this.current_output_value = currentValue;
    }

    @Override
    public synchronized double getCurrentInputValue(){
        // check if delay
        if (this.getCurrentTime()>this.getDelayTime()) {
            this.current_input_value = this.inputBuffer.poll();
        } else {
            this.current_input_value = 0.0;
        }
        
        return this.current_input_value;
    }
    
    @Override
    public synchronized void setCurrentInputValue(double inputValue){
        // this method adds the incoming input to the buffer
        double finalInputValue = inputValue;
        // check max and min input values
        if (inputValue > this.getMaxInputValue()) {
            finalInputValue = this.getMaxInputValue();
        } else if(inputValue < this.getMinInputValue()) {
            finalInputValue = this.getMinInputValue();
        }
        // check dead zones
        if (finalInputValue <= this.getMaxDeadZoneValue() && finalInputValue >= this.getMinDeadZoneValue()) {
            finalInputValue = 0.0;
        }
        
        this.inputBuffer.add(finalInputValue);
        //this.current_input_value = inputValue;
    }
    
    @Override
    public void setMaxSimulationTime(double maxSimulationTime) {
        this.max_simulation_time = maxSimulationTime;
    }

    @Override
    public double getMinSimulationTime() {
        return this.min_simulation_time;
    }

    @Override
    public void setMinSimulationTime(double minSimulationTime) {
        this.min_simulation_time = minSimulationTime;
    }

    @Override
    public void setStepSize(double stepSize) {
        this.step_size = stepSize;
    }

    @Override
    public void setMaxStepSize(double maxStepSize) {
        this.max_step_size = maxStepSize;
    }

    @Override
    public void setMinStepSize(double minStepSize) {
        this.min_step_size = minStepSize;
    }

    @Override
    public double getMaxOutputValue() {
        return this.maxOutputValue;
    }
    
    @Override
    public double getMinOutputValue() {
        return this.minOutputValue;
    }
    
    @Override
    public void setMaxOutputValue(double maxOutputValue) {
        this.maxOutputValue = maxOutputValue;
    }
    
    @Override
    public void setMinOutputValue(double minOutputValue) {
        this.minOutputValue = minOutputValue;
    }
    
    @Override
    public void setMinInputValue(double minInputValue) {
        this.minInputValue = minInputValue;
    }
    
    @Override
    public void setMaxInputValue(double maxInputValue) {
        this.maxInputValue = maxInputValue;
    }
    
    @Override
    public double getMinInputValue() {
        return this.minInputValue;
    }
    
    @Override
    public double getMaxInputValue() {
        return this.maxInputValue;
    }
    
    @Override
    public double getSimulationTime() {
        return this.simulation_time;
    }

    @Override
    public void setMaxDeadZoneValue(double maxDeadZoneValue) {
        this.maxDeadZoneValue = maxDeadZoneValue;
    }

    @Override
    public void setMinDeadZoneValue(double minDeadZoneValue) {
        this.minDeadZoneValue = minDeadZoneValue;
    }

    @Override
    public double getMaxDeadZoneValue() {
        return this.maxDeadZoneValue;
    }

    @Override
    public double getMinDeadZoneValue() {
        return this.minDeadZoneValue;
    }
}
