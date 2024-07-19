package me.fakepumpkin7.pumpkinenchants;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.service.ERomanNumeral;
import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.particle.ParticleEffect;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().getTitle().equals("container.crafting")) {
            return;
        }
        if(event.getCursor() == null || event.getCursor().getType().equals(Material.AIR) || event.getCursor().getAmount() == 0){
            return;
        }


        if (EnchantItem.isEnchantItem(event.getCursor())) {
            if (event.getRawSlot() < 9 && event.getRawSlot() >= 0) {
                // Don't allow them to enchant while it's in crafting grid or equipped.
                Player pl = (Player) event.getWhoClicked();
                ChatUtils.info(pl, ChatColor.RED + "You cannot apply runes to your armor while it is equipped.");
                return;
            }

            if (event.getCurrentItem() != null) {
                // Check if it's eligible.
                final Player player = (Player) event.getWhoClicked();
                final ItemStack toEnchant = event.getCurrentItem();

                CustomEnchantment ce = EnchantItem.getEnchantment(event.getCursor());

                if (!(ce instanceof BaseEnchant) || !ce.canEnchantOnto(toEnchant) || toEnchant.getMaxStackSize() != 1) {
                    return;
                }

                EnchantType enchantmentType = EnchantType.getEnchantmentTypeFromName(ce.name());

                if (enchantmentType == null) {
                    return;
                }


                //If the item already contains the enchantment, simply updates the current enchant level.
                if (EnchantmentAPI.itemHasEnchantment(toEnchant, ce.name())) {
                    final int proposedLevel = EnchantItem.getEnchantmentLevel(event.getCursor());
                    final int existingLevel = EnchantmentAPI.getEnchantments(toEnchant).get(ce);

                    if (existingLevel >= proposedLevel) {
                        // Already has the same or better enchant level.
                        if (existingLevel > proposedLevel) {
                            ChatUtils.info(player, "§cYour item already has a higher level enchant applied (" + ce.name() + " " + ERomanNumeral.numeralOf(existingLevel) + ")");
                        } else {
                            ChatUtils.info(player, "§cYour item already has this enchant applied (" + ce.name() + " " + ERomanNumeral.numeralOf(existingLevel) + ")");
                        }
                        player.sendMessage("§7§l* §7(You are trying to add " + ce.name() + " " + ERomanNumeral.numeralOf(proposedLevel) + ")");
                        return;
                    }

                }

                if ((EnchantmentAPI.getAllEnchantments(toEnchant).size() + 1) > EnchantItem.maxEnchantsOnItem) {

                    player.sendMessage("§c§l(!) §cEquipment can only have §n" + EnchantItem.maxEnchantsOnItem + "Custom Enchantments §capplied!");
                    player.sendMessage("§7§l* §7Enchantments can still be upgraded.");
                    return;
                }

                ItemStack ench_book = event.getCursor().clone();
                player.setItemOnCursor(null);
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                boolean success = EnchantItem.applyEnchantmentRune(player, ench_book, toEnchant);

                //particles and sounds
                if (success) {
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 0.75F);
                    player.playEffect(EntityEffect.VILLAGER_HAPPY);

                    final Location l = player.getLocation();
                    Bukkit.getScheduler().runTaskAsynchronously(PumpkinEnchants.getInstance(), () ->
                    {
                        try {
                            ParticleEffect.sendToLocation(ParticleEffect.SPELL, l.add(0, 1, 0), 0F, 0F, 0F, 0.8F, 85);
                            ParticleEffect.sendToLocation(ParticleEffect.MAGIC_CRIT, l.add(0, 1, 0), 0F, 0F, 0F, 0.65F, 30);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    });
                } else {
                    player.playSound(player.getLocation(), Sound.LAVA_POP, 1F, 0.75F);
                    player.playEffect(EntityEffect.VILLAGER_ANGRY);
                    final Location l = player.getLocation();
                    Bukkit.getScheduler().runTaskAsynchronously(PumpkinEnchants.getInstance(), () ->
                    {
                        ParticleEffect.sendToLocation(ParticleEffect.SPELL, l.add(0, 1, 0), 0F, 0F, 0F, 0.35F, 20);
                    });
                }
                player.updateInventory();
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.hasItem() && EnchantItem.isEnchantItem(e.getItem())) {
            if (e.getItem().getDurability() != 0) {
                e.getItem().setDurability((short) 0);
            }
            player.sendMessage(" ");
            player.sendMessage(ChatColor.YELLOW + "Drag 'n drop this enchant onto any piece of");
            player.sendMessage(ChatColor.YELLOW + "equipment in your inventory to apply it!");
            player.sendMessage(ChatColor.GRAY.toString() + ChatColor.BOLD + "*" + ChatColor.GRAY + " Want more info on this enchant? "
                    + ChatColor.GRAY + ChatColor.BOLD + "*");

            player.spigot().sendMessage(
                    new ComponentBuilder(ChatColor.GOLD.toString() + ChatColor.BOLD + "CLICK HERE")
                            .event(
                                    new HoverEvent(
                                            HoverEvent.Action.SHOW_TEXT,
                                            new ComponentBuilder(ChatColor.GOLD + "Click here for more information").create()
                                    )
                            )
                            .event(new ClickEvent(
                                    ClickEvent.Action.RUN_COMMAND,
                                    "/runes " + EnchantItem.getEnchantment(e.getItem()).name()
                            ))
                            .create()
            );
            player.sendMessage(" ");

            player.updateInventory();
        }
    }
}