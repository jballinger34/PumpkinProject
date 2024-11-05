package me.fakepumpkin7.pumpkinframework.gui.menu.examples;

import me.fakepumpkin7.pumpkinframework.enchants.EnchantAPI;
import me.fakepumpkin7.pumpkinframework.enchants.EnchantManager;
import me.fakepumpkin7.pumpkinframework.gui.menu.Button;
import me.fakepumpkin7.pumpkinframework.gui.menu.Menu;
import me.fakepumpkin7.pumpkinframework.gui.menu.PaginatedMenu;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class MenuExample implements Listener {

    //really simple example of a menu
    //2 rows, chicken in top left slot, clicking chicken closes inventory
    //beef in 2nd slot, clicking does nothing

    /*@EventHandler
    public void TESTBASEMENU(PlayerMoveEvent event){

        Menu menuExample = new BaseMenuExample();
        menuExample.open(event.getPlayer());


    }*/

    // 2 rows, bottom saved for buttons to change page and info
    // 30 random enchants will be added
    /*@EventHandler
    public void TESTPAGEDMENU(PlayerMoveEvent event){

        Menu menuExample = new PagedMenuExample();
        menuExample.open(event.getPlayer());


    }*/

}
class BaseMenuExample extends Menu {
    public BaseMenuExample() {

        super("A Test Menu", 2);
        setButton(0, new Button(new ItemStack(Material.COOKED_CHICKEN), (clicker, event) -> {
            clicker.closeInventory();
        }));
        setItem(1, new ItemStack(Material.COOKED_BEEF));

    }
}
class PagedMenuExample extends PaginatedMenu {
    public PagedMenuExample() {

        super("A Test Paged Menu", 1);
        for(int i = 0; i < 50; i++){
            appendButton(new Button(EnchantManager.getEnchantAPI().getEnchantItem(), ((clicker, event) -> {})));
        }

    }
}