package Life;

public class Entity {
    private int x, y;
    private Type type;
    Entity(Type type, int x, int y)
    {
        this.type = type;
        this.x = x;
        this.y = y;
    }


    public Type getType() {
        return type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void poisonToFood()
    {
        this.type = Type.FOOD;
    }


}
