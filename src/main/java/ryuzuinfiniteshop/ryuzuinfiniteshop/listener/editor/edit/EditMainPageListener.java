package ryuzuinfiniteshop.ryuzuinfiniteshop.listener.editor.edit;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ShopMode;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ShopHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.editor.ShopEditorGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.PersistentUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ShopUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.effect.SoundUtil;

public class EditMainPageListener implements Listener {
    //ショップの編集画面を開く
    @EventHandler
    public void openShopEditor(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player p = event.getPlayer();
        if (!p.hasPermission("ris.op")) return;
        if (!p.isSneaking()) return;
        if (!event.getHand().equals(EquipmentSlot.HAND)) return;
        String id = PersistentUtil.getNMSStringTag(entity, "Shop");
        if (id == null) return;
        Shop shop = ShopUtil.getShop(id);
        if (shop.isEditting()) return;

        ShopUtil.closeAllShopTradeInventory(shop);
        p.openInventory(shop.getEditor(1).getInventory(ShopMode.Edit));

        shop.setEditting(true);
        event.setCancelled(true);
    }

    //編集画面を閉じたとき、ロックを解除する
    @EventHandler
    public void releaseEdittingLock(InventoryCloseEvent event) {
        //インベントリがショップなのかチェック
        Inventory inv = event.getInventory();
        ShopHolder holder = ShopUtil.getShopHolder(inv);
        if (holder == null) return;
        if (!(holder.getGui() instanceof ShopEditorGui)) return;
        if (!ShopUtil.isEditMode(inv)) return;

        //必要なデータを取得
        Player p = (Player) event.getPlayer();
        ShopHolder shopholder = (ShopHolder) inv.getHolder();
        Shop shop = shopholder.getShop();

        shop.setEditting(false);
    }

    //編集画面の切り替え
    @EventHandler
    public void changePage(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ShopHolder holder = ShopUtil.getShopHolder(event);
        if (holder == null) return;
        if (!(holder.getGui() instanceof ShopEditorGui)) return;
        if (event.getClickedInventory() != null) return;

        //必要なデータを取得
        Player p = (Player) event.getWhoClicked();
        ClickType type = event.getClick();
        Shop shop = holder.getShop();
        ShopMode mode = holder.getMode();
        int page = holder.getGui().getPage();

        //ページ切り替え
        boolean fail = false;
        if (type.isLeftClick()) {
            if (shop.getEditor(holder.getGui().getPage() - 1) == null)
                fail = true;
            else
                p.openInventory(shop.getEditor(page - 1).getInventory(mode, holder.getBefore()));
        }
        if (type.isRightClick()) {
            if (shop.getEditor(page + 1) == null)
                fail = true;
            else
                p.openInventory(shop.getEditor(page + 1).getInventory(mode, holder.getBefore()));
        }
        if (fail) {
            SoundUtil.playFailSound(p);
        } else {
            SoundUtil.playClickShopSound(p);
        }

        //イベントキャンセル
        event.setCancelled(true);
    }
}
