//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.Anvil;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AnvilTask extends BukkitRunnable {
    private ItemStack[] contents;
    private AnvilView anvil;

    public AnvilTask(Plugin plugin, AnvilView view) {
        this.anvil = view;
        this.contents = view.getInputSlots();
        this.runTaskTimer(plugin, 2L, 2L);
    }

    public AnvilView getView() {
        return this.anvil;
    }

    public void run() {
        ItemStack[] input = this.anvil.getInputSlots();
        if (input[0] != this.contents[0] || input[1] != this.contents[1]) {
            AnvilMechanics.updateResult(this.anvil, input);
            this.contents = input;
        }

    }
}
