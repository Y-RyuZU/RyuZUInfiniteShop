package ryuzuinfiniteshop.ryuzuinfiniteshop.listeners.editors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ryuzuinfiniteshop.ryuzuinfiniteshop.RyuZUInfiniteShop;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.ShopHolder;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.guis.ShopEditorMainPage;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.guis.ShopGui;
import ryuzuinfiniteshop.ryuzuinfiniteshop.data.shops.*;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.ItemUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.ShopUtil;
import ryuzuinfiniteshop.ryuzuinfiniteshop.utils.SoundUtil;

//NPCのエンティティタイプごとの固有のNBTを変更
public class ChangeIndividualSettingsListener implements Listener {

    //年齢の変更
    @EventHandler
    public void changeSettings(InventoryClickEvent event) {
        //インベントリがショップなのかチェック
        ShopHolder holder = ShopUtil.getShopHolder(event);
        if (holder == null) return;
        if (!(holder.getGui() instanceof ShopEditorMainPage)) return;
        if (!ShopUtil.isEditMode(event)) return;
        if (event.getClickedInventory() == null) return;

        //必要なデータを取得
        Player p = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        ShopEditorMainPage editor = (ShopEditorMainPage) holder.getGui();

        if (!editor.getSettingsMap().containsKey(slot)) return;
        changeAgeLook(holder, event.getSlot());
        changePowered(holder, event.getSlot());
        changeProfession(holder, event.getSlot());
        changeBiome(holder, event.getSlot());
        changeVisible(holder, event.getSlot());
        changeParrotColor(holder, event.getSlot());
        changeDyeColor(holder, event.getSlot());
        changeHorseColor(holder, event.getSlot());
        changeHorseStyle(holder, event.getSlot());

        //音を出す
        SoundUtil.playClickShopSound(p);

        //GUIのアイテムを更新
        editor.setSettings(event.getView().getTopInventory());
    }

    //年齢の変更
    public void changeAgeLook(ShopHolder holder, int slot) {
        //必要なデータを取得
        ShopEditorMainPage editor = (ShopEditorMainPage) holder.getGui();

        if (!editor.getSettingsMap().get(slot).equals(ShopEditorMainPage.ShopSettings.Age)) return;
        if (!(holder.getShop() instanceof AgeableShop)) return;

        //年齢の変更
        ((AgeableShop) holder.getShop()).setAgeLook(!((AgeableShop) holder.getShop()).isAdult());
    }

    //クリーパーを帯電させるか変更
    public void changePowered(ShopHolder holder, int slot) {
        //必要なデータを取得
        if (!((ShopEditorMainPage) holder.getGui()).getSettingsMap().get(slot).equals(ShopEditorMainPage.ShopSettings.Power))
            return;
        if (!(holder.getShop() instanceof PoweredableShop)) return;

        //帯電させるか変更
        ((PoweredableShop) holder.getShop()).setPowered(!((PoweredableShop) holder.getShop()).isPowered());
    }

    //村人の職業を変更
    public void changeProfession(ShopHolder holder, int slot) {
        //必要なデータを取得
        ShopEditorMainPage editor = (ShopEditorMainPage) holder.getGui();

        if (!editor.getSettingsMap().get(slot).equals(ShopEditorMainPage.ShopSettings.Profession)) return;
        if (!(holder.getShop() instanceof VillagerableShop)) return;

        //職業を変更
        ((VillagerableShop) holder.getShop()).setProfession(((VillagerableShop) holder.getShop()).getNextProfession());
    }

    //村人のバイオームを変更
    public void changeBiome(ShopHolder holder, int slot) {
        //必要なデータを取得
        ShopEditorMainPage editor = (ShopEditorMainPage) holder.getGui();

        if (!editor.getSettingsMap().get(slot).equals(ShopEditorMainPage.ShopSettings.Biome)) return;
        if (!(holder.getShop() instanceof VillagerableShop)) return;

        //バイオームを変更
        ((VillagerableShop) holder.getShop()).setBiome(((VillagerableShop) holder.getShop()).getNextBiome());
    }

    //透明か変更
    public void changeVisible(ShopHolder holder, int slot) {
        //必要なデータを取得
        ShopEditorMainPage editor = (ShopEditorMainPage) holder.getGui();

        if (!editor.getSettingsMap().get(slot).equals(ShopEditorMainPage.ShopSettings.Visible)) return;
        if (!(holder.getShop().getNPC() instanceof LivingEntity)) return;

        //透明か変更
        holder.getShop().changeInvisible();
    }

    //オウムの色を変更
    public void changeParrotColor(ShopHolder holder, int slot) {
        //必要なデータを取得
        ShopEditorMainPage editor = (ShopEditorMainPage) holder.getGui();

        if (!editor.getSettingsMap().get(slot).equals(ShopEditorMainPage.ShopSettings.ParrotColor)) return;
        if (!(holder.getShop() instanceof ParrotShop)) return;

        //色を変更
        ((ParrotShop) holder.getShop()).setColor(((ParrotShop) holder.getShop()).getNextColor());
    }

    //色を変更
    public void changeDyeColor(ShopHolder holder, int slot) {
        //必要なデータを取得
        ShopEditorMainPage editor = (ShopEditorMainPage) holder.getGui();

        if (!editor.getSettingsMap().get(slot).equals(ShopEditorMainPage.ShopSettings.DyeColor)) return;
        if (!(holder.getShop() instanceof DyeableShop)) return;

        //色を変更
        ((DyeableShop) holder.getShop()).setColor(((DyeableShop) holder.getShop()).getNextColor());
    }

    //馬の色を変更
    public void changeHorseColor(ShopHolder holder, int slot) {
        //必要なデータを取得
        ShopEditorMainPage editor = (ShopEditorMainPage) holder.getGui();

        if (!editor.getSettingsMap().get(slot).equals(ShopEditorMainPage.ShopSettings.HorseColor)) return;
        if (!(holder.getShop() instanceof HorseShop)) return;

        //色を変更
        ((HorseShop) holder.getShop()).setColor(((HorseShop) holder.getShop()).getNextColor());
    }

    //馬の模様を変更
    public void changeHorseStyle(ShopHolder holder, int slot) {
        //必要なデータを取得
        ShopEditorMainPage editor = (ShopEditorMainPage) holder.getGui();

        if (!editor.getSettingsMap().get(slot).equals(ShopEditorMainPage.ShopSettings.HorseStyle)) return;
        if (!(holder.getShop() instanceof HorseShop)) return;

        //色を変更
        ((HorseShop) holder.getShop()).setStyle(((HorseShop) holder.getShop()).getNextStyle());
    }
}
