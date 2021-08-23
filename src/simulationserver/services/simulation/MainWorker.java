
package simulationserver.services.simulation;

import simulationserver.RuntimeConfiguration;
import simulationserver.system.control.ThirdOrderSystem;
import simulationserver.system.RTAbstractModel;
import simulationserver.system.control.SecondOrderSystem;
import simulationserver.system.control.FirstOrderSystem;
import simulationserver.system.control.ThirdOrderSystem;

/**
 *
 * @author Ernesto
 */
public class MainWorker extends ThreadWorkerAbstract {

    /**
     * Server handler
     */
    public Server server;
    
    /**
     * Default constructor
     */
    public MainWorker () {
        super();
        this.server = new Server();
    }
    
    /**
     * Construct this object based on the params given by the
     * user
     * 
     * @param config options initialization
     */
    public MainWorker (RuntimeConfiguration config) {
        super();
        this.server = new Server();
        
        if (config.paramsCount() > 0) {
            final java.util.Map<String, Object> params = config.getParamsMap();
            // check port
            if (params.containsKey("port")) {
                this.server.setPort((Integer)params.get("port"));
            } 
            // check max connections
            if (params.containsKey("maxconnections")) {
                this.server.setBacklog((Integer)params.get("maxconnections"));
            }
            // check group
            int group = 1;
            if(params.containsKey("group")) {
                group = (Integer) params.get("group");
            } if(group < 0) {
                group = 1;
            } if(group > 20) {
                group = 1;
            }
            // input and output sampling
            int inputSampling = 1;
            int outputSampling = 10;
            // checking input and output sampling
            if (params.containsKey("inputsampling")) {
                inputSampling = (Integer) params.get("inputsampling");
                // check inputSampling
                if (inputSampling <= 0) inputSampling = 1;
            } if (params.containsKey("outputsampling")) {
                outputSampling = (Integer) params.get("outputsampling");
                // check output sampling
                if (outputSampling <= 0) outputSampling = 10;
            }
            // updating the input and ouput sampling on the server
            this.server.setInputSampling(inputSampling);
            this.server.setOutputSampling(outputSampling);
            final double sampleTime = ((double) inputSampling) * 1e-3;
            // check transfer function params
            double s_k = 1.0;
            double s_a = 0.0;
            double s_b = 1.0;
            double s_c = 1.0;
            double s_d = 1.0;
            double s_t = 0.0;
            // do checking
            if (params.containsKey("k")) {
                s_k = (Double)params.get("k");
            } if (params.containsKey("a")) {
                s_a = (Double)params.get("a");
            } if (params.containsKey("b")) {
                s_b = (Double)params.get("b");
                // check s_b
                if (s_b == 0.0) s_b = 1.0;
            } if (params.containsKey("c")) {
                s_c = (Double)params.get("c");
            } if (params.containsKey("d")) {
                s_d = (Double)params.get("d");
            } if (params.containsKey("t")) {
                s_t = (Double)params.get("t");
                // check s_t
                if (s_t < 0.0) s_t = 0.0;
                // set s_t to a multiple of the sample time
                int multiple = (int) Math.ceil(s_t / sampleTime);
                s_t = ((double) multiple) * sampleTime;
                // set the input buffer size of the model
                if (RTAbstractModel.INITIAL_INPUT_BUFFER_SIZE < multiple) {
                    // TODO: assign double ???
                    RTAbstractModel.INITIAL_INPUT_BUFFER_SIZE = 2*multiple;
                }
            }
            // pid controller params
            double kp = 1.0;
            double ki = 0.0;
            double kd = 0.0;
            double N = 20.0;
            double Ts = sampleTime;
            double b = 1.0;
            double c = 1.0;
            double bias = 0.0;
            boolean inverse = false;
            double outputMax = 100.0;
            double outputMin = -100.0;
            double setpoint = 50.0;
            double setpointMax = 100.0;
            double setpointMin = 0.0;
            boolean showSetpointGui = false;
            // check if controller params setted
            if (params.containsKey("kp")) {
                kp = (Double) params.get("kp");
            } if (params.containsKey("ki")) {
                ki = (Double) params.get("ki");
            } if (params.containsKey("kd")) {
                kd = (Double) params.get("kd");
            } if (params.containsKey("N")) {
                N = (Double) params.get("N");
            } if (params.containsKey("b")) {
                b = (Double) params.get("b");
            } if (params.containsKey("c")) {
                c = (Double) params.get("c");
            } if (params.containsKey("bias")) {
                bias = (Double) params.get("bias");
            } if (params.containsKey("outputMax")) {
                outputMax = (Double) params.get("outputMax");
            } if (params.containsKey("outputMin")) {
                outputMin = (Double) params.get("outputMin");
            } if (params.containsKey("setpoint")) {
                setpoint = (Double) params.get("setpoint");
            } if (params.containsKey("setpointMax")) {
                setpointMax = (Double) params.get("setpointMax");
            } if (params.containsKey("setpointMin")) {
                setpointMin = (Double) params.get("setpointMin");
            } if (params.containsKey("setpointGui") ) {
                showSetpointGui = ((String) params.get("setpointGui")).compareToIgnoreCase("true")==0;
            } if (params.containsKey("direction")) {
                inverse = ((String) params.get("direction")).compareToIgnoreCase("inverse")==0;
            }
            // checking system, must exist
            if (params.containsKey("system")) {
                final String systemValue = (String) params.get("system");
                
                if (systemValue.compareToIgnoreCase("firstorder") == 0) 
                {
                    FirstOrderSystem system = new FirstOrderSystem ();
                    system.s_k = s_k;
                    system.s_a = s_a;
                    system.s_b = s_b;
                    system.s_c = s_c;
                    system.s_t = s_t;
                    system.setDelayTime(s_t);
                    system.setStepSize(sampleTime);
                    
                    this.server.setModel(system);
                } 
                else if (systemValue.compareToIgnoreCase("secondorder") == 0) 
                {
                    SecondOrderSystem system = new SecondOrderSystem();
                    
                    system.s_k = s_k;
                    system.s_a = s_a;
                    system.s_b = s_b;
                    system.s_c = s_c;
                    system.s_d = s_d;
                    system.s_t = s_t;
                    system.setDelayTime(s_t);
                    system.setStepSize(sampleTime);
                    
                    this.server.setModel(system);
                }
                else if (systemValue.compareToIgnoreCase("thirdorder") == 0)
                {
                    ThirdOrderSystem system = new ThirdOrderSystem();
                    system.setStepSize(sampleTime);
                    
                    this.server.setModel(system);
                }
                else if (systemValue.compareToIgnoreCase("freqres") == 0) 
                {
                    simulationserver.system.control.FreqResPlant system = new simulationserver.system.control.FreqResPlant();
                    system.setStepSize(sampleTime);
                    system.setMaxInputValue(10.0);
                    system.setMinInputValue(-10.0);
                    
                    this.server.setModel(system);
                }
                else if (systemValue.compareToIgnoreCase("caracs") == 0)
                {
                    simulationserver.system.control.CaracsPlant system = new simulationserver.system.control.CaracsPlant();
                    system.setStepSize(sampleTime);
                    system.setMaxOutputValue(10.0);
                    system.setMaxDeadZoneValue(2.5);
                    
                    // add group
                    final double[] groupsGains = {1.1, 1.45, 0.89, 1.28, 0.77, 0.88, 1.26, 1.54, 0.72};
                    final double[] groupsTaos = {1.21, 2.33, 3.14, 2.56, 3.12, 2.65, 1.66, 1.87, 2.05};
                    int groupIndex = group % 9;
                    
                    system.s_k = groupsGains[groupIndex];
                    system.s_b = groupsTaos[groupIndex];
                    
                    this.server.setModel(system);
                }
                else if (systemValue.compareToIgnoreCase("pid_rst_vel") == 0)
                {
                    simulationserver.system.System system;
                    final simulationserver.system.maths.BaseMatrix num = new simulationserver.system.maths.BaseMatrix(2, 1);
                    final simulationserver.system.maths.BaseMatrix den = new simulationserver.system.maths.BaseMatrix(2, 1);
                    num.set(0, 0, 0.0);
                    num.set(1, 0, 1.5);
                    den.set(1, 0, 1.0);
                    den.set(0, 0, 1.5);
                    
                    try {
                        system = new simulationserver.system.System(num, den);
                        system.setStepSize(sampleTime);
                        this.server.setModel(system);
                    } catch (Exception ex) {
                        System.out.println("Error setting the pid_rst model: "+ex.getMessage());
                        System.exit(0);
                    }
                }
                else if (systemValue.compareToIgnoreCase("pid_rst_pos") == 0) 
                {
                    simulationserver.system.System system;
                    final simulationserver.system.maths.BaseMatrix num = new simulationserver.system.maths.BaseMatrix(3, 1);
                    final simulationserver.system.maths.BaseMatrix den = new simulationserver.system.maths.BaseMatrix(3, 1);
                    num.set(0, 0, 0.0);
                    num.set(1, 0, 0.0);
                    num.set(2, 0, 1.5);
                    den.set(2, 0, 0.0);
                    den.set(1, 0, 2.0);
                    den.set(0, 0, 1.6);
                    
                    try {
                        system = new simulationserver.system.System(num, den);
                        system.setStepSize(sampleTime);
                        this.server.setModel(system);
                    } catch (Exception ex) {
                        System.out.println("Error setting the pid_rst model: "+ex.getMessage());
                        System.exit(0);
                    }
                }
                else if (systemValue.compareToIgnoreCase("pid") == 0) 
                {
                    simulationserver.system.control.Controlador pid = new simulationserver.system.control.Controlador();
                    
                    pid.Kp = kp;
                    pid.Ti = ki;
                    pid.Td = kd;
                    pid.N = N;
                    pid.Ts = Ts;
                    pid.b = b;
                    pid.c = c;
                    pid.bias_inicial = bias;
                    pid.sat_min = outputMin;
                    pid.sat_max = outputMax;
                    pid.referencia = setpoint;
                    
                    if (inverse) pid.setInverse();
                    
                    pid.setStepSize(sampleTime);
                    
                    this.server.setModel(pid);
                    
                    // show if setpoint gui must be shown
                    if (showSetpointGui) {
                        final simulationserver.system.gui.SetpointHandler setHandler = new simulationserver.system.gui.SetpointHandler();
                        setHandler.setModel(pid);
                        setHandler.setMaxValue(setpointMax);
                        setHandler.setMinValue(setpointMin);
                        setHandler.setValue(setpoint);
                        setHandler.start();
                    }
                }
                // predefined systems
                else if (systemValue.compareToIgnoreCase("planta1") == 0) {
                    simulationserver.system.control.Planta1 planta1 = new simulationserver.system.control.Planta1();
                    planta1.s_t = 20.0;
                    planta1.setDelayTime(20.0);
                    final int multiple = (int) (planta1.s_t / sampleTime);
                    RTAbstractModel.INITIAL_INPUT_BUFFER_SIZE = 2*multiple;
                    
                    planta1.setStepSize(sampleTime);
                    
                    this.server.setModel(planta1);
                } else if (systemValue.compareToIgnoreCase("planta2") == 0) {
                    simulationserver.system.control.Planta2 planta2 = new simulationserver.system.control.Planta2();
                    
                    planta2.setStepSize(sampleTime);
                    
                    this.server.setModel(planta2);
                } else if (systemValue.compareToIgnoreCase("planta3") == 0) {
                    simulationserver.system.control.Planta3 planta3 = new simulationserver.system.control.Planta3();
                    planta3.s_t = 3.0;
                    planta3.setDelayTime(3.0);
                    final int multiple = (int) (planta3.s_t / sampleTime);
                    RTAbstractModel.INITIAL_INPUT_BUFFER_SIZE = 2*multiple;
                    planta3.setStepSize(sampleTime);
                    
                    this.server.setModel(planta3);
                } else if (systemValue.compareToIgnoreCase("planta4") == 0) {
                    simulationserver.system.control.Planta4 planta4 = new simulationserver.system.control.Planta4();
                    planta4.s_t = 3.0;
                    planta4.setDelayTime(3.0);
                    final int multiple = (int) (planta4.s_t / sampleTime);
                    RTAbstractModel.INITIAL_INPUT_BUFFER_SIZE = 2*multiple;
                    planta4.setStepSize(sampleTime);
                    
                    this.server.setModel(planta4);
                }
            }
        }
    }
    
    /**
     * Setup the execution
     */
    @Override
    public void setupExecution() {
        System.out.println("Starting");
        System.out.println("Creating the server");
        System.out.println("Setting the server");
        
        try{
            this.server.setServer();
        }catch(Exception ex) {
            System.out.println("Exception in main: "+ex.getMessage());
        }
        
        System.out.println("Looping");
    }

    /**
     * Do the execution
     */
    @Override
    public void doExecution() {
        try{
            server.listen();
        }catch(Exception ex) {
            System.out.println("Exception in main: "+ex.getMessage());
        }
    }
    
    /**
     * Stop the loop
     */
    @Override
    public void stopLoop() {
        System.out.println("Closing the server");
        
        super.stopLoop();
        
        // close the server
        this.server.closeServer();
        System.out.println("Server closed");
    }
}
