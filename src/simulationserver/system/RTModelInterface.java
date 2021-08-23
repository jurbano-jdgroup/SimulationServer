
package simulationserver.system;

/**
 *
 * @author Ernesto
 */
public interface RTModelInterface {
    /* name */
    String getName();
    /* timing */
    double getCurrentTime();
    void setCurrentTime(double currentTime);
    double getDelayTime();
    void setDelayTime(double delayTime);
    /* values */
    double getCurrentOutputValue();
    void setCurrentOutputValue(double currentValue);
    double getCurrentInputValue();
    void setCurrentInputValue(double inputValue);
    /* simulation time */
    double getSimulationTime();
    double getMaxSimulationTime();
    void setMaxSimulationTime(double maxSimulationTime);
    double getMinSimulationTime();
    void setMinSimulationTime(double minSimulationTime);
    /* step size */
    double getStepSize();
    void setStepSize(double stepSize);
    double getMaxStepSize();
    void setMaxStepSize(double maxStepSize);
    double getMinStepSize();
    void setMinStepSize(double minStepSize);
    void makeStep();
    /* simulation */
    void stopSimulation();
    void startSimulation();
    /* saturation values */
    void setMaxOutputValue(double maxOutputValue);
    double getMaxOutputValue();
    void setMinOutputValue(double minOutputValue);
    double getMinOutputValue();
    /* maximum input values */
    void setMaxInputValue(double maxInputValue);
    double getMaxInputValue();
    void setMinInputValue(double minInputValue);
    double getMinInputValue();
    /* dead zone values */
    void setMaxDeadZoneValue(double maxDeadZoneValue);
    void setMinDeadZoneValue(double minDeadZoneValue);
    double getMaxDeadZoneValue();
    double getMinDeadZoneValue();
}
