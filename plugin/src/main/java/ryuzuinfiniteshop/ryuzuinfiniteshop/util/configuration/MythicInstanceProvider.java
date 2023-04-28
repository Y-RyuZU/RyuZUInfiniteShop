package ryuzuinfiniteshop.ryuzuinfiniteshop.util.configuration;

import com.github.ryuzu.searchableinfiniteshop.api.IMythicHandler;
//import com.github.ryuzu.searchableinfiniteshop.v16newer.MythicHandlerV5_2_1;
//import com.github.ryuzu.searchableinfiniteshop.v16older.MythicHandlerV4_12_0;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ryuzuinfiniteshop.ryuzuinfiniteshop.RyuZUInfiniteShop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.config.LanguageKey;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ShopUtil;

public class MythicInstanceProvider {
    private static IMythicHandler instance;

    public static IMythicHandler getInstance() {
        if (instance == null || !instance.getPlugin().isEnabled()) setInstance();
        if (instance == null) throw new NullPointerException(LanguageKey.ERROR_INVALID_LOADED_MYTHICMOBS.getMessage());
        return instance;
    }

    public static boolean isLoaded() {
        return instance != null;
    }

    public static void setInstance() {
        if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") == null) return;
        try {
            if (RyuZUInfiniteShop.VERSION < 16) {
                Class<?> mythicHandlerV4_12_0Class = Class.forName("com.github.ryuzu.searchableinfiniteshop.v16older.MythicHandlerV4_12_0");
                Constructor<?> mythicHandlerV4_12_0Constructor = mythicHandlerV4_12_0Class.getConstructor(JavaPlugin.class, Consumer.class);
                instance = (IMythicHandler) mythicHandlerV4_12_0Constructor.newInstance(RyuZUInfiniteShop.getPlugin(), (Consumer<Runnable>) ShopUtil::reloadAllShopTradeInventory);
            } else {
                Class<?> mythicHandlerV5_2_1Class = Class.forName("com.github.ryuzu.searchableinfiniteshop.v16newer.MythicHandlerV5_2_1");
                Constructor<?> mythicHandlerV5_2_1Constructor = mythicHandlerV5_2_1Class.getConstructor();
                instance = (IMythicHandler) mythicHandlerV5_2_1Constructor.newInstance();
            }
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
