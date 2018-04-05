package Life;

public class VirtualMachine {
    public static final int FOOD_HP = 13;
    public static final int POISON_HP = FOOD_HP * (-3)/2;

    public void execute(Robot robot)
    {
        boolean isFinished = false;
        for (int i = 0; i < 3 && !isFinished; i++) {
            int output = robot.apply(input(robot));
            if (output < 3)
            {
                move(robot, output);
                isFinished = true;
            }
            else if (output < 6)
            {
                grab(robot, output - 3);
                isFinished = true;
            }
            else
            {
                rotate(robot, output - 6);
            }
        }
        robot.addHp(-1);
        if (robot.getHp() <= 0)
        {
            Map.remove(robot);
        }

    }
    private double[] input(Robot r)
    {
        double[] values = new double[8];
        int index = 0;
        int x = (int)Math.sin(r.getRotation()*Math.PI/2d), y = (int) Math.cos(r.getRotation()*Math.PI/2);
        if (x == 0)
        {
            for (int i = -1; i <= 1; i++) {
                values[index++] = Map.getEntityAt(r.getX() + i, r.getY() + y).getType().getCode();
            }
            for (int i = -2; i <= 2; i++) {
                values[index++] = Map.getEntityAt(r.getX() + i, r.getY() + y + 1).getType().getCode();
            }
        }
        else
        {
            for (int i = -1; i <= 1; i++) {
                values[index++] = Map.getEntityAt(r.getX() + x, r.getY() + i).getType().getCode();
            }
            for (int i = -2; i <= 2; i++) {
                values[index++] = Map.getEntityAt(r.getX() + x + 1, r.getY() + i).getType().getCode();
            }
        }
        return values;
    }
    private void move(Robot r, int command)
    {
        int x = (int)Math.sin(r.getRotation()*Math.PI/2d), y = (int) Math.cos(r.getRotation()*Math.PI/2);
        if (x == 0)
            x = command - 1;
        else
            y = command - 1;
        Entity e = Map.getEntityAt(r.getX() + x, r.getY() + y);
        if (e.getType() == Type.EMPTY) {
            Map.move(r, x + r.getX(), y + r.getY());
        }
        if (e.getType() == Type.FOOD)
        {
            r.addHp(FOOD_HP);
            Map.remove(e);
            Map.move(r, x + r.getX(), y + r.getY());
        }
        if (e.getType() == Type.POISON)
        {
            r.addHp(POISON_HP);
            Map.remove(e);
            Map.move(r, x+ r.getX(), y + r.getY());
        }
    }
    private int rotate(Robot robot, int command)
    {
        int rotation = robot.getRotation();
        command = command == 0? -1: 1;
        rotation += command;
        rotation = rotation == -1? 3: (rotation == 4? 0: rotation);
        robot.setRotation(rotation);
        return 1;
    }
    private void grab(Robot r, int command)
    {
        int x = (int)Math.sin(r.getRotation()*Math.PI/2d), y = (int) Math.cos(r.getRotation()*Math.PI/2);
        if (x == 0)
            x = command - 1;
        else
            y = command - 1;

        Entity e = Map.getEntityAt(r.getX() + x, r.getY() + y);

        if (e.getType() == Type.FOOD)
        {
            r.addHp(FOOD_HP);
            Map.remove(e);
        }
        if (e.getType() == Type.POISON)
        {
            e.poisonToFood();
            Map.foodCount++;
            Map.poisonCount--;
        }
    }
}
