package Agents.utils;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class Line {
    private double x, y;
    private Vector2 vector;

    public Line(double x, double y, Vector2 vector) {
        this.x = x;
        this.y = y;
        this.vector = vector;
    }

    public Vector2 getVector() {
        return vector;
    }

    public Vector2 intersects(Circle c)
    {
        /*
        * Solves the system of equations:
        * {y = kx+b
        * {(x - x0)^2 + (y - y0)^2 = R^2
        * */
        double k = vector.getY()/vector.getX();
        double b = -k*x + y;
        double A = 1+k*k;
        double B = 2*(k*(b-c.getY())-c.getX());
        double C = c.getX()*c.getX() + Math.pow(b-c.getY(),2) - c.getRadius()*c.getRadius();

        double D = B*B - 4*A*C;
        if (D < 0)
            return null;
        double x1 = (-B + Math.sqrt(D))/(2*A);
        double x2 = (-B - Math.sqrt(D))/(2*A);

        double y1 = k*x1+b;
        double y2 = k*x2+b;
        Vector2 point = new Vector2(x,y);//, solution1 = new Vector2(x1,y1), solution2 = new Vector2(x2,y2);
        Optional<Vector2> vec = Stream.of(new Vector2(x1,y1), new Vector2(x2,y2)).filter(v -> (v.getX() - point.getX())/vector.getX() >= 0).
                min(Comparator.comparingDouble(Vector2::length));
        return vec.orElse(null);
        //double alpha1 = (solution1.getX() - point.getX())/vector.getX();
        //double alpha2 =


        //Vector2 vector1 = new Vector2(x1, y1).sub(new Vector2(x,y)), vector2 = new Vector2(x2,y2).sub(new Vector2(x,y));
        //return vector1.length() < vector2.length()? vector1: vector2;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
