package game;

import java.io.*;
import java.util.*;

public class RecordsManager {
    private static final String RECORDS_FILE = "records.dat";
    private List<RecordEntry> records;

    public RecordsManager() {
        records = loadRecords();
    }

    public void addRecord(String playerName, int score, String mapName) {
        records.add(new RecordEntry(playerName, score, mapName, new Date()));
        records.sort(Comparator.comparingInt(RecordEntry::getScore).reversed());

        // Оставляем только топ-5
        if (records.size() > 5) {
            records = records.subList(0, 5);
        }
        saveRecords();
    }

    public void printTopRecords() {
        System.out.println("\n=== ТОП-5 РЕКОРДОВ ===");
        for (int i = 0; i < records.size(); i++) {
            System.out.printf("%d. %s - %d очков (карта: %s) [%tF]%n",
                    i+1,
                    records.get(i).getPlayerName(),
                    records.get(i).getScore(),
                    records.get(i).getMapName(),
                    records.get(i).getDate());
        }
    }

    private List<RecordEntry> loadRecords() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RECORDS_FILE))) {
            return (List<RecordEntry>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void saveRecords() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RECORDS_FILE))) {
            oos.writeObject(records);
        } catch (IOException e) {
            System.err.println("Ошибка сохранения рекордов: " + e.getMessage());
        }
    }

    private static class RecordEntry implements Serializable {
        private final String playerName;
        private final int score;
        private final String mapName;
        private final Date date;

        public RecordEntry(String playerName, int score, String mapName, Date date) {
            this.playerName = playerName;
            this.score = score;
            this.mapName = mapName;
            this.date = date;
        }

        // Геттеры
        public String getPlayerName() { return playerName; }
        public int getScore() { return score; }
        public String getMapName() { return mapName; }
        public Date getDate() { return date; }
    }
}