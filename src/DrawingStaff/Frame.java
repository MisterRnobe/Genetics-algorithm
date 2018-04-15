package DrawingStaff;

import Agents.utils.Line;
import Agents.utils.Vector2;

import javax.swing.*;
import java.awt.*;

public class Frame {
    JFrame f;
    DrawableLine line = new DrawableLine(new Line(0,270, new Vector2(1 , 0)));
    DrawableCircle[] circles = new DrawableCircle[]{new DrawableCircle(300, 255, 40),
    new DrawableCircle(360, 400, 30)};
    Frame()
    {
        f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(1024, 1024);
        f.setContentPane(new Panel());
        f.setVisible(true);
    }

    public static void main(String[] args) {
        Frame f = new Frame();
    }
    private class Panel extends JPanel
    {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for(DrawableCircle c: circles)
            {
                c.draw(g);
                line.drawToCircle(c, g);
            }
        }
    }

}
