package game;

import map.GameBoard;
import map.Obstacle;

import java.io.*;
import java.util.Scanner;

public class MapEditor {
    private GameBoard board;
    private final Scanner scanner;
    private boolean casinoAllowed;

    public MapEditor(boolean casinoAllowed) {
        this.board = new GameBoard();
        this.scanner = new Scanner(System.in);
        this.casinoAllowed = casinoAllowed;
    }

    public void start() {
        while (true) {
            board.printCleanBoard();
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            switch (choice) {
                case 1 -> addElement();
                case 2 -> removeElement();
                case 3 -> saveMap();
                case 4 -> loadMap();
                case 5 -> { return; }
                default -> System.out.println("Неверный выбор");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== РЕДАКТОР КАРТ ===");
        System.out.println("1. Добавить элемент");
        System.out.println("2. Удалить элемент");
        System.out.println("3. Сохранить карту");
        System.out.println("4. Загрузить карту");
        System.out.println("5. Выход");
    }

    private void addElement() {
        System.out.println("Выберите тип: 1-Препятствие 2-Казино" + (casinoAllowed ? "" : " (недоступно)"));
        int type = scanner.nextInt();
        System.out.println("Координаты (x y):");
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        if (type == 1) {
            board.obstacles.add(new Obstacle(x, y));
            board.cleanBoard[x][y] = "\u001B[31m|\u001B[0m";
        } else if (type == 2 && casinoAllowed) {
            board.addCasino(x, y);
        }
    }

    private void removeElement() {
        System.out.println("Координаты (x y):");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        board.cleanBoard[x][y] = "\u001B[32m.\u001B[0m";
        // Удаляем из всех коллекций
        board.obstacles.removeIf(o -> o.getX() == x && o.getY() == y);
        board.casinos.removeIf(c -> c.getX() == x && c.getY() == y);
    }

    private void saveMap() {
        System.out.println("Имя файла для сохранения (без расширения):");
        String fileName = scanner.nextLine();
        String path = "maps/" + fileName + ".map"; // Папка maps для карт

        try {
            new File("maps").mkdirs(); // Создаём папку, если её нет
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
                oos.writeObject(board);
                System.out.println(" Карта сохранена как: " + path);
            }
        } catch (IOException e) {
            System.out.println(" Ошибка сохранения: " + e.getMessage());
        }
    }

    private void loadMap() {
        System.out.println("Имя файла для загрузки (без расширения):");
        String fileName = scanner.nextLine();
        String path = "maps/" + fileName + ".map";

        if (!new File(path).exists()) {
            System.out.println("❌ Файл не найден: " + path);
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            board = (GameBoard) ois.readObject();
            System.out.println(" Карта загружена: " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" Ошибка загрузки: " + e.getMessage());
        }
    }

    public GameBoard getEditedBoard() {
        return this.board;
    }
}