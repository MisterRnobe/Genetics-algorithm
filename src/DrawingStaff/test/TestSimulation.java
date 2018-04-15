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
        int foodCount = 18;
        for (int i = 0; i < foodCount; i++) {
            addFood();
        }
    }
    public void doSimulation()
    {
        Frame f = Frame.getInstance();
        while (true)
        {
            f.repaint();
            nextStep(f.getGraphics());
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

    public boolean nextStep(Graphics g)
    {
        checkIntersection(currentAgent);
        double[] input = getInputFor(currentAgent);
        this.getEntities().forEach(e->e.draw(g));
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
        List<Vector2> vectors = a.getDirections();
        List<Line> lines = vectors.stream().map(vector2 -> new Line(a.getX(), a.getY(), vector2)).collect(Collectors.toList());
        return lines.stream().mapToDouble(line -> foods.stream().map(line::intersects).filter(Objects::nonNull).
                mapToDouble(Vector2::length).min().orElse(-1)).toArray();
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
        TestSimulation testSimulation = TestSimulation.getInstance();
        testSimulation.doSimulation();
    }
}
