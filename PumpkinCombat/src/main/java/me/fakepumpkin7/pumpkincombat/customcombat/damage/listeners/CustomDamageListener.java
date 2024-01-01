package me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners;

import me.fakepumpkin7.pumpkincombat.customcombat.CombatUtils;
import me.fakepumpkin7.pumpkincombat.customcombat.damage.CustomDamage;
import me.fakepumpkin7.pumpkincombat.customcombat.damage.event.CustomDamageEvent;
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

    @EventHandler
    public void onCustomDamage(CustomDamageEvent e){

        Entity attacker = e.getAttacker();
        Entity target = e.getTarget();
        EntityDamageEvent.DamageCause cause = e.getDamageCause();

        if(attacker == null){
            //no attacker
            //use damage cause and target stats to work out custom health to deduct from targets custom health amount


            return;
        }
        // attacker
        // work out how much damage to deal, should be item in main hand
        double damage = workOutDamage(attacker);
        System.out.println("Damage:" + damage);
        // use this and target stats to work out custom health to deduct from targets custom health amount
        double targetMaxHealth = workOutMaxHealth(target);
        System.out.println("tmh:" + targetMaxHealth);

        double targetDefence = workOutDefence(target);
        System.out.println("Defence:" + targetDefence);

        double finalDamage = scaleDamage(damage,targetDefence);
        System.out.println("final damage:" + finalDamage);


        CombatUtils.dealDamage(target, finalDamage, targetMaxHealth);

    }



    /*
        Works out damage by doing
        (Playerbasedamage + weaponbasedamage)*playerdamagemulti*weapondamagemulti

     */
    private double workOutDamage(Entity attacker){
        double dmg = 0;
        if(attacker instanceof Player){
            Player player = (Player) attacker;
            //player stats
            double playerBaseDamage = cd.getEntityBaseDamage(player);
            System.out.println("pbd:" + playerBaseDamage);
            double playerDamageMultiplier = cd.getEntityDamageMulti(player);
            System.out.println("pdm:" + playerDamageMultiplier);
            //weapon stats
            ItemStack weapon = player.getInventory().getItemInHand();
            double weaponBaseDamage = cd.getItemBaseDamage(weapon);
            System.out.println("wbd:" + weaponBaseDamage);
            double weaponDamageMultiplier = cd.getItemDamageMulti(weapon);
            System.out.println("wdm:" + weaponDamageMultiplier);

            dmg = (playerBaseDamage+weaponBaseDamage)*playerDamageMultiplier*weaponDamageMultiplier ;
            return dmg;
        }


        return dmg;
    }


    private double workOutMaxHealth(Entity entity){
        double health = 20;
        if(entity.hasMetadata("pumpkin-custom-health")){
            health = entity.getMetadata("pumpkin-custom-health").get(0).asDouble();
        }
        return health;
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
