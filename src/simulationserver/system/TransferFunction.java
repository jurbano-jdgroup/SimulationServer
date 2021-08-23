
package simulationserver.control;

import simulationserver.maths.Matrix;

/**
 *
 * @author Ernesto
 */
public interface TransferFunction {
    Matrix<Number> getNum();
    Matrix<Number> getDen();
    void setNum(Matrix<Number> num);
    void setDen(Matrix<Number> den);
}
