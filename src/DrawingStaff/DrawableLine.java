package DrawingStaff;

import Agents.utils.Circle;
import Agents.utils.Line;
import Agents.utils.Vector2;

import java.awt.*;

public class DrawableLine {
    Line line;
    public DrawableLine(Line line)
    {
        this.line = line;
    }
    public void drawToCircle(Circle c, Graphics g)
    {
        Vector2 v = line.intersects(c);
        if (v == null)
        {
            System.out.println("No intersection!");
            return;
        }
        Vector2 startPoint = new Vector2(line.getX(), line.getY());//v.add(line.getVector());
        System.out.println(v);
        System.out.println(startPoint);
        g.drawLine((int)v.getX(), (int)v.getY(), (int)startPoint.getX(), (int)startPoint.getY());
    }
}
