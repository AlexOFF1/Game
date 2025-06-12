package game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {
    public static final String SAVES_DIR = "saves";
    public final String playerName;

    public SaveManager(String playerName) {
        this.playerName = playerName;
        new File(SAVES_DIR).mkdirs();
    }

    public void saveGame(GameState state) throws IOException {
        String filename = SAVES_DIR + "/" + playerName + "_save.dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(state);
        }
    }

    public GameState loadGame() throws IOException, ClassNotFoundException {
        String filename = SAVES_DIR + "/" + playerName + "_save.dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameState) ois.readObject();
        }
    }


    public void autoSave(GameState state) throws IOException {
        String filename = SAVES_DIR + "/" + playerName + "_autosave.dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(state);
        }
    }
}