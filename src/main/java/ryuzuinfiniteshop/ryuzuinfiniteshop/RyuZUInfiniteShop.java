package ryuzuinfiniteshop.ryuzuinfiniteshop;

import com.github.ryuzu.ryuzucommandsgenerator.RyuZUCommandsGenerator;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ryuzuinfiniteshop.ryuzuinfiniteshop.commands.ListCommand;
import ryuzuinfiniteshop.ryuzuinfiniteshop.commands.SpawnCommand;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.guis.DisplayConfig;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.ShopUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public final class RyuZUInfiniteShop extends JavaPlugin {
    private static RyuZUInfiniteShop plugin;
    public final static String prefix = ChatColor.GOLD + "[RyuZUInfiniteShop]";

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        registerEvents();
        registerCommands();
        new RyuZUCommandsGenerator(this);
        ShopUtil.loadAllShops();
        DisplayConfig.loadDisplay();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ShopUtil.saveAllShops();
        ShopUtil.removeAllNPC();
        DisplayConfig.saveDisplay();
    }

    public static RyuZUInfiniteShop getPlugin() {
        return plugin;
    }

    public static void registerEvents() {
//        getPlugin().getServer().getPluginManager().registerEvents(new EditMainPageListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new EditTradePageListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new CancelItemMoveListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new OpenShopListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new OpenShopListListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new TradeListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new CancelAffectNpc(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new ChangeEquipmentListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new ChangeDisplayNameListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new ChangeNpcTypeListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new ChangeIndividualSettingsListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new ChangeShopTypeListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new ChangeNpcDirecationListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new ChangeLockListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new ConvartListener(), getPlugin());
//        getPlugin().getServer().getPluginManager().registerEvents(new RemoveShopListener(), getPlugin());
        try {
            String listenerspath = RyuZUInfiniteShop.getPlugin().getClass().getResource("listeners").getFile()
                    .replaceFirst("file:/" , "")
                    .replace("\\" , "/")
                    .replace("/RyuZUInfiniteShop-1.0.0.jar!/ryuzuinfiniteshop/ryuzuinfiniteshop/listeners" , "");
            System.out.println(listenerspath);
            HashSet<Class<? extends Listener>> classes = Files.walk(Paths.get(listenerspath) , 5)
                    .filter(path -> path.toString().endsWith(".class"))
                    .map(path -> {
                        try {
                            return Class.forName(path.toString().replace(".class", ""));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .filter(clazz -> clazz != null && Listener.class.isAssignableFrom(clazz))
                    .map(clazz -> (Class<? extends Listener>) clazz)
                    .collect(HashSet::new, HashSet::add, HashSet::addAll);
            for (Class<?> clazz : classes) {
                Object o;
                try {
                    o = clazz.newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    return;
                }
                if (o instanceof Listener)
                    getPlugin().getServer().getPluginManager().registerEvents((Listener) o, getPlugin());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void registerCommands() {
        SpawnCommand.registerCommand();
        ListCommand.registerCommand();
    }
}
