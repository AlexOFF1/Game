package game;

import buildings.*;
        import map.*;
        import units.*;

import java.io.IOException;
import java.util.*;



public class Game {
    private Player player;
    private Player bot;
    private GameBoard board;
    private boolean casinoUnlocked = false;
    private final Scanner scanner = new Scanner(System.in);
    RecordsManager recordsManager = new RecordsManager();

    public static void main(String[] args) {
        new Game().run();
    }

    public void run() {
        initializeGame();
        mainLoop();
    }

    private void initializeGame() {
        System.out.println("Введите ваше имя:");
        String name = scanner.nextLine();
        this.player = new Player(name, 5, 1, 100, 10000);
        this.bot = new Player("Bot", 5, 10, 100, 1000);
        this.board = new GameBoard();

        player.addBuilding(new Hotel(1));
        player.addBuilding(new Cafe(2));
        player.addBuilding(new BarberShop(3));

        player.addUnit(new Hero(1, 5, 2));
        player.addUnit(new Archer(2, 6, 2));
        bot.addUnit(new Archer(1, 6, 9));
        player.startNPCVisitors();
    }

    private void mainLoop() {
        while (true) {
            board.updateBoard(player, bot);
            board.printBoard();

            System.out.println("1. Сделать ход | 2. Редактор карт | 3. Блэкджек | 4. Сохранить игру | 5. Загрузить игру | 6. Рекорды | 7. Выход");
            switch (scanner.nextInt()) {
                case 1 -> game();
                case 2 -> {
                    MapEditor editor = new MapEditor(casinoUnlocked);
                    editor.start();
                    this.board = editor.getEditedBoard();
                }
                case 3 -> playBlackjack();
                case 4 -> saveGame();
                case 5 -> loadGame();
                case 6 -> recordsManager.printTopRecords();
                case 7 -> { return; }
                default -> System.out.println("Неверный выбор");

            }
        }
    }

    private void playBlackjack() {
        if (new BlackjackGame().play()) {
            casinoUnlocked = true;
            System.out.println("Казино разблокировано! Теперь можно добавлять казино в редакторе карт.");
        }
    }

    private void game() {
        boolean gameOver = false;
        checkCasino();
        while (!gameOver) {
            board.updateBoard(player, bot);
            board.printBoard();
            System.out.println("Выбери, кто будет спасать мир:");
            for (Unit unit : player.getUnits()) {
                if (unit.isAlive()) {
                    System.out.println(unit.getNumber() + " - " + unit.getMark() + " (x: " + unit.getX() + ", y: " + unit.getY() + ")");
                }
            }
            int unitChoice = scanner.nextInt();

            Unit playerUnit = null;
            for (Unit unit : player.getUnits()) {
                if ((unit.getNumber() == unitChoice) && unit.isAlive()) {
                    playerUnit = unit;
                    break;
                }
            }
            if (playerUnit == null) {
                System.out.println("Ошибка в выборе юнита");
            }

            int action;

            if (playerUnit != null) {
                System.out.println("Выбери действие: 1 - Идти, 2 - Атака, 3 - Использовать здание, 4 - Разрушить препятствие");
                action = scanner.nextInt();
            }
            else
                action = 0;


            if (action == 1) {
                System.out.println("Куда держим путь? введи координату точки (x,y): ");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                playerUnit.move(x, y, board);

            }
            else if (action == 2) {
                if (playerUnit instanceof Archer) {
                    System.out.println("Введи координату для атаки (x y): ");
                    int posX = scanner.nextInt();
                    int posY = scanner.nextInt();
                    ((Archer) playerUnit).attack(posX, posY, bot);
                }
                else if (playerUnit instanceof Hero) {
                    System.out.println("Введи координату для атаки (x y): ");
                    int posX = scanner.nextInt();
                    int posY = scanner.nextInt();
                    ((Hero) playerUnit).attack(posX, posY, bot);
                }
                else if (playerUnit instanceof Cannon) {
                    System.out.println("Введи координату для атаки (x y): ");
                    int posX = scanner.nextInt();
                    int posY = scanner.nextInt();
                    ((Cannon) playerUnit).attack(posX, posY, bot);
                }
                else if (playerUnit instanceof Infantry) {
                    ((Infantry) playerUnit).attack(bot);
                }
                else if (playerUnit instanceof Cavalry) {
                    ((Cavalry) playerUnit).attack(bot);
                }
                else
                    System.out.println("Этот враг сейчас с нами в одной комнате?..");
            }

            else if (action == 3) {
                if (playerUnit instanceof Hero) {
                    useBuilding((Hero)playerUnit);
                }
            }
            else if (action == 4) {
                if (playerUnit instanceof Hero) {
                    System.out.println("Введи координату препятствия (x y): ");
                    int posX = scanner.nextInt();
                    int posY = scanner.nextInt();
                    board.deleteObstacle(posX, posY, (Hero) playerUnit);
                }
            }

            if (bot.isBaseDestroyed()) {
                System.out.println("Победа! База врага стерта с лица земли");
                recordsManager.addRecord(player.getName(), calculateScore(), "DefaultMap");
                autoSave();
                gameOver = true;
                continue;
            }


            //  Ход бота

            System.out.println("Ход бота...");
            Unit botUnit = bot.getUnits().get(0);
            if (player.getUnits().isEmpty() == false) {
                ((Archer)botUnit).attack(player.getUnits().get(0).getX(), player.getUnits().get(0).getY(), player);
            }
            else if (player.getUnits().isEmpty() == true) {
                ((Archer)botUnit).attack(player.getBaseX(), player.getBaseY(), player);
            }
            else {
                botUnit = bot.getUnits().get(1);
                if ((board.getCell(botUnit.getX(), botUnit.getY() - 1) == "\u001B[32m.\u001B[0m") || (board.getCell(botUnit.getX(), botUnit.getY() - 1) == "\u001B[33m&\u001B[0m"))
                    botUnit.move(botUnit.getX(), botUnit.getY() - 1, board);
            }

            if (player.isBaseDestroyed()) {
                System.out.println("Поражение! Бот одержал победу");
                autoSave();
                gameOver = true;
                continue;

            }

            System.out.println("Выйти в меню?");
            int exit = scanner.nextInt();
            switch (exit) {
                case 1: return;
                case 2: continue;
                default: break;
            }
        }

    }

