
package simulationserver.services.simulation;

import simulationserver.system.RTAbstractModel;

/**
 *
 * @author Ernesto
 */
public class Server implements RTClientConnectionListener {
    
    /** max connections allowed */
    public static final int MAX_CONNECTIONS_ALLOWED = 64;
    /** connection status */
    public static final int CONNECTION_CLOSED = 0;
    // public static final int CONNECTION_OPENED = 1;
    private int port = 10080;
    private int backlog = 5;
    private int inputSampling = 1;
    private int outputSampling = 10;
    private java.net.ServerSocket server_socket;
    private boolean opened = false;
    /* clients track list */
    private final java.util.List<RTClient> clientsList;
    /** model */
    private RTAbstractModel model = null;
    
    /**
     * Default constructor
     */
    public Server(){
        this.clientsList = new java.util.ArrayList<>(this.backlog);
    }
    
    /**
     * Create this server setting the port and max connections
     * 
     * @param port port of the server
     * @param backlog max connections allowed
     */
    public Server(int port, int backlog){
        this.port = port;
        this.backlog = backlog;
        this.clientsList = new java.util.ArrayList<>(this.backlog);
    }
    
    /**
     * Create this server setting the port
     * 
     * @param port port of the server
     */
    public Server (int port) {
        this.port = port;
        this.backlog = 5;
        this.clientsList = new java.util.ArrayList<>(this.backlog);
    }
    
    /**
     * Set the server port
     * @param port 
     */
    public void setPort (int port) {
        if (port<0) return;
        this.port = port;
    }
    
    /**
     * Get the port value
     * @return 
     */
    public int getPort() {
        return this.port;
    }
    
    /**
     * Set the backlog
     * @param backlog 
     */
    public void setBacklog (int backlog) {
        if (backlog<0 || backlog>Server.MAX_CONNECTIONS_ALLOWED) return;
        this.backlog = backlog;
    }
    
    /**
     * Get the backlog
     * @return 
     */
    public int getBacklog() {
        return this.backlog;
    }
    
    /**
     * Set the input sampling
     * @param inputSampling 
     */
    public void setInputSampling (int inputSampling) {
        if (inputSampling<0) return;
        this.inputSampling = inputSampling;
    }
    
    /**
     * Set the output sampling
     * @param outputSampling 
     */
    public void setOutputSampling (int outputSampling) {
        if (outputSampling<0) return;
        this.outputSampling = outputSampling;
    }
    
    /**
     * Set the model to pass to the client
     * @param model 
     */
    public void setModel (RTAbstractModel model) {
        this.model = model;
    }
    
    /**
     * Open the server socket
     * 
     * @throws java.io.IOException refer to socket handling
     */
    public void setServer() throws java.io.IOException {
        this.server_socket = new java.net.ServerSocket(this.port, this.backlog);
        this.opened = true;
        System.out.println("Server started at port: "+this.port+", with backlog: "+this.backlog);
    }
    
    /**
     * Open the server socked if close ans start listening
     * for new incoming connections
     * 
     * @throws Exception if no model has been setted
     */
    public void listen() throws Exception {
        if(this.model == null){
            throw new Exception("Must set a model to start the server");
        }
        
        if(!this.opened){
            this.setServer();
        }
        
        if(this.clientsList.size() == this.backlog){
            // max clients allowed
            System.out.println("Max clients reached");
            return;
        }
        
        System.out.println("Clients connected: "+this.clientsList.size());
        System.out.println("Listening a new connection");
        
        final RTClient client = new RTClient(this.server_socket.accept());
        
        /* find next valid id */
        int valid_id = 0;
        for(int i=0; i<this.clientsList.size(); ++i){
            if(this.clientsList.get(i).id >= valid_id){
                valid_id = this.clientsList.get(i).id + 1;
            }
        }
        
        client.setExecutionStep(this.outputSampling);
        client.setInputSampling(this.inputSampling);
        client.id = valid_id;
        client.addConnectionListener(this);
        client.model = this.model;
        client.sendDouble();
        this.clientsList.add(client);
        
        /* start running client */
        client.start();
    }
    
    /**
     * 
     * @return 
     */
    public boolean isOpened() {
        return this.opened;
    }
    
    /**
     * 
     */
    public void closeServer() {
        if(!this.opened){
            return;
        }
        
        // stop all the clients
        for(int i=0; i<this.clientsList.size(); ++i) {
            try{
                this.clientsList.get(i).stopLoop();
                this.clientsList.get(i).closeSocket();
            }catch(Exception ex){}
        }
        
        // clear the clients list
        this.clientsList.clear();
        
        try{
            this.server_socket.close();
        }catch(java.io.IOException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        
        this.opened = false;
    }
    
    /**
     * Get connections count
     * @return 
     */
    public synchronized int connectionsCount () {
        return this.clientsList.size();
    }
    
    /**
     * Get the simulation time if any connection
     * and a model has been setted
     * 
     * @return Simulation time of the first index connection
     */
    public synchronized double getSimulationTime () {
        if(this.clientsList.isEmpty()){
            return -1.0;
        }
        
        return this.clientsList.get(0).model.getCurrentTime();
    }

    /**
     * 
     * @param client
     * @param connectionStatus 
     */
    @Override
    public void onConnectionChange(RTClient client, int connectionStatus) {
        if(connectionStatus == Server.CONNECTION_CLOSED) {
            try{
                /* try to close the client */
                client.stopLoop();
                client.closeSocket();
                
            }catch(Exception ex){
                System.out.println("Error closing the connection with client: "+ex.getMessage());
            }
            
            // causes an exception on server closing
            if(this.clientsList.isEmpty()){
                return;
            }
            
            /* deleting the client from the track list */
            int index = 0;
            for(int i=0; i<this.clientsList.size(); ++i){
                if(this.clientsList.get(i).id == client.id) {
                    index = i;
                }
            }
            
            this.clientsList.remove(index);
        }
    }
}
