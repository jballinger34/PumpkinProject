package me.fakepumpkin7.pumpkinframework.gui.menu.listener;

import me.fakepumpkin7.pumpkinframework.gui.menu.Button;
import me.fakepumpkin7.pumpkinframework.gui.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MenuListener implements Listener {

    @EventHandler
    public void onClickMenu(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();

        Menu menu = Menu.getMenus().get(player.getUniqueId());

        if(menu == null) return;

        event.setCancelled(true);

        int slot = event.getSlot();
        if(player.getOpenInventory().getTopInventory().equals(inventory)){
            if(menu.getButton(slot) == null) return;

            Button button = menu.getButton(slot);
            button.click(player, event);
        }





    }


}
