
package simulationserver.system.control.per2022I;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import simulationserver.services.simulation.SystemMapper;
import simulationserver.system.RTAbstractModel;
import simulationserver.system.maths.BaseMatrix;

/**
 *
 * @author Julio
 */
public class Per2022IMapper implements SystemMapper {
 
    private final Map<String, RTAbstractModel> map;
    
    public Per2022IMapper() {
        this.map = new HashMap();
        this.loadMappings();
    }
    
    @Override
    public Map<String, RTAbstractModel> getMap() {
        return this.map;
    }
    
    @Override
    public Set<String> getSystems() {
        return this.map.keySet();
    }

    @Override
    public RTAbstractModel getSystem(String system) {
        return (RTAbstractModel) this.map.get(system);
    }
    
    private void loadMappings() {
        this.map.put("p1_temperatura_cafe", new P1TemperaturaCafe());
        this.map.put("p2_nivel_calderin", new P2NivelCalderin());
        this.map.put("p3_generadores_sincronicos", new P3GeneradoresSincronicos());
    }
}