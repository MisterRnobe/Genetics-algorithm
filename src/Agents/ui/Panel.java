package Agents.ui;

import Agents.Simulation;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    public Panel()
    {
        new Thread(()->{
            while (true)
            {
                try
                {
                    repaint();
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {

                }
            }
        }).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Simulation.getInstance().nextStep();
        Simulation.getInstance().getEntities().forEach(e->e.draw(g));

    }
}
