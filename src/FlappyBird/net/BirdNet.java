package FlappyBird.net;

import FlappyBird.game.Manager;

import java.util.Arrays;
import java.util.Random;

public class BirdNet {
    public static final int BORDER = 1024;
    private NeuralNode[] nodes;

    public BirdNet(int[][] weights)
    {
        nodes = new NeuralNode[9];
        nodes[0] = new NeuralNode(1);
        nodes[0].setWeights(weights[0]);
        nodes[1] = new NeuralNode(1);
        nodes[1].setWeights(weights[1]);

        nodes[8] = new NeuralNode(6);
        nodes[8].setWeights(weights[8]);

        for (int i = 2; i < 8; i++) {
            nodes[i] = new NeuralNode(2);
            nodes[i].setWeights(weights[i]);

            nodes[i].addNextNeuron(nodes[8]);
            nodes[0].addNextNeuron(nodes[i]);
            nodes[1].addNextNeuron(nodes[i]);
        }

    }
    public double execute(int v1, int v2)
    {
        nodes[0].acceptInput(v1).output();
        nodes[1].acceptInput(v2).output();
        for (int i = 2; i < 8; i++) {
            nodes[i].output();
        }
        return nodes[8].getOutput();
    }
    public void mutate()
    {
        Random r = new Random();
        int neuron = r.nextInt(9);
        nodes[neuron].mutateWeight(BORDER);
    }
    public BirdNet clone()
    {
        int[][] weights = new int[9][];
        Arrays.setAll(weights, i-> nodes[i].getWeights());

        return new BirdNet(weights);
    }

    public static void main(String[] args) {
        BirdNet net = new BirdNet(Manager.getInstance().generateWeights());
        net.execute(0,0);
        net.execute(0,0);
    }

}
