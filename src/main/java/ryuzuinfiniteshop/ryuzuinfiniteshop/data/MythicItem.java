package ryuzuinfiniteshop.ryuzuinfiniteshop.data;

import io.lumine.xikage.mythicmobs.MythicMobs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.ItemUtil;

import java.util.LinkedHashMap;
import java.util.Map;

@Value
public class MythicItem implements ConfigurationSerializable {
    String id;
    int amount;

    public ItemStack convertItemStack() {
        return ItemUtil.getMythicItem(id, amount);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("id", id);
        result.put("amount", amount);
        return result;
    }
}
