package me.fakepumpkin7.pumpkinarmour.listener;

import me.fakepumpkin7.pumpkinframework.items.ItemUtil;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static me.fakepumpkin7.pumpkinarmour.PumpkinArmour.PUMPKIN_ARMOUR_ID;

public class EquipArmourTweaks implements Listener {


    @EventHandler
    public void onPlayerRightClickArmour(PlayerInteractEvent event){
        if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        if(!NbtUtil.hasNbt(event.getItem(), PUMPKIN_ARMOUR_ID)) return;

        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        event.setCancelled(true);
        if(ItemUtil.isHelmet(item)){
            ItemStack current = player.getEquipment().getHelmet();
            player.getInventory().setHelmet(item);
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), current);

            return;
        }
        if(ItemUtil.isChestplate(item)){
            ItemStack current = player.getEquipment().getChestplate();
            player.getInventory().setChestplate(item);
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), current);

            return;
        }
        if(ItemUtil.isLeggings(item)){
            ItemStack current = player.getEquipment().getLeggings();
            player.getInventory().setLeggings(item);
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), current);

            return;
        }
        if(ItemUtil.isBoots(item)){

            ItemStack current = player.getEquipment().getBoots();
            player.getInventory().setBoots(item);
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), current);

            return;
        }

    }


    //make it so players cant place the head items
    @EventHandler
    public void onPlaceHeadHelmet(BlockPlaceEvent event){

        //this shouldnt actually be ran becuase of the above method

        if(event.getPlayer() == null) return;
        if(event.getItemInHand() == null ) return;

        if(event.getItemInHand().getType() != Material.SKULL && event.getItemInHand().getType() != Material.SKULL_ITEM) return;



        ItemStack skull = event.getItemInHand();
        if(NbtUtil.hasNbt(skull, PUMPKIN_ARMOUR_ID)){
            event.setCancelled(true);
        }
    }

    //make it so head helmets can be shift clicked to equip
    @EventHandler
    public void onShiftClickHead(InventoryClickEvent event){
        //check in default player inventory
        if (!event.getInventory().getTitle().equals("container.crafting")) return;

        Player player = (Player) event.getWhoClicked();

        //check if actually shift clicking a head
        if(event.getCurrentItem().getType() != Material.SKULL && event.getCurrentItem().getType() != Material.SKULL_ITEM) return;
        if (event.getClick() != ClickType.SHIFT_RIGHT && event.getClick() != ClickType.SHIFT_LEFT) return;
        if(event.getSlotType() == InventoryType.SlotType.ARMOR) return;

        ItemStack head = event.getCurrentItem();

        //return if helmet slot is not empty
        if(ItemUtil.isValid(player.getEquipment().getHelmet())) return;

        event.setCancelled(true);

        int slot = event.getSlot();
        player.getInventory().setItem(slot, null);
        player.getEquipment().setHelmet(head);

    }

}
