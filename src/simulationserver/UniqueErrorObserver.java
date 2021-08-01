
package simulationserver;

/**
 *
 * @author Ernesto
 */
public interface UniqueErrorObserver<T> {
    void onError (T emitter);
}
