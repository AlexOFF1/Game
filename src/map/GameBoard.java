package map;

import units.Hero;
import units.Unit;
import game.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.util.logging.*;

public class GameBoard implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private transient static final Logger logger = Logger .getLogger(GameBoard.class.getName());
    private final int WIDTH = 10;
    private final int HEIGHT = 10;
    public String[][] board;
    public String[][] cleanBoard;
    public ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    public ArrayList<Casino> casinos = new ArrayList<>();
    private String mapName = "Default";

    static {
        try {
            FileHandler handler = new FileHandler("game.log", true);
            handler.setFormatter(new SimpleFormatter());
            Logger.getLogger(GameBoard.class.getName()).addHandler(handler);
            Logger.getLogger(GameBoard.class.getName()).setUseParentHandlers(false);
        } catch (Exception e) {
            System.err.println("Failed to configure logger: " + e.getMessage());
        }
    }

    public GameBoard() {
        logger.info("Создание игрового поля");

        cleanBoard = new String[HEIGHT+1][WIDTH+1];

        cleanBoard[0][0] = "0";
        try {
            for (int i = 1; i < HEIGHT + 1; i++)
                cleanBoard[i][0] = String.valueOf(i);
            for (int i = 1; i < HEIGHT + 1; i++)
                cleanBoard[0][i] = String.valueOf(i);

            for (int i = 1; i < HEIGHT + 1; i++) {
                for (int j = 1; j < WIDTH + 1; j++) {
                    cleanBoard[i][j] = "\u001B[32m.\u001B[0m";
                }
            }
            for (int i = 1; i < HEIGHT + 1; i++) {
                for (int j = 5; j < 7; j++) {
                    cleanBoard[i][j] = "\u001B[33m&\u001B[0m";
                }
            }
            generateObstacles();
            System.out.println("Игровое поле инициализировано");
            logger.info("Игровое поле инициализировано");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка инициализации поля", e);
        }
        generateObstacles();
    }


    public void generateObstacles() {
        Random random = new Random();
        int count = random.nextInt(5) + 2;

        if (count > 6) {
            logger.warning("Сгенерировано необычное количество препятствий: " + count);
        }

        Obstacle newObstacle = null;
        try {
            for (int k = 0; k < count; k++) {
                int xPos = random.nextInt(3) + 4;
                int yPos = random.nextInt(3) + 4;

                if (xPos < 0 || xPos > WIDTH || yPos < 0 || yPos > HEIGHT) {
                    logger.log(Level.SEVERE, "Некорректные координаты препятствия: (" + xPos + "," + yPos + ")");
                    continue;
                }

                newObstacle = new Obstacle(xPos, yPos);
                obstacles.add(newObstacle);
                cleanBoard[xPos][yPos] = "\u001B[31m|\u001B[0m";
            }
        }
        catch (Exception e) {
            logger.warning("ошибка при генерации препятствий");
        }
    }

    public void deleteObstacle(int xPos, int yPos, Hero hero) {
        Obstacle toDestroy = null;
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isExist() && (obstacle.getX() == xPos) && (obstacle.getY() == yPos)) {
                toDestroy = obstacle;
                break;
            }
        }

        if ((hero.destroyObstacle(xPos, yPos) > 0) && (toDestroy != null)) {
            int damage = hero.destroyObstacle(xPos, yPos) * 100;
            toDestroy.decreaseObstacleHealth(damage);

            if (!toDestroy.isExist()) {
                cleanBoard[xPos][yPos] = "\u001B[32m.\u001B[0m";
                hero.takeEffect(); 
            }
            else
                System.out.println("Препятствие получило " + damage + " урона");
        }

        else {
            System.out.println("Герой не может тут ничего разрушить или у вас нет здания Строителя");
        }

    }

    
    public void updateBoard(Player player1, Player player2) {
        board = new String[HEIGHT+1][WIDTH+1];

        for (int i = 0; i < 11; ++i) {
            for (int j = 0; j < 11; ++j) {
                board[i][j] = cleanBoard[i][j];
            }
        }
        for (Casino casino : casinos) {
            board[casino.getX()][casino.getY()] = casino.getMark();
        }

        board[player1.getBaseX()][player1.getBaseY()] = "P";
        board[player2.getBaseX()][player2.getBaseY()] = "B";

        Iterator<Unit> iterator = player1.getUnits().iterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if (unit.isAlive()) {
                board[unit.getX()][unit.getY()] = unit.getMark();
            } 
            else {
                iterator.remove();
            }
        }

        Iterator<Unit> iterator2 = player2.getUnits().iterator();
        while (iterator2.hasNext()) {
            Unit unit = iterator2.next();
            if (unit.isAlive()) {
                board[unit.getX()][unit.getY()] = unit.getMark() + "b";
            } 
            else {
                iterator2.remove(); 
            }
        }
    }

    public String getCell(int xPos, int yPos) {
        return board[xPos][yPos];
    }

    public void printBoard() {
        for (int i = 0; i < HEIGHT+1; i++) {
            for (int j = 0; j < WIDTH+1; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public void addCasino(int x, int y) {
        casinos.add(new Casino(x, y));
        cleanBoard[x][y] = "$";
    }

    public Casino getCasino(int x, int y) {
        for (Casino casino : casinos) {
            if (casino.getX() == x && casino.getY() == y) {
                return casino;
            }
        }
        return null;
    }

    public void printCleanBoard() {
        for (int i = 0; i < HEIGHT+1; i++) {
            for (int j = 0; j < WIDTH+1; j++) {
                System.out.print(cleanBoard[i][j] + "\t");
            }
            System.out.println();
        }
    }

}