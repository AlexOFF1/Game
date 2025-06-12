package buildings;

import units.Hero;

import java.io.Serializable;

public class Builder extends Building implements Serializable {
    public Builder(int number) {
        super(number, "BH",1000);
    }

    public void action(Hero hero) {
        hero.upgradeBuilderLevel();
        System.out.println("success");
    }
}
