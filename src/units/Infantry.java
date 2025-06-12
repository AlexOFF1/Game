package units;

import game.Player;
import map.*;

import java.io.Serializable;

public class Infantry extends Unit implements Serializable {
    
    public Infantry(int number,int x, int y) {
        super(number, 30, 50, 20, 50, x, y, "I");
    }

    @Override
    public void move(int newX, int newY, GameBoard board) {
        if ((newX == this.x) && ((newY == this.y + 1) || (newY == this.y - 1))) {
            if (board.getCell(newX, newY) == "\u001B[32m.\u001B[0m") {
                stamina -= 1;
                this.y = newY;
            }
            else if (board.getCell(newX, newY) == "\u001B[33m&\u001B[0m") {
                stamina -= 4;
                this.y = newY;
            }
            else {
                System.out.println("Пехотинец может двигаться только на свободную клетку!");    
            }
        } else {
            System.out.println("Пехотинец может двигаться только на одну клетку вперед/назад!");
        }
    }

    @Override
    public void damaging(Unit target) {
        target.takeDamage(damage);
    }

    public void attack(Player enemy) {
        int targetX = this.x;
        int targetY = this.y + 1;

        for (Unit unit : enemy.getUnits()) {
            if ((unit.getX() == targetX) && (unit.getY() == targetY) && unit.isAlive()) {
                damaging(unit);
                System.out.println("Пехотинец атаковал врага на (" + targetX + ", " + targetY + ") и нанёс урона: " + damage);
                return;
            }
        }
        
        if ((enemy.getBaseX() == targetX) && (enemy.getBaseY() == targetY) && (enemy.getUnits().isEmpty() == true)) {
            enemy.takeDamageBase(damage);
            System.out.println("Пехотинец атаковал базу! наненсено урона: " + damage);
        } else {
            System.out.println("*звук сверчков...*");
        }
    }

    public int getDamage() { return damage; }
}