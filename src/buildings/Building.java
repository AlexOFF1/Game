package buildings;

import units.Hero;

import java.io.Serializable;

public abstract class Building implements Serializable {
    protected String mark;
    protected int cost;
    protected int number;

    public Building(int number, String mark, int cost) {
        this.mark = mark;
        this.cost = cost;
        this.number = number;
    }

    public abstract void action(Hero hero);


    public int getCost() {
        return cost;
    }
    public int getNumber() {
        return number;
    }
    public String getMark() {
        return mark;
    }
}
