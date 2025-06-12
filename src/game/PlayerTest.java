package game;

import buildings.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import units.Infantry;
import units.Unit;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private Unit infantry;

    @BeforeEach
    void setUp() {
        player = new Player("Test", 5, 5, 100, 10000);
        infantry = new Infantry(1, 3, 3);
    }

    @Test
    void testInitialState() {
        assertEquals("Test", player.getName());
        assertEquals(5, player.getBaseX());
        assertEquals(5, player.getBaseY());
        assertEquals(100, player.getBaseHealth());
        assertEquals(10000, player.getMoney());
        assertTrue(player.getUnits().isEmpty());
    }

    @Test
    void testAddUnit() {
        player.addUnit(infantry);
        assertEquals(1, player.getUnits().size());
        assertEquals(10000 - 50, player.getMoney());
    }

    @Test
    void testAddUnitNotEnoughMoney() {
        player = new Player("Test", 5, 5, 100, 40);
        player.addUnit(infantry);
        assertTrue(player.getUnits().isEmpty()); // Юнит не добавлен
    }

    @Test
    void testTakeDamageBase() {
        player.takeDamageBase(30);
        assertEquals(70, player.getBaseHealth());
    }

    @Test
    void testIsBaseDestroyed() {
        assertFalse(player.isBaseDestroyed());
        player.takeDamageBase(100);
        assertTrue(player.isBaseDestroyed());
    }

    @Test
    void testAddBuilding() {
        Builder builder = new Builder(1);
        player.addBuilding(builder);
        assertEquals(1, player.getBuildings().size());
        assertEquals(10000 - 1000, player.getMoney());
    }
}
