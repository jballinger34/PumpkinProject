package me.fakepumpkin7.pumpkincombat;

import lombok.Getter;
import me.fakepumpkin7.pumpkincombat.customcombat.damage.CustomDamage;
import me.fakepumpkin7.pumpkincombat.customcombat.defence.CustomDefence;
import me.fakepumpkin7.pumpkincombat.customcombat.health.CustomHealth;
import me.fakepumpkin7.pumpkincombat.customcombat.speed.CustomSpeed;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinCombat extends JavaPlugin {

    //TODO
    // MAKE SPEED ALL ENTITIES NOT JUST PLAYERS

    @Getter static PumpkinCombat instance;

    @Getter CustomDamage customDamage;
    @Getter CustomHealth customHealth;
    @Getter CustomDefence customDefence;
    @Getter CustomSpeed customSpeed;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        customDamage = new CustomDamage(this);
        customHealth = new CustomHealth(this);
        customDefence = new CustomDefence(this);
        customSpeed = new CustomSpeed(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
