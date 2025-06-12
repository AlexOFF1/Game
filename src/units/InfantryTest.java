package units;

import game.Player;
import map.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InfantryTest {
    private Infantry infantry;
    private Player enemy;
    private Player player;
    private GameBoard board;

    @BeforeEach
    void setUp() {
        infantry = new Infantry(1, 5, 2);
        player = new Player("Player", 1, 1, 100, 1000);
        player.addUnit(infantry);
        enemy = new Player("Enemy", 5, 4, 100, 1000);
        board = new GameBoard();
    }

    @Test
    void testInitialStats() {
        assertEquals(30, infantry.getHealth());
        assertEquals(50, infantry.getDamage());
        assertEquals(20, infantry.getStamina());
        assertEquals(50, infantry.getCost());
    }

    @Test
    void testMoveForward() {
        board.updateBoard(player, enemy);
        infantry.move(5, 3, board);
        assertEquals(3, infantry.getY());
        assertEquals(20 - 1, infantry.getStamina());
    }

    @Test
    void testMoveBackward() {
        board.updateBoard(player, enemy);
        infantry.move(5, 1, board);
        assertEquals(1, infantry.getY());
    }

    @Test
    void testInvalidMove() {
        infantry.move(6, 2, board); // Попытка двигаться вбок
        assertEquals(5, infantry.getX()); // Остается на месте
    }

    @Test
    void testAttack() {
        Unit target = new Infantry(2, 5, 3);
        enemy.addUnit(target);
        infantry.attack(enemy);
        assertEquals(0, target.getHealth());
    }

    @Test
    void testAttackBase() {
        board.updateBoard(player, enemy);
        enemy.getUnits().clear(); // Очищаем юнитов для атаки базы
        infantry.move(5,3, board);
        infantry.attack(enemy);
        assertEquals(100 - 50, enemy.getBaseHealth());
    }
}
