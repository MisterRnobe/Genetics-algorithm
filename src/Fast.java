import Agents.utils.NeuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public class Fast {
    public static void main(String[] args) {
        ArrayList<Integer[]> list = new ArrayList<>();
        list.add(new Integer[]{9,8, 7});
        list.add(new Integer[]{4,2,6});
        list.add(new Integer[]{5});
        String s =  list.stream().flatMap(Arrays::stream).map(Object::toString).collect(joining(", "));
        System.out.println(
               s
        );
    }
    public static double[] getThetas()
    {
        NeuralNetwork n = new NeuralNetwork(new double[]{1,2,3,4,5,6,7,8,9,10}, 1, 2 ,2);
        return n.getThetas();
    }
    public static String toString(double[] d)
    {
        return Arrays.stream(d).boxed().map(Object::toString).collect(joining(", "));
    }
}
