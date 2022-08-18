package ryuzuinfiniteshop.ryuzuinfiniteshop.listeners.trades;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.ShopTrade;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.ShopHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.guis.ShopGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.guis.ShopTradeGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.PersistentUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.ShopUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.SoundUtil;

import java.util.ArrayList;
import java.util.List;

public class OpenShopListener implements Listener {
    //ショップを開く
    @EventHandler
    public void openShop(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player p = event.getPlayer();
        if (p.isSneaking()) return;
        if (!event.getHand().equals(EquipmentSlot.HAND)) return;
        String id = PersistentUtil.getNMSStringTag(entity, "Shop");
        if (id == null) return;
        Shop shop = ShopUtil.getShop(id);
        if (!shop.isAvailableShop(p)) return;

        Inventory inv = shop.getPage(1).getInventory(ShopHolder.ShopMode.Trade, p);
        p.openInventory(inv);
        event.setCancelled(true);
    }

    //ショップのページ切り替え
    @EventHandler
    public void changePage(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ShopHolder holder = ShopUtil.getShopHolder(event);
        if (holder == null) return;
        if (event.getClickedInventory() != null) return;

        //必要なデータを取得
        Player p = (Player) event.getWhoClicked();
        ClickType type = event.getClick();
        Inventory inv = event.getView().getTopInventory();
        ShopHolder.ShopMode mode = holder.getShopMode();
        Shop shop = holder.getShop();
        int page = holder.getGui().getPage();

        if (!(holder.getGui() instanceof ShopTradeGui)) return;

        //ページ切り替え
        boolean fail = false;
        if (type.isLeftClick()) {
            if (shop.getPage(page - 1) == null)
                fail = true;
            else
                p.openInventory(shop.getPage(page - 1).getInventory(mode, p));
        }
        if (type.isRightClick()) {
            if (shop.getPage(page + 1) == null) {
                fail = true;
                if (ShopUtil.isEditMode(event) && shop.ableCreateNewPage()) {
                    //取引を上書きし、取引として成立しないものは削除する
                    shop.checkTrades(inv);
                    if (shop.ableCreateNewPage()) {
                        shop.createNewPage();
                        p.openInventory(shop.getPage(page + 1).getInventory(mode, p));
                        fail = false;
                    }
                }
            } else
                p.openInventory(shop.getPage(page + 1).getInventory(mode, p));
        }
        if (fail) {
            SoundUtil.playFailSound(p);
        } else {
            SoundUtil.playClickShopSound(p);
        }

        if (ShopUtil.isTradeMode(event)) ((ShopTradeGui) holder.getGui()).setTradeStatus(p, inv);

        //イベントキャンセル
        event.setCancelled(true);
    }

    //ショップのステータスの更新
    @EventHandler
    public void updateStatus(InventoryDragEvent event) {
        //インベントリがショップなのかチェック
        ShopHolder holder = ShopUtil.getShopHolder(event.getInventory());
        if (holder == null) return;
        if (!ShopUtil.isTradeMode(event.getInventory())) return;

        //必要なデータを取得
        Player p = (Player) event.getWhoClicked();
        Inventory inv = event.getView().getTopInventory();

        ((ShopTradeGui) holder.getGui()).setTradeStatus(p, inv);
    }
}
