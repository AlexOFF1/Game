package units;

import map.*;
import game.Player;

import java.io.Serializable;

public class Archer extends Unit implements Serializable {
    public Archer(int number,int x, int y) {
        super( number,50, 10, 0, 100, x, y, "A");
    }


    public void move(int newX, int newY, GameBoard board) {
        System.out.println("Лучник неподвижен");
    }

    @Override
    public void damaging(Unit target) {
        target.takeDamage(damage);
    }

    public void attack(int targetX, int targetY, Player enemy) {
        for (Unit unit : enemy.getUnits()) {
            if ((unit.getX() == targetX) && (unit.getY() == targetY) && unit.isAlive()) {
                damaging(unit);
                System.out.println("Лучник атаковал врага на (" + targetX + ", " + targetY + ") и нанёс урона: " + damage);
                return;
            }
        }

        if ((enemy.getBaseX() == targetX) && (enemy.getBaseY() == targetY) && (enemy.getUnits().isEmpty() == true)) {
            enemy.takeDamageBase(damage);
            System.out.println("Лучник атаковал базу! нанесено урона: " + damage);
        } 
        else {
            System.out.println("*звук сверчков...*");
        }
    }

    public int getDamage() { return damage; }
}