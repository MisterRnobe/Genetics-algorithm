package Agents;

import Agents.utils.Circle;

import java.awt.*;
import java.util.Random;

public class ConsumingObject extends Entity implements Circle {
    private static final Random random = new Random();
    private static final int MAX_HP = 50, MIN_HP = 10;
    private final int points;
    private final int radius;
    private ConsumingObject(int x, int y, int sign) {
        super(x, y);
        this.points = sign * (random.nextInt(MAX_HP - MIN_HP)+MIN_HP);
        radius = (int)(Math.abs(this.points) /(float)MIN_HP * 5);
    }
    public static ConsumingObject asFood(int x, int y)
    {
        return new ConsumingObject(x,y, 1);
    }
    public static ConsumingObject asPoison(int x, int y)
    {
        return new ConsumingObject(x,y, -1);
    }

    @Override
    public void draw(Graphics g) {
        if (points > 0)
            g.setColor(Color.GREEN);
        else
            g.setColor(Color.RED);
        g.fillOval(x - radius, y - radius, 2*radius, 2*radius);
        g.setColor(Color.BLACK);
        g.drawOval(x - radius, y - radius, 2*radius, 2*radius);
    }

    public int getPoints() {
        return points;
    }
    public int getCode()
    {
        return points > 0? 1: -1;
    }

    public int getRadius() {
        return radius;
    }
}
