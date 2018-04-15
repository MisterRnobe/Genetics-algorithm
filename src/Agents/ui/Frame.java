package Agents.ui;

import javax.swing.*;
import java.awt.*;

public class Frame {
    private static Frame instance;
    JFrame f;
    private Frame()
    {
        f = new JFrame();
        f.setContentPane(new Panel());
        f.setSize(1024,1024);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    public void repaint()
    {
        f.getContentPane().repaint();
    }
    public Graphics getGraphics()
    {
        return f.getContentPane().getGraphics();
    }
    public static Frame getInstance()
    {
        if (instance == null)
            instance = new Frame();
        return instance;
    }

}
