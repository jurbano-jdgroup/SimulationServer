
package simulationserver.system;

import simulationserver.system.maths.Matrix;

/**
 *
 * @author Ernesto
 */
public interface StateSpace extends TransferFunction {
    Matrix<Number> getA();
    Matrix<Number> getB();
    Matrix<Number> getC();
    Matrix<Number> getD();
    void setA(Matrix<Number> matrix);
    void setB(Matrix<Number> matrix);
    void setC(Matrix<Number> matrix);
    void setD(Matrix<Number> matrix);
    boolean isControllable();
    boolean isObservable();
    Matrix<Number> eigenValues();
}

