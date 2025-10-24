package me.fakepumpkin7.pumpkinframework.items.interactive;

import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;


public class InteractiveItemUtils implements Listener {

    private static final Map<String, InteractiveItem> registeredItems = new HashMap<>();

    public static void register(String id, InteractiveItem item) {
        registeredItems.put(id, item);
    }

    public static ItemStack getItem(String id) {
        return registeredItems.get(id).getItem();
    }

    public static InteractiveItem fromItemStack(ItemStack item) {
        String id = InteractiveItemUtils.getInteractiveId(item);
        if (id == null) return null;
        return registeredItems.get(id);
    }


    private static final String NBT_KEY = "interactive-id";

    public static ItemStack getItemWithInteractiveId(ItemStack item, String id){
        return NbtUtil.addNbt(item,NBT_KEY,id);
    }
    public static String getInteractiveId(ItemStack item){
        if(item == null) return null;
        return NbtUtil.getNbtString(item,NBT_KEY);
    }



    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        Action action = e.getAction();

        if(!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) return;

        ItemStack item = e.getItem();
        InteractiveItem interactiveItem = fromItemStack(item);

        if(interactiveItem == null) return;
        interactiveItem.onInteract(e.getPlayer());

    }

    public static Map<String, InteractiveItem> getRegisteredItems() {
        return registeredItems;
    }
}
