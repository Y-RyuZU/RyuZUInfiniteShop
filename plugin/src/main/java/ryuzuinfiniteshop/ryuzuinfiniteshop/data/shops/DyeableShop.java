package ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops;

import lombok.Getter;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Colorable;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.EntityUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.util.inventory.ItemUtil;

import java.util.function.Consumer;

//狼、熱帯魚、羊などの染料と同じ色を付けられるもの
@Getter
public class DyeableShop extends Shop {
    protected DyeColor color;
    protected boolean optionalInfo;

    public DyeableShop(Location location, EntityType entitytype, ConfigurationSection config) {
        super(location, entitytype, config);
    }

    public void setColor(DyeColor color) {
        this.color = color;
        if(npc == null) return;
        if (npc instanceof Colorable) ((Colorable) npc).setColor(color);
        if (npc instanceof Wolf) {
            ((Wolf) npc).setTamed(!color.equals(DyeColor.WHITE));
            ((Wolf) npc).setCollarColor(color);
        }
//        NBTBuilder.setColor(color.ordinal());
//        NBTBuilder.setOptionalInfo(optionalInfo);
    }

    public void setOptionalInfo(boolean optionalInfo) {
        this.optionalInfo = optionalInfo;
        if(npc == null) return;
        if(npc instanceof Wolf) {
            if(color.equals(DyeColor.WHITE))
                ((Wolf) npc).setAngry(optionalInfo);
            else
                ((Wolf) npc).setSitting(optionalInfo);
        } else if(npc instanceof Sheep)
            ((Sheep) npc).setSheared(optionalInfo);
    }

    public DyeColor getNextColor() {
        return EntityUtil.getNextColor(color);
    }

    public boolean getOptionalInfo() {
        return optionalInfo;
    }

    @Override
    public Consumer<YamlConfiguration> getSaveYamlProcess() {
        return super.getSaveYamlProcess().andThen(yaml -> {
            yaml.set("Npc.Options.Color", color.toString());
            yaml.set("Npc.Options.OptionalInfo", optionalInfo);
        });
    }

    @Override
    public Consumer<YamlConfiguration> getLoadYamlProcess() {
        return super.getLoadYamlProcess().andThen(yaml -> {
            this.color = DyeColor.valueOf(yaml.getString("Npc.Options.Color", "WHITE"));
            this.optionalInfo = yaml.getBoolean("Npc.Options.OptionalInfo", false);
        });
    }

    @Override
    public void respawnNPC() {
        super.respawnNPC();
        setColor(color);
        setOptionalInfo(optionalInfo);
    }

    public ItemStack getColorMaterial() {
        return ItemUtil.getColoredItem(color);
    }
}
