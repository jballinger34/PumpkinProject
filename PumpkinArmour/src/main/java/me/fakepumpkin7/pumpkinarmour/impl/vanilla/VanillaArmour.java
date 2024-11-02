package me.fakepumpkin7.pumpkinarmour.impl.vanilla;

import me.fakepumpkin7.pumpkinframework.armor.events.ArmorEquipEvent;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import static me.fakepumpkin7.pumpkinarmour.PumpkinArmour.PUMPKIN_ARMOUR_ID;

public class VanillaArmour implements Listener {

    //new vanilla armour sets
    @EventHandler
    public void onEquipVanillaArmour(ArmorEquipEvent e){
        ItemStack item = e.getItem();
        if(NbtUtil.hasNbt(item, "pumpkin-armour-id")) return;

        switch(item.getType()){
            case DIAMOND_HELMET:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-diamond")
                        .setDefence(3)
                        .build();
                e.getPlayer().getEquipment().setHelmet(item);
                break;
            case DIAMOND_CHESTPLATE:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-diamond")
                        .setDefence(8)
                        .build();
                e.getPlayer().getEquipment().setChestplate(item);
                break;
            case DIAMOND_LEGGINGS:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-diamond")
                        .setDefence(6)
                        .build();
                e.getPlayer().getEquipment().setLeggings(item);
                break;
            case DIAMOND_BOOTS:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-diamond")
                        .setDefence(3)
                        .build();
                e.getPlayer().getEquipment().setBoots(item);
                break;
            case IRON_HELMET:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-iron")
                        .setDefence(2)
                        .build();
                e.getPlayer().getEquipment().setHelmet(item);
                break;
            case IRON_CHESTPLATE:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-iron")
                        .setDefence(6)
                        .build();
                e.getPlayer().getEquipment().setChestplate(item);
                break;
            case IRON_LEGGINGS:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-iron")
                        .setDefence(5)
                        .build();
                e.getPlayer().getEquipment().setLeggings(item);
                break;
            case IRON_BOOTS:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-iron")
                        .setDefence(2)
                        .build();
                e.getPlayer().getEquipment().setBoots(item);
                break;
            case CHAINMAIL_HELMET:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-chain")
                        .setDefence(2)
                        .build();
                e.getPlayer().getEquipment().setHelmet(item);
                break;
            case CHAINMAIL_CHESTPLATE:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-chain")
                        .setDefence(5)
                        .build();
                e.getPlayer().getEquipment().setChestplate(item);
                break;
            case CHAINMAIL_LEGGINGS:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-chain")
                        .setDefence(4)
                        .build();
                e.getPlayer().getEquipment().setLeggings(item);
                break;
            case CHAINMAIL_BOOTS:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-chain")
                        .setDefence(1)
                        .build();
                e.getPlayer().getEquipment().setBoots(item);
                break;
            case GOLD_HELMET:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-gold")
                        .setDefence(2)
                        .build();
                e.getPlayer().getEquipment().setHelmet(item);
                break;
            case GOLD_CHESTPLATE:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-gold")
                        .setDefence(5)
                        .build();
                e.getPlayer().getEquipment().setChestplate(item);
                break;
            case GOLD_LEGGINGS:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-gold")
                        .setDefence(3)
                        .build();
                e.getPlayer().getEquipment().setLeggings(item);
                break;
            case GOLD_BOOTS:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-gold")
                        .setDefence(1)
                        .build();
                e.getPlayer().getEquipment().setBoots(item);
                break;
            case LEATHER_HELMET:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-leather")
                        .setDefence(1)
                        .build();
                e.getPlayer().getEquipment().setHelmet(item);
                break;
            case LEATHER_CHESTPLATE:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-leather")
                        .setDefence(3)
                        .build();
                e.getPlayer().getEquipment().setChestplate(item);
                break;
            case LEATHER_LEGGINGS:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-leather")
                        .setDefence(2)
                        .build();
                e.getPlayer().getEquipment().setLeggings(item);
                break;
            case LEATHER_BOOTS:
                item = new ItemBuilder(item).addNBT(PUMPKIN_ARMOUR_ID, "vanilla-leather")
                        .setDefence(1)
                        .build();
                e.getPlayer().getEquipment().setBoots(item);
                break;
            default:
                break;

        }

    }



}
