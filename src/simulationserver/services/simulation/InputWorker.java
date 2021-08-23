
package simulationserver.services.simulation;

/**
 *
 * @author Ernesto
 */
public class InputWorker extends ThreadWorkerAbstract {

    protected java.io.InputStream inputStream;

    /* calculation vars */
    float float_value;
    double double_value;
    int float_bits;
    long double_bits;
    byte[] float_array = new byte[4];
    byte[] double_array = new byte[8];
    int available = 0;
    
    /* float or double data */
    private boolean send_float = true;
    
    /* error observer */
    protected UniqueErrorObserver<InputWorker> errorObserver;
    
    public InputWorker (java.io.InputStream input) {
        super();
        this.inputStream = input;
        // clean the input stream
        try{
            if (this.inputStream.available()>0) {
                this.inputStream.skip(this.inputStream.available());
            }
        }catch(Exception ex){}
    }
    
    void setErrorObserver (UniqueErrorObserver<InputWorker> errorObserver) {
        this.errorObserver = errorObserver;
    }

    public void sendFloat () {
        this.send_float = true;
    }
    
    public void sendDouble () {
        this.send_float = false;
    }
    
    @Override
    public void doExecution() {
        try{
            available = this.inputStream.available();
            
            if(this.send_float && available>=4) {
                this.inputStream.read(float_array, 0, 4);

                float_bits = 0;
                float_bits += ((int) float_array[0] ) << 24;
                float_bits += ((int) float_array[1] ) << 16;
                float_bits += ((int) float_array[2] ) << 8;
                float_bits += ((int) float_array[3] );

                float_value = Float.intBitsToFloat(float_bits);
                //System.out.println("float input: "+double_value+", available: "+available+", hex: "+String.format("%x", float_bits));
                this.fireOnExecution(new ThreadWorkerData<>((double)float_value));
                
            }else if(!this.send_float && available>=8){
                this.inputStream.read(double_array, 0, 8);

                double_bits = 0;
                double_bits |= (((long) double_array[0] ) << 56) & 0xff00000000000000L;
                double_bits |= (((long) double_array[1] ) << 48) & 0x00ff000000000000L;
                double_bits |= (((long) double_array[2] ) << 40) & 0x0000ff0000000000L;
                double_bits |= (((long) double_array[3] ) << 32) & 0x000000ff00000000L;
                double_bits |= (((long) double_array[4] ) << 24) & 0x00000000ff000000L;
                double_bits |= (((long) double_array[5] ) << 16) & 0x0000000000ff0000L;
                double_bits |= (((long) double_array[6] ) << 8) & 0x000000000000ff00L;
                double_bits |= (((long) double_array[7] )) & 0x00000000000000ffL;

                double_value = Double.longBitsToDouble(double_bits);
                //System.out.println("double input: "+double_value+", available: "+available+
                //        ", hex: "+String.format("%x", double_bits));
                this.fireOnExecution(new ThreadWorkerData<>((double)double_value));
            }
        }catch(Exception ex){
            System.out.println("Exception in InputWorker data reading: "+ex.getLocalizedMessage());
            
            if(this.errorObserver != null){
                this.errorObserver.onError(this);
            }
        }
    }

    @Override
    public void setupExecution() {
        
    }
}
