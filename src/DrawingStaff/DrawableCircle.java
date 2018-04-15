package DrawingStaff;

import Agents.utils.Circle;

import java.awt.*;

public class DrawableCircle implements Circle {
    private int x, y, radius;

    public DrawableCircle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillOval(x - radius, y - radius, 2*radius, 2*radius);
        g.setColor(Color.BLACK);
        g.drawOval(x - radius, y - radius, 2*radius, 2*radius);
    }
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getRadius() {
        return radius;
    }
}
