package Game2048;

import FlappyBird.net.NeuralNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Player {
    private NeuralNode[] input;
    private NeuralNode[] hidden;
    private NeuralNode[] output;
    private static final int BORDER = 512;
    //For random
    public Player()
    {
        init();
        Random r = new Random();

        int[] weights = new int[16];

        for(NeuralNode n: input)
        {
            n.setWeights(new int[]{1});
        }


        for(NeuralNode n: hidden)
        {
            Arrays.setAll(weights, i->r.nextInt(2*BORDER) - BORDER);
            n.setWeights(weights);
        }


        for(NeuralNode n: output)
        {
            Arrays.setAll(weights, i->r.nextInt(2*BORDER) - BORDER);
            n.setWeights(weights);
        }
    }
    //For cloning
    public Player(Player p)
    {
        init();
        for (int i = 0; i < input.length; i++) {
            input[i].setWeights(p.input[i].getWeights());
            hidden[i].setWeights(p.hidden[i].getWeights());
        }
        for (int i = 0; i < output.length; i++) {
            output[i].setWeights(p.output[i].getWeights());
        }

    }

    public void mutate(int percent)
    {
        int totalNeurons = 20;
        int iterations = 320*percent/100;
        Random r = new Random();
        for (int i = 0; i < iterations; i++) {
            int weight = r.nextInt(totalNeurons);
            NeuralNode[] array;
            if (weight<16)
                array = hidden;
            else
                {
                    weight -= 16;
                    array = output;
                }
            array[weight].mutateWeight(BORDER);

        }
    }
    public int execute(double[] values)
    {
        for (int i = 0; i < values.length; i++){
            input[i].acceptInput(values[i]);
            input[i].output();
        }
        Arrays.stream(hidden).forEach(NeuralNode::output);
        double[] outputs = new double[4];
        Arrays.setAll(outputs, i -> output[i].getOutput());
        int max = 0;
        for (int i = 1; i < outputs.length; i++) {
            if (outputs[i] > outputs[max])
                max = i;
        }
        return max;

    }

    private void init(){
        input = new NeuralNode[16];
        hidden = new NeuralNode[16];
        output = new NeuralNode[4];
        Arrays.setAll(input, i-> new NeuralNode(1));
        Arrays.setAll(hidden, i-> new NeuralNode(16));
        Arrays.setAll(output, i-> new NeuralNode(16));

        for (NeuralNode i : input)
        {
            for (NeuralNode h: hidden) {
                i.addNextNeuron(h);
            }
        }
        for (NeuralNode h : hidden)
        {
            for (NeuralNode o: output) {
                h.addNextNeuron(o);
            }
        }

    }
}
