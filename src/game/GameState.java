package game;

import map.*;
import units.*;
import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private final Player player;
    private final Player bot;
    private final GameBoard board;
    private final boolean casinoUnlocked;

    public GameState(Player player, Player bot, GameBoard board, boolean casinoUnlocked) {
        this.player = player;
        this.bot = bot;
        this.board = board;
        this.casinoUnlocked = casinoUnlocked;
    }

    // Геттеры
    public Player getPlayer() { return player; }
    public Player getBot() { return bot; }
    public GameBoard getBoard() { return board; }
    public boolean isCasinoUnlocked() { return casinoUnlocked; }
}