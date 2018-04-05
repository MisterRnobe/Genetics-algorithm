package Life;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Statistic {
    private static Statistic ourInstance = new Statistic();

    public static Statistic getInstance() {
        return ourInstance;
    }
    public int generation = 0;

    private List<Integer> lifetime;
    private Robot top;
    private Statistic() {
        lifetime = new ArrayList<>();
    }
    public void addLifetime(int time, Robot top)
    {
        generation++;
        lifetime.add(time);
        this.top = top;
        new Thread(this::saveRobot).start();
        new Thread(this::saveStat).start();
    }
    public int[] getLastLifetime(int count)
    {
        count = count<lifetime.size()? count: lifetime.size();
        int[] reply = new int[count];
        for (int i = 0; i < count; i++) {
            reply[i] = lifetime.get(lifetime.size() - 1 - i);
        }
        return reply;
    }
    public void saveRobot()
    {
//        try(BufferedWriter writer = new BufferedWriter(new FileWriter("genome.txt", true)))
//        {
//            writer.write("\nGeneration: "+generation+"\n");
//            for (int i = 0; i < VirtualMachine.MAX_COMMAND_COUNT; i++) {
//                writer.write(Integer.toString(top.getCommand(i))+", ");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public void saveStat()
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("stat.txt")))
        {
            for (int i = 0; i < lifetime.size(); i++) {
                writer.write(i+": "+lifetime.get(i)+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
