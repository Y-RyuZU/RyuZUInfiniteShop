package ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Rabbit;

import java.util.Arrays;
import java.util.function.Consumer;

public class RabbitShop extends Shop {
    protected Rabbit.Type type;

    public RabbitShop(Location location, String entityType, ConfigurationSection config) {
        super(location, entityType, config);
    }

    public void setRabbitType(Rabbit.Type type) {
        this.type = type;
        if (npc == null) return;
        ((Rabbit) npc).setRabbitType(type);
    }

    public Rabbit.Type getNextType() {
        int nextindex = Arrays.asList(Rabbit.Type.values()).indexOf(type) + 1;
        return nextindex == Rabbit.Type.values().length ?
                Rabbit.Type.values()[0] :
                Rabbit.Type.values()[nextindex];
    }

    @Override
    public Consumer<YamlConfiguration> getSaveYamlProcess() {
        return super.getSaveYamlProcess().andThen(yaml -> yaml.set("Npc.Options.RabbitType", type.toString()));
    }

    @Override
    public Consumer<YamlConfiguration> getLoadYamlProcess() {
        return super.getLoadYamlProcess().andThen(yaml -> {
            this.type = Rabbit.Type.valueOf(yaml.getString("Npc.Options.RabbitType", "BROWN"));
        });
    }

    @Override
    public void respawnNPC() {
        super.respawnNPC();
        if (isEditableNpc()) setRabbitType(type);
    }
}
