package me.fakepumpkin7.pumpkinarmour.listener;

import me.fakepumpkin7.pumpkinarmour.struct.ArmourSet;
import me.fakepumpkin7.pumpkinarmour.struct.ArmourSetRegistry;
import me.fakepumpkin7.pumpkinframework.armor.events.ArmorEquipEvent;
import me.fakepumpkin7.pumpkinframework.armor.events.ArmorUnEquipEvent;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ArmourChangeListener implements Listener {


    //uuid as string to set bonus id
    HashMap<String, String> setBonusMap = new HashMap<>();

    @EventHandler
    public void onArmourEquip(ArmorEquipEvent e){
        List<ItemStack> armour = Arrays.asList(e.getPlayer().getInventory().getArmorContents());

        String setId = null;

        for(ItemStack piece : armour){
            if(piece == null || piece.getType().equals(Material.AIR)) return;
            if(!NbtUtil.hasNbt(piece, "pumpkin-armour-id")) return;

            String tempSetId = NbtUtil.getNbtString(piece, "pumpkin-armour-id");
            if(!(setId == null) && !setId.equals(tempSetId)){
                return;
            }
            setId = tempSetId;
        }
        //here we know all pieces are the same
        if(ArmourSetRegistry.getSetById(setId) != null){
            setBonusMap.put(e.getPlayer().getUniqueId().toString(), setId);
            ArmourSetRegistry.getSetById(setId).enableSetBonus(e.getPlayer());
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ItemStack[] armour = player.getEquipment().getArmorContents();

        String setId = null;
        for(ItemStack piece : armour){
            if(piece == null || piece.getType().equals(Material.AIR)) return;
            if(!NbtUtil.hasNbt(piece, "pumpkin-armour-id")) return;

            String tempSetId = NbtUtil.getNbtString(piece, "pumpkin-armour-id");
            if(!(setId == null) && !setId.equals(tempSetId)){
                return;
            }
            setId = tempSetId;
        }
        //here we know all pieces are the same
        if(ArmourSetRegistry.getSetById(setId) != null){
            setBonusMap.put(player.getUniqueId().toString(), setId);
            ArmourSetRegistry.getSetById(setId).enableSetBonus(player);
        }

    }
    @EventHandler
    public void onArmourUnEquip(ArmorUnEquipEvent e){
        if(e.getItem() == null || e.getItem().getType().equals(Material.AIR) || e.getItem().getAmount() == 0) return;

        if(NbtUtil.hasNbt(e.getItem(),"pumpkin-armour-id")){
            String setId = NbtUtil.getNbtString(e.getItem(), "pumpkin-armour-id");
            if(setBonusMap.get(e.getPlayer().getUniqueId().toString()) != null){
                ArmourSetRegistry.getSetById(setId).disableSetBonus(e.getPlayer());
                setBonusMap.remove(e.getPlayer().getUniqueId().toString());
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        setBonusMap.remove(e.getPlayer());
    }


}
