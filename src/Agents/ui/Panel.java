package Agents.ui;

import Agents.Simulation;
import DrawingStaff.test.TestAgentCell;
import DrawingStaff.test.TestSimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.lang.Math.PI;

public class Panel extends JPanel {
    Panel()
    {
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                TestAgentCell.setValue(0);
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    TestAgentCell.setValue(-PI / 4);
                }
                if (e.getKeyCode() == KeyEvent.VK_D)
                    TestAgentCell.setValue(PI/4);
                repaint();


            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        TestSimulation.getInstance().doSimulation(g);//.getEntities().forEach(e->e.draw(g));

    }
}
