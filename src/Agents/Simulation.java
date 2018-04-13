package Agents;

import Agents.utils.NeuralNetwork;
import io.jenetics.DoubleGene;
import io.jenetics.Genotype;

import java.util.*;

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
    private int foodCount = 20;
    private Map<double[],AgentCell> agentMap;
    private List<Food> foods;

    private int iteration = 0;
    private Simulation()
    {
        foods = new ArrayList<>();
        agentMap = new HashMap<>();
        for (int i = 0; i < foodCount; i++) {
            addFood();
        }
    }
    public void startSimulation()
    {
        int alive = agentMap.size();
        while (alive!=0)
        {
            alive = nextStep();
        }
    }
    public void addAgent(double[] genotype, int... neurons)
    {
        Random r = new Random();
        agentMap.put(genotype,
                new AgentCell(
                        r.nextInt(1024),
                        r.nextInt(1024),
                        new NeuralNetwork(genotype,neurons)
                )
        );
    }
    public int nextStep()
    {
        int alive = 0;
        for(Iterator<Map.Entry<double[], AgentCell>> iterator = agentMap.entrySet().iterator(); iterator.hasNext();)
        {
            AgentCell a = iterator.next().getValue();
            if (!a.isAlive())
                continue;
            checkIntersection(a);
            Food f = findClosest(a);
            double[] d = a.apply(f.x, f.y);
            a.rotate(d[0]);
            a.move();
            if (iteration == 4)
                a.addHP(-1);
        }
        if (iteration == 4)
            iteration = 0;
        else
            iteration++;
        return alive;
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
    public AgentCell getAgent(double[] array)
    {
        return agentMap.entrySet().stream().
                filter(e->Arrays.equals(e.getKey(), array)).
                findFirst().orElseThrow(()->new RuntimeException("Not found")).getValue();
    }
    private void addFood()
    {
        Random r = new Random();
        foods.add(new Food(r.nextInt(1024), r.nextInt(1025)));
    }

    public List<Entity> getEntities() {
        List<Entity> l = new ArrayList<>();
        List<AgentCell> list = agentMap.entrySet().stream().map(Map.Entry::getValue).filter(AgentCell::isAlive).collect(toList());
        l.addAll(list);
        l.addAll(foods);
        return l;
    }
}
