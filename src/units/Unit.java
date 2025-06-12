package units;

import map.*;

import java.io.Serializable;

public abstract class Unit implements Serializable {
    protected int health;
    protected int damage;
    protected int stamina;
    protected int x, y;
    protected String mark;
    protected int number;
    protected int cost;

    public Unit(int number, int health, int damage, int stamina, int cost, int x, int y, String mark) {
        this.health = health;
        this.damage = damage;
        this.stamina = stamina;
        this.cost = cost;
        this.x = x;
        this.y = y;
        this.mark = mark;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getHealth() {
        return health;
    }

    public int getStamina() {
        return stamina;
    }

    public int getCost() {
        return cost;
    }

    public abstract void damaging(Unit target);

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void move(int newX, int newY, GameBoard board) {
        this.x = newX;
        this.y = newY;
        
    }

    public void decreaseStamina(int decreaser) {
        stamina -= decreaser;
        if (stamina < 0) stamina = 0;
    }

    public void decreaseDamage(int decreaser) {
        damage -= decreaser;
        if (damage < 0) damage = 1;
    }

    public void increaseHealth(int increaser) {
        health += increaser;
    }
    public void increaseDamage(int increaser) { damage += increaser; }
    public void increaseStamina(int increaser) { stamina += increaser; }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getMark() {
        return mark;
    }
    

}