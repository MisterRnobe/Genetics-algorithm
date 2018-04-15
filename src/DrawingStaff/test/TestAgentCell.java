package DrawingStaff.test;

import Agents.Entity;
import Agents.Food;
import Agents.utils.NeuralNetwork;
import Agents.utils.Vector2;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class TestAgentCell extends Entity
{
    private static final double MAX_DEGREE = PI/6;
    private static final double VELOCITY = 10;
    private static final double[] ANGLES = new double[]{-PI/12, -PI/24, 0 ,PI/24, PI/12};
    private static double VALUE = 0;

    private int currentHP;
    private static final int RADIUS = 12;
    private double angle = 0;

    TestAgentCell(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);//new Color(currentHP, 255-currentHP, 0));
        g.fillOval(x - RADIUS, y - RADIUS, 2*RADIUS, 2*RADIUS);
        g.setColor(Color.BLACK);
        g.drawOval(x - RADIUS, y - RADIUS, 2*RADIUS, 2*RADIUS);
        //debug(g);
        g.setColor(Color.WHITE);
        g.drawLine(x,y, x+ (int)((RADIUS)*cos(angle)),y+ (int)((RADIUS)*sin(angle))  );
        g.drawString(Integer.toString(currentHP), x,y);
    }
    public void apply(double[] values, Graphics g)
    {
        System.out.println(toString(values));
        debug(g, values);
        this.rotate(VALUE);
        move();
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

    public boolean intersects(Food f)
    {
        int dx = f.getX() - this.x;
        int dy = f.getY() - this.y;
        double d = Math.sqrt(dx*dx + dy*dy);
        return d < RADIUS + f.getRadius();
    }


    private void debug(Graphics g, double[] values)
    {
        for(int i = 0; i < values.length; i++)
        {
            if (values[i] == -1)
                values[i] = 1024;
            g.drawLine(x, y, x + (int)(values[i]*cos(angle+ANGLES[i])), y + (int)(values[i]*sin(angle+ANGLES[i])));
        }
    }
    public List<Vector2> getDirections()
    {
        List<Vector2> list = new LinkedList<>();
        for(double angle: ANGLES)
        {
            list.add(new Vector2(cos(this.angle+ angle), sin(this.angle+angle)));
        }
        return list;
    }
    public static String toString(double[] d)
    {
        return Arrays.stream(d).boxed().map(Object::toString).collect(Collectors.joining(", "));
    }
    public static void setValue(double d)
    {
        VALUE = d;
    }
}
