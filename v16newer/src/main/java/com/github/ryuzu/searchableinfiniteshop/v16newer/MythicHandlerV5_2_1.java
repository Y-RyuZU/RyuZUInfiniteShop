package com.github.ryuzu.searchableinfiniteshop.v16newer;

import com.github.ryuzu.searchableinfiniteshop.api.IMythicHandler;
import io.lumine.mythic.api.exceptions.InvalidMobTypeException;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.function.Consumer;


public class MythicHandlerV5_2_1 implements IMythicHandler {

    @Override
    public void reload(Consumer<Runnable> consumer) {
    }

    @Override
    public String getID(ItemStack item) {
        if (item == null || item.getType().isAir()) return null;
        ItemStack copy = item.clone();
        copy.setAmount(1);
        return getMythicMobsInstance().getItemManager().getMythicTypeFromItem(copy);
    }

    @Override
    public boolean exsistsMythicMob(String id) {
        return getMythicMobsInstance().getAPIHelper().getMythicMob(id) != null;
    }

    @Override
    public Collection<String> getMythicMobs() {
        return getMythicMobsInstance().getMobManager().getMobNames();
    }

    @Override
    public ItemStack getMythicItem(String id, int amount) {
        ItemStack item = getMythicMobsInstance().getItemManager().getItemStack(id);
        if (item == null) return null;
        item.setAmount(amount);
        return item;
    }

    @Override
    public Entity spawnMythicMob(Location location, String id) {
        try {
            return getMythicMobsInstance().getAPIHelper().spawnMythicMob(id, location);
        } catch (InvalidMobTypeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public EntityType getEntityType(String id) {
        if(!exsistsMythicMob(id)) return null;
        return EntityType.valueOf(getMythicMobsInstance().getMobManager().getMythicMob(id).get().getEntityTypeString().toUpperCase());
    }

    @Override
    public JavaPlugin getPlugin() {
        return getMythicMobsInstance();
    }

    @Override
    public boolean isMythicMob(Entity entity) {
        return getMythicMobsInstance().getAPIHelper().isMythicMob(entity);
    }

    private MythicBukkit getMythicMobsInstance() {
        return MythicBukkit.inst();
    }
}
