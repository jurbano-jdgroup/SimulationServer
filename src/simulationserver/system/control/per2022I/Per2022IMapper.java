
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
 
    private Map<String, RTAbstractModel> map;
    
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
        this.map.put("p6_control_posicion", new P6ControlPosicion());
        this.map.put("p7_control_velocidad", new P7ControlVelocidad());
        
        this.loadP4();
        this.loadP5();
    }
    
    private void loadP4() {
        BaseMatrix num = new BaseMatrix(4,1);
        BaseMatrix den = new BaseMatrix(4,1);
        
        num.fill(0.0);
        num.set(3, 0, 20.0);
        
        den.set(0, 0, 1.0);
        den.set(1, 0, 10.1);
        den.set(2, 0, 26.0);
        den.set(3, 0, 250.0);
        
        simulationserver.system.System system = null;
        
        try {
            system = new simulationserver.system.System(num, den);
            ((BaseMatrix) system.getA()).dump();
        }catch (Exception ex) {
            System.out.println("Error creating the system P4: " + ex.getMessage());
        }
        
        this.map.put("p4_control_posicion", system);
    }
    
    private void loadP5() {
        BaseMatrix num = new BaseMatrix(4,1);
        BaseMatrix den = new BaseMatrix(4,1);
        
        num.fill(0.0);
        num.set(3, 0, 6.0);
        num.set(2, 0, 3.04);
        num.set(1, 0, 1.0);
        
        den.set(0, 0, 1.0);
        den.set(1, 0, 1.04);
        den.set(2, 0, 3.04);
        den.set(3, 0, 3.0);
        
        simulationserver.system.System system = null;
        
        try {
            system = new simulationserver.system.System(num, den);
        }catch (Exception ex) {
            System.out.println("Error creating the system P5: " + ex.getMessage());
        }
        
        this.map.put("p5_control_posicion", system);
    }
}