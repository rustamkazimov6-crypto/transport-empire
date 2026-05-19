package com.transportgame.core;

import com.transportgame.ui.panels.GamePanel.MapTileType;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class SaveManager {

    private static final String SAVE_DIR = "saves";
    private static final String SAVE_EXTENSION = ".sav";

    public static class SaveData implements Serializable {
        private static final long serialVersionUID = 1L;

        public String companyName;
        public long playerCash;
        public int dayCounter;
        public int minuteCounter;
        public long ironResources;
        public long coalResources;
        public long woodResources;

        public int rows;
        public int cols;
        public MapTileType[][] tiles;
        public int[][] forestChopMinutes;
        public int[][] forestRegrowMinutes;

        public List<BuildingData> buildings = new ArrayList<>();
        public List<BusStopData> busStops = new ArrayList<>();
        public List<ResourceBuildingData> resourceBuildings = new ArrayList<>();
        public List<VehicleData> vehicles = new ArrayList<>();

        public long saveTime;
        public String saveTimeFormatted;
    }

    public static class BuildingData implements Serializable {
        private static final long serialVersionUID = 1L;
        public MapTileType type;

        public BuildingData(MapTileType type) {
            this.type = type;
        }
    }

    public static class BusStopData implements Serializable {
        private static final long serialVersionUID = 1L;
        public int row, col;
        public int passengers;

        public BusStopData(int row, int col, int passengers) {
            this.row = row;
            this.col = col;
            this.passengers = passengers;
        }
    }

    public static class ResourceBuildingData implements Serializable {
        private static final long serialVersionUID = 1L;
        public MapTileType type;
        public int row, col;
        public int goods;

        public ResourceBuildingData(MapTileType type, int row, int col, int goods) {
            this.type = type;
            this.row = row;
            this.col = col;
            this.goods = goods;
        }
    }

    public static class VehicleData implements Serializable {
        private static final long serialVersionUID = 1L;
        public String type;
        public double row, col;
        public int currentStop;
        public int passengers;
        public int cargo;
        public boolean returning;
        public int routeFromRow;
        public int routeFromCol;
        public int routeToRow;
        public int routeToCol;
        public boolean hasExplicitRoute;
        public String serviceState;
        public int minutesUntilDamage;
        public int repairMinutesRemaining;
        public int garageRow;
        public int garageCol;
        public int purchasePrice;

        public VehicleData(String type, double row, double col, int currentStop,
                          int passengers, int cargo, boolean returning,
                          int routeFromRow, int routeFromCol, int routeToRow, int routeToCol,
                          boolean hasExplicitRoute,
                          String serviceState, int minutesUntilDamage, int repairMinutesRemaining,
                          int garageRow, int garageCol, int purchasePrice) {
            this.type = type;
            this.row = row;
            this.col = col;
            this.currentStop = currentStop;
            this.passengers = passengers;
            this.cargo = cargo;
            this.returning = returning;
            this.routeFromRow = routeFromRow;
            this.routeFromCol = routeFromCol;
            this.routeToRow = routeToRow;
            this.routeToCol = routeToCol;
            this.hasExplicitRoute = hasExplicitRoute;
            this.serviceState = serviceState;
            this.minutesUntilDamage = minutesUntilDamage;
            this.repairMinutesRemaining = repairMinutesRemaining;
            this.garageRow = garageRow;
            this.garageCol = garageCol;
            this.purchasePrice = purchasePrice;
        }
    }

    private static void ensureSaveDirectoryExists() {
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static boolean saveGame(String saveName, SaveData data) {
        ensureSaveDirectoryExists();

        if (saveName == null) {
            return false;
        }

        saveName = saveName.replaceAll("[^a-zA-Z0-9_\\-\\s]", "").trim();
        if (saveName.isEmpty() || "autosave".equalsIgnoreCase(saveName)) {
            return false;
        }

        String fileName = SAVE_DIR + File.separator + saveName + SAVE_EXTENSION;

        data.saveTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data.saveTimeFormatted = sdf.format(new Date(data.saveTime));

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            oos.writeObject(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static SaveData loadGame(String saveName) {
        String fileName = SAVE_DIR + File.separator + saveName;
        if (!fileName.endsWith(SAVE_EXTENSION)) {
            fileName += SAVE_EXTENSION;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(fileName))) {
            return (SaveData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> listSaves() {
        ensureSaveDirectoryExists();
        List<String> saves = new ArrayList<>();

        File dir = new File(SAVE_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(SAVE_EXTENSION));

        if (files != null) {
            for (File file : files) {
                String name = file.getName();

                String saveName = name.substring(0, name.length() - SAVE_EXTENSION.length());
                if (!"autosave".equalsIgnoreCase(saveName)) {
                    saves.add(saveName);
                }
            }
        }

        saves.sort((a, b) -> {
            File fileA = new File(SAVE_DIR + File.separator + a + SAVE_EXTENSION);
            File fileB = new File(SAVE_DIR + File.separator + b + SAVE_EXTENSION);
            return Long.compare(fileB.lastModified(), fileA.lastModified());
        });

        return saves;
    }

    public static Map<String, String> getSaveInfo(String saveName) {
        String fileName = SAVE_DIR + File.separator + saveName + SAVE_EXTENSION;
        File file = new File(fileName);

        Map<String, String> info = new HashMap<>();
        if (file.exists()) {
            SaveData data = loadGame(saveName);
            if (data != null) {
                info.put("companyName", data.companyName);
                info.put("cash", String.valueOf(data.playerCash));
                info.put("day", String.valueOf(data.dayCounter));
                info.put("saveTime", data.saveTimeFormatted);
            }
        }
        return info;
    }

    public static boolean deleteSave(String saveName) {
        String fileName = SAVE_DIR + File.separator + saveName + SAVE_EXTENSION;
        File file = new File(fileName);
        return file.delete();
    }
}
