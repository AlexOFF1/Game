package game;

import buildings.Building;
import units.Unit;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable{
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private ArrayList<Building> buildings = new ArrayList<Building>();;
    private int baseX, baseY;
    private String name;
    private int baseHealth;
    private int money;

    public Player(String name, int baseX, int baseY, int baseHealth, int money) {
        this.name = name;
        this.baseX = baseX;
        this.baseY = baseY;
        this.units = new ArrayList<>();
        this.buildings = new ArrayList<>();
        this.baseHealth = baseHealth;
        this.money = money;
    }

    public void addUnit(Unit unit) {
        if (money > unit.getCost()) {
            units.add(unit);
            decreaseMoney(unit.getCost());
        }
        else {
            System.out.println("Деняки кончились :(");
        }
    }

    public void addBuilding(Building building) {
        if (money > building.getCost()) {
            buildings.add(building);
            decreaseMoney(building.getCost());
        }
        else {
            System.out.println("Деняки кончились :(");
        }
    }    

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public int getBaseX() {
        return baseX;
    }

    public int getBaseY() {
        return baseY;
    }

    public String getName() {
        return name;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getMoney() {
        return money; 
    }

    public void decreaseMoney(int cost) {
        money -= cost;
    }

    public void takeDamageBase(int damage) {
        baseHealth -= damage;
        if (baseHealth < 0) baseHealth = 0;
        System.out.println("База " + name + " получила урон, осталось здоровья: " + baseHealth);
    }

    public boolean isBaseDestroyed() {
        return baseHealth <= 0;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public void startNPCVisitors() {
        NPCVisitor.startNPCVisitors(this);
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

}