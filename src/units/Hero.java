package units;

import java.io.Serializable;
import java.util.Random;

import game.Player;
import map.*;

public class Hero extends Unit implements Serializable {
    public boolean isBuilder = false;
    public int builderLevel = 0;
    public int moveDist = 3;
    public Hero(int number,int x, int y) {
        super(number,400, 100, 70, 1000, x, y, "H");
    }

    @Override
    public void damaging(Unit target) {
        target.takeDamage(damage);
    }

    public void attack(int targetX, int targetY, Player enemy) {
        for (Unit unit : enemy.getUnits()) {
            if ((unit.getX() == targetX) && (unit.getY() == targetY) && unit.isAlive() && (Math.abs(this.x - targetX) < 3) && (Math.abs(this.y - targetY) < 3)) {
                damaging(unit);
                System.out.println("Герой " + number + " атаковал врага на (" + targetX + ", " + targetY + ")!");
                return;
            }
        }

        
        if ((enemy.getBaseX() == targetX) && (enemy.getBaseY() == targetY) && (enemy.getUnits().isEmpty() == true)) {
            enemy.takeDamageBase(damage);
            System.out.println("Герой атаковал базу! нанесено урона: " + damage);
        } 
        else {
            System.out.println("*звук сверчков...*");
        }
    }

    public int destroyObstacle(int targetX, int targetY) {

        if (Math.abs(this.x - targetX) < 2 && Math.abs(this.y - targetY) < 2) {
            return builderLevel;
        }
        else {
            return -1;
        }
    }

    public void takeEffect() {
        Random random = new Random();
        int effect = random.nextInt(4);

        if (effect == 0) {
            takeDamage(15);
            System.out.println("Получен урон от яда 15");
        }

        if (effect == 1) {
            increaseHealth(15);
            System.out.println("+15 хп");
        }

        if (effect == 2) {
            decreaseStamina(10);
            System.out.println("-15 стамины");
        }

        if (effect == 3) {
            decreaseDamage(10);
            System.out.println("-15 к урону");
        }
    }

    @Override
    public void move(int newX, int newY, GameBoard board) {
        if (Math.abs(this.x - newX) < moveDist && Math.abs(this.y - newY) < moveDist) {
            if (board.getCell(newX, newY) == "\u001B[32m.\u001B[0m") {
                stamina -= 1;
                System.out.println("-1 стамина, осталось " + stamina);
                this.x = newX;
                this.y = newY;
            }
            else if (board.getCell(newX, newY) == "\u001B[33m&\u001B[0m" || board.getCell(newX, newY) == "\u001B[35m$\u001B[0m" ) {
                stamina -= 4;
                System.out.println("-4 стамина, осталось " + stamina);
                this.x = newX;
                this.y = newY;
            }
            else {
                System.out.println("Герой может двигаться только на свободную клетку!");    
            }
        }
        else {
            System.out.println("Так далеко герой не сможет пойти!");
        }
    }

    public void upgradeMoveDist() {
        if (moveDist < 4) 
            moveDist++;
        else
            System.out.println("Достигнут максимум");
    }

    public void upgradeBuilderLevel() {
        if (builderLevel < 3) 
            builderLevel++;
        else
            System.out.println("Достигнут максимум");
    }

    public int getBuilderLevel() {
        return builderLevel;
    }

    public int getMoveDist() { return moveDist; }

    public int getDamage() { return damage; }
}

