package units;

import game.Player;
import map.*;

import java.io.Serializable;

public class Cavalry extends Unit implements Serializable {
    public Cavalry(int number,int x, int y) {
        super(number,150, 30, 30, 200, x, y, "C");
    }

    @Override
    public void damaging(Unit target) {
        target.takeDamage(damage);
    }

    public void attack(Player enemy) {
        int targetX = this.x;
        int targetY = this.y + 1; // Клетка перед кавалерией

        for (Unit unit : enemy.getUnits()) {
            if ((unit.getX() == targetX) && (unit.getY() == targetY) && unit.isAlive()) {
                damaging(unit);
                System.out.println("Кавалерия " + number + " атаковала врага на (" + targetX + ", " + targetY + ")!");
                return;
            }
        }

        if ((enemy.getBaseX() == targetX) && (enemy.getBaseY() == targetY) && (enemy.getUnits().isEmpty() == true)) {
            enemy.takeDamageBase(damage);
            System.out.println("Кавалерия атаковала базу! нанесено урона: " + damage);
        } 
        else {
            System.out.println("*звук сверчков...*");
        }
    }

    @Override
    public void move(int newX, int newY, GameBoard board) {
        if (Math.abs(this.x - newX) < 3 && Math.abs(this.y - newY) < 3) {
            if (board.getCell(newX, newY) == "\u001B[32m.\u001B[0m") {
                stamina -= 1;
                this.x = newX;
                this.y = newY;
            }
            else if (board.getCell(newX, newY) == "\u001B[33m&\u001B[0m") {
                stamina -= 4;
                this.x = newX;
                this.y = newY;
            }
            else {
                System.out.println("Кавалерия может двигаться только на свободную клетку!");    
            }
        }
        else {
            System.out.println("Так далеко кавалерия не сможет пойти!");
        }
    }
}