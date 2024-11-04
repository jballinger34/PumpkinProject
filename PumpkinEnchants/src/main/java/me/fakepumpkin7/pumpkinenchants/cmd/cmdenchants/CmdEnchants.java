package me.fakepumpkin7.pumpkinenchants.cmd.cmdenchants;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.fakepumpkin7.pumpkinenchants.EnchantType;
import me.fakepumpkin7.pumpkinframework.gui.book.populators.BookPopulator;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import net.minecraft.server.v1_8_R3.MojangsonParser;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.EnumMap;

public class CmdEnchants implements Listener {

    private final EnumMap<EnchantType, BookPopulator> enchantmentPopulators = new EnumMap<>(EnchantType.class);
    private final EnumMap<ItemRarity, BookPopulator> tierPopulators = new EnumMap<>(ItemRarity.class);

    private final MainMenuPopulator mainMenu = new MainMenuPopulator();

    public CmdEnchants() {
        mainMenu.getBookContents(); //Pre-cache on startup

        for (EnchantType enchantmentType : EnchantType.values()) {

            BookPopulator populator = new EnchantPopulator(enchantmentType);
            populator.getBookContents(); //Pre-cache on startup

            enchantmentPopulators.put(enchantmentType, populator);
        }

        for (ItemRarity rarity : ItemRarity.values()) {
            BookPopulator populator = new TierPopulator(rarity);
            populator.getBookContents(); //Pre-cache on startup

            tierPopulators.put(rarity, populator);
        }
    }

    //

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();

        String command = event.getMessage().substring(1);
        String[] args = command.split("[ ]");

        command = args[0].toLowerCase();

        if (args.length > 1) {
            args = Arrays.copyOfRange(args, 1, args.length);
        } else {
            args = new String[]{};
        }

        if (    command.equals("enchant") || command.equals("enchants") ||
                command.equals("enchantment") || command.equals("enchantments")
        ) {
            event.setCancelled(true);
            //Find the rune
            if (args.length > 0) {
                String objectName = "";
                for (String arg : args) {
                    objectName += arg + " ";
                }

                objectName = objectName.toLowerCase();
                //Reliably remove ending whitespace
                if (!objectName.isEmpty()) {
                    objectName = objectName.substring(0, objectName.length() - 1);
                }

                for (EnchantType enchantmentType : EnchantType.values()) {
                    if (enchantmentType.getEnchant().name().toLowerCase().equals(objectName)) {
                        openBookFor(player, getBook(enchantmentPopulators.get(enchantmentType)));
                        return;
                    }
                }

                for (ItemRarity rarity : ItemRarity.values()) {

                    if (rarity.name().toLowerCase().equals(objectName)) {
                        openBookFor(player, getBook(tierPopulators.get(rarity)));
                        return;
                    }
                }
            }

            openBookFor(player, getBook(mainMenu));
        }
    }

    //

    public ItemStack getBook(BookPopulator bookPopulator) {
        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);

        try {
            nmsStack.setTag(MojangsonParser.parse(bookPopulator.getBookContents()));
        } catch (Throwable e) {
            e.printStackTrace();
        }

        itemStack = CraftItemStack.asCraftMirror(nmsStack);
        return itemStack;
    }

    public void openBookFor(Player player, ItemStack bookItem) {
        if (bookItem == null) {
            getBook(null);
        }

        int slot = player.getInventory().getHeldItemSlot();
        ItemStack old = player.getInventory().getItem(slot);
        player.getInventory().setItem(slot, bookItem);

        ByteBuf buf = Unpooled.buffer(256);
        buf.setByte(0, (byte) 0);
        buf.writerIndex(1);

        PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buf));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        player.getInventory().setItem(slot, old);
    }
}