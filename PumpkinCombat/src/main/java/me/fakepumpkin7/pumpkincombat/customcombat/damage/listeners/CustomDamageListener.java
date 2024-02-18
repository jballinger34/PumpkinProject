package me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners;

import me.fakepumpkin7.pumpkincombat.customcombat.damage.CustomDamage;
import me.fakepumpkin7.pumpkinframework.CombatUtils;
import me.fakepumpkin7.pumpkinframework.event.CustomDamageEvent;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;


public class CustomDamageListener implements Listener {

    // TODO
    //  SCALE DAMAGE METHOD TO SCALE DAMAGE ACCORDING DEFENCE BETTER

    CustomDamage cd;

    public CustomDamageListener(CustomDamage cd){
        this.cd = cd;
    }


    //TODO
    // scale the damage in the non attacker case by a different defence metric
    // true defence// fall damage defence // different stat
    @EventHandler
    public void onCustomDamage(CustomDamageEvent e){

        Entity attacker = e.getAttacker();
        Entity target = e.getTarget();
        System.out.println("location " + target.getLocation());
        System.out.println("type " + target.getType());

        EntityDamageEvent.DamageCause cause = e.getDamageCause();
        System.out.println("cause " +cause );
        double damage;

        //target health/defence stats
        double targetMaxHealth = CombatUtils.getEntityMaxHealth(target);
        System.out.println("tmh:" + targetMaxHealth);
        double targetDefence = workOutDefence(target);
        System.out.println("Defence:" + targetDefence);
        double knockback = workOutKnockback(attacker, target);
        System.out.println("Knockback:" + knockback);
        double finalDamage;
        //work out damage
        if(attacker == null){
            //no attacker
            //use damage cause and target stats to work out custom health to deduct from targets custom health amount

            damage = workOutDamage(cause, e.getVanillaDamage());
            System.out.println("Damage:" + damage);

        } else {
            // attacker
            // work out how much damage to deal, should be item in main hand
            // use this and target stats to work out custom health to deduct from targets custom health amount

            damage = workOutDamage(attacker);
            System.out.println("Damage:" + damage);

        }
        finalDamage = scaleDamage(damage,targetDefence);
        System.out.println("final damage:" + finalDamage);


        CombatUtils.dealDamage(target, finalDamage, targetMaxHealth);
        CombatUtils.dealKnockback(target, attacker, knockback);
    }



    /*
        Works out damage by doing
        (Playerbasedamage + weaponbasedamage)*playerdamagemulti*weapondamagemulti

     */
    private double workOutDamage(Entity attacker){
        double dmg = 0;

        //entity stats
        double baseDamage = CombatUtils.getEntityBaseDamage(attacker);
        //System.out.println("pbd:" + playerBaseDamage);
        double damageMultiplier = CombatUtils.getEntityDamageMulti(attacker);
        //System.out.println("pdm:" + playerDamageMultiplier);

        //weapon stats
        if(attacker instanceof Player){
            Player player = (Player) attacker;
            ItemStack weapon = player.getInventory().getItemInHand();
            double weaponBaseDamage = CombatUtils.getItemBaseDamage(weapon);
            //System.out.println("wbd:" + weaponBaseDamage);
            double weaponDamageMultiplier = CombatUtils.getItemDamageMulti(weapon);
            //System.out.println("wdm:" + weaponDamageMultiplier);

            dmg = (baseDamage+weaponBaseDamage)*damageMultiplier*weaponDamageMultiplier ;
            return dmg;
        }

        dmg = baseDamage*damageMultiplier;
        return dmg;
    }

    private double workOutDamage(EntityDamageEvent.DamageCause cause, double vanillaDamage){
        //TODO
        // make a switch statement for different damage causes
        return vanillaDamage;

    }

    //TODO make anti-kb stat for target
    private double workOutKnockback(Entity attacker, Entity target){
        double knockback = 1;
        if(attacker instanceof Player) {
            Player player = (Player) attacker;
            ItemStack weapon = player.getInventory().getItemInHand();
            knockback *= CombatUtils.getItemKnockbackMulti(weapon);
            return knockback;
        }
        return knockback;
    }

    private double getVanillaHealth(Entity entity){
        if(entity instanceof Creature){
            Creature creature = (Creature) entity;
            return creature.getMaxHealth();
        }
        return 1;
    }


    private double workOutDefence(Entity entity){
        double defence = 0;
        if(entity.hasMetadata("pumpkin-custom-defence")){
            defence = entity.getMetadata("pumpkin-custom-defence").get(0).asDouble();
        }
        return defence;
    }

    private double scaleDamage(double damage, double defence){
        double scaled = damage* (1-(defence/1000));
        return scaled;
    }
}
