package FlappyBird.game;

import FlappyBird.gui.Frame;
import FlappyBird.net.BirdNet;
import FlappyBird.net.NeuralNode;

import java.awt.*;

public class Bird implements Drawable{
    private int x, y, radius;
    private int velocity;
    private final int MAX_VELOCITY = 15;
    private final int JUMP_VELOCITY = 10;
    private BirdNet net;

    private Color color;

    public Bird(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        velocity = 0;
    }
    public void setNet(BirdNet net)
    {
        this.net = net;
    }
    public BirdNet getNet()
    {
        return this.net;
    }
    public void getValues(int height, int distance)
    {
        if (this.net.execute(height, distance) >= 0.5d)
            fly();
    }
    public void fly()
    {
        velocity = -JUMP_VELOCITY;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(x - radius, Frame.HEIGHT - (y + radius), 2*radius, 2* radius);
        g.setColor(Color.BLACK);
        g.drawOval(x - radius, Frame.HEIGHT - (y + radius), 2*radius, 2* radius);
        this.y -= velocity;
        if (velocity < MAX_VELOCITY)
            velocity++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return Frame.HEIGHT - y;
    }

    public int getRadius() {
        return radius;
    }
}
