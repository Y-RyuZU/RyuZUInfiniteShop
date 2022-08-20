package ryuzuinfiniteshop.ryuzuinfiniteshop.listeners.editors;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.ShopHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.guis.ShopEditorMainPage;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.guis.ShopGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.EquipmentUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.ItemUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.ShopUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.SoundUtil;

//ショップのNPCの装備を変更する
public class ChangeEquipmentListener implements Listener {
    @EventHandler
    public void changeEquipment(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ShopHolder holder = ShopUtil.getShopHolder(event);
        if (holder == null) return;
        if (!(holder.getGui() instanceof ShopEditorMainPage)) return;
        if (!ShopUtil.isEditMode(event)) return;
        if (event.getClickedInventory() == null) return;

        //必要なデータを取得
        Player p = (Player) event.getWhoClicked();
        ClickType type = event.getClick();
        Shop shop = holder.getShop();
        int slot = event.getSlot();

        if (!EquipmentUtil.getEquipmentsSlot().containsKey(slot)) return;

        //装備を変更
        if ((type.isRightClick() || type.isLeftClick()) && !type.isShiftClick()) {
            if (ItemUtil.isAir(event.getCursor())) {
                if (ItemUtil.isAir(shop.getEquipmentItem(EquipmentUtil.getEquipmentSlotNumber(EquipmentUtil.getEquipmentSlot(slot)))))
                    return;
                 else
                    event.setCurrentItem(EquipmentUtil.getEquipmentDisplayItem(EquipmentUtil.getEquipmentSlot(slot)));
            } else
                event.setCurrentItem(ItemUtil.getOneItemStack(event.getCursor()));

            shop.setEquipmentItem(ItemUtil.getOneItemStack(event.getCursor()), EquipmentUtil.getEquipmentSlotNumber(EquipmentUtil.getEquipmentSlot(slot)));

            //音を出す
            SoundUtil.playClickShopSound(p);
        }
    }
}
