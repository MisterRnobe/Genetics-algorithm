package Life;

import java.awt.*;

import static Life.VirtualMachine.POISON_HP;
import static Life.VirtualMachine.FOOD_HP;

public enum Type
{
    POISON {
        @Override
        public Color getColor() {
            return Color.RED;
        }

        @Override
        public int getCode() {
            return POISON_HP;
        }
    }, FOOD {
    @Override
    public Color getColor() {
            return Color.GREEN;
        }

    @Override
    public int getCode() {
        return FOOD_HP;
    }
    },
    WALL {
    @Override
    public Color getColor() {
            return Color.gray;
        }

        @Override
        public int getCode() {
            return Integer.MIN_VALUE;
        }
    },
    ROBOT {
        @Override
        public Color getColor() {
            return Color.BLUE;
        }

        @Override
        public int getCode() {
            return Integer.MIN_VALUE;
        }
    },
    EMPTY {
        @Override
        public Color getColor() {
            return Color.white;
        }

        @Override
        public int getCode() {
            return 0;
        }
    };

    public abstract Color getColor();
    public abstract int getCode();
}
