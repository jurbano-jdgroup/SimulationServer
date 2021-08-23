
package simulationserver.system.maths;

/**
 *
 * @author Ernesto
 * @param <T>
 */
public interface Matrix<T> {
    int cols();
    int rows();
    T get(int row, int col);
    void set(int row, int col, T value);
    Matrix<T> dot(Matrix<T> vector);
    void selfAdd(Matrix<T> matrix);
    void selfAdd(T value);
    void selfSubs(Matrix<T> matrix);
    void selfSubs(T value);
    void selfMultiply(Matrix<T> matrix);
    void selfMultiply(T value);
    Matrix<T> add(Matrix<T> matrix);
    Matrix<T> subs(Matrix<T> matrix);
    Matrix<T> multiply(Matrix<T> matrix);
    Matrix<T> multiply(T value);
}
