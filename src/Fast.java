import Agents.AgentCell;
import Agents.utils.NeuralNetwork;
import Agents.utils.Vector2;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Fast {
    public static void main(String[] args) {
        double[] d = new double[]{2,2};
        AgentCell agentCell;
        System.out.println(Arrays.equals(d, d));
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
