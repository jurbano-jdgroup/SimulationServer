
package simulationserver.system;

import simulationserver.system.maths.BaseMatrix;
import simulationserver.system.maths.Matrix;

/**
 *
 * @author Ernesto
 */
public class System extends RTAbstractModel implements StateSpace  {
    // state space and transfer function vars
    protected Matrix<Number> num;
    protected Matrix<Number> den;
    protected Matrix<Number> A;
    protected Matrix<Number> B;
    protected Matrix<Number> C;
    protected Matrix<Number> D;
    // system dynamics vars
    protected Matrix<Number> states;
    protected Number system_output;
    
    /**
     * Construct the system based on a numerator
     * and denominator of a transfer function
     * 
     * @param num
     * @param den 
     * @throws java.lang.Exception 
     */
    public System(Matrix<Number> num, Matrix<Number> den) throws Exception {
        super();
        this.step_size = 1e-2;
        
        // check the num and den
        if(!checkVector(num) || !checkVector(den)) {
            throw new Exception("Num or Den is empty");
        }
        
        if(num.rows()!=den.rows()) {
            throw new Exception("Num and Den must have the same size");
        }
        
        this.num = num;
        this.den = den;
        
        // check the last value of the denominator, 
        // cannot be zero
        final int len = den.rows();
        final double lastValue = ((double)den.get(0, 0));
        
        if(lastValue < 1e-16) {
            throw new Exception("Last value of Den is zero");
        }
        
        // check if normalized
        double tmp;
        if(lastValue != 1.0) {
            for(int k=0; k<len; ++k) {
                tmp = (double) this.num.get(k, 0);
                this.num.set(k, 0, tmp/lastValue);
                tmp = (double) this.den.get(k, 0);
                this.den.set(k, 0, tmp/lastValue);
            }
        }
        
        // create the state space matrices
        final int order = len-1;
        this.A = new BaseMatrix(order, order);
        this.B = new BaseMatrix(order, 1);
        this.C = new BaseMatrix(1, order);
        this.D = new BaseMatrix(1,1);
        this.states = new BaseMatrix(order, 1);
        this.system_output = 0.0;
        
        ((BaseMatrix)this.states).fill(0.0);
        
        // form the B matrix
        ((BaseMatrix) this.B).fill(0.0);
        this.B.set(order-1, 0, 1.0);
        // form the D matrix
        this.D.set(0, 0, 0.0);
        
        // form the A matrix
        for(int k=0; k<(order-1); ++k) {
            this.A.set(k, 0, 0.0);
        }
        
        for(int i=0; i<(order-1); ++i) {
            for(int j=1; j<order; ++j) {
                this.A.set(i, j, (i+1)==j?1.0:0.0);
            }
        }
        
        for(int k=0; k<order; ++k) {
            tmp = ((double) this.den.get(len-1-k, 0)) * -1.0;
            this.A.set(order-1, k, tmp);
        }
        
        // form the C matrix
        for(int k=0; k<order; ++k) {
            tmp = ((double) this.num.get(len-1-k, 0));
            this.C.set(0, k, tmp);
        }
    }
    
    /**
     * 
     * @param vector
     * @return 
     */
    private boolean checkVector(Matrix<Number> vector) {
        return vector.cols()==1 && vector.rows()>0;
    }
    
    @Override
    public String getName() {
        return "Generic System";
    }

    @Override
    public void makeStep() {
        // update the time
        ++t_iter;
        this.setCurrentTime(((double)t_iter)*step_size);
        
        // calculate the value of the states
        final double inputValue = this.getCurrentInputValue();
        final Matrix bMulu = this.B.multiply(inputValue);
        final Matrix aMulx = this.A.multiply(this.states);
        final Matrix aAddb = aMulx.add(bMulu);
        this.states.selfAdd(aAddb.multiply(this.step_size));
        
        // calculate the output
        final Matrix dMulu = this.D.multiply(inputValue);
        final Matrix cMulx = this.C.multiply(this.states);
        double output = ((double) dMulu.get(0,0)) + ((double) cMulx.get(0,0));
        
        // update the output
        this.setCurrentOutputValue(output);
    }

    @Override
    public Matrix<Number> getA() {
        return this.A;
    }

    @Override
    public Matrix<Number> getB() {
        return this.B;
    }

    @Override
    public Matrix<Number> getC() {
        return this.C;
    }

    @Override
    public Matrix<Number> getD() {
        return this.D;
    }

    @Override
    public void setA(Matrix<Number> matrix) {
        // check the matrix
        if(matrix.rows()!=this.A.rows() || matrix.cols()!=this.A.cols()) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        
        this.A = matrix;
    }

    @Override
    public void setB(Matrix<Number> matrix) {
        // check the matrix
        if(matrix.rows()!=this.B.rows() || matrix.cols()!=this.B.cols()) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        
        this.B = matrix;
    }

    @Override
    public void setC(Matrix<Number> matrix) {
        // check the matrix
        if(matrix.rows()!=this.C.rows() || matrix.cols()!=this.C.cols()) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }

        this.C = matrix;
    }

    @Override
    public void setD(Matrix<Number> matrix) {
        // check the matrix
        if(matrix.rows()!=this.D.rows() || matrix.cols()!=this.D.cols()) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        
        this.D = matrix;
    }

    @Override
    public boolean isControllable() {
        return false;
    }

    @Override
    public boolean isObservable() {
        return false;
    }

    @Override
    public Matrix<Number> eigenValues() {
        return null;
    }

    @Override
    public Matrix<Number> getNum() {
        return this.num;
    }

    @Override
    public Matrix<Number> getDen() {
        return this.den;
    }

    @Override
    public void setNum(Matrix<Number> num) {
        
    }

    @Override
    public void setDen(Matrix<Number> den) {
        
    }
}
