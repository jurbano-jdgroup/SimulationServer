
package simulationserver;

/**
 *
 * @author Ernesto
 */
public interface RTClientConnectionListener {
    void onConnectionChange(RTClient client, int connectionStatus);
}
