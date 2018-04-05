package Game2048.game;

import java.util.Random;

public class Map {
    private static Map instance = new Map();

    public static Map getInstance() {
        return instance;
    }

    private int currentScore;
    private final int[][] map;
    private Map()
    {
        map = new int[4][4];
        restart();
    }
    public void restart()
    {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                map[i][j] = 0;
            }
        }
        currentScore = 0;
        generate();
        generate();
        generate();
    }
    public boolean generate()
    {
        int currentCell = 0;
        int[][] freeCells = new int[16][2];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 0)
                {
                    freeCells[currentCell][0] = i;
                    freeCells[currentCell][1] = j;
                    currentCell++;
                }
            }
        }
        if (currentCell == 0)
            return false;
        Random r = new Random();
        int index = r.nextInt(currentCell);
        map[ freeCells[index][0] ][ freeCells[index][1] ] = 2;
        return true;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public boolean moveLeft()
    {
        boolean moved = false;
        for (int i = 1; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if ( map[i][j]!=0 )
                {
                    if (map[i][j] == map[i-1][j])
                    {
                        currentScore += map[i][j];
                        map[i-1][j] *= 2;
                        map[i][j] = 0;
                        moved = true;
                    }
                    else
                    if (map[i-1][j] == 0)
                    {
                        map[i - 1][j] = map[i][j];
                        map[i][j] = 0;
                        moved = true;
                    }
                }
            }
        }
        if (moved)
            moved = generate();
        return moved;
    }

    public boolean moveRight()
    {
        boolean moved = false;
        for (int i = map.length - 2; i >= 0; i--) {
            for (int j = 0; j < map[i].length; j++) {
                if ( map[i][j]!=0 )
                {
                    if (map[i][j] == map[i+1][j])
                    {
                        currentScore += map[i][j];
                        map[i+1][j] *= 2;
                        map[i][j] = 0;
                        moved = true;
                    }
                    else
                    if (map[i+1][j] == 0)
                    {
                        map[i+1][j] = map[i][j];
                        map[i][j] = 0;
                        moved = true;
                    }
                }
            }
        }
        if (moved)
            moved = generate();
        return moved;
    }
    public boolean moveUp()
    {
        boolean moved = false;
        for (int i = 1; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if ( map[j][i]!=0 )
                {
                    if (map[j][i] == map[j][i-1])
                    {
                        currentScore += map[j][i];
                        map[j][i-1] *= 2;
                        map[j][i] = 0;
                        moved = true;
                    }
                    else
                    if (map[j][i-1] == 0)
                    {
                        map[j][i-1] = map[j][i];
                        map[j][i] = 0;
                        moved = true;
                    }
                }
            }
        }
        if (moved)
            moved = generate();
        return moved;
    }

    public boolean moveDown()
    {
        boolean moved = false;
        for (int i = map.length-2; i >= 0; i--) {
            for (int j = 0; j < map[i].length; j++) {
                if ( map[j][i]!=0 )
                {
                    if (map[j][i] == map[j][i+1])
                    {
                        currentScore += map[j][i];
                        map[j][i+1] *= 2;
                        map[j][i] = 0;
                        moved = true;
                    }
                    else
                    if (map[j][i+1] == 0)
                    {
                        map[j][i+1] = map[j][i];
                        map[j][i] = 0;
                        moved = true;
                    }
                }
            }
        }
        if (moved)
            moved = generate();
        return moved;
    }

    public int[][] getMap()
    {
        return map;
    }
}
