//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.enchanting;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.PostToolEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import com.rit.sucy.config.LanguageNode;
import com.rit.sucy.config.RootConfig;
import com.rit.sucy.config.RootNode;
import com.rit.sucy.service.ENameParser;
import com.rit.sucy.service.PermissionNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.server.v1_8_R3.EnchantmentDurability;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EListener implements Listener {
    EnchantmentAPI plugin;
    HashMap<String, TableTask> tasks = new HashMap();
    HashMap<String, int[]> levels = new HashMap();
    public static boolean excuse = false;
    private final Random random = new Random();

    public EListener(EnchantmentAPI plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onHit(EntityDamageByEntityEvent event) {
        if (!excuse && event.getCause() != DamageCause.CUSTOM && !(event.getDamage() <= 0.0)) {
            if (event.getEntity() instanceof LivingEntity) {
                if (event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.PROJECTILE) {
                    LivingEntity damaged = (LivingEntity)event.getEntity();
                    LivingEntity damager = null;
                    if (event.getDamager() instanceof LivingEntity) {
                        damager = (LivingEntity)event.getDamager();
                    } else if (event.getDamager() instanceof Projectile && ((Projectile)event.getDamager()).getShooter() instanceof LivingEntity) {
                        damager = (LivingEntity)((Projectile)event.getDamager()).getShooter();
                    }

                    Map.Entry entry;
                    Iterator var8;
                    if (damager != null) {
                        boolean valid = true;
                        if (event.getCause() == DamageCause.PROJECTILE && damager instanceof Player) {
                            ItemStack itemInHand = ((Player)damager).getItemInHand();
                            if (itemInHand == null || itemInHand.getType() != Material.BOW) {
                                valid = false;
                            }
                        }

                        if (valid) {
                            var8 = this.getValidEnchantments(this.getItems(damager)).entrySet().iterator();

                            while(var8.hasNext()) {
                                entry = (Map.Entry)var8.next();
                                ((CustomEnchantment)entry.getKey()).applyEffect(damager, damaged, (Integer)entry.getValue(), event);
                            }
                        }
                    }

                    TreeMultiMap<PostDefenceEffectRunnable> postRunTasks = new TreeMultiMap((o1, o2) -> {
                        return -Integer.compare((Integer) o1, (Integer) o2);
                    });
                    var8 = this.getValidEnchantments(this.getItems(damaged)).entrySet().iterator();

                    while(var8.hasNext()) {
                        entry = (Map.Entry)var8.next();
                        ((CustomEnchantment)entry.getKey()).applyDefenseEffect(damaged, damager, (Integer)entry.getValue(), event, postRunTasks);
                    }

                    var8 = postRunTasks.getAll().iterator();

                    while(var8.hasNext()) {
                        PostDefenceEffectRunnable runnable = (PostDefenceEffectRunnable)var8.next();
                        runnable.execute(damaged, damager, event);
                    }

                }
            }
        } else {
            excuse = false;
        }
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onDamaged(EntityDamageEvent event) {
        if (!(event.getDamage() <= 0.0) && event.getEntity() instanceof LivingEntity) {
            LivingEntity damaged = (LivingEntity)event.getEntity();
            TreeMultiMap<PostDefenceEffectRunnable> postRunTasks = new TreeMultiMap((o1, o2) -> {
                return -Integer.compare((Integer) o1,(Integer) o2);
            });
            Iterator var4 = this.getValidEnchantments(this.getItems(damaged)).entrySet().iterator();

            while(var4.hasNext()) {
                Map.Entry<CustomEnchantment, Integer> entry = (Map.Entry)var4.next();
                ((CustomEnchantment)entry.getKey()).applyDefenseEffect(damaged, (LivingEntity)null, (Integer)entry.getValue(), event, postRunTasks);
            }

            var4 = postRunTasks.getAll().iterator();

            while(var4.hasNext()) {
                PostDefenceEffectRunnable runnable = (PostDefenceEffectRunnable)var4.next();
                runnable.execute(damaged, (LivingEntity)null, event);
            }

        }
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onDamaged(EntityDamageByBlockEvent event) {
        if (!(event.getDamage() <= 0.0) && event.getEntity() instanceof LivingEntity) {
            LivingEntity damaged = (LivingEntity)event.getEntity();
            TreeMultiMap<PostDefenceEffectRunnable> postRunTasks = new TreeMultiMap((o1, o2) -> {
                return -Integer.compare((Integer)o1,(Integer) o2);
            });
            Iterator var4 = this.getValidEnchantments(this.getItems(damaged)).entrySet().iterator();

            while(var4.hasNext()) {
                Map.Entry<CustomEnchantment, Integer> entry = (Map.Entry)var4.next();
                ((CustomEnchantment)entry.getKey()).applyDefenseEffect(damaged, (LivingEntity)null, (Integer)entry.getValue(), event, postRunTasks);
            }

            var4 = postRunTasks.getAll().iterator();

            while(var4.hasNext()) {
                PostDefenceEffectRunnable runnable = (PostDefenceEffectRunnable)var4.next();
                runnable.execute(damaged, (LivingEntity)null, event);
            }

        }
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onDamageBlock(BlockDamageEvent event) {
        Iterator var2 = this.getValidEnchantments(this.getItems(event.getPlayer())).entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<CustomEnchantment, Integer> entry = (Map.Entry)var2.next();
            ((CustomEnchantment)entry.getKey()).applyToolEffect(event.getPlayer(), event.getBlock(), (List)null, (TreeMultiMap)null, (Integer)entry.getValue(), event);
        }

    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onBreakBlock(BlockBreakEvent event) {
        ItemStack itemInHand = event.getPlayer().getItemInHand();
        Collection<ItemStack> dropCollection = event.getBlock().getDrops(itemInHand);
        if (!(dropCollection instanceof List)) {
            dropCollection = new ArrayList((Collection)dropCollection);
        }

        List<ItemStack> drops = (List)dropCollection;
        boolean affected = false;
        TreeMultiMap<PostToolEffectRunnable> postRunTasks = new TreeMultiMap((o1, o2) -> {
            return - Integer.compare((Integer) o1, (Integer) o2);
        });

        Iterator var6;
        Map.Entry entry;
        for(var6 = this.getValidEnchantments(this.getItems(event.getPlayer())).entrySet().iterator(); var6.hasNext(); affected = ((CustomEnchantment)entry.getKey()).applyToolEffect(event.getPlayer(), event.getBlock(), drops, postRunTasks, (Integer)entry.getValue(), event) || affected) {
            entry = (Map.Entry)var6.next();
        }

        var6 = postRunTasks.getAll().iterator();

        while(var6.hasNext()) {
            PostToolEffectRunnable runnable = (PostToolEffectRunnable)var6.next();
            runnable.execute(event.getPlayer(), event.getBlock(), drops, event);
        }

        if (affected) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            if (EnchantmentDurability.a(CraftItemStack.asNMSCopy(itemInHand), event.getPlayer().getItemInHand().getItemMeta().getEnchantLevel(Enchantment.DURABILITY) + 1, this.random)) {
                PlayerItemDamageEvent damageEvent = new PlayerItemDamageEvent(event.getPlayer(), itemInHand, 1);
                Bukkit.getServer().getPluginManager().callEvent(damageEvent);
                if (!damageEvent.isCancelled()) {
                    itemInHand.setDurability((short)(itemInHand.getDurability() + 1));
                    if (itemInHand.getDurability() >= itemInHand.getType().getMaxDurability()) {
                        itemInHand = null;
                    }

                    damageEvent.getPlayer().setItemInHand(itemInHand);
                }
            }

            World world = event.getBlock().getWorld();
            Location blockLocation = event.getBlock().getLocation().clone().add(0.5, 0.5, 0.5);
            Iterator var8 = drops.iterator();

            while(var8.hasNext()) {
                ItemStack drop = (ItemStack)var8.next();
                world.dropItem(blockLocation, drop);
            }

            if (event.getBlock().getType() == Material.ENCHANTMENT_TABLE) {
                var8 = this.tasks.values().iterator();

                while(var8.hasNext()) {
                    TableTask task = (TableTask)var8.next();
                    task.restore();
                }
            }

        }
    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onInteract(PlayerInteractEvent event) {
        Iterator var2 = this.getValidEnchantments(this.getItems(event.getPlayer())).entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<CustomEnchantment, Integer> entry = (Map.Entry)var2.next();
            ((CustomEnchantment)entry.getKey()).applyMiscEffect(event.getPlayer(), (Integer)entry.getValue(), event);
        }

        (new EEquip(event.getPlayer())).runTaskLater(this.plugin, 1L);
    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onInteract(PlayerInteractEntityEvent event) {
        Iterator var2 = this.getValidEnchantments(this.getItems(event.getPlayer())).entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<CustomEnchantment, Integer> entry = (Map.Entry)var2.next();
            ((CustomEnchantment)entry.getKey()).applyEntityEffect(event.getPlayer(), (Integer)entry.getValue(), event);
        }

    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onEquip(InventoryClickEvent event) {
        (new EEquip(this.plugin.getServer().getPlayer(event.getWhoClicked().getName()))).runTaskLater(this.plugin, 1L);
    }

    @EventHandler
    public void onBreak(PlayerItemBreakEvent event) {
        (new EEquip(event.getPlayer())).runTaskLater(this.plugin, 1L);
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onConnect(PlayerJoinEvent event) {
        (new EEquip(event.getPlayer())).runTaskLater(this.plugin, 1L);
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onDisconnect(PlayerQuitEvent event) {
        EEquip.clearPlayer(event.getPlayer());
        this.levels.remove(event.getPlayer().getName());
        if (this.tasks.containsKey(event.getPlayer().getName())) {
            this.tasks.remove(event.getPlayer().getName());
        }

    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onProjectile(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof LivingEntity) {
            LivingEntity shooter = (LivingEntity)event.getEntity().getShooter();
            Iterator var3 = this.getValidEnchantments(this.getItems(shooter)).entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<CustomEnchantment, Integer> entry = (Map.Entry)var3.next();
                ((CustomEnchantment)entry.getKey()).applyProjectileEffect(shooter, (Integer)entry.getValue(), event);
            }

        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getType() == InventoryType.ENCHANTING && this.tasks.containsKey(event.getWhoClicked().getName())) {
            ((TableTask)this.tasks.get(event.getWhoClicked().getName())).restore();
        }

    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.ENCHANTING && event.getPlayer().hasPermission(PermissionNode.TABLE.getNode())) {
            this.tasks.put(event.getPlayer().getName(), new TableTask(this.plugin, this.plugin.getServer().getPlayer(event.getPlayer().getName())));
        }

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory() instanceof EnchantingInventory && this.tasks.containsKey(event.getPlayer().getName())) {
            TableTask task = (TableTask)this.tasks.remove(event.getPlayer().getName());
            task.restore();
            task.cancel();
        }

    }

    @EventHandler
    public void onPrepare(PrepareItemEnchantEvent event) {
        this.levels.put(event.getEnchanter().getName(), Arrays.copyOf(event.getExpLevelCostsOffered(), 3));
    }

    @EventHandler(
            ignoreCancelled = true
    )
    public void onEnchant(EnchantItemEvent event) {
        if (event.getEnchanter().hasPermission(PermissionNode.TABLE.getNode()) && this.tasks.containsKey(event.getEnchanter().getName())) {
            event.setCancelled(true);
            if (EnchantmentAPI.getEnchantments(event.getItem()).isEmpty()) {
                ItemStack storedItem = ((TableTask)this.tasks.get(event.getEnchanter().getName())).stored;
                if (storedItem.getAmount() > 1) {
                    storedItem.setAmount(storedItem.getAmount() - 1);
                    event.getEnchanter().getInventory().addItem(new ItemStack[]{storedItem.clone()});
                    storedItem.setAmount(1);
                }

                int maxEnchants = ((RootConfig)this.plugin.getModuleForClass(RootConfig.class)).getInt(RootNode.MAX_ENCHANTS);
                EnchantResult result = EEnchantTable.enchant(event.getEnchanter(), storedItem, event.getExpLevelCost(), maxEnchants);
                ItemStack item = result.getItem();
                if (item != null) {
                    event.getInventory().setItem(0, (ItemStack)null);
                    event.getEnchantsToAdd().clear();
                    boolean randomName = ((RootConfig)this.plugin.getModuleForClass(RootConfig.class)).getBoolean(RootNode.ITEM_LORE);
                    if (randomName && event.getEnchanter().hasPermission(PermissionNode.NAMES.getNode())) {
                        String name = "§";
                        int random = (int)(Math.random() * 14.0) + 49;
                        if (random > 57) {
                            random += 39;
                        }

                        String format = (String)this.plugin.getConfig().getStringList(LanguageNode.NAME_FORMAT.getFullPath()).get(0);
                        format = format.replace("{adjective}", this.plugin.getAdjective(result.getLevel() / 11 + 1 > 4 ? 4 : result.getLevel() / 11 + 1));
                        format = format.replace("{weapon}", this.plugin.getWeapon(item.getType().name()));
                        format = format.replace("{suffix}", this.plugin.getSuffix(result.getFirstEnchant()));
                        name = name + (char)random + format;
                        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : this.plugin.getServer().getItemFactory().getItemMeta(item.getType());
                        meta.setDisplayName(name);
                        item.setItemMeta(meta);
                    }

                    event.getInventory().addItem(new ItemStack[]{item});
                    int cost = 1;
                    int[] costs = (int[])this.levels.get(event.getEnchanter().getName());

                    for(int i = 0; i < costs.length; ++i) {
                        if (costs[i] == event.getExpLevelCost()) {
                            cost = i + 1;
                        }
                    }

                    if (event.getEnchanter().getGameMode() != GameMode.CREATIVE) {
                        event.getEnchanter().setLevel(event.getEnchanter().getLevel() - cost);
                        if (event.getInventory().getItem(1).getAmount() <= cost) {
                            event.getInventory().setItem(1, (ItemStack)null);
                        } else {
                            event.getInventory().getItem(1).setAmount(event.getInventory().getItem(1).getAmount() - cost);
                        }
                    }

                }
            }
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPrepareEnchant(PrepareItemEnchantEvent event) {
        ItemMeta itemMeta;
        if (event.getItem().hasItemMeta() && (itemMeta = event.getItem().getItemMeta()).hasLore() && itemMeta.getLore().contains(((TableTask)this.tasks.get(event.getEnchanter().getName())).cantEnchant)) {
            event.setCancelled(true);
        }

    }

    private Map<CustomEnchantment, Integer> getValidEnchantments(List<ItemStack> items) {
        Map<CustomEnchantment, Integer> validEnchantments = new HashMap();
        Iterator var3 = items.iterator();

        while(true) {
            ItemMeta meta;
            do {
                do {
                    ItemStack item;
                    do {
                        if (!var3.hasNext()) {
                            return validEnchantments;
                        }

                        item = (ItemStack)var3.next();
                    } while(item == null);

                    meta = item.getItemMeta();
                } while(meta == null);
            } while(!meta.hasLore());

            Iterator var6 = meta.getLore().iterator();

            while(var6.hasNext()) {
                String lore = (String)var6.next();
                String name = ENameParser.parseName(lore);
                if (name != null) {
                    int level = ENameParser.parseLevel(lore);
                    if (level != 0 && EnchantmentAPI.isRegistered(name)) {
                        CustomEnchantment enchant = EnchantmentAPI.getEnchantment(name);
                        if (enchant.canStack() && validEnchantments.containsKey(enchant)) {
                            level += (Integer)validEnchantments.get(enchant);
                        }

                        validEnchantments.put(enchant, level);
                    }
                }
            }
        }
    }

    private ArrayList<ItemStack> getItems(LivingEntity entity) {
        ItemStack[] armor = entity.getEquipment().getArmorContents();
        ItemStack weapon = entity.getEquipment().getItemInHand();
        ArrayList<ItemStack> items = new ArrayList(Arrays.asList(armor));
        items.add(weapon);
        return items;
    }
}
