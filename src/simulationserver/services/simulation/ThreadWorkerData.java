
package simulationserver.services.simulation;

/**
 *
 * @author Ernesto
 * @param <T>
 */
public class ThreadWorkerData<T> {
    
    private T data;
    
    public ThreadWorkerData(T dataRes) {
        this.data = dataRes;
    }
    
    public T getData () {
        return this.data;
    }
    
    public void setData (T dataRes) {
        this.data = dataRes;
    }
}
