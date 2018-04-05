package Life;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Robot extends Entity{
    private static final int MAX_HP = 100;
    private Brain brain;


    private int hp = 35;
    private int rotation = 0;

    private List<List<Integer>> input, output;

    public Robot(int x, int y, int... neurons)
    {
        super(Type.ROBOT, x, y);
        this.brain = new Brain(neurons);
        input = new LinkedList<>();
        output = new LinkedList<>();
    }
    //Cloning
    public Robot(Brain brain, int x, int y, int mutationPercent)
    {
        super(Type.ROBOT, x, y);
        this.brain = brain.clone();
        this.brain.mutate(mutationPercent);
    }


    public int getHp() {
        return hp;
    }

    public void addHp(int hp) {
        this.hp += hp;
        if (this.hp > MAX_HP)
            this.hp = MAX_HP;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int apply(double... values)
    {
        double[] result = brain.apply(values);
        int max = 0;
        for (int i = 1; i < result.length; i++) {
            if (result[max] < result[i])
                max = i;
        }
//        int max = control(values);

        return max;
    }
    private int control(double... values)
    {
        int value = new Scanner(System.in).nextInt();
        save(values, value);
        if (input.size()%10 == 0)
            writeData();
        return value;
    }

    public Brain getBrain() {
        return brain;
    }
    private void save(double[] input, int output)
    {
        Integer[] inputInteger = new Integer[input.length];
        Arrays.setAll(inputInteger, i -> (int)input[i]);
        List<Integer> inputVector = Arrays.asList(inputInteger);
        List<Integer> outputVector = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            outputVector.add(i, i == output? 1: 0);
        }
        this.input.add(inputVector);
        this.output.add(outputVector);
    }
    private void writeData()
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("input.txt", true)))
        {
            for(List<Integer> list: input)
            {
                for(Integer value: list)
                {
                    writer.write(Integer.toString(value) + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true)))
        {
            for(List<Integer> list: output)
            {
                for(Integer value: list)
                {
                    writer.write(Integer.toString(value) + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
