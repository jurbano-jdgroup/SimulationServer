
package simulationserver.services.simulation;

/**
 *
 * @author Ernesto
 */
public abstract class ThreadWorkerAbstract extends Thread implements ThreadWorker {
    
    protected long executionStep = 10;
    protected boolean running = true;
    
    /* worker observers list */
    protected java.util.List<ThreadWorkerObserver> listeners;
    
    public ThreadWorkerAbstract() {
        super();
        
        this.listeners = new java.util.ArrayList<>(16);
    }
    
    public void addWorkerObserverListener (ThreadWorkerObserver listener) {
        if(this.listeners.size() == 16){
            return;
        }
        
        this.listeners.add(listener);
    }
    
    public boolean removeWorkerObserver (ThreadWorkerObserver listener) {
        boolean ret = false;
        
        try{
            ret = this.listeners.remove(listener);
        }catch(Exception e){}
        
        return ret;
    }
    
    public void fireOnExecution (ThreadWorkerData data) {
        for(int i=0; i<this.listeners.size(); ++i){
            this.listeners.get(i).onExecutionDone(data);
        }
    }
    
    public synchronized void stopLoop() {
        this.running = false;
    }
    
    public synchronized boolean isRunning () {
        return this.running;
    }
    
    @Override
    public void setExecutionStep (long step) {
        this.executionStep = step;
    }
    
    @Override
    public long getExecutionStep () {
        return this.executionStep;
    }
    
    @Override
    public void run() {
        
        this.setupExecution();
        
        while(this.isRunning()) {
            this.doExecution();
            
            if (this.getExecutionStep() > 0) {
                try{
                    Thread.sleep(this.getExecutionStep());
                }catch(Exception e){}                
            }
        }
    }
}
