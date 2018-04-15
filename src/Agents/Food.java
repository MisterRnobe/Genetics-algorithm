package Agents;

import Agents.utils.Circle;

import java.awt.*;
import java.util.Random;

public class Food extends Entity implements Circle {
    private static final int MAX_HP = 50, MIN_HP = 10;
    private final int healthPoints;
    private final int radius;
    public Food(int x, int y) {
        super(x, y);
        this.healthPoints = new Random().nextInt(MAX_HP - MIN_HP)+MIN_HP;
        radius = (int)(this.healthPoints/(float)MIN_HP * 5);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x - radius, y - radius, 2*radius, 2*radius);
        g.setColor(Color.BLACK);
        g.drawOval(x - radius, y - radius, 2*radius, 2*radius);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getRadius() {
        return radius;
    }
}
