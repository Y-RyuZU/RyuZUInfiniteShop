package ryuzuinfiniteshop.ryuzuinfiniteshop.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import ryuzuinfiniteshop.ryuzuinfiniteshop.RyuZUInfiniteShop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ShopHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.configuration.FileUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ShopUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.TradeUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Config {
    public static int autoSaveInterval = 20;
    public static boolean editLog = true;
    public static boolean tradeLog = true;
    public static BukkitTask respawnTask;
    private static BukkitTask autoSaveTask;

    public static void load() {
        File file = FileUtil.initializeFile("config.yml");
        YamlConfiguration yaml = new YamlConfiguration();
        try {
            yaml.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        autoSaveInterval = yaml.getInt("AutoSaveInterval", 20);
        editLog = yaml.getBoolean("EditLog", true);
        tradeLog = yaml.getBoolean("TradeLog", true);
        runAutoSave();
    }

    public static void save() {
        File file = FileUtil.initializeFile("config.yml");
        YamlConfiguration yaml = new YamlConfiguration();
        try {
            yaml.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        if(yaml.contains("AutoSaveInterval")) yaml.set("AutoSaveInterval", autoSaveInterval);
        if(yaml.contains("EditLog")) yaml.set("EditLog", true);
        if(yaml.contains("TradeLog")) yaml.set("TradeLog", true);
        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runAutoSave() {
        if(autoSaveInterval <= 0) return;
        if(autoSaveTask != null) autoSaveTask.cancel();
        if(respawnTask != null) respawnTask.cancel();
        autoSaveTask = Bukkit.getScheduler().runTaskTimer(RyuZUInfiniteShop.getPlugin(), FileUtil::reloadAllWithMessage, 20L * 60 * autoSaveInterval, 20L * 60 * autoSaveInterval);
        respawnTask = Bukkit.getScheduler().runTaskTimer(RyuZUInfiniteShop.getPlugin(), () -> ShopUtil.getShops().values().forEach(Shop::respawnNPC), 30L, 30L);
    }
}
