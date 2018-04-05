package Life;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Manager {
    private static Manager instance;

    List<Robot> robots;
    Stack<Robot> died;
    VirtualMachine machine = new VirtualMachine();
    private int currentLifetime = 0;
    private final int MUTATION_PERCENT = 6;

    private Manager()
    {
        instance = this;
        robots = new LinkedList<>();
        died = new Stack<>();
        double[][][] array = readArray();
        Brain b = new Brain(array, 280);
        Random r = new Random();
        for (int i = 0; i < 32; ) {
            Robot robot = new Robot(b, r.nextInt(Map.WIDTH), r.nextInt(Map.HEIGHT),0);//new Robot(r.nextInt(Map.WIDTH), r.nextInt(Map.HEIGHT), 8, 16, 8);
            if (Map.addEntity(robot))
            {
                robots.add(robot);
                i++;
            }
        }
        generate(Map.MAX_FOOD*3/4, Type.FOOD);
        generate(Map.MAX_POISON*3/4, Type.POISON);
    }
    public void nextStep()
    {
        for(Iterator<Robot> iterator = robots.iterator(); iterator.hasNext(); )
        {
            Robot robot = iterator.next();
            machine.execute(robot);
            if (robot.getHp() < 0)
            {
                died.push(robot);
                iterator.remove();
            }
        }
        currentLifetime++;

        if (Map.foodCount < Map.MAX_FOOD)
            generate(Map.MAX_FOOD/10, Type.FOOD);
        if (Map.poisonCount < Map.MAX_POISON)
            generate(Map.MAX_POISON/10, Type.POISON);
        if (robots.isEmpty()) {
            Statistic.getInstance().addLifetime(currentLifetime, died.peek());
            currentLifetime = 0;
            Map.clearMap();
            generate(Map.MAX_FOOD, Type.FOOD);
            generate(Map.MAX_POISON, Type.POISON);
            generateRobots();

        }
    }
    public static Manager getInstance() {
        if (instance == null)
            instance = new Manager();
        return instance;
    }
    private void generateRobots()
    {
        Random r = new Random();
        Robot[] robotsArray = new Robot[8];
        for (int i = 0; i < robotsArray.length; i++) {
            robotsArray[i] = died.pop();
        }
        died = new Stack<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8;) {
                Robot oldRobot = robotsArray[i%robotsArray.length];
                Robot newRobot = new Robot(oldRobot.getBrain(),
                        1 + r.nextInt(Map.WIDTH - 2), 1 + r.nextInt(Map.HEIGHT - 2), MUTATION_PERCENT);
                //mutate(newRobot);
                if (Map.addEntity(newRobot))
                {
                    robots.add(newRobot);
                    j++;
                }
            }
        }
        robots.addAll(Arrays.asList(robotsArray));
    }
    private void generate(int count, Type t)
    {
        Random r = new Random();
        for (int i = 0; i < count; ) {
            Entity e = new Entity(t, r.nextInt(Map.WIDTH), r.nextInt(Map.HEIGHT));
            if (Map.addEntity(e))
            {
                i++;
            }
        }
    }
    private double[][][] readArray()
    {
        double[][][] array = new double[2][][];
        double[] longArray = new double[280];
        try(Scanner scanner = new Scanner(new FileInputStream("array.txt")))
        {
            Arrays.setAll(longArray, i->Double.parseDouble(scanner.nextLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        array[0] = new double[16][9];
        array[1] = new double[8][17];
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < array[i].length; j++) {
                for (int k = 0; k < array[i][j].length; k++) {
                    array[i][j][k] = longArray[index++];
                }
            }
        }
        return array;
    }

}
