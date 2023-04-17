package ryuzuinfiniteshop.ryuzuinfiniteshop.data.system.item;

import lombok.Value;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import ryuzuinfiniteshop.ryuzuinfiniteshop.config.LanguageKey;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.system.ShopTrade;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ItemUtil;

import java.util.HashMap;

@Value
public class DisplayPanel {
    private static final HashMap<ShopTrade.TradeResult, String> panels = new HashMap<ShopTrade.TradeResult, String>() {{
        put(ShopTrade.TradeResult.Success, ChatColor.GREEN + LanguageKey.ITEM_TRADE_SUCCESS.getMessage());
        put(ShopTrade.TradeResult.NotAfford, ChatColor.RED + LanguageKey.ITEM_NOT_ENOUGH_ITEMS.getMessage());
        put(ShopTrade.TradeResult.Full, ChatColor.YELLOW + LanguageKey.ITEM_NOT_ENOUGH_SPACE.getMessage());
        put(ShopTrade.TradeResult.Limited, ChatColor.RED + LanguageKey.ITEM_TRADE_LIMITED.getMessage());
        put(ShopTrade.TradeResult.Normal, ChatColor.GREEN + LanguageKey.ITEM_TRADE_NORMAL.getMessage());
        put(ShopTrade.TradeResult.Error, ChatColor.RED + LanguageKey.ITEM_TRADE_INVALID.getMessage());
    }};


    ShopTrade.TradeResult result;
    Material material;
    int data;
    public ConfigurationSection serialize() {
        ConfigurationSection result = new YamlConfiguration();
        result.set("Material", material.name());
        result.set("CustomModelData", data);
        return result;
    }

    public ItemStack getItemStack(int limit, int count) {
        ItemStack item = ItemUtil.withCustomModelData(ItemUtil.getNamedItem(material, panels.get(result)), data);
        if(result.equals(ShopTrade.TradeResult.Success)) {
            ItemUtil.withLore(item, ChatColor.YELLOW + LanguageKey.ITEM_TRADE_ONCE.getMessage());
            ItemUtil.withLore(item, ChatColor.YELLOW + LanguageKey.ITEM_TRADE_TEN.getMessage());
            ItemUtil.withLore(item, ChatColor.YELLOW + LanguageKey.ITEM_TRADE_STACK.getMessage());
            if(limit != 0)
                ItemUtil.withLore(item, ChatColor.YELLOW + LanguageKey.ITEM_TRADE_REMAINING.getMessage(limit - count));
        } else if(!result.equals(ShopTrade.TradeResult.Normal)) {
            ItemUtil.withLore(item, ChatColor.GREEN + LanguageKey.ITEM_SEARCH_BY_VALUEORPRODUCT.getMessage());
        }
        return item;
    }

    public ItemStack getItemStack() {
        return getItemStack(0, 0);
    }
}