package game;

import java.util.Random;
import java.util.Scanner;

public class BlackjackGame {
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    public boolean play() {
        System.out.println("\n=== Блэкджек ===");
        int player = drawCard() + drawCard();
        int dealer = drawCard();

        System.out.println("Ваши карты: " + player);
        System.out.println("Карта дилера: " + dealer);

        while (true) {
            System.out.println("1. Взять карту 2. Остановиться");
            if (scanner.nextInt() == 1) {
                player += drawCard();
                System.out.println("Ваши карты: " + player);
                if (player > 21) {
                    System.out.println("Перебор! Вы проиграли");
                    return false;
                }
            } else break;
        }

        while (dealer < 17) dealer += drawCard();
        System.out.println("Итог: Вы " + player + " | Дилер " + dealer);

        if (dealer > 21 || player > dealer) {
            System.out.println("Вы выиграли!");
            return true;
        }
        System.out.println("Вы проиграли");
        return false;
    }

    private int drawCard() {
        return random.nextInt(11) + 1;
    }
}