package Agents;

import Agents.ui.Frame;
import Agents.utils.Circle;
import Agents.utils.Line;
import Agents.utils.Vector2;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    private final Predicate<Integer> doDrawSimulation = i-> i%100 == 0;

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
                new AgentCell(r.nextInt(512)+256, r.nextInt(512)+256, array);
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
        AgentCell.debug = true;
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
        AgentCell.debug = false;
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
//            Food f = getInputFor(a);
//            double[] d = a.apply(f.x, f.y);
//            a.rotate(d[0]);
//            a.move();
//            if (iteration%2 == 0)
//                a.addHP(-1);
//        }
        if (!currentAgent.isAlive())
                return false;

        checkIntersection(currentAgent);
        double[] input = getInputFor(currentAgent);
        currentAgent.apply(input);
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
    private double[] getInputFor(AgentCell a)
    {
        Vector2 position = a.getPosition();
        List<Vector2> vectors = a.getDirections();
        List<Line> lines = vectors.stream().sequential().map(vector2 -> new Line(a.getX(), a.getY(), vector2)).collect(Collectors.toList());
        //lines.forEach(l -> System.out.println("Line: "+l.getX()+", "+l.getY()+", Vector: "+l.getVector()));
        return lines.stream().sequential().mapToDouble(line -> foods.stream().map(line::intersects).filter(Objects::nonNull).
                mapToDouble(vector -> vector.sub(position).length()).min().orElse(-1)).toArray();
    }
    public boolean checkCollision(Circle c, Vector2 p1, Vector2 p2)
    {
        return c.isInside(p1) && !c.isInside(p2) || !c.isInside(p1) && c.isInside(p2);
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
        foods.add(new Food(r.nextInt(512)+256, r.nextInt(512)+256));
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
