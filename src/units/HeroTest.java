package units;

import game.Player;
import map.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HeroTest {
    private Hero hero;
    private Player player;
    private Player enemy;
    private GameBoard board;

    @BeforeEach
    void setUp() {
        hero = new Hero(1, 2, 2);
        player = new Player("Player", 1, 1, 100, 10000000);
        player.addUnit(hero);
        enemy = new Player("Enemy", 3, 3, 100, 10000000);
        board = new GameBoard();
    }

    @Test
    void testInitialStats() {
        assertEquals(400, hero.getHealth());
        assertEquals(70, hero.getStamina());
        assertEquals(1000, hero.getCost());
        assertEquals(100, hero.getDamage());
        assertEquals(3, hero.getMoveDist());
        assertEquals(0, hero.getBuilderLevel());
    }

    @Test
    void testAttackUnit() {
        Unit target = new Infantry(2, 2, 1);
        enemy.addUnit(target);
        hero.attack(2, 1, enemy);
        assertEquals(0, target.getHealth());
    }

    @Test
    void testAttackBase() {
        enemy.getUnits().clear(); // Очищаем юнитов для атаки базы
        hero.attack(3, 3, enemy);
        assertEquals(0, enemy.getBaseHealth());
    }

    @Test
    void testMoveValid() {
        board.updateBoard(player, enemy);
        hero.move(1, 2, board);
        assertEquals(1, hero.getX());
        assertEquals(2, hero.getY());
        assertEquals(70 - 1, hero.getStamina());
    }

    @Test
    void testMoveTooFar() {
        hero.move(9, 9, board);
        assertEquals(2, hero.getX()); // Остается на месте
        assertEquals(2, hero.getY());
    }

    @Test
    void testDestroyObstacle() {
        assertEquals(-1, hero.destroyObstacle(4, 4)); // Слишком далеко
        assertEquals(0, hero.destroyObstacle(3, 2)); // В зоне досягаемости
    }

    @Test
    void testUpgrades() {
        hero.upgradeBuilderLevel();
        assertEquals(1, hero.getBuilderLevel());

        hero.upgradeMoveDist();
        assertEquals(4, hero.getMoveDist());
    }

    @Test
    void testTakeEffect() {
        int initialHealth = hero.getHealth();
        hero.takeEffect(); // Может быть любой из 4 эффектов
        assertTrue(hero.getHealth() != initialHealth || hero.getDamage() != 100 || hero.getStamina() != 70);
    }
}