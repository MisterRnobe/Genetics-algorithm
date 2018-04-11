import Agents.utils.NeuralNetwork;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Fast {
    public static void main(String[] args) {
        NeuralNetwork n = new NeuralNetwork(new double[]{1,2,3,4,5,6,7,8,9,10}, 1, 2 ,2);
        double[] arr = n.getThetas();
        System.out.println(toString(arr));
        arr = n.getThetas(1);
        System.out.println(toString(arr));
        arr = n.apply(-10000);
        System.out.println(toString(arr));
    }
    private static String toString(double[] d)
    {
        return Arrays.stream(d).boxed().map(Object::toString).collect(Collectors.joining(", "));
    }
}
