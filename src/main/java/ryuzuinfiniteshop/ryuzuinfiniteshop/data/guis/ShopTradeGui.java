package ryuzuinfiniteshop.ryuzuinfiniteshop.data.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.ShopHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.ShopTrade;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.ItemUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.JavaUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class ShopTradeGui extends ShopGui {

    protected static final List<Integer> displayslot = new ArrayList<>();
    protected static final List<Integer> convertslot = new ArrayList<>();

    public ShopTradeGui(Shop shop, int page) {
        super(shop, page);
        setTrades();
    }

    public boolean isConvertSlot(int slot) {
        return convertslot.contains(slot);
    }

    public boolean isDisplaySlot(int slot) {
        return convertslot.contains(slot) || displayslot.contains(slot);
    }

    public List<ShopTrade> getTrades() {
        return trades;
    }

    public ShopTrade getTrade(int number) {
        if (getTrades().size() < number) return null;
        if (number <= 0) return null;
        return getTrades().get(number - 1);
    }

    public void setTrades() {
        List<ShopTrade>[] trades = JavaUtil.splitList(getShop().getTrades(), getShop().getLimitSize());
        if (trades.length == getPage() - 1) return;
        this.trades = trades[getPage() - 1];
    }

    @Override
    public Inventory getInventory(ShopHolder.ShopMode mode) {
        return Bukkit.createInventory(new ShopHolder(mode, getShop(), this), 9 * 6, JavaUtil.getOrDefault(getShop().getNPC().getCustomName(), "ショップ") + "ショップ ページ" + getPage());
    }

    public Inventory getInventory(ShopHolder.ShopMode mode, Player p) {
        Inventory inv = getInventory(mode);
        if (mode.equals(ShopHolder.ShopMode.Trade)) setTradeStatus(p, inv);
        return inv;
    }

    public boolean existTrades() {
        return getTrades().size() > (getPage() - 2) * getShop().getLimitSize();
    }

    public abstract ShopTrade getTradeFromSlot(int slot);

    public void setTradeStatus(Player p, Inventory inventory) {
        ItemStack status1 = ItemUtil.getNamedItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "購入可能");
        ItemStack status2 = ItemUtil.getNamedItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "アイテムが足りません");
        ItemStack status3 = ItemUtil.getNamedItem(Material.YELLOW_STAINED_GLASS_PANE, ChatColor.YELLOW + "インベントリに十分な空きがありません");

        int addslot = 0;
        switch (getShop().getShopType()) {
            case TwotoOne:
                addslot = 2;
                break;
            case FourtoFour:
                addslot = 4;
                break;
            case SixtoTwo:
                addslot = 6;
                break;
        }
        for (int i = 0; i < getTrades().size(); i++) {
            int baseslot = getShop().getShopType().equals(Shop.ShopType.TwotoOne) ?
                    (i / 2) * 9 + (i % 2 == 1 ? 5 : 0) :
                    i * 9;
            ShopTrade trade = getTradeFromSlot(baseslot);
            ShopTrade.Result result = trade.getResult(p);
            switch (result) {
                case notAfford:
                    inventory.setItem(baseslot + addslot, status2);
                    break;
                case Full:
                    inventory.setItem(baseslot + addslot, status3);
                    break;
                case Success:
                    inventory.setItem(baseslot + addslot, status1);
                    break;
            }
        }
    }
}
