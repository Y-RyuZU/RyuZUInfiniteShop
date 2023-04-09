package ryuzuinfiniteshop.ryuzuinfiniteshop.listener.editor.system;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ryuzuinfiniteshop.ryuzuinfiniteshop.RyuZUInfiniteShop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.common.SelectSearchItemGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.common.ShopListGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.common.SearchTradeGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.*;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.trade.ShopTradeGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.system.ShopTrade;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.effect.SoundUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ItemUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.NBTUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ShopUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.TradeUtil;

import java.util.LinkedHashMap;

public class SearchTradeListener implements Listener {
    @EventHandler
    public void selectSearchItem(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ModeHolder holder = ShopUtil.getModeHolder(event);
        if (holder == null) return;
        if (!(holder.getGui() instanceof SelectSearchItemGui)) return;
        if (event.getClickedInventory() == null) return;

        event.setCancelled(true);
        //必要なデータを取得

        Player p = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        ClickType type = event.getClick();
        ItemStack searchItem = event.getView().getTopInventory().getItem(4);
        ItemStack panel = ItemUtil.getNamedItem(ItemUtil.getColoredItem("WHITE_STAINED_GLASS_PANE"), ChatColor.BLUE + "検索するアイテムを持ってクリック", ChatColor.GREEN + "シフトクリック: NPCの名前で検索");
        if (slot != 0 && slot != 4 && slot != 8) return;

        ShopMode mode = p.hasPermission("sis.op") ? ShopMode.EDIT : ShopMode.SEARCH;

        if ((type.isRightClick() || type.isLeftClick())) {
            if (slot == 4) {
                if (event.isShiftClick()) {
                    // NPC名で検索
                    SchedulerListener.setSchedulers(p, "search", (message) -> {
                        p.openInventory(new ShopListGui(1, ShopUtil.getSortedShops(mode, message)).getInventory(mode, holder));
                        SoundUtil.playClickShopSound(p);
                    });
                    p.sendMessage(RyuZUInfiniteShop.prefixCommand + ChatColor.GREEN + "検索するNPCの名前をチャットに入力してください");
                    p.sendMessage(RyuZUInfiniteShop.prefixCommand + ChatColor.GREEN + "20秒待つか'Cancel'と入力することでキャンセルことができます");
                } else if (ItemUtil.isAir(event.getCursor()))
                    event.setCurrentItem(panel);
                else
                    event.setCurrentItem(ItemUtil.getOneItemStack(event.getCursor()));
                SoundUtil.playClickShopSound(p);
            } else {
                if (event.isShiftClick()) {
                    // 対価名、商品名で検索
                    SchedulerListener.setSchedulers(p, "search", (message) -> {
                        LinkedHashMap<ShopTrade, Shop> searchedTrades = slot == 0 ? TradeUtil.getTradesFromTakeByDisplayName(message, mode) : TradeUtil.getTradesFromGiveByDisplayName(message, mode);
                        if (searchedTrades.size() == 0) {
                            p.sendMessage(RyuZUInfiniteShop.prefixCommand + ChatColor.RED + "検索結果がありませんでした");
                            SoundUtil.playFailSound(p);
                            return;
                        }
                        p.openInventory(new SearchTradeGui(1, p, searchedTrades).getInventory(mode, holder));
                        SoundUtil.playClickShopSound(p);
                    });
                    p.sendMessage(RyuZUInfiniteShop.prefixCommand + ChatColor.GREEN + "検索するアイテムの名前をチャットに入力してください");
                    p.sendMessage(RyuZUInfiniteShop.prefixCommand + ChatColor.GREEN + "20秒待つか'Cancel'と入力することでキャンセルことができます");
                } else {
                    if (panel.equals(searchItem))
                        // アイテム未設定
                        SoundUtil.playFailSound(p);
                    else {
                        // アイテムで検索
                        LinkedHashMap<ShopTrade, Shop> searchedTrades = slot == 0 ? TradeUtil.getTradesFromTake(searchItem, mode) : TradeUtil.getTradesFromGive(searchItem, mode);
                        if (searchedTrades.size() == 0) {
                            p.sendMessage(RyuZUInfiniteShop.prefixCommand + ChatColor.RED + "検索結果がありませんでした");
                            SoundUtil.playFailSound(p);
                            return;
                        }
                        p.openInventory(new SearchTradeGui(1, p, searchedTrades).getInventory(mode, holder));
                        SoundUtil.playClickShopSound(p);
                    }
                }
            }
        }
    }

