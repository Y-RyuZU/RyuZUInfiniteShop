package ryuzuinfiniteshop.ryuzuinfiniteshop.utils.inventory;

import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class ItemUtil {
    //アイテムを与えることが可能か調べる
    public static boolean ableGive(Inventory inventory, ItemStack item) {
        if (inventory.firstEmpty() != -1) return true;
        if (item == null) return true;
        int stackSize = item.getType().getMaxStackSize();
        int sum = Arrays.stream(getContents(inventory)).filter(Objects::nonNull).filter(i -> i.isSimilar(item)).mapToInt(i -> stackSize - i.getAmount()).sum();
        return item.getAmount() <= sum;
    }

    //アイテムを与えることが可能か調べる
    public static boolean ableGive(Inventory inventory, ItemStack... items) {
        if (items == null) return true;
        if(items.length <= Arrays.stream(getContents(inventory)).filter(ItemUtil::isAir).count()) return true;
        HashMap<ItemStack, Integer> give = new HashMap<>();
        Arrays.stream(items).forEach(item -> give.put(item, containsCount(items, item)));
        give.replaceAll((i, v) -> give.get(i) - capacityCount(getContents(inventory), i));
        int needslot = give.keySet().stream()
                .filter(item -> give.get(item) > 0)
                .mapToInt(item -> {
                    int size = give.get(item) / item.getType().getMaxStackSize();
                    if (give.get(item) % item.getType().getMaxStackSize() != 0) size++;
                    return size;
                }).sum();
        int emptyslot = (int) Arrays.stream(getContents(inventory)).filter(Objects::isNull).count();
        return needslot <= emptyslot;
    }

    //アイテムを含んでいるか調べる
    public static boolean contains(Inventory inventory, ItemStack... items) {
        if (items == null) return true;
        HashMap<ItemStack, Integer> need = new HashMap<>();
        Arrays.stream(items).filter(Objects::nonNull).forEach(item -> need.put(item, containsCount(items, item)));
        HashMap<ItemStack, Integer> has = new HashMap<>();
        if (need.keySet().stream().anyMatch(item -> Arrays.stream(getContents(inventory)).noneMatch(item::isSimilar)))
            return false;
        need.keySet().forEach(item -> has.put(item, containsCount(getContents(inventory), item)));
        return need.keySet().stream().noneMatch(item -> has.get(item) < need.get(item));
    }

    public static ItemStack[] getContents(Inventory inventory) {
        ItemStack[] contents = inventory.getContents();
        return inventory instanceof PlayerInventory ? Arrays.copyOf(contents, contents.length - 5) : contents;
    }

    public static ItemStack getOneItemStack(ItemStack item) {
        ItemStack copy = item.clone();
        copy.setAmount(1);
        return copy;
    }

    public static boolean isAir(@Nullable ItemStack item) {
        return item == null || item.getType().equals(Material.AIR);
    }

    //アイテムを含んでいるか調べる
    public static boolean contains(Inventory inventory, ItemStack item) {
        if (item == null) return true;
        int sum = containsCount(getContents(inventory), item);
        return item.getAmount() <= sum;
    }

    public static int containsCount(ItemStack[] contents, ItemStack item) {
        return Arrays.stream(contents).filter(Objects::nonNull).filter(i -> i.isSimilar(item)).mapToInt(ItemStack::getAmount).sum();
    }

    public static int capacityCount(ItemStack[] contents, ItemStack item) {
        return Arrays.stream(contents).filter(Objects::nonNull).filter(i -> i.isSimilar(item)).mapToInt(i -> i.getType().getMaxStackSize() - i.getAmount()).sum();
    }

    //名前付きアイテムを返す
    public static ItemStack getNamedItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.values());
        item.setItemMeta(meta);
        return item;
    }

    //名前付きアイテムを返す
    public static ItemStack getNamedItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.addItemFlags(ItemFlag.values());
        item.setItemMeta(meta);
        return item;
    }

    //名前付きエンチャント済みアイテムを返す
    public static ItemStack getNamedEnchantedItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.values());
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        item.setItemMeta(meta);
        return item;
    }

    //名前付きエンチャント済みアイテムを返す
    public static ItemStack getNamedEnchantedItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.addItemFlags(ItemFlag.values());
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack[] getItemSet(Inventory inv, int slot, int lengh) {
        List<ItemStack> set = new ArrayList<>();
        for (int i = 0; i < lengh; i++) {
            if (inv.getItem(slot + i) == null) continue;
            set.add(inv.getItem(slot + i));
        }
        return set.toArray(new ItemStack[0]);
    }

    public static String toStringFromItemStack(ItemStack item) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("ShopItemStack", item);
        return config.saveToString();
    }

    public static String toStringFromItemStackArray(ItemStack[] item) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("ShopItemStacks", item);
        return config.saveToString();
    }

    public static ItemStack toItemStackFromString(String string) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(string);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return config.getItemStack("ShopItemStack", null);
    }

    public static ItemStack[] toItemStackArrayFromString(String string) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(string);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ((List<ItemStack>) config.getList("ShopItemStacks", null)).toArray(new ItemStack[0]);
    }

    public static ItemStack getMythicItem(String id) {
        return MythicMobs.inst().getItemManager().getItemStack(id);
    }

    public static ItemStack getMythicItem(String id , int amount) {
        ItemStack item =  MythicMobs.inst().getItemManager().getItemStack(id);
        item.setAmount(amount);
        return item;
    }

    public static boolean isEmptySlot(Inventory inventory, int slot) {
        return isAir(inventory.getItem(slot));
    }

    public static void setItemIfEmpyty(Inventory inventory, int slot, ItemStack item) {
        if (isEmptySlot(inventory, slot)) inventory.setItem(slot, item);
    }
}