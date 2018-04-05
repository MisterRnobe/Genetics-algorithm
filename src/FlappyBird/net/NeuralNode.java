package FlappyBird.net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NeuralNode {
    private ArrayList<NeuralNode> nextLayer;
    private int[] weights;
    private double[] input;

    public NeuralNode(int number)
    {
        weights = new int[number];
        input = new double[number];
        nextLayer = new ArrayList<>();
        Arrays.setAll(input, i->-1d);
    }
    public NeuralNode acceptInput(double input)
    {
        int i = 0;
        for (;i < this.input.length; i++) {
            if (this.input[i] == -1)
                break;
        }
        if (i == this.input.length)
            throw new RuntimeException("Too much inputs. Expected: " + this.input.length+ ", received: "+i);
        this.input[i] = input;
        return this;
    }
    public void output()
    {
        double sum = getOutput();
        nextLayer.forEach(neuralNode -> neuralNode.acceptInput(sum));
    }
    public double getOutput()
    {
        double value = 0;
        for (int i = 0; i < weights.length; i++) {
            value += weights[i]*input[i];
        }
        Arrays.setAll(input, i->-1);
        return function(value);
    }
    public NeuralNode setWeights(int[] weights)
    {
        if (weights.length != this.weights.length)
            throw new RuntimeException("Wrong size of given array of weights. Expected: "+this.weights.length+
            ", found: "+weights.length);

        System.arraycopy(weights, 0, this.weights, 0, weights.length);
        return this;
    }

    public int[] getWeights() {
        return weights;
    }

    public void mutateWeight(int border)
    {
        Random r = new Random();
        int index = r.nextInt(weights.length);
        int value = r.nextInt(2*border) - border;
        weights[index] = value;
    }
    public void addNextNeuron(NeuralNode node)
    {
        nextLayer.add(node);
    }

    protected double function(double value)
    {
        return 1/(1+Math.exp(-value));
    }
}
