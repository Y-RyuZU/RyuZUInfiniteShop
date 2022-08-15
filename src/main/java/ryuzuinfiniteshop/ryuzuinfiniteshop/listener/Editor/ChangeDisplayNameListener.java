package ryuzuinfiniteshop.ryuzuinfiniteshop.listener.Editor;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ryuzuinfiniteshop.ryuzuinfiniteshop.RyuZUInfiniteShop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.ShopHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.listener.TradeListener;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.ShopUtil;

import java.util.HashMap;
import java.util.UUID;

//ショップのNPCの名前を変更する
public class ChangeDisplayNameListener implements Listener {
    private static HashMap<UUID, Long> namingTime = new HashMap<>();
    private static HashMap<UUID, String> namingShop = new HashMap<>();

    @EventHandler
    public void changeDisplay(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        if (event.getClickedInventory() != null) return;
        if (!ShopUtil.isShopEditInventory(event)) return;

        //必要なデータを取得
        Player p = (Player) event.getWhoClicked();
        ClickType type = event.getClick();
        ShopHolder shopholder = (ShopHolder) event.getView().getTopInventory().getHolder();
        Shop shop = TradeListener.getShop(shopholder.tags.get(0));
        int slot = event.getSlot();
        if (slot != 5 * 9 + 8) return;

        namingTime.put(p.getUniqueId(), System.currentTimeMillis());
        namingShop.put(p.getUniqueId(), shop.getID());
        p.sendMessage(RyuZUInfiniteShop.prefix + ChatColor.GREEN + "ページに設定する名前をチャットに入力してください");
        p.sendMessage(RyuZUInfiniteShop.prefix + ChatColor.GREEN + "20秒待つか'Cancel'と入力することでキャンセルことができます");
        p.sendMessage(RyuZUInfiniteShop.prefix + ChatColor.GREEN + "カラーコードを使う際は'&'か'{color:R,G,B}'を使用してください");
        p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 2);

        event.setCancelled(true);
    }

    @EventHandler
    public void changeDisplay(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        if (!namingTime.containsKey(p.getUniqueId())) return;
        if ((double) (System.currentTimeMillis() - namingTime.get(p.getUniqueId())) / 1000 > 20) return;
        if (event.getMessage().equalsIgnoreCase("Cancel"))  {
            p.sendMessage(RyuZUInfiniteShop.prefix + ChatColor.GREEN + "名前変更をキャンセルしました");
            p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 2);
        } else {
            p.sendMessage(RyuZUInfiniteShop.prefix + ChatColor.GREEN + "名前が設定されました");
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }
        namingTime.remove(p.getUniqueId());
        namingShop.remove(p.getUniqueId());
        event.setCancelled(true);
    }
}
