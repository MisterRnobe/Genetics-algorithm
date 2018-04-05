package Agents.ui;

import Agents.Entity;
import Agents.Manager;

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
        Manager.getInstance().nextStep();
        Manager.getInstance().getEntities().forEach(e->e.draw(g));

    }
}
