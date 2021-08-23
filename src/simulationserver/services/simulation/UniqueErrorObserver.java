
package simulationserver.services.simulation;

/**
 *
 * @author Ernesto
 */
public interface UniqueErrorObserver<T> {
    void onError (T emitter);
}
