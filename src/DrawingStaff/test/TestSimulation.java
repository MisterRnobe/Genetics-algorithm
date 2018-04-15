package DrawingStaff.test;

import Agents.Entity;
import Agents.Food;
import Agents.ui.Frame;
import Agents.utils.Circle;
import Agents.utils.Line;
import Agents.utils.Vector2;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TestSimulation
{
    private static TestSimulation instance;
    public static TestSimulation getInstance()
    {
        if (instance == null)
            instance = new TestSimulation();
        return instance;
    }

    private TestAgentCell currentAgent = new TestAgentCell(50,50);
    private List<Food> foods;


    private TestSimulation()
    {
        foods = new ArrayList<>();
        int foodCount = 19;
        for (int i = 0; i < foodCount; i++) {
            addFood();
        }
    }
    public void doSimulation(Graphics g)
    {
//        while (true)
//        {
            nextStep(g);
            this.getEntities().forEach(e->e.draw(g));
            try
            {
                Thread.sleep(25);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        //}
    }

    public boolean nextStep(Graphics g)
    {
        checkIntersection(currentAgent);
        double[] input = getInputFor(currentAgent);
        currentAgent.apply(input, g);

        return true;
    }

    private void checkIntersection(TestAgentCell a)
    {
        int removedNumber = 0;
        Iterator<Food> iterator = foods.iterator();
        for(;iterator.hasNext();)
        {
            Food f = iterator.next();
            if (a.intersects(f))
            {
                iterator.remove();
                removedNumber++;
            }
        }
        for (int i = 0; i < removedNumber; i++) {
            addFood();
        }
    }
    private double[] getInputFor(TestAgentCell a)
    {
        Vector2 position = a.getPosition();
        List<Vector2> vectors = a.getDirections();
        List<Line> lines = vectors.stream().sequential().map(vector2 -> new Line(a.getX(), a.getY(), vector2)).collect(Collectors.toList());
        //lines.forEach(l -> System.out.println("Line: "+l.getX()+", "+l.getY()+", Vector: "+l.getVector()));
        return lines.stream().sequential().mapToDouble(line -> foods.stream().map(line::intersects).filter(Objects::nonNull).
                mapToDouble(vector -> vector.sub(position).length()).min().orElse(-1)).toArray();
//        Vector2 vector = a.getDirections().get(0);
//        Line l = new Line(a.getX(), a.getY(), vector);
//        Vector2 position = a.getPosition();
//        Food min = null;
//        Vector2 minVector = null;
//        for(Food f: foods)
//        {
//            Vector2 vec = l.intersects(f);
//            if (vec == null)
//                continue;
//            if (min == null || vec.sub(position).length() < minVector.length())
//            {
//                min = f;
//                minVector = vec.sub(position);
//            }
//        }
//        return min;
    }
    public boolean checkCollision(Circle c, Vector2 p1, Vector2 p2)
    {
        return c.isInside(p1) && !c.isInside(p2) || !c.isInside(p1) && c.isInside(p2);
    }
    private void addFood()
    {
        Random r = new Random();
        foods.add(new Food(r.nextInt(512)+256, r.nextInt(512)+256));
    }

    public List<Entity> getEntities() {
        List<Entity> l = new ArrayList<>();
        l.add(currentAgent);
        l.addAll(foods);
        return l;
    }

    public static void main(String[] args) {
        TestSimulation.getInstance();
        Frame f = Frame.getInstance();
    }
}
