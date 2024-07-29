package me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import me.fakepumpkin7.pumpkinframework.economy.Bank;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Pickpocket extends BaseEnchant {
    public Pickpocket() {
        super("Pickpocket","Chance to steal money when you attack someone.", EnchantmentGroup.MELEE_WEAPONS, 7);
    }
    double percentOfMoneyStolen = 1;
    double procChancePerLevel = 0.08;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, CustomDamageEvent event) {


        double random = Math.random();
        double chance = procChancePerLevel * enchantLevel;

        if(random < chance) {
            if(!(user instanceof Player)) return;
            if(!(target instanceof Player)) return;

            Player attacker = (Player) user;
            Player victim = (Player) target;

            double toAdd = Bank.getBalance(victim.getUniqueId())*(percentOfMoneyStolen/100);

            Bank.addBalance(attacker.getUniqueId(), toAdd);
            Bank.addBalance(victim.getUniqueId(), -toAdd);

            ChatUtils.notify(attacker, "Pickpocket: +" + toAdd);
            ChatUtils.warn(victim, "Pickpocket: -" + toAdd);
        }

    }
}
