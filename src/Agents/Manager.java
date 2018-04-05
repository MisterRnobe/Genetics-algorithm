package Agents;

import Life.Map;
import Life.Robot;

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
    public int foodCount = 20;

    private List<Food> foods;
    private List<Agent> agents;
    private LinkedList<Agent> died;
    int iteration = 0;
    private Manager()
    {
        foods = new ArrayList<>();
        agents = new ArrayList<>();
        died = new LinkedList<>();

        Random r = new Random();
        for (int i = 0; i < 12; i++) {
            Agent a = new Agent(r.nextInt(1024), r.nextInt(1024), new Controller(2,6,1));
            agents.add(a);
        }
        for (int i = 0; i < foodCount; i++) {
            addFood();
        }
    }
    public void nextStep()
    {
        for(Iterator<Agent> iterator = agents.iterator(); iterator.hasNext();)
        {
            Agent a = iterator.next();
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
        iteration = iteration == 4? iteration = 0: iteration+1;
        if (agents.isEmpty())
            generateAgents();
    }
    private void checkIntersection(Agent a)
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
    private Food findClosest(Agent a)
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
        Agent[] agentsArray = new Agent[4];
        for (int i = 0; i < agentsArray.length; i++) {
            agentsArray[i] = died.removeLast();
        }
        died.clear();
        for (int i = 0; i < agentsArray.length; i++) {
            for (int j = 0; j < 8;j++) {
                Agent oldAgent = agentsArray[i];
                Controller c = oldAgent.getController();
                c.mutate(8);
                Agent newAgent = new Agent(r.nextInt(1024), r.nextInt(1024), c);
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
