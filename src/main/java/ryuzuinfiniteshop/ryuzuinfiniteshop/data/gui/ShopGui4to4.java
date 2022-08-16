package ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.ShopHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.ShopTrade;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.ItemUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.JavaUtil;

import java.util.ArrayList;
import java.util.List;

public class ShopGui4to4 extends ShopTradeGui {

    private static final List<Integer> displayslot = new ArrayList<>();

    static {
        for (int i = 0; i < 6; i++) {
            displayslot.add(i * 9 + 4);
        }
    }

    public ShopGui4to4(Shop shop, int page) {
        super(shop, page);
    }

    @Override
    public Inventory getInventory(ShopHolder.ShopMode mode) {
        Inventory inv = Bukkit.createInventory(new ShopHolder(mode, getShop(), getPage(), ShopGui.class.getName()), 9 * 6, "ショップ ページ" + getPage());

        ItemStack filler = ItemUtil.getNamedItem(Material.BLACK_STAINED_GLASS_PANE, "");
        for (int i = 0; i < 6; i++) {
            inv.setItem(i * 9 + 4, filler);
        }

        for (int i = 0; i < getTrades().size(); i++) {
            ShopTrade trade = getTrades().get(i);
            int slot = i * 9;
            for (int j = 0; j < 4; j++) {
                inv.setItem(slot + j, trade.give[j]);
                inv.setItem(slot + j + 5, trade.take[j]);
            }
        }

        return inv;
    }

    @Override
    public ShopTrade getTrade(int slot) {
        int mod9 = slot % 9;
        if (mod9 == 4) return null;
        int tradenumber = slot / 9;
        if (getTrades().size() - 1 <= tradenumber) return null;
        return getTrades().get(tradenumber);
    }

    @Override
    public void setTrades(int page) {
        this.trades = JavaUtil.splitList(getShop().getTrades(), 6)[page - 1];
    }

    @Override
    public boolean existTrade(int page) {
        return getTrades().size() > (page - 1) * 6;
    }

    @Override
    public boolean isDisplayItem(int slot) {
        return displayslot.contains(slot);
    }
}
