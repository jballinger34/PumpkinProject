package me.fakepumpkin7.pumpkinenchants.enchants.weapons;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Block extends BaseEnchant {
    public Block() {
        super("Block","Gain damage reduction when blocking",3, EnchantmentGroup.WEAPONS);
    }

    private double damageReductionPerLevel = 0.1;

    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, CustomDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
        if(!(user instanceof Player)) return;

        Player player = (Player) user;
        if(!player.isBlocking()) return;

        event.setDamage(event.getDamage()*damageReductionPerLevel*enchantLevel);


    }

}
