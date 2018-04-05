package FlappyBird.gui;

import javax.swing.*;

public class Frame {
    public static final int HEIGHT = 600, WIDTH = 800;
    public Frame()
    {
        JFrame frame = new JFrame("Testing...");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(new Panel());
        frame.setVisible(true);
    }
}
