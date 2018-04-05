package Life;

public class Map {
    public static final int WIDTH = 96, HEIGHT = 68;
    public static final int MAX_FOOD = 800, MAX_POISON = 800;
    public static int foodCount = 0, poisonCount = 0;

    private static final Entity EMPTY = new Entity(Type.EMPTY, 0, 0);
    private static final Entity WALL = new Entity(Type.WALL, 0, 0);
    private static Entity[][] MAP = new Entity[WIDTH][HEIGHT];
    static {
        for (int i = 0; i < WIDTH; i++) {
            MAP[i][0] = new Entity(Type.WALL, i, 0);
            MAP[i][HEIGHT - 1] = new Entity(Type.WALL, i, HEIGHT - 1);
        }
        for (int i = 1; i < HEIGHT - 1; i++) {
            MAP[WIDTH-1][i] = new Entity(Type.WALL, WIDTH - 1, i);
            MAP[0][i] = new Entity(Type.WALL, 0, i);
        }
        for (int i = 1; i < 24; i++) {
            MAP[15][i] = new Entity(Type.WALL, 15, i);
            MAP[WIDTH-i][HEIGHT - 15] = new Entity(Type.WALL, WIDTH - i, HEIGHT-15);
        }
        for (int i = 0; i < 30; i++) {
            MAP[70][i+1] = new Entity(Type.WALL, 70,i+1);
        }
    }

    public static Entity getEntityAt(int x, int y) {

        return x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT? WALL:
                (MAP[x][y] == null? EMPTY: MAP[x][y]);
    }

    public static void move(Entity e, int x, int y)
    {
        if (e.getType() == Type.FOOD)
            foodCount--;
        else
            if (e.getType() == Type.POISON)
                poisonCount--;
        MAP[e.getX()][e.getY()] = null;
        MAP[x][y] = e;
        e.setX(x);
        e.setY(y);
    }
    public static void remove(Entity e)
    {
        if (e.getType() == Type.FOOD)
            foodCount--;
        else
        if (e.getType() == Type.POISON)
            poisonCount--;
        MAP[e.getX()][e.getY()] = null;
    }
    public static boolean addEntity(Entity e)
    {
        if (MAP[e.getX()][e.getY()] != null)
            return false;
        MAP[e.getX()][e.getY()] = e;
        if (e.getType() == Type.FOOD)
            foodCount++;
        else
        if (e.getType() == Type.POISON)
            poisonCount++;
        return true;
    }
    public static void clearMap()
    {
        for (int i = 0; i <  WIDTH;i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (MAP[i][j]!= null && MAP[i][j].getType() != Type.WALL)
                    MAP[i][j] = null;
            }
        }
        foodCount = 0;
        poisonCount = 0;
    }
}
