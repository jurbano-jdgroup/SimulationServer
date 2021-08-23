
package simulationserver.system.maths;

/**
 *
 * @author Ernesto
 */
public class BaseMatrix implements Matrix<Number> {

    private final int cols;
    private final int rows;
    private final Number[] data;
    
    /**
     * Default constructor
     */
    public BaseMatrix() {
        cols = 2;
        rows = 2;
        data = new Number[4];
    }
    
    /**
     * 
     * @param rows
     * @param cols 
     */
    public BaseMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new Number[rows*cols];
    }
    
    /**
     * 
     * @param rows
     * @param cols
     * @param value 
     */
    public BaseMatrix(int rows, int cols, Number value) {
        this.rows = rows;
        this.cols = cols;
        data = new Number[rows*cols];
        
        this.fill(value);
    }
    
    /**
     * Set all the matrix entries with 
     * the value specified
     * 
     * @param value 
     */
    public void fill(Number value) {
        for(int i=0; i<rows; ++i) {
            for(int j=0; j<cols; ++j) {
                this.data[i*cols + j] = value;
            }
        }
    }
    
    /**
     * Set all the matrix entries based
     * on the specified matrix
     * 
     * @param matrix 
     */
    public void fill(Matrix<Number> matrix)  {
        // check the matrix
        if(matrix.rows()!=rows || matrix.cols()!=cols) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        
        for(int i=0; i<rows; ++i) {
            for(int j=0; j<cols; ++j) {
                this.set(i, j, matrix.get(i, j));
            }
        }
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public int cols() {
        return cols;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int rows() {
        return rows;
    }

    /**
     * 
     * @param row
     * @param col
     * @return 
     */
    @Override
    public Number get(int row, int col) {
        if(row>=rows || col>=cols) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        
        return data[row*cols + col];
    }

    /**
     * 
     * @param row
     * @param col
     * @param value 
     */
    @Override
    public void set(int row, int col, Number value) {
        if(row>=rows || col>=cols) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        
        data[row*cols + col] = value;
    }

    /**
     * 
     * @param vector
     * @return 
     */
    @Override
    public Matrix dot(Matrix vector) {
        // dot only if this is a vector
        if(cols!=1) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        
        return this.multiply(vector);
    }

    /**
     * 
     * @param matrix 
     */
    @Override
    public void selfAdd(Matrix<Number> matrix) {
        // check the matrix
        if(matrix.cols()!=cols || matrix.rows()!=rows){
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        
        for(int i=0; i<rows; ++i) {
            for(int j=0; j<cols; ++j) {
                final double value = ((double)this.get(i, j)) + ((double)matrix.get(i, j));
                this.set(i, j, value);
            }
        }
    }

    /**
     * 
     * @param value 
     */
    @Override
    public void selfAdd(Number value) {
        for(int i=0; i<rows; ++i) {
            for(int j=0; j<cols; ++j) {
                final double fVal = ((double)this.get(i, j)) + ((double)value);
                this.set(i, j, fVal);
            }
        }
    }

    /**
     * 
     * @param matrix 
     */
    @Override
    public void selfSubs(Matrix<Number> matrix) {
        // check the matrix
        if(matrix.cols()!=cols || matrix.rows()!=rows){
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        
        for(int i=0; i<rows; ++i) {
            for(int j=0; j<cols; ++j) {
                final double value = ((double)this.get(i, j)) - ((double)matrix.get(i, j));
                this.set(i, j, value);
            }
        }
    }

    /**
     * 
     * @param value 
     */
    @Override
    public void selfSubs(Number value) {
        for(int i=0; i<rows; ++i) {
            for(int j=0; j<cols; ++j) {
                final double fVal = ((double)this.get(i, j)) - ((double)value);
                this.set(i, j, fVal);
            }
        }
    }

    /**
     * 
     * @param matrix 
     */
    @Override
    public void selfMultiply(Matrix<Number> matrix) {
        final Matrix ret = this.multiply(matrix);
        this.fill(ret);
    }

    /**
     * 
     * @param value 
     */
    @Override
    public void selfMultiply(Number value) {
        final Matrix ret = this.multiply(value);
        this.fill(ret);
    }

    @Override
    public Matrix<Number> add(Matrix<Number> matrix) {
        // check the matrix
        if(matrix.cols()!=cols || matrix.rows()!=rows) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        
        Matrix ret = new BaseMatrix(rows, cols);
        
        for(int i=0; i<rows; ++i) {
            for(int j=0; j<cols; ++j) {
                final double finalValue = ((double)this.get(i, j)) + ((double)matrix.get(i, j));
                ret.set(i, j, finalValue);
            }
        }
        
        return ret;
    }

    /**
     * 
     * @param matrix
     * @return 
     */
    @Override
    public Matrix<Number> subs(Matrix<Number> matrix) {
        // check the matrix
        if(matrix.cols()!=cols || matrix.rows()!=rows) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        
        Matrix ret = new BaseMatrix(rows, cols);
        
        for(int i=0; i<rows; ++i) {
            for(int j=0; j<cols; ++j) {
                final double finalValue = ((double)this.get(i, j)) - ((double)matrix.get(i, j));
                ret.set(i, j, finalValue);
            }
        }
        
        return ret;
    }

    /**
     * 
     * @param matrix
     * @return 
     */
    @Override
    public Matrix<Number> multiply(Matrix<Number> matrix) {
        // check the matrix
        if(matrix.rows()!=cols) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        
        Matrix ret = new BaseMatrix(rows, matrix.cols());
        
        for(int i=0; i<rows; ++i) {
            for(int j=0; j<matrix.cols(); ++j) {
                
                double tmp = 0.0;
                for(int k=0; k<cols; ++k) {
                    tmp += ((double)this.get(i, k)) * ((double)matrix.get(k, j));
                }
                
                ret.set(i, j, tmp);
            }
        }
        
        return ret;
    }

    /**
     * 
     * @param value
     * @return 
     */
    @Override
    public Matrix<Number> multiply(Number value) {
        Matrix ret = new BaseMatrix(rows, cols);
        
        for(int i=0; i<rows; ++i) {
            for(int j=0; j<cols; ++j) {
                final double finalValue = ((double)this.get(i, j)) * ((double)value);
                ret.set(i, j, finalValue);
            }
        }
        
        return ret;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        String ret = "" ;
        
        for(int i=0; i<rows; ++i) {
            ret += "[";
            
            for(int j=0; j<cols; ++j) {
                ret += this.get(i, j) + (j<(cols-1)?"\t":"");
            }
            
            ret += "]\n";
        }
        
        return ret;
    }
    
    public void dump() {
        final String ret = this.toString();
        System.out.println(ret);
    }
}
