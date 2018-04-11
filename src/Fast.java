import Agents.utils.NeuralNetwork;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Fast {
    public static void main(String[] args) {
        NeuralNetwork n = new NeuralNetwork(new double[]{1,2,3,4,5,6,7,8,9,10}, 1, 2 ,2);
        double[] arr = n.getThetas();
        System.out.println(toString(arr));
        arr = n.getThetas(0);
        System.out.println("Theta 0: " + toString(arr));
        arr = n.getThetas(1);
        System.out.println("Theta 1: " + toString(arr));
        arr = n.apply(-10000);
        System.out.println("Result = " + toString(arr) + " length = " + arr.length);
        Double d = 2.0;
    }
    public static double[] getThetas()
    {
        NeuralNetwork n = new NeuralNetwork(new double[]{1,2,3,4,5,6,7,8,9,10}, 1, 2 ,2);
        return n.getThetas();
    }
    private static String toString(double[] d)
    {
        return Arrays.stream(d).boxed().map(Object::toString).collect(Collectors.joining(", "));
    }
}
