package ryuzuinfiniteshop.ryuzuinfiniteshop.listeners.Editor;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import ryuzuinfiniteshop.ryuzuinfiniteshop.RyuZUInfiniteShop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.ShopHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.ShopTrade;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.ShopEditorMainPage;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.ShopGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.ShopTradeGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.ShopUtil;

import java.util.ArrayList;
import java.util.List;

public class EditTradePageListener implements Listener {
    //ショップのラインナップを変更
    @EventHandler
    public void onEdit(InventoryCloseEvent event) {
        //インベントリがショップなのかチェック
        Inventory inv = event.getInventory();
        ShopGui gui = ShopUtil.getShopGui(inv);
        if (gui == null) return;
        if (!(gui instanceof ShopTradeGui)) return;
        if (!ShopUtil.isEditMode(inv)) return;

        //必要なデータを取得
        Player p = (Player) event.getPlayer();
        ShopHolder shopholder = (ShopHolder) inv.getHolder();
        Shop shop = shopholder.getShop();

        //取引を上書きし、取引として成立しないものは削除する
        List<ShopTrade> emptytrades = new ArrayList<>();
        for (int i = 0; i < 9 * 6; i += shop.getShopType().equals(Shop.ShopType.TwotoOne) ? 4 : 9) {
            if (shop.getShopType().equals(Shop.ShopType.TwotoOne) && i % 9 == 4) i++;
            ShopTrade trade = ((ShopTradeGui) gui).getTrade(((ShopTradeGui) gui).getTradeNumber(i));
            boolean available = ShopUtil.isAvailableTrade(inv, i, shop.getShopType());
            if (trade == null && available)
                shop.addTrade(inv, i);
            else if (available)
                trade.setTrade(inv, i, shop.getShopType());
            else
                emptytrades.add(trade);
        }
        shop.removeTrades(emptytrades);

        //ショップを更新する
        shop.updateTradeContents();

        //1tick送らせてエディターのメインページに戻る
        Bukkit.getScheduler().runTaskLater(RyuZUInfiniteShop.getPlugin(), () -> p.openInventory(shop.getEditor(gui.getPage() / 18 + 1).getInventory(ShopHolder.ShopMode.Edit)), 1L);

        //音を出す
        p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1, 2);
    }

    //ディスプレイをクリックしたときイベントをキャンセルする
    @EventHandler
    public void cancelClickDisplay(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ShopGui gui = ShopUtil.getShopGui(event);
        if (gui == null) return;
        if (!(gui instanceof ShopTradeGui)) return;
        if (!ShopUtil.isEditMode(event)) return;
        int slot = event.getSlot();
        if (!((ShopTradeGui) gui).isDisplayItem(slot)) return;

        //イベントキャンセル
        event.setCancelled(true);
    }

    //ショップの取引編集ページを開く
    @EventHandler
    public void openTradePage(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ShopGui gui = ShopUtil.getShopGui(event);
        if (gui == null) return;
        if (!(gui instanceof ShopEditorMainPage)) return;
        if (!ShopUtil.isEditMode(event)) return;

        //必要なデータを取得
        Player p = (Player) event.getWhoClicked();
        ShopHolder shopholder = (ShopHolder) event.getView().getTopInventory().getHolder();
        Shop shop = shopholder.getShop();
        int slot = event.getSlot();
        if (slot < 0 || 17 < slot) return;
        if (slot >= (shop.getTradePageCount() - (shopholder.getPage() - 1) * 18) + (shop.ableCreateNewPage() ? 1 : 0))
            return;

        //取引編集ページを開く
        if (shop.ableCreateNewPage() && slot + 1 == shop.getTradePageCountFromTradesCount()) {
            shop.createTradeNewPage();
            p.openInventory(shop.getPage(slot + 1).getInventory(ShopHolder.ShopMode.Edit));
        } else
            p.openInventory(shop.getPage(slot + (shopholder.getPage() - 1) * 18 + 1).getInventory(ShopHolder.ShopMode.Edit));

        //GUI操作処理
        ShopUtil.playClickEffect(event);
    }
}
