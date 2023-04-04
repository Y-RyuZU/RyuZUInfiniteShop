package ryuzuinfiniteshop.ryuzuinfiniteshop.listener.trades;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ryuzuinfiniteshop.ryuzuinfiniteshop.RyuZUInfiniteShop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.config.UnderstandSystemConfig;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ModeHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ShopMode;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ShopHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.trade.ShopTradeGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.configuration.FileUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ItemUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.NBTUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ShopUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.effect.SoundUtil;

public class OpenShopListener implements Listener {
    //ショップを開く
    @EventHandler
    public void openShop(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player p = event.getPlayer();
        if (p.isSneaking()) return;
        if (!event.getHand().equals(EquipmentSlot.HAND)) return;
        String id = NBTUtil.getNMSStringTag(entity, "Shop");
        if (id == null) return;
        if(FileUtil.isSaveBlock(p)) return;
        Shop shop = ShopUtil.getShop(id);
        if (!shop.isAvailableShop(p)) return;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (!(ItemUtil.isAir(item) || NBTUtil.getNMSStringTag(item, "ShopData") == null)) return;

        Inventory inv = shop.getPage(1).getInventory(ShopMode.Trade, p);
        TextComponent understand = new TextComponent(ChatColor.YELLOW + "[分かった！]");
        understand.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sis understand"));
        if(UnderstandSystemConfig.signedPlayers.contains(p.getUniqueId().toString())) understand = null;
        p.sendMessage(RyuZUInfiniteShop.prefixCommand + ChatColor.GREEN + "GUIの画面外を右クリック: 次のページに移動、左クリック: 前のページに移動できます" + understand);
        p.openInventory(inv);
        event.setCancelled(true);
    }

    @EventHandler
    public void openShop(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player p = event.getPlayer();
        if (p.isSneaking()) return;
        if (!event.getHand().equals(EquipmentSlot.HAND)) return;
        String id = NBTUtil.getNMSStringTag(entity, "Shop");
        if (id == null) return;
        event.setCancelled(true);
    }

    //ショップのページ切り替え
    @EventHandler
    public void changePage(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ShopHolder holder = ShopUtil.getShopHolder(event);
        if (holder == null) return;
        if (event.getClickedInventory() != null) return;
        if (!(holder.getGui() instanceof ShopTradeGui)) return;

        //必要なデータを取得
        Player p = (Player) event.getWhoClicked();
        ClickType type = event.getClick();
        Inventory inv = event.getView().getTopInventory();
        ShopMode mode = holder.getMode();
        Shop shop = holder.getShop();
        int page = holder.getGui().getPage();

        //ページ切り替え
        boolean fail = false;
        if (type.isLeftClick()) {
            if (shop.getPage(page - 1) == null)
                fail = true;
            else
                p.openInventory(shop.getPage(page - 1).getInventory(mode, p, holder.getBefore()));
        }
        if (type.isRightClick()) {
            if (shop.getPage(page + 1) == null) {
                fail = true;
                if (ShopUtil.isEditMode(event) && shop.ableCreateNewPage()) {
                    //取引を上書きし、取引として成立しないものは削除する
                    boolean warn = shop.checkTrades(inv);
                    if(warn) p.sendMessage(RyuZUInfiniteShop.prefixCommand + ChatColor.RED + "重複している取引がありました");
                    if (shop.ableCreateNewPage()) {
                        shop.createNewPage();
                        p.openInventory(shop.getPage(page + 1).getInventory(mode, p, holder.getBefore()));
                        fail = false;
                    }
                }
            } else
                p.openInventory(shop.getPage(page + 1).getInventory(mode, p, holder.getBefore()));
        }
        if (fail) {
            SoundUtil.playFailSound(p);
        } else {
            SoundUtil.playClickShopSound(p);
        }

        //イベントキャンセル
        event.setCancelled(true);
    }

    @EventHandler
    public void openBeforePage(InventoryCloseEvent event) {
        //インベントリがショップなのかチェック
        Inventory inv = event.getInventory();
        ModeHolder holder = ShopUtil.getModeHolder(inv);
        if (holder == null) return;

        //必要なデータを取得
        Player p = (Player) event.getPlayer();
        if(FileUtil.isSaveBlock(p)) return;

        Bukkit.getScheduler().runTaskLater(RyuZUInfiniteShop.getPlugin(), () -> {
            if(p.getOpenInventory().getType().equals(InventoryType.CREATIVE) || p.getOpenInventory().getType().equals(InventoryType.CRAFTING)) SoundUtil.playCloseShopSound(p);
            if (ShopUtil.getModeHolder(p.getOpenInventory().getTopInventory()) != null) return;
            if (holder.getBefore() == null) return;
            p.openInventory(holder.getBefore().getGui().getInventory(holder.getMode(), holder.getBefore().getBefore()));
        }, 1L);
    }

    //ショップのステータスの更新
    @EventHandler
    public void updateStatus(InventoryDragEvent event) {
        updateStatusProcess(event);
    }

    @EventHandler
    public void updateStatus(InventoryClickEvent event) {
        updateStatusProcess(event);
    }

    private void updateStatusProcess(InventoryInteractEvent event) {
        ShopHolder holder = ShopUtil.getShopHolder(event.getView().getTopInventory());
        if (holder == null) return;
        if (!holder.getMode().equals(ShopMode.Trade)) return;

        //必要なデータを取得
        Player p = (Player) event.getWhoClicked();
        Inventory inv = event.getView().getTopInventory();

        Bukkit.getScheduler().runTaskLater(RyuZUInfiniteShop.getPlugin(), () -> ((ShopTradeGui) holder.getGui()).setTradeStatus(p, inv), 1L);
    }
}
