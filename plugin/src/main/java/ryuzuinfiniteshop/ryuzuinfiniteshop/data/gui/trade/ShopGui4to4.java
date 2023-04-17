package ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.trade;

import org.bukkit.inventory.Inventory;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder.ShopMode;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.system.ShopTrade;

import java.util.ArrayList;
import java.util.List;

public class ShopGui4to4 extends ShopTradeGui {
    private static List<Integer> convertSlot = new ArrayList<>();

    static {
        for (int i = 0; i < 6; i++) {
            convertSlot.add(i * 9 + 4);
        }
    }

    public ShopGui4to4(Shop shop, int page) {
        super(shop, page);
    }

    @Override
    public Inventory getInventory(ShopMode mode) {
        Inventory inv = getInventory(i -> i * 9 , mode);
        for (int i = 0; i < 6; i++) {
            inv.setItem(i * 9 + 4, ShopTrade.getFilter(mode , 0));
        }
        return inv;
    }

    @Override
    public ShopTrade getTradeFromSlot(int slot) {
        return getTrade(slot / 9);
    }

    @Override
    public List<Integer> getDisplaySlot() {
        return convertSlot;
    }

    @Override
    public List<Integer> getConvertSlot() {
        return convertSlot;
    }
}