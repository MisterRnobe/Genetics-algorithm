package Agents.utils;

import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.matrix.DenseMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

public class NeuralNetwork {
    private DenseMatrix[] thetas;
    private Function<Double, Double> function = NeuralNetwork::sigmoid;


    public NeuralNetwork(int... neurons)
    {
        thetas = new DenseMatrix[neurons.length - 1];
        Arrays.setAll(thetas, i -> DenseMatrix.random(neurons[i+1], neurons[i]+1, new Random()));
    }
    public NeuralNetwork(double[][][] thetasArray)
    {
        thetas = new DenseMatrix[thetasArray.length];
        Arrays.setAll(thetas, i ->
                DenseMatrix.from2DArray(thetasArray[i]));
    }
    public NeuralNetwork(double[] thetasArray, int... neurons)
    {
        thetasArray = thetasArray.clone();
        thetas = new DenseMatrix[neurons.length - 1];
        for (int i = 0; i < thetas.length; i++) {
            thetas[i] = DenseMatrix.from1DArray(neurons[i+1], neurons[i]+1, thetasArray);
            thetasArray = Arrays.copyOfRange(thetasArray, neurons[i+1]*(neurons[i]+1), thetasArray.length);
        }
    }
    public double[] getThetas()
    {
        double[][][] thetas = new double[this.thetas.length][][];
        Arrays.setAll(thetas, i-> this.thetas[i].toArray());
        ArrayList<Double> arrayList = new ArrayList<>();
        for (int i = 0; i < thetas.length; i++) {
            for (int j = 0; j < thetas[i].length; j++) {
                for (int k = 0; k < thetas[i][j].length; k++) {
                    arrayList.add(thetas[i][j][k]);
                }
            }
        }
        return arrayList.stream().mapToDouble(Double::doubleValue).toArray();
    }
    public double[] getThetas(int layer)
    {
        double[][] thetas = this.thetas[layer].toArray();
        int rows = thetas.length, columns = thetas[0].length;
        double[] unrolled = new double[rows*columns];
        Arrays.setAll(unrolled, i->thetas[i/columns][i%columns]);
        return unrolled;
    }
    public double[] apply(double... values)
    {
        DenseMatrix m = DenseMatrix.from1DArray(values.length, 1, values);
        Vector one = Vector.fromArray(new double[]{1d});
        for (Matrix theta: thetas) {
            m = (DenseMatrix) m.insertRow(0, one);
            m = (DenseMatrix) theta.multiply(m);
            m.update((i, j, v) -> function.apply(v));
        }
        return m.toArray()[0];
    }
    public NeuralNetwork clone()
    {
        double[][][] thetasArray = new double[thetas.length][][];
        for (int i = 0; i < thetas.length; i++) {
            thetasArray[i] = thetas[i].toArray();
        }
        return new NeuralNetwork(thetasArray);
    }

    public void setFunction(Function<Double, Double> function) {
        this.function = function;
    }

    private static double sigmoid(double d)
    {
        return 1/(1+Math.exp(-d));
    }
}
