package Agents.ui;

import Agents.Simulation;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    Panel()
    {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //Simulation.getInstance().getEntities().forEach(e->e.draw(g));

    }
}
