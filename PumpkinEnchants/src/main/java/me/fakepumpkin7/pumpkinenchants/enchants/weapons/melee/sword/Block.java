package me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee.sword;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Block extends BaseEnchant {
    public Block() {
        super("Block","Gain damage reduction when blocking", EnchantmentGroup.SWORD, 3);
    }

    private double damageReductionPerLevel = 0.1;

    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, EntityDamageByEntityEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
        if(!(user instanceof Player)) return;

        Player player = (Player) user;
        if(!player.isBlocking()) return;

        event.setDamage(event.getDamage()*damageReductionPerLevel*enchantLevel);


    }

}
