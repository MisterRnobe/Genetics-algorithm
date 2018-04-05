package FlappyBird.gui;

import FlappyBird.game.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Panel extends JPanel {
    Panel()
    {
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Manager.getInstance().pressed();
            }
        });
        new Thread(()->{
            while (true)
            {
                try
                {
                    Thread.sleep(25);
                    repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(0x01E0F3));
        g.fillRect(0,0, Frame.WIDTH, Frame.HEIGHT);
        Manager.getInstance().nextStep();
        Manager.getInstance().draw(g);
    }
}
