package units;

import game.Player;
import map.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArcherTest {
    private Archer archer;
    private Player enemy;
    private GameBoard board;

    @BeforeEach
    void setUp() {
        archer = new Archer(1, 5, 5);
        enemy = new Player("Enemy", 10, 10, 100, 1000);
        board = new GameBoard();
    }

    @Test
    void testInitialStats() {
        assertEquals(50, archer.getHealth());
        assertEquals(10, archer.getDamage());
        assertEquals(0, archer.getStamina());
        assertEquals(100, archer.getCost());
    }

    @Test
    void testCannotMove() {
        archer.move(5, 6, board);
        assertEquals(5, archer.getX()); // Остается на месте
        assertEquals(5, archer.getY());
    }

    @Test
    void testAttack() {
        Unit target = new Infantry(2, 7, 7);
        enemy.addUnit(target);
        archer.attack(7, 7, enemy);
        assertEquals(30 - 10, target.getHealth());
    }

    @Test
    void testAttackBase() {
        enemy.getUnits().clear(); // Очищаем юнитов для атаки базы
        archer.attack(10, 10, enemy);
        assertEquals(100 - 10, enemy.getBaseHealth());
    }
}
