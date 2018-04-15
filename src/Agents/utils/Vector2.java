package Agents.utils;

public class Vector2 {
    private final double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double length()
    {
        return Math.sqrt(x*x+y*y);
    }
    public double dotProduct(Vector2 vec)
    {
        return x*vec.x + y*vec.y;
    }
    public double angle(Vector2 vec)
    {
        return Math.signum(this.crossProduct(vec))*Math.acos(this.dotProduct(vec)/(this.length()*vec.length()));
    }
    public double crossProduct(Vector2 vec)
    {
        return x*vec.y - y*vec.x;
    }
    public Vector2 sub(Vector2 vec)
    {
        return new Vector2(x-vec.x, y - vec.y);
    }
    public Vector2 add(Vector2 vec)
    {
        return new Vector2(x + vec.x, y + vec.y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[x = "+x+", y = "+y+"]";
    }
}
