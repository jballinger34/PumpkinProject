package me.fakepumpkin7.pumpkinframework.gui;

import lombok.Getter;
import lombok.Setter;
import me.fakepumpkin7.pumpkinframework.gui.actions.ClickAction;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class Button {

    private ItemStack itemStack;
    private ClickAction clickAction;

    public Button(ItemStack itemStack, ClickAction clickAction) {
        this.itemStack = itemStack;
        this.clickAction = clickAction;
    }

    public Button(ItemStack itemStack) {
        this(itemStack, null);
    }

}
