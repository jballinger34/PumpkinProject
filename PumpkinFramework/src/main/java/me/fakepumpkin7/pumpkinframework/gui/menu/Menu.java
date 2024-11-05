package me.fakepumpkin7.pumpkinframework.gui.menu;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public abstract class Menu {


    private static final Map<UUID, Menu> menus = Maps.newConcurrentMap();

    private Map<Integer, Button> buttonListeners;

    Inventory inventory;
    String name;
    int rows;

    public Menu(String name, int rows){
        inventory = Bukkit.createInventory(null, rows*9, name);
        this.name = name;
        this.rows = rows;
        this.buttonListeners = Maps.newHashMap();
    }

    public void setItem(int slot, ItemStack item){
        inventory.setItem(slot, item);
    }

    public void setButton(int slot, Button button){
        buttonListeners.put(slot, button);
        inventory.setItem(slot,button.getItemStack());
    }

    public void open(Player player){
        player.openInventory(inventory);
        menus.put(player.getUniqueId(), this);
    }

    public int getSize() {
        return inventory.getSize();
    }

    public static Map<UUID, Menu> getMenus() {
        return menus;
    }
    public Button getButton(int slot){
        return buttonListeners.get(slot);
    }
    public String getName(){
        return name;
    }
    public int getRows(){
        return rows;
    }
}
