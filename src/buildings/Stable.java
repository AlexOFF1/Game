package buildings;

import units.Hero;

import java.io.Serializable;

public class Stable extends Building implements Serializable {
    public Stable(int number) {
        super(number, "HS",500);
    }

    @Override
    public void action(Hero hero) {
        hero.upgradeMoveDist();
    }
}
