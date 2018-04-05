package Game2048.game;

import Game2048.Player;
import Game2048.gui.Frame;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeMap;

public class Manager {
    private static Manager ourInstance;
    public static void init(){ ourInstance = new Manager();}

    public static Manager getInstance() {
        return ourInstance;
    }


    private int totalPlayers = 10;
    private LinkedList<Player> currentPlayers;
    private TreeMap<Integer, Player> diedPlayers;
    private final int percent = 5;
    private int generation = 0;
    private int maxScore = 0;
    private int maxScoreGeneration = 0;
    private Manager() {
        currentPlayers = new LinkedList<>();
        for(int i = 0; i< totalPlayers; i++)
            currentPlayers.addLast(new Player());
        diedPlayers = new TreeMap<>();

        new Thread(()->{
            while (true)
            {
                try {
                    Thread.sleep(100);
                    nextStep();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void nextStep()
    {
        double[] values = new double[16];
        int currentPosition = 0;
        int[][] map = Map.getInstance().getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                values[currentPosition] = map[i][j];
                currentPosition++;
            }
        }
        double max = Arrays.stream(values).max().getAsDouble();
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] / max;
        }
        int code = currentPlayers.getLast().execute(values);
        //System.out.println("Code is: "+code);
        boolean next;
        switch (code)
        {
            case 0:
                next = Map.getInstance().moveLeft();
                break;
            case 1:
                next = Map.getInstance().moveUp();
                break;
            case 2:
                next = Map.getInstance().moveDown();
                break;
            case 3:
                next = Map.getInstance().moveRight();
                break;
            default:
                throw new RuntimeException("Wrong code! Expected 3 or less, found: "+code);
        }
        if (!next)
            nextPlayer();
        if (Map.getInstance().getCurrentScore() > maxScore) {
            maxScore = Map.getInstance().getCurrentScore();
            maxScoreGeneration = generation;
        }
        Frame.instance.repaint();
    }
    private void nextPlayer()
    {
        Player died = currentPlayers.removeLast();
        diedPlayers.put(Map.getInstance().getCurrentScore(), died);


        if (currentPlayers.isEmpty())
        {
            int maxScore = diedPlayers.keySet().stream().max(Integer::compare).get();
            Player p = diedPlayers.get(maxScore);
            for (int i = 0; i < totalPlayers-1; i++) {
                Player clone = new Player(p);
                clone.mutate(percent);
                currentPlayers.addLast(clone);
            }
            currentPlayers.addLast(p);
            generation++;

        }
        Map.getInstance().restart();
    }

    public int getGeneration() {
        return generation;
    }

    public int currentPlayer() {
        return totalPlayers - currentPlayers.size()+1;
    }
    public int getMaxScore()
    {
        return maxScore;
    }

    public int getMaxScoreGeneration() {
        return maxScoreGeneration;
    }

    private int log(int value)
    {
        if (value == 0)
            return value;
        int result = 0;
        for ( ; value!=1 ; value>>=1)
            result++;
        return result;
    }
}
