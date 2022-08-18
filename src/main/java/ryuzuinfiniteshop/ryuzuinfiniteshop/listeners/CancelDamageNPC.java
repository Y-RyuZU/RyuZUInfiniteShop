package ryuzuinfiniteshop.ryuzuinfiniteshop.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.PersistentUtil;

public class CancelDamageNPC implements Listener {
    //ダメージを無効化する
    @EventHandler(priority = EventPriority.HIGH)
    public void openShop(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        String id = PersistentUtil.getNMSStringTag(entity, "Shop");
        if (id == null) return;

        event.setCancelled(true);
    }
}
