package ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Ageable;

import java.util.function.Consumer;

public class AgeableShop extends Shop {
    @Getter
    protected boolean adult;

    public AgeableShop(Location location, String entityType, ConfigurationSection config) {
        super(location, entityType, config);
    }

    public void setAgeLook(boolean look) {
        this.adult = look;
        if (npc == null) return;
        if (look)
            ((Ageable) npc).setAdult();
        else
            ((Ageable) npc).setBaby();
//        NBTBuilder.setIsBaby(!look);
    }

    @Override
    public Consumer<YamlConfiguration> getSaveYamlProcess() {
        return super.getSaveYamlProcess().andThen(yaml -> yaml.set("Npc.Options.Adult", adult));
    }

    @Override
    public Consumer<YamlConfiguration> getLoadYamlProcess() {
        return super.getLoadYamlProcess().andThen(yaml -> this.adult = yaml.getBoolean("Npc.Options.Adult", true));
    }

    @Override
    public void respawnNPC() {
        super.respawnNPC();
        if (isEditableNpc()) setAgeLook(adult);
    }
}
