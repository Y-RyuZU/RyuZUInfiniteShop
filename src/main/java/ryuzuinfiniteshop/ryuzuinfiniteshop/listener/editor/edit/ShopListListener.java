package ryuzuinfiniteshop.ryuzuinfiniteshop.listener.editor.edit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ryuzuinfiniteshop.ryuzuinfiniteshop.RyuZUInfiniteShop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ModeHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ShopListHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ShopMode;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.common.ShopListGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ItemUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.NBTUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ShopUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.effect.SoundUtil;

public class ShopListListener implements Listener {
    //ショップのページ切り替え
    @EventHandler
    public void changePage(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ModeHolder holder = ShopUtil.getModeHolder(event);
        if (holder == null) return;
        if (!(holder.getGui() instanceof ShopListGui)) return;
        if (event.getClickedInventory() != null) return;

        //イベントキャンセル
        event.setCancelled(true);

        //必要なデータを取得
        ShopListHolder shopListHolder = (ShopListHolder) holder;
        Player p = (Player) event.getWhoClicked();
        ClickType type = event.getClick();
        ShopMode mode = holder.getMode();
        int page = shopListHolder.getGui().getPage();

        //ページ切り替え
        boolean fail = false;
        if (type.isLeftClick()) {
            if (page - 1 == 0)
                fail = true;
            else
                p.openInventory(new ShopListGui(page - 1, shopListHolder.getShops()).getInventory(mode));
        }
        if (type.isRightClick()) {
            if (page == shopListHolder.getMaxPage()) {
                fail = true;
            } else
                p.openInventory(new ShopListGui(page + 1, shopListHolder.getShops()).getInventory(mode));
        }
        if (fail) {
            SoundUtil.playFailSound(p);
        } else {
            SoundUtil.playClickShopSound(p);
        }
    }

    //ショップのページ切り替え
    @EventHandler
    public void openShopList(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ModeHolder holder = ShopUtil.getModeHolder(event);
        if (holder == null) return;
        if (!(holder.getGui() instanceof ShopListGui)) return;
        if (event.getClickedInventory() == null) return;

        //イベントキャンセル
        event.setCancelled(true);

        //必要なデータを取得
        ItemStack item = event.getCurrentItem();
        if (ItemUtil.isAir(item)) return;
        Player p = (Player) event.getWhoClicked();
        Shop shop = ShopUtil.getShop(NBTUtil.getNMSStringTag(item, "Shop"));
        if(shop == null) {
            p.sendMessage(ChatColor.RED + "ショップが見つかりませんでした。");
            SoundUtil.playFailSound(p);
            return;
        }

        if (event.isShiftClick() && p.hasPermission("sis.op")) {
            //編集画面を開く
            ShopUtil.closeAllShopTradeInventory(shop);
            p.openInventory(shop.getEditor(1).getInventory(ShopMode.Edit, holder));
            SoundUtil.playClickShopSound(p);
            shop.setEditting(true);
        } else {
            //取引画面を開く
            if (!shop.isAvailableShop(p)) return;
            if(shop.getTrades().size() == 0) {
                p.sendMessage(ChatColor.RED + "ショップに取引がありません。");
                SoundUtil.playFailSound(p);
                return;
            }
            p.openInventory(shop.getPage(1).getInventory(ShopMode.Trade, p, holder));
            SoundUtil.playClickShopSound(p);
        }
    }
}
