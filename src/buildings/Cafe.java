package buildings;

import units.Hero;
import units.Unit;
import game.Player;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Cafe extends Building {
    private static final int MAX_WAITERS = 3;
    private static final int GUESTS_PER_WAITER = 4;
    private static final int MAX_GUESTS = MAX_WAITERS * GUESTS_PER_WAITER;
    private int currentGuests = 0;
    private final ExecutorService executor = Executors.newFixedThreadPool(MAX_WAITERS);

    public Cafe(int number) {
        super(number, "CF", 600);
    }

    public synchronized void processService(Player player, int choice) {
        try {
            while (currentGuests >= MAX_GUESTS) {
                if (player != null) {
                    System.out.println("Все официанты заняты. Ожидаем...");
                }
                wait();
            }

            currentGuests++;
            if (player != null) {
                System.out.println("Заказан " + (choice == 1 ? "перекус" : "обед"));
            }

            executor.execute(() -> {
                try {
                    long duration = choice == 1 ? 1500 : 3000;
                    TimeUnit.MILLISECONDS.sleep(duration);

                    if (player != null) {
                        applyStaminaBoost(player, choice == 1 ? 2 : 3);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    synchronized (this) {
                        currentGuests--;
                        notifyAll();
                        if (player != null) {
                            System.out.println("Место освободилось. Свободно: " + (MAX_GUESTS - currentGuests));
                        }
                    }
                }
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void applyStaminaBoost(Player player, int boost) {
        if (player != null) {
            for (Unit unit : player.getUnits()) {
                unit.increaseStamina(boost);
            }
            System.out.println("Все юниты получили +" + boost + " к выносливости!");
        }
    }

    @Override
    public void action(Hero hero) {
        System.out.println("Для использования кафе выберите его через меню зданий");
    }
}