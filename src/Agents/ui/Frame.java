package Agents.ui;

import javax.swing.*;

public class Frame {
    JFrame f;
    public Frame()
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

}
