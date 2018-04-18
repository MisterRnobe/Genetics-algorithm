package Agents;

import Agents.ui.Frame;
import Agents.utils.Line;
import Agents.utils.Vector2;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulation
{
    private static Simulation instance;
    public static Simulation getInstance()
    {
        if (instance == null)
            instance = new Simulation();
        return instance;
    }
    private Map<double[],AgentCell> agentMap;
    private AgentCell currentAgent;
    private List<ConsumingObject> consumingObjects;

    private int iteration = 0;
    public static final int MAX_ITERATION = 2*1000;
    private final Predicate<Integer> doDrawSimulation = i-> i%500 == 0;
    private final Random random = new Random();

    private Simulation()
    {
        consumingObjects = new ArrayList<>();
        agentMap = new HashMap<>();
        int foodCount = 18;
        for (int i = 0; i < foodCount; i++) {
            addFood();
        }
        int poisonCount = foodCount/2;
        for (int i = 0; i < poisonCount; i++) {
            addPoison();
        }
    }
    public double doSimulation(int someNumber, double[] array)
    {
        currentAgent =
                new AgentCell(random.nextInt(512)+256, random.nextInt(512)+256, array);
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
        agentMap.put(genotype,
                new AgentCell(
                        random.nextInt(1024),
                        random.nextInt(1024),
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
//            ConsumingObject f = getInputFor(a);
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
        if (iteration%2 == 0)
            currentAgent.addHP(-1);
        iteration++;
        return true;
    }

    private void checkIntersection(AgentCell a)
    {
        int removedFood = 0;
        int removedPoison = 0;
        Iterator<ConsumingObject> iterator = consumingObjects.iterator();
        for(;iterator.hasNext();)
        {
            ConsumingObject f = iterator.next();
            if (a.intersects(f))
            {
                a.addHP(f.getPoints());
                if (f.getPoints()>0)
                    removedFood++;
                else
                    removedPoison++;
                iterator.remove();
            }
        }
        for (int i = 0; i < removedFood; i++) {
            addFood();
        }
        for (int i = 0; i < removedPoison; i++) {
            addPoison();
        }
    }
    private double[] getInputFor(AgentCell a)
    {
        Vector2 position = a.getPosition();
        List<Vector2> vectors = a.getDirections();
        List<Line> lines = vectors.stream().sequential().map(vector2 -> new Line(a.getX(), a.getY(), vector2)).collect(Collectors.toList());
        return lines.stream().sequential().map(
                line -> {
                    AbstractMap.SimpleEntry<Integer, Double> simpleEntry =consumingObjects.stream().map(o -> new AbstractMap.SimpleEntry<>(o.getCode(), line.intersects(o)))
                            .filter(entry -> entry.getValue() != null).map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().sub(position).length()))
                            .min(Comparator.comparingDouble(AbstractMap.SimpleEntry::getValue))
                            .orElse(new AbstractMap.SimpleEntry<>(0, (double) -1));
                    return Stream.of(simpleEntry.getKey().doubleValue(), simpleEntry.getValue());
                }
                ).flatMapToDouble(doubleStream -> doubleStream.mapToDouble(Double::doubleValue)).toArray();
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
        consumingObjects.add(ConsumingObject.asFood(random.nextInt(924)+50, random.nextInt(924)+50));
    }
    private void addPoison()
    {
        consumingObjects.add(ConsumingObject.asPoison(random.nextInt(924)+50, random.nextInt(924)+50));
    }

    public List<Entity> getEntities() {
        List<Entity> l = new ArrayList<>();
        l.add(currentAgent);
        l.addAll(consumingObjects);
        return l;
    }
}
