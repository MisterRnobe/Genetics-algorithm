package Agents;

import Agents.utils.NeuralNetwork;
import Agents.utils.Vector2;


import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.*;

public class AgentCell extends Entity
{
    private static final int MAX_HP = 255;
    private static final double MAX_DEGREE = PI/6;
    private static final double VELOCITY = 10;
    public static final int[] LAYERS = new int[]{5, 9, 2};
    //public static final double DELTA_ANGLE = PI/12;
    private static final double[] ANGLES = new double[]{-PI/12, -PI/24, 0 ,PI/24, PI/12};
    public static boolean debug = false;

    private int currentHP;
    private static final int RADIUS = 12;
    private final NeuralNetwork neuralNetwork;
    private double angle = 0;
    private int aged = 0;
    private double[] lastInput = new double[]{-1,-1,-1};

    protected Double fitnessFunction()
    {
        return (double) aged;
    }
    AgentCell(int x, int y, double[] weights) {
        super(x, y);
        currentHP = 40;
        this.neuralNetwork = new NeuralNetwork(weights, LAYERS);
        neuralNetwork.setFunction(d-> 2d/(1+Math.exp(-d))-1);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(currentHP, 255-currentHP, 0));
        g.fillOval(x - RADIUS, y - RADIUS, 2*RADIUS, 2*RADIUS);
        g.setColor(Color.BLACK);
        g.drawOval(x - RADIUS, y - RADIUS, 2*RADIUS, 2*RADIUS);
        debug(g);
        //g.drawLine(x,y, x+ (int)((RADIUS+100)*cos(angle)),y+ (int)((RADIUS+100)*sin(angle))  );
        //g.drawLine(x,y, x+ (int)((RADIUS+100)*cos(angle+DELTA_ANGLE)),y+ (int)((RADIUS+100)*sin(angle+DELTA_ANGLE))  );
        //g.drawLine(x,y, x+ (int)((RADIUS+100)*cos(angle-DELTA_ANGLE)),y+ (int)((RADIUS+100)*sin(angle-DELTA_ANGLE))  );
        g.drawString(Integer.toString(currentHP), x,y);
    }
    public void apply(double... values)
    {

        if (values.length != LAYERS[0])
            throw new RuntimeException("Wrong input number!");
//        if (debug)
//        {
//            System.out.print("Values: ");
//            for(double d: values)
//                System.out.print(d+" ");
//            System.out.println();
//            try {
//                int a = System.in.read();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        lastInput = values;

        //double angle = angle(x,y);
        //double distance = sqrt(x*x+y*y);
        double[] result = neuralNetwork.apply(values);
        this.rotate(result[0]);
        this.move((result[1]+1)/2);
    }
    public void rotate(double value)
    {
        this.angle += value*MAX_DEGREE;
        this.angle = this.angle > 2*PI? this.angle - 2*PI : this.angle < 0? this.angle + 2*PI: this.angle;
    }
    public void move(double value)
    {
        this.x += (int)(value*VELOCITY*cos(angle));
        this.y += (int)(value*VELOCITY*sin(angle));
    }

    public int getCurrentHP() {
        return currentHP;
    }
    public void addHP(int HP)
    {
        this.currentHP += HP;
        if (currentHP > MAX_HP)
            currentHP = MAX_HP;
        if (HP == -1)
            aged++;
    }
    public boolean intersects(Food f)
    {
        int dx = f.x - this.x;
        int dy = f.y - this.y;
        double d = Math.sqrt(dx*dx + dy*dy);
        return d < RADIUS + f.getRadius();
    }

    public NeuralNetwork getController() {
        return neuralNetwork;
    }

    private double angle(double x, double y)
    {
        Vector2 rotation = new Vector2(cos(angle), sin(angle));
        Vector2 position = new Vector2(x - this.x, y - this.y);
        return rotation.angle(position);
    }
    public boolean isAlive()
    {
        return currentHP > 0;
    }
    private void debug(Graphics g)
    {
        for(int i = 0; i < lastInput.length; i++)
        {
            //if (lastInput[i] == -1)
                //continue;
            g.drawLine(x, y, x + (int)(300*cos(angle+ANGLES[i])), y + (int)(300*sin(angle+ANGLES[i])));
        }
    }
    public List<Vector2> getDirections()
    {
        List<Vector2> list = new LinkedList<>();
        for(double angle: ANGLES)
        {
            list.add(new Vector2(cos(this.angle+ angle), sin(this.angle+angle)));
        }
        return list;
    }
}
