package me.fakepumpkin7.pumpkincombat;

import me.fakepumpkin7.pumpkinframework.CombatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class InitPlayerStatsListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        initStats(player);

        ItemStack[] armour = player.getEquipment().getArmorContents();

        for(ItemStack item : armour){
            if(item != null && item.getAmount() != 0  && item.getType() != Material.AIR){
                initArmourStats(player,item);
            }
        }
    }


    private void initStats(Player player){
        CombatUtils.setEntityBaseDamage(player,1);
        CombatUtils.setEntityDefence(player,0);
        CombatUtils.setEntityMaxHealth(player, 20);
        CombatUtils.setPlayerSpeed(player,0.2);
    }

    private void initArmourStats(Player player, ItemStack item){
        CombatUtils.addEntityBaseDamage(player, CombatUtils.getItemBaseDamage(item));
        CombatUtils.addEntityDefence(player, CombatUtils.getItemDefence(item));
        CombatUtils.addEntityHealth(player, CombatUtils.getItemMaxHealth(item));
        CombatUtils.addPlayerSpeed(player, CombatUtils.getItemSpeed(item));
    }

}
