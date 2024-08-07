package ryuzuinfiniteshop.ryuzuinfiniteshop.listener.canceller;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ModeHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ShopMode;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.trade.ShopTradeGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ShopUtil;

//ショップのラインナップを変更時以外はインベントリ内のアイテム移動を禁止するように
public class CancelItemMoveListener implements Listener {
    @EventHandler
    public void cancelMoveToOtherInventory(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ModeHolder holder = ShopUtil.getModeHolder(event.getView().getTopInventory());
        if (holder == null) return;
        if (event.getClickedInventory() == null) return;
        if (!(event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || event.getAction().equals(InventoryAction.COLLECT_TO_CURSOR)))
            return;
        if (holder.getMode().equals(ShopMode.EDIT) && holder.getGui() instanceof ShopTradeGui) return;

        //キャンセルイベント
        event.setCancelled(true);
    }

    @EventHandler
    public void cancelClickInShop(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ModeHolder holder = ShopUtil.getModeHolder(event);
        if (holder == null) return;
        if (holder.getMode().equals(ShopMode.EDIT) && holder.getGui() instanceof ShopTradeGui) return;

        //キャンセルイベント
        event.setCancelled(true);
    }

    @EventHandler
    public void cancelClickOption(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ModeHolder holder = ShopUtil.getModeHolder(event);
        if (holder == null) return;
        int slot = event.getSlot();
        if (!(holder.getGui() instanceof ShopTradeGui)) return;
        if (!((ShopTradeGui) holder.getGui()).isDisplaySlot(slot) && !((ShopTradeGui) holder.getGui()).isConvertSlot(slot))
            return;

        //キャンセルイベント
        event.setCancelled(true);
    }

    @EventHandler
    public void cancelDragToOtherInventory(InventoryDragEvent event) {
        //インベントリがショップなのかチェック
        ModeHolder holder = ShopUtil.getModeHolder(event.getInventory());
        if (holder == null) return;
        if (holder.getMode().equals(ShopMode.EDIT) && holder.getGui() instanceof ShopTradeGui) return;
        if (event.getRawSlots().stream().noneMatch(i -> i < event.getInventory().getSize())) return;

        //キャンセルイベント
        event.setCancelled(true);

    }
}
