package me.fakepumpkin7.pumpkinframework.armor;

import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import me.fakepumpkin7.pumpkinframework.armor.events.ArmorEquipEvent;
import me.fakepumpkin7.pumpkinframework.armor.events.ArmorUnEquipEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Hashtable;
import java.util.UUID;

public class ArmorTask implements Runnable {

    @Getter private static Hashtable<UUID, ItemStack[]> playerEquipment = new Hashtable<>();

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            ItemStack[] currentEquipment = player.getEquipment().getArmorContents();
            ItemStack[] previousEquipment = playerEquipment.get(player.getUniqueId());

            for (int i = 0; i < currentEquipment.length; i++) {
                if (isEmpty(currentEquipment[i]) && previousEquipment != null && !isEmpty(previousEquipment[i])) {
                    previousEquipment[i].setAmount(1);
                    callUnEquip(player, previousEquipment[i]);

                } else if (!isEmpty(currentEquipment[i]) && (previousEquipment == null || isEmpty(previousEquipment[i]))){
                    currentEquipment[i].setAmount(1);
                    callEquip(player, currentEquipment[i]);
                } else if (!isEmpty(currentEquipment[i]) && !currentEquipment[i].isSimilar(previousEquipment[i])) {
                    previousEquipment[i].setAmount(1);
                    currentEquipment[i].setAmount(1);

                    callUnEquip(player, previousEquipment[i]);
                    callEquip(player, currentEquipment[i]);
                }
            }
            playerEquipment.put(player.getUniqueId(), currentEquipment);
        }
    }

    public static void clear(Player player) {
        playerEquipment.remove(player.getUniqueId());
    }

    private void callUnEquip(Player player, ItemStack itemStack) {
        Bukkit.getScheduler().runTask(PumpkinFramework.getInstance(),
                () -> Bukkit.getPluginManager().callEvent(new ArmorUnEquipEvent(player, itemStack)));
    }

    private void callEquip(Player player, ItemStack itemStack) {
        Bukkit.getScheduler().runTask(PumpkinFramework.getInstance(), () ->
                Bukkit.getPluginManager().callEvent(new ArmorEquipEvent(player, itemStack)));
    }

    private boolean isEmpty(ItemStack itemStack){
        if(itemStack == null || itemStack.getType().equals(Material.AIR)){
            return true;
        }
        return false;
    }

}
