package units;

import game.Player;
import map.*;

import java.io.Serializable;

public class Cannon extends Unit implements Serializable {
    public Cannon(int number,int x, int y) {
        super(number,120, 50, 0, 300, x, y, "Z");
    }

    @Override
    public void damaging(Unit target) {
        target.takeDamage(damage);
    }

    public void attack(int targetX, int targetY, Player enemy) {

        for (Unit unit : enemy.getUnits()) {
            if ((unit.getX() == targetX) && (unit.getY() == targetY) && unit.isAlive()) {
                damaging(unit);
                System.out.println("Пушка атаковала врага на (" + targetX + ", " + targetY + ") и нанёс урона: " + damage);
                return;
            }
        }
    }

    public void move(int newX, int newY, GameBoard board) {
        System.out.println("Пушка неподвижна!");
    }
}