package FlappyBird.game;

import FlappyBird.gui.Frame;

import java.awt.*;

public class Block implements Drawable{
    private static final Color color = Color.GREEN;
    public static final int width = 90;
    private int x, height, y;

    public Block(int x, int height, boolean onTop) {
        this.x = x;
        this.height = height;
        y = onTop? 0: Frame.HEIGHT - height;
    }
    public void move(int value)
    {
        this.x -= value;
    }

    public int getX() {
        return x;
    }

    public int getHeight() {
        return height;
    }

    public int getY() {
        return y;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }
}
