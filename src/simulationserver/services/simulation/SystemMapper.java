
package simulationserver.services.simulation;

import java.util.Map;
import java.util.Set;
import simulationserver.system.RTAbstractModel;

/**
 *
 * @author Julio
 */
public interface SystemMapper {
    Map<String, RTAbstractModel> getMap();
    Set<String> getSystems();
    RTAbstractModel getSystem(String system);
}