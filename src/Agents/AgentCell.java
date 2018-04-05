package Agents;

import Agents.geneticstuff.Agent;
import Agents.geneticstuff.Genome;

import java.awt.*;

import static java.lang.Math.*;

public class AgentCell extends Entity implements Agent
{
    private static final int MAX_HP = 255;
    private static final double MAX_DEGREE = PI/6;
    private static final double VELOCITY = 10;

    private int currentHP;
    private static final int RADIUS = 12;
    private final Controller controller;
    private double angle = 0;

    protected Double FitnessFunction()
    {
        return 0.0;
    }
    protected AgentCell(int x, int y, Controller controller) {
        super(x, y);
        currentHP = 20;
        this.controller = controller;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(currentHP, 255-currentHP, 0));
        g.fillOval(x - RADIUS, y - RADIUS, 2*RADIUS, 2*RADIUS);
        g.setColor(Color.BLACK);
        g.drawOval(x - RADIUS, y - RADIUS, 2*RADIUS, 2*RADIUS);
        g.drawLine(x,y, x+ (int)((RADIUS+5)*cos(angle)),y+ (int)((RADIUS+5)*sin(angle))  );
        g.drawString(Integer.toString(currentHP), x,y);
    }
    public double[] apply(double x, double y)
    {
        double angle = angle(x,y);
        double distance = sqrt(x*x+y*y);
        return controller.apply(angle - this.angle, distance);
    }
    public void rotate(double value)
    {
        this.angle += value*MAX_DEGREE;
        this.angle = this.angle > 2*PI? this.angle - 2*PI : this.angle < 0? this.angle + 2*PI: this.angle;
    }
    public void move()
    {
        this.x += (int)(VELOCITY*cos(angle));
        this.y += (int)(VELOCITY*sin(angle));
    }

    public int getCurrentHP() {
        return currentHP;
    }
    public void addHP(int HP)
    {
        this.currentHP += HP;
        if (currentHP > MAX_HP)
            currentHP = MAX_HP;
    }
    public boolean intersects(Food f)
    {
        int dx = f.x - this.x;
        int dy = f.y - this.y;
        double d = Math.sqrt(dx*dx + dy*dy);
        return d < RADIUS + f.getRadius();
    }

    public Controller getController() {
        return controller;
    }

    public static final double angle(double x, double y)
    {
        double d = sqrt(x*x + y*y);
        double val = acos(x/d);
        if (y < 0)
            val = 2*PI - val;
        return Math.toDegrees(val);
    }

    @Override
    public String fitnessFunction()
    {
        //Todo Implement this stuff with number of iterations
        return null;
    }

    @Override
    public Genome getGenome()
    {
        //todo implement this with weights from neural network
        return null;
    }
}
