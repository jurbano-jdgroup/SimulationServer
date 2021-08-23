
package simulationserver.services.simulation;

/**
 *
 * @author Ernesto
 */
public interface ThreadWorker {
    /* thread sleep, processing low */
    long getExecutionStep();
    /* set execution step */
    void setExecutionStep(long step);
    /* setup execution */
    void setupExecution();
    /* void execute */
    void doExecution();
}
