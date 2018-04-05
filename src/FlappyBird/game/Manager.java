package FlappyBird.game;

import FlappyBird.gui.Frame;
import FlappyBird.net.BirdNet;

import java.awt.*;
import java.util.*;
import java.util.List;

import static FlappyBird.gui.Frame.HEIGHT;
import static FlappyBird.gui.Frame.WIDTH;
import static FlappyBird.net.BirdNet.BORDER;

public class Manager {
    private static Manager instance;
    public static Manager getInstance(){ if (instance == null) instance = new Manager(); return instance;}

    private final int holeHeight = 120;
    private final int minHeight = 40;
    private final int step = 130;
    private final int move = 7;
    private final int BIRDS_COUNT = 5;

    private int START_POSITION = WIDTH/4;

    private List<Block> blocks;
    private List<Bird> birds;
    private Bird last;
    private Manager()
    {
        blocks = new LinkedList<>();
        birds = new LinkedList<>();
        for (int i = 0; i < BIRDS_COUNT; i++) {
            Bird b = new Bird(START_POSITION, HEIGHT/2, 12, Color.gray);
            BirdNet net = new BirdNet(generateWeights());
            b.setNet(net);
            birds.add(b);
        }

        generateBlocks();
    }



    private void generateBlocks()
    {
        Random r = new Random();
        int height1 = minHeight+r.nextInt(HEIGHT - minHeight - holeHeight);
        int height2 = HEIGHT - holeHeight - height1;
        blocks.add(new Block(WIDTH, height1, true));
        blocks.add(new Block(WIDTH, height2, false));
    }
    public void nextStep()
    {
        blocks.forEach(b->b.move(move));
        
        if (blocks.get(0).getX() <= - Block.width)
        {
            blocks.remove(0);
            blocks.remove(0);
        }
        if (blocks.get(blocks.size() - 1).getX() <= HEIGHT - step)
            generateBlocks();
        checkIntersection();
        if (birds.isEmpty())
        {
            createNew();
            return;
        }
        Block b = blocks.get(0).getX()+Block.width<START_POSITION? blocks.get(2): blocks.get(0);
        System.out.println("X = "+b.getX()+", Y = "+b.getY()+", HEIGHT = "+b.getHeight());
        birds.forEach(bird->bird.getValues(b.getY()+b.getHeight() + holeHeight/2 - bird.getY(),
                b.getX()+Block.width - bird.getX()));
    }
    public void createNew()
    {
        blocks.clear();
        generateBlocks();
        for (int i = 0; i < BIRDS_COUNT; i++) {
            Bird b = new Bird(START_POSITION, HEIGHT/2, 12, getRandomColor());
            BirdNet net = last.getNet().clone();
            net.mutate();
            b.setNet(net);
            birds.add(b);
        }
    }

    public void draw(Graphics g)
    {
        blocks.forEach(block->block.draw(g));
        birds.forEach(bird->bird.draw(g));
    }
    public Color getRandomColor()
    {
        return new Color(new Random().nextInt(Integer.MAX_VALUE));
    }
    private void checkIntersection()
    {

        for(Iterator<Bird> iterator = birds.iterator(); iterator.hasNext();)//Bird b:birds)
        {
            boolean intersected = false;
            Bird b = iterator.next();
            int x = b.getX(), y = b.getY(), r = b.getRadius();
            for(Block block: blocks)
            {
                if (x > block.getX() && x < block.getX()+Block.width &&
                    y > block.getY() && y < block.getY()+block.getHeight() ||
                        y < 0 || y>HEIGHT)
                {
                    intersected = true;
                    break;
                }
            }
            if (intersected) {
                last = b;
                iterator.remove();
            }
        }
    }
    public void pressed()
    {
        birds.get(0).fly();
    }
    public int[][] generateWeights()
    {
        int[][] weights = new int[9][];
        weights[0] = new int[1];
        weights[1] = new int[1];
        for (int i = 2; i < 8; i++) {
            weights[i] = new int[2];
        }
        Random r = new Random();
        weights[8] = new int[6];
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = r.nextInt(2*BORDER) - BORDER;
            }
        }
        return weights;
    }
}
