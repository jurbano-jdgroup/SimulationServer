
package simulationserver;

/**
 *
 * @author Ernesto
 */
public class Request {
    private RuntimeConfiguration config;
    
    public void buildRequest(final String[] args) throws Exception{
        this.config = new RuntimeConfiguration();
        
        if (!this.config.parseArgs(args, false)) {
            this.config.printUSage();
            throw new Exception("Error parsing the application commands");
        }
        
        this.config.dumpOptions();
    }
    
    public RuntimeConfiguration getConfig() throws Exception {
        if (this.config == null)
            throw new Exception("The config object is not setted");
        
        return this.config;
    }
}