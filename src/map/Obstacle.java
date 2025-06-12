package map;

import java.io.Serializable;

public class Obstacle implements Serializable {
    private int obstacleHealth = 50;
    public int x;
    public int y;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void decreaseObstacleHealth(int damage) {
        obstacleHealth -= damage;
    }

    public boolean isExist() {
        return obstacleHealth > 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
