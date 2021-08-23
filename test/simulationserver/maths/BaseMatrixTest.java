/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulationserver.maths;

import simulationserver.system.maths.BaseMatrix;
import simulationserver.system.maths.Matrix;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Ernesto
 */
public class BaseMatrixTest {
    
    public BaseMatrixTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }

    /**
     * Test of fill method, of class BaseMatrix.
     */
    @Test
    public void testFill_Number() {
        System.out.println("fill");
        Number value = null;
        BaseMatrix instance = new BaseMatrix();
        instance.fill(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fill method, of class BaseMatrix.
     */
    @Test
    public void testFill_Matrix() {
        System.out.println("fill");
        Matrix<Number> matrix = null;
        BaseMatrix instance = new BaseMatrix();
        instance.fill(matrix);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cols method, of class BaseMatrix.
     */
    @Test
    public void testCols() {
        System.out.println("cols");
        BaseMatrix instance = new BaseMatrix();
        int expResult = 0;
        int result = instance.cols();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rows method, of class BaseMatrix.
     */
    @Test
    public void testRows() {
        System.out.println("rows");
        BaseMatrix instance = new BaseMatrix();
        int expResult = 0;
        int result = instance.rows();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class BaseMatrix.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int row = 0;
        int col = 0;
        BaseMatrix instance = new BaseMatrix();
        Number expResult = null;
        Number result = instance.get(row, col);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of set method, of class BaseMatrix.
     */
    @Test
    public void testSet() {
        System.out.println("set");
        int row = 0;
        int col = 0;
        Number value = null;
        BaseMatrix instance = new BaseMatrix();
        instance.set(row, col, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dot method, of class BaseMatrix.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        Matrix vector = null;
        BaseMatrix instance = new BaseMatrix();
        Matrix expResult = null;
        Matrix result = instance.dot(vector);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selfAdd method, of class BaseMatrix.
     */
    @Test
    public void testSelfAdd_Matrix() {
        System.out.println("selfAdd");
        Matrix<Number> matrix = null;
        BaseMatrix instance = new BaseMatrix();
        instance.selfAdd(matrix);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selfAdd method, of class BaseMatrix.
     */
    @Test
    public void testSelfAdd_Number() {
        System.out.println("selfAdd");
        Number value = null;
        BaseMatrix instance = new BaseMatrix();
        instance.selfAdd(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selfSubs method, of class BaseMatrix.
     */
    @Test
    public void testSelfSubs_Matrix() {
        System.out.println("selfSubs");
        Matrix<Number> matrix = null;
        BaseMatrix instance = new BaseMatrix();
        instance.selfSubs(matrix);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selfSubs method, of class BaseMatrix.
     */
    @Test
    public void testSelfSubs_Number() {
        System.out.println("selfSubs");
        Number value = null;
        BaseMatrix instance = new BaseMatrix();
        instance.selfSubs(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selfMultiply method, of class BaseMatrix.
     */
    @Test
    public void testSelfMultiply_Matrix() {
        System.out.println("selfMultiply");
        Matrix<Number> matrix = null;
        BaseMatrix instance = new BaseMatrix();
        instance.selfMultiply(matrix);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selfMultiply method, of class BaseMatrix.
     */
    @Test
    public void testSelfMultiply_Number() {
        System.out.println("selfMultiply");
        Number value = null;
        BaseMatrix instance = new BaseMatrix();
        instance.selfMultiply(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class BaseMatrix.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Matrix<Number> matrix = null;
        BaseMatrix instance = new BaseMatrix();
        Matrix<Number> expResult = null;
        Matrix<Number> result = instance.add(matrix);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of subs method, of class BaseMatrix.
     */
    @Test
    public void testSubs() {
        System.out.println("subs");
        Matrix<Number> matrix = null;
        BaseMatrix instance = new BaseMatrix();
        Matrix<Number> expResult = null;
        Matrix<Number> result = instance.subs(matrix);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of multiply method, of class BaseMatrix.
     */
    @Test
    public void testMultiply_Matrix() {
        System.out.println("multiply");
        Matrix<Number> matrix = null;
        BaseMatrix instance = new BaseMatrix();
        Matrix<Number> expResult = null;
        Matrix<Number> result = instance.multiply(matrix);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of multiply method, of class BaseMatrix.
     */
    @Test
    public void testMultiply_Number() {
        System.out.println("multiply");
        Number value = null;
        BaseMatrix instance = new BaseMatrix();
        Matrix<Number> expResult = null;
        Matrix<Number> result = instance.multiply(value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class BaseMatrix.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BaseMatrix instance = new BaseMatrix();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dump method, of class BaseMatrix.
     */
    @Test
    public void testDump() {
        System.out.println("dump");
        BaseMatrix instance = new BaseMatrix();
        instance.dump();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}