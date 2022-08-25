package ryuzuinfiniteshop.ryuzuinfiniteshop.commands;

import com.github.ryuzu.ryuzucommandsgenerator.CommandsGenerator;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import ryuzuinfiniteshop.ryuzuinfiniteshop.RyuZUInfiniteShop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops.Shop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.LocationUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.ShopUtil;

public class SpawnCommand {
    public static void registerCommand() {
        CommandsGenerator.registerCommand("ris",
                data -> {
                    if (data.getSender().hasPermission("ris.op")) {
                        data.sendMessage(RyuZUInfiniteShop.prefix + ChatColor.RED + "/" + data.getLabel() + " [spawn/add/remove]");
                    } else {
                        data.sendMessage(RyuZUInfiniteShop.prefix + ChatColor.RED + "/" + data.getLabel() + " []");
                    }
                },
                "ris.player",
                data -> data.getArgs().length == 0
        );

        CommandsGenerator.registerCommand("ris.spawn",
                data -> {
                    Player p = (Player) data.getSender();
                    Location loc = p.getLocation();
                    if (ShopUtil.getShops().containsKey(LocationUtil.toStringFromLocation(loc))) {
                        p.sendMessage(RyuZUInfiniteShop.prefix + RyuZUInfiniteShop.prefix + ChatColor.RED + "既にその場所にはショップが存在します");
                        return;
                    }
                    ShopUtil.createShop(loc , EntityType.VILLAGER);
                    data.sendMessage(RyuZUInfiniteShop.prefix + ChatColor.GREEN + "ショップを設置しました");
                },
                "ris.op",
                data -> true,
                data -> {
                    if (!(data.getSender() instanceof Player)) {
                        data.sendMessage(RyuZUInfiniteShop.prefix + ChatColor.RED + "このコマンドはプレイヤーのみ実行できます");
                        return false;
                    }
                    return data.getArgs().length == 1;
                }
        );

        CommandsGenerator.registerCommand("ris.spawn",
                data -> {
                    Player p = (Player) data.getSender();
                    Location loc = p.getLocation();
                    if (ShopUtil.getShops().containsKey(LocationUtil.toStringFromLocation(loc))) {
                        p.sendMessage(RyuZUInfiniteShop.prefix + ChatColor.RED + "既にその場所にはショップが存在します");
                        return;
                    }
                    ShopUtil.createShop(loc , EntityType.valueOf(data.getArgs()[1].toUpperCase()));
                    data.sendMessage(RyuZUInfiniteShop.prefix + ChatColor.GREEN + "ショップを設置しました");
                },
                "ris.op",
                data -> {
                    try {
                        EntityType.valueOf(data.getArgs()[1].toUpperCase());
                        return true;
                    } catch (IllegalArgumentException e) {
                        data.sendMessage(RyuZUInfiniteShop.prefix + ChatColor.RED + "有効なエンティティタイプを入力して下さい");
                        return false;
                    }
                },
                data -> {
                    if (!(data.getSender() instanceof Player)) {
                        data.sendMessage(RyuZUInfiniteShop.prefix + ChatColor.RED + "このコマンドはプレイヤーのみ実行できます");
                        return false;
                    }
                    return data.getArgs().length != 1;
                }
        );
    }
}
