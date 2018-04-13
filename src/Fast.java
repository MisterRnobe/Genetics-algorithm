import Agents.utils.NeuralNetwork;
import Agents.utils.Vector2;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Fast {
    public static void main(String[] args) {
        Vector2 vec1 = new Vector2(1,1);
        Vector2 vec2 = new Vector2(1, 0);
        System.out.println(vec1.angle(vec2));
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
