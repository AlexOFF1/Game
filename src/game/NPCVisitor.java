package game;

import buildings.*;
import java.util.Random;

public class NPCVisitor extends Thread {
    private final Building building;
    private final Random random = new Random();
    private static final int NPC_COUNT = 10;

    public NPCVisitor(Building building) {
        this.building = building;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(5000) + 3000); // Интервал 3-8 секунд

                if (building instanceof Hotel) {
                    ((Hotel) building).processService(null, random.nextInt(2) + 1);
                }
                else if (building instanceof Cafe) {
                    ((Cafe) building).processService(null, random.nextInt(2) + 1);
                }
                else if (building instanceof BarberShop) {
                    ((BarberShop) building).processService(null, random.nextInt(2) + 1);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public static void startNPCVisitors(Player player) {
        for (Building building : player.getBuildings()) {
            if (building instanceof Hotel || building instanceof Cafe || building instanceof BarberShop) {
                for (int i = 0; i < NPC_COUNT; i++) {
                    new NPCVisitor(building).start();
                }
            }
        }
    }
}