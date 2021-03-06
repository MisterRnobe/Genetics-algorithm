package Agents;

import Agents.utils.NeuralNetwork;
import Agents.utils.Vector2;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;

public class AgentCell extends Entity
{
    private int currentHP;
    private final NeuralNetwork neuralNetwork;
    private double angle = 0;
    private int aged = 0;
    private double lastAngle = angle;
    private double[] lastValues;


    //Static useful variables
    private static final int MAX_HP = 255;
    private static final double MAX_DEGREE = PI/6;
    private static final double VELOCITY = 10;
    private static final int RADIUS = 12;

    static final int[] LAYERS = new int[]{30, 40, 30, 2};
    private static final double DELTA_ANGLE = PI/36;
    private static final double[] ANGLES = new double[LAYERS[0]/2];
    static
    {
        int countForSide = (LAYERS[0]/2 - 1) / 2;
        double minAngle = DELTA_ANGLE*countForSide;
        Arrays.setAll(ANGLES, i -> minAngle + i*DELTA_ANGLE);
    }




    protected Double fitnessFunction()
    {
        return (double) aged;
    }
    AgentCell(int x, int y, double[] weights) {
        super(x, y);
        currentHP = 40;
        this.neuralNetwork = new NeuralNetwork(weights, LAYERS);
        neuralNetwork.setFunction(d-> 2d/(1+Math.exp(-d))-1);
        lastValues = new double[LAYERS[0]/2];
        Arrays.fill(lastValues, -1);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0, currentHP, 255-currentHP));
        g.fillOval(x - RADIUS, y - RADIUS, 2*RADIUS, 2*RADIUS);
        g.setColor(Color.BLACK);
        g.drawOval(x - RADIUS, y - RADIUS, 2*RADIUS, 2*RADIUS);
        debug(g);
        /*
        g.drawLine(x,y, x+ (int)((RADIUS+100)*cos(angle)),y+ (int)((RADIUS+100)*sin(angle))  );
        g.drawLine(x,y, x+ (int)((RADIUS+100)*cos(angle+DELTA_ANGLE)),y+ (int)((RADIUS+100)*sin(angle+DELTA_ANGLE))  );
        g.drawLine(x,y, x+ (int)((RADIUS+100)*cos(angle-DELTA_ANGLE)),y+ (int)((RADIUS+100)*sin(angle-DELTA_ANGLE))  );
        */
        g.drawString(Integer.toString(currentHP), x,y);
    }
    public void apply(double... values)
    {
        //System.out.println("Values: "+values.length+"\nInput layer "+LAYERS[0]);
        if (values.length != LAYERS[0])
            throw new RuntimeException("Wrong input number!");
        //double angle = angle(x,y);
        //double distance = sqrt(x*x+y*y);
        double[] result = neuralNetwork.apply(values);
        Arrays.setAll(lastValues, i -> values[1+2*i]);
        lastAngle = angle;
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
    public boolean intersects(ConsumingObject f)
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
        for(int i = 0; i < ANGLES.length; i++)
        {
            if (lastValues[i] == -1)
                continue;
            g.drawLine(x, y,
                    x + (int)(lastValues[i]*cos(lastAngle+ANGLES[i])),
                    y + (int)(lastValues[i]*sin(lastAngle+ANGLES[i])));
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
