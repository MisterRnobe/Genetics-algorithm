package Life.gui;

import Life.*;
import Life.Robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Panel extends JPanel {
    public static final int SCALE = 12;
    public Panel()
    {
        super();
        this.setFocusable(true);
//        this.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                repaint();
//            }
//        });
        new Thread(()->{
            while (true) {
                try {
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
        super.paint(g);
        Manager.getInstance().nextStep();
        for (int i = 0; i < Map.WIDTH; i++) {
            for (int j = 0; j < Map.HEIGHT; j++) {
                int x = i*SCALE, y = j*SCALE;
                Entity e = Map.getEntityAt(i,j);
                g.setColor(e.getType().getColor());
                g.fillRect(x,y, SCALE, SCALE);
                g.setColor(Color.BLACK);
                g.drawRect(x,y, SCALE, SCALE);
                if (e.getType() == Type.ROBOT)
                {
                    Robot r = (Robot) e;
                    g.drawString(Integer.toString(r.getHp()),x,y);
                }
            }
        }
        g.setColor(Color.BLACK);
        int generation = Statistic.getInstance().generation;
        g.drawString("Generation: "+ generation,Map.WIDTH*SCALE+2*SCALE, 10);
        int[] ages = Statistic.getInstance().getLastLifetime(100);
        for (int i = 0; i < ages.length; i++) {
            g.drawString("Generation #"+(generation - i - 1)+" : "+ages[i],Map.WIDTH*SCALE+2*SCALE, 20+i*10);
        }

    }


}
