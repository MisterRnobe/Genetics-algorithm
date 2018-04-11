package Agents;

import Agents.utils.NeuralNetwork;

import java.util.*;

import static java.lang.Math.sqrt;

public class Manager {
    private static Manager instance;
    public static Manager getInstance()
    {
        if (instance == null)
            instance = new Manager();
        return instance;
    }
    private int foodCount = 20;
    private List<Food> foods;
    private List<AgentCell> agents;
    private LinkedList<AgentCell> died;
    private int iteration = 0;
    private Manager()
    {
        foods = new ArrayList<>();
        agents = new ArrayList<>();
        died = new LinkedList<>();

        Random r = new Random();
        for (int i = 0; i < 12; i++) {
            AgentCell a = new AgentCell(r.nextInt(1024), r.nextInt(1024), new NeuralNetwork(2,6,1));
            agents.add(a);
        }
        for (int i = 0; i < foodCount; i++) {
            addFood();
        }
    }
    public void nextStep()
    {
        for(Iterator<AgentCell> iterator = agents.iterator(); iterator.hasNext();)
        {
            AgentCell a = iterator.next();
            checkIntersection(a);
            Food f = findClosest(a);
            double[] d = a.apply(f.x, f.y);
            a.rotate(d[0]);
            a.move();
            if (iteration == 4)
                a.addHP(-1);
            if (a.getCurrentHP() == 0)
            {

                died.addLast(a);
                iterator.remove();
            }
        }
        iteration = iteration == 4? 0: iteration+1;
        if (agents.isEmpty())
            generateAgents();
    }
    private void checkIntersection(AgentCell a)
    {
            Iterator<Food> iterator = foods.iterator();
            for(;iterator.hasNext();)
            {
                Food f = iterator.next();
                if (a.intersects(f))
                {
                    a.addHP(f.getHealthPoints());
                    iterator.remove();
                }
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
    private void generateAgents()
    {
        Random r = new Random();
        AgentCell[] agentsArray = new AgentCell[4];
        for (int i = 0; i < agentsArray.length; i++) {
            agentsArray[i] = died.removeLast();
        }
        died.clear();
        for (int i = 0; i < agentsArray.length; i++) {
            for (int j = 0; j < 8;j++) {
                AgentCell oldAgent = agentsArray[i];
                NeuralNetwork c = oldAgent.getController();
                AgentCell newAgent = new AgentCell(r.nextInt(1024), r.nextInt(1024), c);
                agents.add(newAgent);
            }
        }
        agents.addAll(Arrays.asList(agentsArray));
        foods.clear();
        for (int i = 0; i < foodCount; i++) {
            addFood();
        }
    }
    private void addFood()
    {
        Random r = new Random();
        foods.add(new Food(r.nextInt(1024), r.nextInt(1025)));
    }

    public List<Entity> getEntities() {
        List<Entity> l = new ArrayList<>();
        l.addAll(agents);
        l.addAll(foods);
        return l;
    }
}
