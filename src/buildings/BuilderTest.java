package buildings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import units.Hero;

import static org.junit.jupiter.api.Assertions.*;

class BuilderTest {
    private Builder builder;
    private Hero hero;

    @BeforeEach
    void setUp() {
        builder = new Builder(1);
        hero = new Hero(1, 5, 5);
    }

    @Test
    void testInitialState() {
        assertEquals("BH", builder.getMark());
        assertEquals(1000, builder.getCost());
    }

    @Test
    void testAction() {
        assertEquals(0, hero.getBuilderLevel());
        builder.action(hero);
        assertEquals(1, hero.getBuilderLevel());
    }

    @Test
    void testMaxLevel() {
        for (int i = 0; i < 4; i++) {
            builder.action(hero);
        }
        assertEquals(3, hero.getBuilderLevel()); // Не должен превысить максимум
    }
}
