package Agents;

import Agents.ui.Frame;
import Agents.utils.NeuralNetwork;
import io.jenetics.DoubleGene;
import io.jenetics.Genotype;

import java.util.*;
import java.util.function.Predicate;

import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.toList;

public class Simulation
{
    private static Simulation instance;
    public static Simulation getInstance()
    {
        if (instance == null)
            instance = new Simulation();
        return instance;
    }
    private int foodCount = 18;
    private Map<double[],AgentCell> agentMap;
    private AgentCell currentAgent;
    private List<Food> foods;

    private int iteration = 0;
    public static final int MAX_ITERATION = 2*1000;
    private final Predicate<Integer> doDrawSimulation = i-> i%50 == 0;

    private Simulation()
    {
        foods = new ArrayList<>();
        agentMap = new HashMap<>();
        for (int i = 0; i < foodCount; i++) {
            addFood();
        }
    }
    public double doSimulation(int someNumber, double[] array)
    {
        Random r = new Random();
        currentAgent =
                new AgentCell(r.nextInt(1024), r.nextInt(1024), array);
        iteration = 0;
        if (doDrawSimulation.test(someNumber))
            drawSimulation();
        else
            simpleSimulation();
        return currentAgent.fitnessFunction();
    }
    public void simpleSimulation()
    {
        //int alive = agentMap.size();
        boolean alive = true;
        while (alive && iteration < MAX_ITERATION)
        {
            alive = nextStep();
        }
    }
    public void drawSimulation()
    {
        //int alive = agentMap.size();
        boolean alive = true;
        Frame f = Frame.getInstance();
        while (alive && iteration < MAX_ITERATION)
        {
            f.repaint();
            alive = nextStep();
            try
            {
                Thread.sleep(25);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    public void addAgent(double[] genotype, int... neurons)
    {
        Random r = new Random();
        agentMap.put(genotype,
                new AgentCell(
                        r.nextInt(1024),
                        r.nextInt(1024),
                        genotype
                )
        );
    }
    public boolean nextStep()
    {
//        for(Iterator<Map.Entry<double[], AgentCell>> iterator = agentMap.entrySet().iterator(); iterator.hasNext();)
//        {
//            AgentCell a = iterator.next().getValue();
//            if (!a.isAlive())
//                continue;
//            else
//                alive++;
//            checkIntersection(a);
//            Food f = findClosest(a);
//            double[] d = a.apply(f.x, f.y);
//            a.rotate(d[0]);
//            a.move();
//            if (iteration%2 == 0)
//                a.addHP(-1);
//        }
        if (!currentAgent.isAlive())
                return false;

        checkIntersection(currentAgent);
        Food f = findClosest(currentAgent);
        currentAgent.apply(f.x, f.y);
        //currentAgent.rotate(d[0]);
        //currentAgent.move();
        if (iteration%2 == 0)
            currentAgent.addHP(-1);
        iteration++;
        return true;
    }
    private void checkIntersection(AgentCell a)
    {
        int removedNumber = 0;
        Iterator<Food> iterator = foods.iterator();
        for(;iterator.hasNext();)
        {
            Food f = iterator.next();
            if (a.intersects(f))
            {
                a.addHP(f.getHealthPoints());
                iterator.remove();
                removedNumber++;
            }
        }
        for (int i = 0; i < removedNumber; i++) {
            addFood();
        }
    }
    private Food findClosest(AgentCell a)
    {
        Food food = foods.get(0);
        double distance = sqrt((a.x-food.x)*(a.x-food.x) + (a.y-food.y)*(a.y-food.y));

        for(Food f: foods)
        {
            int x = a.x - f.x;
            int y = a.y - f.y;
            double d = sqrt(x*x + y*y);
            if (d < distance)
            {
                food = f;
                distance = d;
            }
        }
        return food;
    }
    public void clearAgents()
    {
        agentMap.clear();
    }
    public double getAgent(double[] array)
    {
        Optional<Map.Entry<double[], AgentCell>> o = agentMap.entrySet().stream().
                filter(e->Arrays.equals(e.getKey(), array)).
                findFirst();//orElseThrow(()->new RuntimeException("Not found")).getValue();
        return o.isPresent()? o.get().getValue().fitnessFunction(): 0;
    }
    private void addFood()
    {
        Random r = new Random();
        foods.add(new Food(r.nextInt(1024), r.nextInt(1025)));
    }

    public List<Entity> getEntities() {
        List<Entity> l = new ArrayList<>();
        //List<AgentCell> list = agentMap.entrySet().stream().map(Map.Entry::getValue).filter(AgentCell::isAlive).collect(toList());
        //l.addAll(list);
        l.add(currentAgent);
        l.addAll(foods);
        return l;
    }
}
