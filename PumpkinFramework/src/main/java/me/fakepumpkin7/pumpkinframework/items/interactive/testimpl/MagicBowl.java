package me.fakepumpkin7.pumpkinframework.items.interactive.testimpl;

import de.tr7zw.nbtapi.NBT;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.items.interactive.InteractiveItem;
import me.fakepumpkin7.pumpkinframework.items.interactive.InteractiveItemUtils;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import me.fakepumpkin7.pumpkinframework.particle.ParticleEffect;
import me.fakepumpkin7.pumpkinframework.player.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MagicBowl implements InteractiveItem {

    public static String id = "magic-bowl";
    ItemStack item;


    public MagicBowl() {
        initItem();
    }
    private void initItem(){
        item = new ItemStack(Material.BOWL);
        item = InteractiveItemUtils.getItemWithInteractiveId(item, id);
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public void onInteract(Player player) {
        ChatUtils.success(player, "You drank from the magic bowl!");
    }

    @Override
    public void onDragDrop(Player player, ItemStack droppedTo) {
        return;
    }




}
