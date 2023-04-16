package ryuzuinfiniteshop.ryuzuinfiniteshop.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.configuration.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UnderstandSystemConfig {
    private static Set<String> signedPlayers;

    public static void load() {
        File file = FileUtil.initializeFile("sign.yml");
        YamlConfiguration yaml = new YamlConfiguration();
        try {
            yaml.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        signedPlayers =  new HashSet(yaml.getList("signedPlayers", new ArrayList<>()));
    }

    public static void save() {
        File file = FileUtil.initializeFile("sign.yml");
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("signedPlayers", signedPlayers);
        try {
            yaml.save(file);
        } catch (IOException e) {
            if(!Config.readOnlyIgnoreIOException) e.printStackTrace();
        }
    }

    public static void addPlayer(Player p) {
        signedPlayers.add(p.getUniqueId().toString());
    }

    public static boolean contains(Player p) {
        return signedPlayers.contains(p.getUniqueId().toString());
    }
}
