package simulationserver.services.simulation;

import simulationserver.system.RTAbstractModel;

/**
 *
 * @author Ernesto
 */

public class RTClient extends ThreadWorkerAbstract implements UniqueErrorObserver, ThreadWorkerObserver {
    /** id */
    public int id = 0;
    /** listener list */
    private final java.util.List<RTClientConnectionListener> listenerList;
    /** send floats or doubles */
    private boolean send_float = true;
    /** real time simulation model */
    public RTAbstractModel model;
    /** connection socket */
    private final java.net.Socket socket;
    /** input worker */
    private InputWorker inputWoker;
    /** input sampling */
    private int inputSampling = 1;
    
    /* calculation vars */
    float float_value;
    int float_bits;
    long double_bits;
    byte[] float_array = new byte[4];
    byte[] double_array = new byte[8];
    
    public RTClient(java.net.Socket clientSocket) {
        super();
        
        this.socket = clientSocket;
        this.listenerList = new java.util.ArrayList<>(16);
        
        try{
            final java.io.InputStream input = this.socket.getInputStream();
            this.inputWoker = new InputWorker(input);
            this.inputWoker.setExecutionStep(1);
        }catch(Exception e){
            System.out.println("Error creating the input worker object: "+e.getMessage());
        }
    }
    
    public void setInputSampling (int inputSampling) {
        if (inputSampling<0) return;
        this.inputSampling = inputSampling;
    }
    
    /* add listener */
    public boolean addConnectionListener (RTClientConnectionListener listener) {
        if(this.listenerList.size() >= 16) {
            return false;
        }
        
        this.listenerList.add(listener);
        return true;
    }
    
    /* remove listener */
    public boolean removeConnectionListener (RTClientConnectionListener listener) {
        
        try{
            this.listenerList.remove(listener);
        }catch(Exception ex){
            return false;
        }          
        
        return true;
    }
    
    /* fire event */
    public void fireConnectionChangeEvent(int connectionStatus) {
        if(this.inputWoker != null){
            this.inputWoker.stopLoop();
            this.model.stopSimulation();
        }
        
        for(int i=0; i<this.listenerList.size(); ++i) {
            this.listenerList.get(i).onConnectionChange(this, connectionStatus);
        }
    }
    
    /* send data as float value */
    public void sendFloat() {
        this.send_float = true;
    }
    
    /* send doubles, bandwidth required */
    public void sendDouble() {
        this.send_float = false;
    }
    
    /* close the client socket */
    public void closeSocket () {
        try{
            this.socket.close();
        }catch(Exception ex) {
            System.out.println("Exception in RTClient: "+ex.getMessage());
        }
    }
    
    @Override
    public void stopLoop () {
        super.stopLoop();
        this.model.stopSimulation();
    }

    @Override
    public void onError(Object emitter) {
        this.fireConnectionChangeEvent(Server.CONNECTION_CLOSED);
    }

    @Override
    public synchronized void onExecutionDone(ThreadWorkerData data) {
        this.model.setCurrentInputValue((double) data.getData());
        this.model.makeStep();
    }

    @Override
    public synchronized void doExecution() {
        /* write data to the output stream */
        if(this.send_float) {
            float_value = (float) this.model.getCurrentOutputValue();
            float_bits = Float.floatToIntBits(float_value);
            float_array[0] = (byte) (float_bits >> 24);
            float_array[1] = (byte) (float_bits >> 16);
            float_array[2] = (byte) (float_bits >> 8);
            float_array[3] = (byte) (float_bits);

            try{
                this.socket.getOutputStream().write(float_array);
            }catch(Exception ex){
                System.out.println("Exception: "+ex.getMessage());

                //if(this.socket.isClosed() || this.socket.isOutputShutdown()) {
                    this.fireConnectionChangeEvent(Server.CONNECTION_CLOSED);
                //}
            }
        }else {
            double_bits = Double.doubleToLongBits(this.model.getCurrentOutputValue());
            double_array[0] = (byte) (double_bits >> 56);
            double_array[1] = (byte) (double_bits >> 48);
            double_array[2] = (byte) (double_bits >> 40);
            double_array[3] = (byte) (double_bits >> 32);
            double_array[4] = (byte) (double_bits >> 24);
            double_array[5] = (byte) (double_bits >> 16);
            double_array[6] = (byte) (double_bits >> 8);
            double_array[7] = (byte) (double_bits);

            try{
                this.socket.getOutputStream().write(double_array);
            }catch(Exception ex) {
                System.out.println("Exception: "+ex.getMessage());

                //if(this.socket.isClosed() || this.socket.isOutputShutdown()) {
                    this.fireConnectionChangeEvent(Server.CONNECTION_CLOSED);
                //}
            }
        }
    }

    @Override
    public void setupExecution() {
        if(this.inputWoker != null){
            if(this.send_float){
                this.inputWoker.sendFloat();
            }else{
                this.inputWoker.sendDouble();
            }
            
            System.out.println("System data type: "+(this.send_float?"Single":"Double"));
            
            this.model.setStepSize(((double)this.getExecutionStep())*1e-3);
            this.inputWoker.setExecutionStep(this.inputSampling);
            this.inputWoker.setErrorObserver(this);
            this.inputWoker.addWorkerObserverListener(this);
            this.inputWoker.start();
            
            System.out.println("Client started with dynamic: "+this.model.getName()+", "+
                    "input sampling: "+this.inputSampling+" ms, and output sampling: "+
                    this.getExecutionStep()+" ms.");
            System.out.println("Model started with sampling time: "+this.model.getStepSize());
        }
    }
}
