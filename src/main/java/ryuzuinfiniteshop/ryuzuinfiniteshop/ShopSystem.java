package ryuzuinfiniteshop.ryuzuinfiniteshop;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.PersistentUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopSystem implements Listener {
    private static HashMap<String , Shop> shops = new HashMap<>();

    public static Shop getShop(String id) {
        return shops.get(id);
    }

    @EventHandler
    public void openShop(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player p = event.getPlayer();
        String id = PersistentUtil.getNMSStringTag(entity , "Shop");
        if(id == null) return;
        Shop shop = ShopSystem.getShop(id);
        p.openInventory(shop.getPage(1).getInventory(ShopHolder.ShopMode.Trade));
    }
}
