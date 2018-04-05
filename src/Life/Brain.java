package Life;

import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.matrix.DenseMatrix;

import java.util.Arrays;
import java.util.Random;

public class Brain {
    private DenseMatrix[] thetas;
    private int weights;
    public Brain(int... neurons)
    {
        weights = 0;
        for (int i = 0; i < neurons.length-1; i++) {
            weights += (neurons[i]+1) * neurons[i+1];
        }
        thetas = new DenseMatrix[neurons.length - 1];
        Arrays.setAll(thetas, i -> DenseMatrix.random(neurons[i+1], neurons[i]+1, new Random()));
    }
    public Brain(double[][][] thetasArray, int weights)
    {
        this.weights = weights;
        thetas = new DenseMatrix[thetasArray.length];
        Arrays.setAll(thetas, i ->
            DenseMatrix.from2DArray(thetasArray[i]));
    }
    public double[] apply(double... values)
    {
        Matrix m = DenseMatrix.from1DArray(values.length, 1, values);
        Vector one = Vector.fromArray(new double[]{1d});
        //m = m.insertRow(0, one);
        for (Matrix theta: thetas) {
            m = m.insertRow(0, one);
            m = theta.multiply(m);
            m.update((i, j, v) -> sigmoid(v));
        }
        double[] result = new double[m.rows()];
        int i = 0;
        for (Double aM : m) {
            result[i++] = aM;
        }
        return result;
    }
    private static double sigmoid(double d)
    {
        return 1d/(1d+Math.exp(-d));
    }
    public Brain clone()
    {
        double[][][] thetasArray = new double[thetas.length][][];
        for (int i = 0; i < thetas.length; i++) {
            thetasArray[i] = thetas[i].toArray();
        }
        return new Brain(thetasArray, weights);
    }
    public void mutate(int percent)
    {
        int border = 2;
        int count = percent*weights / 100;
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            double value = Math.random()*2*border - border;
            DenseMatrix m = thetas[r.nextInt(thetas.length)];
            int row = r.nextInt(m.rows()), column = r.nextInt(m.columns());
            m.set(row, column, value);
        }
    }
}
