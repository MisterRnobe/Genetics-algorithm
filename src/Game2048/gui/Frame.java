package Game2048.gui;

import javax.swing.*;

public class Frame {
    public static final int WIDTH = 600;
    public static Frame instance;
    private Panel panel;
    public Frame()
    {
        JFrame frame = new JFrame("Playing 2048...");
        panel = new Panel();
        frame.setContentPane(panel);
        frame.setSize(WIDTH, WIDTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        instance = this;
    }
    public void repaint()
    {
        panel.repaint();
    }
}
