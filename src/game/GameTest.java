package game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import map.GameBoard;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private Player player;
    private Player bot;
    private GameBoard board;

    @BeforeEach
    void setUp() {
        board = new GameBoard();
        player = new Player("Player", 5, 1, 5, 10000);
        bot = new Player("Bot", 5, 10, 5, 10000);
    }

    @Test
    void testGameInitialization() {
        assertNotNull(board);
        assertNotNull(player);
        assertNotNull(bot);
    }

    @Test
    void testPlayerBaseDestruction() {
        player.takeDamageBase(5); // Наносим урон базе бота

        assertTrue(player.isBaseDestroyed());
    }

    @Test
    void testEnemyBaseDestruction() {
        bot.takeDamageBase(5); // Наносим урон базе бота

        assertTrue(bot.isBaseDestroyed());
    }
}