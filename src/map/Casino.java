package map;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public class Casino implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int x;
    private final int y;
    private static final String MARK = "\u001B[35m$\u001B[0m";

    public Casino(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Геттеры остаются без изменений
    public int getX() { return x; }
    public int getY() { return y; }
    public String getMark() { return MARK; }
    public int takeMoney() { return 1000; }

}