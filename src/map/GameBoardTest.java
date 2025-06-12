package map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import units.Hero;
import game.Player;
import units.Archer;
import units.Infantry;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


class GameBoardTest {
    private GameBoard gameBoard;
    private Player player1;
    private Player player2;
    private Hero hero;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        gameBoard = new GameBoard();
        player1 = new Player("Player1", 5, 1, 100, 1000);
        player2 = new Player("Player2", 5, 10, 100, 1000);
        hero = new Hero(1, 5, 5);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testInitialBoardCreation() {
        gameBoard.updateBoard(player1, player2);
        assertNotNull(gameBoard);
        assertEquals(11, gameBoard.board.length);
        assertEquals(11, gameBoard.board[0].length);
    }

    @Test
    void testObstacleGeneration() {
        assertFalse(gameBoard.obstacles.isEmpty());
        assertTrue(outContent.toString().contains("Игровое поле инициализировано"));
    }

    @Test
    void testUpdateBoardWithPlayers() {
        player1.addUnit(new Archer(1, 3, 3));
        player2.addUnit(new Infantry(2, 7, 7));

        gameBoard.updateBoard(player1, player2);

        assertEquals("A", gameBoard.board[3][3]);
        assertEquals("Ib", gameBoard.board[7][7]);
        assertEquals("P", gameBoard.board[5][1]);
        assertEquals("B", gameBoard.board[5][10]);
    }

    @Test
    void testDeleteObstacleSuccess() {
        Obstacle obstacle = gameBoard.obstacles.get(0);
        int x = obstacle.getX();
        int y = obstacle.getY();

        hero = new Hero(1, x, y-1);


        hero.upgradeBuilderLevel();

        gameBoard.deleteObstacle(x, y, hero);

        assertEquals("\u001B[32m.\u001B[0m", gameBoard.cleanBoard[x][y]);
    }
}
