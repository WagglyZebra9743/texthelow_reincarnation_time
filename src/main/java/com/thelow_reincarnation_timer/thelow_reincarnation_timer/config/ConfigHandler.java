package com.thelow_reincarnation_timer.thelow_reincarnation_timer.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

    private static Configuration config;

    private static final String CATEGORY_GENERAL = "general";

    public static int hudX = 5;
    public static int hudY = 5;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);
        config.load();

        hudX = config.get(CATEGORY_GENERAL, "hudX", 5, "HUDのX座標").getInt();
        hudY = config.get(CATEGORY_GENERAL, "hudY", 5, "HUDのY座標").getInt();

        if (config.hasChanged()) {
            config.save();
        }
        //保存する部分
        String[] saved = config.get("times", "time_sword", new String[0]).getStringList();
        time_sword.clear();
        for (String s : saved) {
            try {
                time_sword.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {}
        }
        
        String[] saved_bow = config.get("times", "time_bow", new String[0]).getStringList();
        time_bow.clear();
        for (String s : saved_bow) {
            try {
                time_bow.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {}
        }
        
        String[] saved_magic = config.get("times", "time_magic", new String[0]).getStringList();
        time_magic.clear();
        for (String s : saved_magic) {
            try {
                time_magic.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {}
        }
        
        String[] saved_all = config.get("times", "time_all", new String[0]).getStringList();
        time_all.clear();
        for (String s : saved_all) {
            try {
                time_all.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {}
        }
    }

    //HUDの位置を保存
    public static void save() {
        config.get(CATEGORY_GENERAL, "hudX", hudX).set(hudX);
        config.get(CATEGORY_GENERAL, "hudY", hudY).set(hudY);
        if (config.hasChanged()) {
            config.save();
        }
    }
    public static List<Integer> time_sword = new ArrayList<>();
    public static void addCurrentTickToSword(int time_tick) {
        // リストに追加
        time_sword.add(time_tick);

        // List<Integer> → String[]
        String[] timeArray = time_sword.stream()
            .map(String::valueOf)
            .toArray(String[]::new);

        // config に保存
        config.get("times", "time_sword", new String[0]).set(timeArray);
        config.save();
    }
    
    public static List<Integer> time_bow = new ArrayList<>();
    public static void addCurrentTickToBow(int time_tick) {
        // リストに追加
        time_bow.add(time_tick);

        // List<Integer> → String[]
        String[] timeArray = time_bow.stream()
            .map(String::valueOf)
            .toArray(String[]::new);

        // config に保存
        config.get("times", "time_bow", new String[0]).set(timeArray);
        config.save();
    }
    
    public static List<Integer> time_magic = new ArrayList<>();
    public static void addCurrentTickToMagic(int time_tick) {
        // リストに追加
        time_magic.add(time_tick);

        // List<Integer> → String[]
        String[] timeArray = time_magic.stream()
            .map(String::valueOf)
            .toArray(String[]::new);

        // config に保存
        config.get("times", "time_magic", new String[0]).set(timeArray);
        config.save();
    }
    
    public static List<Integer> time_all = new ArrayList<>();
    public static void addCurrentTickToAll(int time_tick) {
        // リストに追加
        time_all.add(time_tick);

        // List<Integer> → String[]
        String[] timeArray = time_all.stream()
            .map(String::valueOf)
            .toArray(String[]::new);

        // config に保存
        config.get("times", "time_all", new String[0]).set(timeArray);
        config.save();
    }
    
    public static List<Integer> getTimeSwordList() {
        List<Integer> result = new ArrayList<>();

        String[] data = config.get("times", "time_sword", new String[0]).getStringList();
        for (String s : data) {
            try {
                result.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {
            }
        }

        return result;
    }
    
    public static List<Integer> getTimeBowList() {
        List<Integer> result = new ArrayList<>();

        String[] data = config.get("times", "time_bow", new String[0]).getStringList();
        for (String s : data) {
            try {
                result.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {
            }
        }

        return result;
    }
    
    public static List<Integer> getTimeMagicList() {
        List<Integer> result = new ArrayList<>();

        String[] data = config.get("times", "time_magic", new String[0]).getStringList();
        for (String s : data) {
            try {
                result.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {
            }
        }

        return result;
    }
    
    public static List<Integer> getTimeAllList() {
        List<Integer> result = new ArrayList<>();

        String[] data = config.get("times", "time_all", new String[0]).getStringList();
        for (String s : data) {
            try {
                result.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {
            }
        }

        return result;
    }
}