    @EventHandler
    public void changePage(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ModeHolder holder = ShopUtil.getModeHolder(event);
        if (holder == null) return;
        if (!(holder.getGui() instanceof SearchTradeGui)) return;
        if (event.getClickedInventory() != null) return;

        //必要なデータを取得
        SeachTradeHolder seachTradeHolder = (SeachTradeHolder) holder;
        Player p = (Player) event.getWhoClicked();
        ClickType type = event.getClick();
        ShopMode mode = holder.getMode();
        int page = seachTradeHolder.getGui().getPage();

        //ページ切り替え
        boolean fail = false;
        if (type.isLeftClick()) {
            if (page - 1 == 0)
                fail = true;
            else
                p.openInventory(new SearchTradeGui(page - 1, p, seachTradeHolder.getTrades()).getInventory(mode));
        }
        if (type.isRightClick()) {
            if (page == seachTradeHolder.getMaxPage()) {
                fail = true;
            } else
                p.openInventory(new SearchTradeGui(page + 1, p, seachTradeHolder.getTrades()).getInventory(mode));
        }
        if (fail) {
            SoundUtil.playFailSound(p);
        } else {
            SoundUtil.playClickShopSound(p);
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void openShop(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ModeHolder holder = ShopUtil.getModeHolder(event);
        if (holder == null) return;
        if (!(holder.getGui() instanceof SearchTradeGui)) return;
        if (event.getClickedInventory() == null) return;

        event.setCancelled(true);

        //必要なデータを取得
        Player p = (Player) event.getWhoClicked();
        Inventory inv = event.getView().getTopInventory();
        int slot = event.getSlot();
        int base = (event.getSlot() / 9) * 9;
        int info = NBTUtil.getNMSStringTag(inv.getItem(4 + base), "Shop") == null ? 6 : 4;
        ItemStack item = inv.getItem(info + base);
        Shop shop = ShopUtil.getShop(NBTUtil.getNMSStringTag(item, "Shop"));
        boolean isOpenShop = slot % 9 == info;
        if (shop == null && isOpenShop) {
            p.sendMessage(ChatColor.RED + "ショップが見つかりませんでした。");
            SoundUtil.playFailSound(p);
            return;
        }

        if (isOpenShop) {
            if(p.hasPermission("sis.op") && event.isShiftClick()) {
                //編集画面を開く
                ShopUtil.closeAllShopTradeInventory(shop);
                p.openInventory(shop.getEditor(1).getInventory(ShopMode.EDIT, holder));
                SoundUtil.playClickShopSound(p);
                shop.setEditting(true);
            } else {
                //取引画面を開く
                if (!shop.isAvailableShop(p)) return;
                ShopTradeGui gui = shop.getPage(Integer.parseInt(NBTUtil.getNMSStringTag(item, "Page")));
                if (gui == null) {
                    SoundUtil.playFailSound(p);
                    return;
                }
                p.openInventory(gui.getInventory(ShopMode.TRADE, p, holder));
                SoundUtil.playClickShopSound(p);
            }
        } else {
            if(ItemUtil.isAir(event.getCurrentItem())) return;
            if(!(p.hasPermission("sis.search") || p.hasPermission("sis.op"))) return;
            if(slot % 9 == info) return;
            LinkedHashMap<ShopTrade, Shop> searchedTrades = (slot % 9 < info) ? TradeUtil.getTradesFromGive(event.getCurrentItem(), holder.getMode()) : TradeUtil.getTradesFromTake(event.getCurrentItem(), holder.getMode());
            if (searchedTrades.size() == 0) {
                p.sendMessage(RyuZUInfiniteShop.prefixCommand + ChatColor.RED + "検索結果がありませんでした");
                SoundUtil.playFailSound(p);
                return;
            }
            p.openInventory(new SearchTradeGui(1, p, searchedTrades).getInventory(holder.getMode(), holder));
            SoundUtil.playClickShopSound(p);
        }
    }

    //取引、対価で検索を行う
    @EventHandler
    public void onSearch(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ShopHolder holder = ShopUtil.getShopHolder(event);
        if (holder == null) return;
        if (!(holder.getGui() instanceof ShopTradeGui)) return;
        if (!holder.getMode().equals(ShopMode.TRADE)) return;

        //必要なデータを取得
        Player p = (Player) event.getWhoClicked();
        Shop.ShopType type = holder.getShop().getShopType();
        int slot = event.getSlot();
        ShopTrade trade = ((ShopTradeGui) holder.getGui()).getTradeFromSlot(slot);

        if (trade == null) return;
        if (trade.getResult(p).equals(ShopTrade.TradeResult.Success)) return;

        int info = ShopUtil.getSubtractSlot(type);
        int surplus = slot % 9;
        int base = (type.equals(Shop.ShopType.TwotoOne) && surplus > 4) ? surplus - 5 : surplus;
        if(ItemUtil.isAir(event.getCurrentItem())) return;
        if(!(p.hasPermission("sis.search") || p.hasPermission("sis.op"))) return;
        if(base == info) return;

        LinkedHashMap<ShopTrade, Shop> searchedTrades = base < info ? TradeUtil.getTradesFromGive(event.getCurrentItem(), ShopMode.SEARCH) : TradeUtil.getTradesFromTake(event.getCurrentItem(), ShopMode.SEARCH);
        if (searchedTrades.size() == 0) {
            p.sendMessage(RyuZUInfiniteShop.prefixCommand + ChatColor.RED + "検索結果がありませんでした");
            SoundUtil.playFailSound(p);
            return;
        }
        p.openInventory(new SearchTradeGui(1, p, searchedTrades).getInventory(ShopMode.SEARCH, holder));
        SoundUtil.playClickShopSound(p);
    }
}
