package buildings;

import units.Hero;
import game.Player;
import units.Unit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BarberShop extends Building {
    private static final int MAX_BARBERS = 2;
    private int currentCustomers = 0;
    private final ExecutorService executor = Executors.newFixedThreadPool(MAX_BARBERS);

    public BarberShop(int number) {
        super(number, "BS", 400);
    }

    public synchronized void processService(Player player, int choice) {
        try {
            while (currentCustomers >= MAX_BARBERS) {
                if (player != null) {
                    System.out.println("Все мастера заняты. Ожидаем...");
                }
                wait();
            }

            currentCustomers++;
            if (player != null) {
                System.out.println("Начата " + (choice == 1 ? "простая" : "модная") + " стрижка");
            }

            executor.execute(() -> {
                try {
                    long duration = choice == 1 ? 1000 : 3000;
                    TimeUnit.MILLISECONDS.sleep(duration);

                    if (player != null) {
                        applyAttackEffect(player, choice == 1 ? 3 : 6);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    synchronized (this) {
                        currentCustomers--;
                        notifyAll();
                        if (player != null) {
                            System.out.println("Мастер освободился. Свободно: " + (MAX_BARBERS - currentCustomers));
                        }
                    }
                }
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void applyAttackEffect(Player player, int boost) {
        if (player != null) {
            for (Unit unit : player.getUnits()) {
                unit.increaseDamage(boost);
            }
            System.out.println("Все юниты получили +" + boost + " к атаке!");
        }
    }

    @Override
    public void action(Hero hero) {
        System.out.println("Для использования парикмахерской выберите ее через меню зданий");
    }
}