package Agents.utils;

public interface Circle {
    int getX();
    int getY();
    int getRadius();
    default boolean isInside(Vector2 point)
    {
        return Math.pow(point.getX() - getX(),2) + Math.pow(point.getY() - getY(), 2) <= Math.pow(getRadius(),2);
    }
}
