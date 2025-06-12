package buildings;

import units.Hero;
import units.Unit;
import game.Player;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Hotel extends Building {
    private static final int MAX_GUESTS = 5;
    private int currentGuests = 0;
    private final ExecutorService executor = Executors.newFixedThreadPool(MAX_GUESTS);

    public Hotel(int number) {
        super(number, "HO", 800);
    }

    public synchronized void processService(Player player, int choice) {
        try {
            while (currentGuests >= MAX_GUESTS) {
                if (player != null) {
                    System.out.println("Ожидаем свободный номер...");
                }
                wait();
            }

            currentGuests++;
            if (player != null) {
                System.out.println("Начат " + (choice == 1 ? "короткий" : "длинный") + " отдых");
            }

            executor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(choice == 1 ? 1 : 3);

                    if (player != null) {
                        applyRestEffect(player, choice == 1 ? 2 : 3);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    synchronized (this) {
                        currentGuests--;
                        notifyAll();
                        if (player != null) {
                            System.out.println("Отдых завершён. Свободно номеров: " + (MAX_GUESTS - currentGuests));
                        }
                    }
                }
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void applyRestEffect(Player player, int boost) {
        if (player != null) {
            for (Unit unit : player.getUnits()) {
                unit.increaseHealth(boost);
            }
            System.out.println("Все юниты получили +" + boost + " к здоровью!");
        }
    }

    @Override
    public void action(Hero hero) {
        System.out.println("Для использования отеля выберите его через меню зданий");
    }
}