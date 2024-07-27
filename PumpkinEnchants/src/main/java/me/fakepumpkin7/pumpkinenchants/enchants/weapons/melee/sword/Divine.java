package me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee.sword;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.LivingEntity;

public class Divine extends BaseEnchant {

    public Divine() {
        super("Divine","Heal 1 health on attack", EnchantmentGroup.SWORD,3);
    }
    double healthPerProc = 1;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, CustomDamageEvent event) {
        CombatUtils.healEntity(user,enchantLevel*healthPerProc);

    }


}
