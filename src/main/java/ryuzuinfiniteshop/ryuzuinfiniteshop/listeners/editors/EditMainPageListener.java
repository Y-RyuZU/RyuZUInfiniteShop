package ryuzuinfiniteshop.ryuzuinfiniteshop.listeners.editors;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.ShopHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.ShopEditorMainPage;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.ShopGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.listeners.trades.TradeListener;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.PersistentUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.ShopUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.SoundUtil;

public class EditMainPageListener implements Listener {
    //ショップの編集画面を開く
    @EventHandler
    public void openShopEditor(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player p = event.getPlayer();
        if(!p.isSneaking()) return;
        String id = PersistentUtil.getNMSStringTag(entity, "Shop");
        if (id == null) return;
        Shop shop = TradeListener.getShop(id);
        if (shop.isEditting()) return;
        p.openInventory(shop.getEditor(1).getInventory(ShopHolder.ShopMode.Edit));

        shop.setEditting(true);
    }

    //編集画面を閉じたとき、ロックを解除する
    @EventHandler
    public void releaseEdittingLock(InventoryCloseEvent event) {
        //インベントリがショップなのかチェック
        Inventory inv = event.getInventory();
        ShopGui gui = ShopUtil.getShopGui(inv);
        if (gui == null) return;
        if (!(gui instanceof ShopEditorMainPage)) return;
        if (!ShopUtil.isEditMode(inv)) return;

        //必要なデータを取得
        Player p = (Player) event.getPlayer();
        ShopHolder shopholder = (ShopHolder) inv.getHolder();
        Shop shop = shopholder.getShop();

        shop.setEditting(false);

        //音を出す
        SoundUtil.playCloseShopSound(p);
    }
}
