package Game2048.gui;

import Game2048.game.Manager;
import Game2048.game.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class Panel extends JPanel {
    final int SCALE = 150;
    Panel()
    {
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A)
                    Map.getInstance().moveLeft();
                else if (e.getKeyCode() == KeyEvent.VK_D)
                    Map.getInstance().moveRight();
                else if (e.getKeyCode() == KeyEvent.VK_W)
                    Map.getInstance().moveUp();
                else if (e.getKeyCode() == KeyEvent.VK_S)
                    Map.getInstance().moveDown();
                repaint();
            }
        });
    }
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.setColor(Color.BLACK);
        try {
            g.drawString("Generation: " + Manager.getInstance().getGeneration(), Frame.WIDTH + 5, 40);
            g.drawString("Player #" + Manager.getInstance().currentPlayer(), Frame.WIDTH + 5, 80);
            g.drawString("Max Score: " + Manager.getInstance().getMaxScore()+" by generation :" +
                    Manager.getInstance().getMaxScoreGeneration(),
                    Frame.WIDTH + 5, 120);
        }
        catch (NullPointerException ignored)
        {

        }
        g.drawString("Score: "+ Map.getInstance().getCurrentScore(),Frame.WIDTH+5, 160);
        int[][] map = Map.getInstance().getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                g.setColor(getColor(map[i][j]));
                g.fillRect(i*SCALE, j*SCALE, SCALE, SCALE);
                g.setColor(Color.BLACK);
                g.drawRect(i*SCALE, j*SCALE, SCALE, SCALE);
                g.setColor(Color.WHITE);
                if (map[i][j] != 0)
                    g.drawString(Integer.toString(map[i][j]), i*SCALE+SCALE/2, j*SCALE+SCALE/2);
            }
        }
    }
    private Color getColor(int number)
    {
        float value =  0.9f*number/65536+0.1f;
        return new Color(value, value, value);
    }
}
