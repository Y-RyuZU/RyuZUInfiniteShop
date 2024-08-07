package ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.holder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.inventory.Inventory;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.gui.editor.ShopGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops.Shop;

@EqualsAndHashCode(callSuper = true)
@Getter
public class ShopHolder extends PageableHolder {
    protected final Shop shop;

    public ShopHolder(ShopMode mode, Shop shop, ShopGui gui) {
        this(mode, shop, gui, null);
    }

    public ShopHolder(ShopMode mode, Shop shop, ShopGui gui, ModeHolder before) {
        super(mode, gui, before);
        this.shop = shop;
    }

    @Override
    public Inventory getInventory() {
        return gui.getInventory(mode);
    }

    @Override
    public ShopGui getGui() {
        return (ShopGui) gui;
    }

    @Override
    public int getMaxPage() {
        return shop.getPageCount();
    }
}
