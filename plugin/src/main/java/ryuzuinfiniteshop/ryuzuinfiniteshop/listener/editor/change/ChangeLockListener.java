package ryuzuinfiniteshop.ryuzuinfiniteshop.listener.editor.change;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.editor.ShopEditorGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ShopHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ShopMode;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.effect.SoundUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ItemUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ShopUtil;

//ショップのロック状態を変更する
public class ChangeLockListener implements Listener {
    @EventHandler
    public void changeLock(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ShopHolder holder = ShopUtil.getShopHolder(event);
        if (holder == null) return;
        if (!(holder.getGui() instanceof ShopEditorGui)) return;
        if (!holder.getMode().equals(ShopMode.EDIT)) return;
        if (event.getClickedInventory() == null) return;
        if (ItemUtil.getWhitePanel().equals(event.getCurrentItem())) return;

        //必要なデータを取得
        Player p = (Player) event.getWhoClicked();
        Shop shop = holder.getShop();
        int slot = event.getSlot();
        if (slot != 4 * 9 + 6) return;

        //音を出す
        SoundUtil.playClickShopSound(p);

        //ロック状態を切り替え
        shop.setLock(!shop.isLock());

        //インベントリを更新する
        shop.getEditor(holder.getGui().getPage()).setDisplay(event.getClickedInventory());
    }
}