    private void checkCasino() {
        for (Unit unit : player.getUnits()) {
            Casino casino = board.getCasino(unit.getX(), unit.getY());
            if (casino != null) {
                int money = casino.takeMoney();
                player.addMoney(money);
                System.out.println(unit.getMark() + " получил " + money + " денег из казино!");
            }
        }
    }

    private void saveGame() {
        try {
            SaveManager saveManager = new SaveManager(player.getName());
            saveManager.saveGame(new GameState(player, bot, board, casinoUnlocked));
            System.out.println(" Игра сохранена!");
        } catch (IOException e) {
            System.out.println(" Ошибка сохранения: " + e.getMessage());
        }
    }

    private void loadGame() {
        try {
            SaveManager saveManager = new SaveManager(player.getName());
            GameState loadedState = saveManager.loadGame();

            if (loadedState != null) {
                this.player = loadedState.getPlayer();
                this.bot = loadedState.getBot();
                this.board = loadedState.getBoard();
                this.casinoUnlocked = loadedState.isCasinoUnlocked();
                System.out.println(" Игра загружена!");
            } else {
                System.out.println(" Файл сохранения не найден.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" Ошибка загрузки: " + e.getMessage());
        }
    }

    private void autoSave() {
        try {
            new SaveManager(player.getName()).autoSave(new GameState(player, bot, board, casinoUnlocked));
        } catch (IOException e) {
            System.out.println(" Автосохранение не удалось: " + e.getMessage());
        }
    }

    private int calculateScore() {
        int baseHealthPoints = player.getBaseHealth();
        int moneyPoints = player.getMoney() * 10;
        int killedUnitsPoints = (10 - player.getUnits().size()) * 100;
        return baseHealthPoints + moneyPoints + killedUnitsPoints;
    }

    private void useBuilding(Hero hero) {
        System.out.println("Выбери здание:");
        List<Building> buildings = player.getBuildings();
        for (int i = 0; i < buildings.size(); i++) {
            System.out.println((i+1) + " - " + buildings.get(i).getMark());
        }

        int buildingChoice = scanner.nextInt();
        if (buildingChoice < 1 || buildingChoice > buildings.size()) {
            System.out.println("Ошибка в выборе здания");
            return;
        }

        Building selectedBuilding = buildings.get(buildingChoice - 1);

        if (selectedBuilding instanceof Hotel) {
            System.out.println("1. Короткий отдых (+2 здоровья, 1 день)\n2. Длинный отдых (+3 здоровья, 3 дня)");
            int choice = scanner.nextInt();
            ((Hotel) selectedBuilding).processService(player, choice);
        }
        else if (selectedBuilding instanceof Cafe) {
            System.out.println("1. Просто перекус (+2 к выносливости, 15 мин)\n2. Плотный обед (+3 к выносливости, 30 мин)");
            int choice = scanner.nextInt();
            ((Cafe) selectedBuilding).processService(player, choice);
        }
        else if (selectedBuilding instanceof BarberShop) {
            System.out.println("1. Просто стрижка (+3 к атаке, 10 мин)\n2. Модная стрижка (+6 к атаке, 30 мин)");
            int choice = scanner.nextInt();
            ((BarberShop) selectedBuilding).processService(player, choice);
        }
        else {
            selectedBuilding.action(hero);
        }
    }

}

