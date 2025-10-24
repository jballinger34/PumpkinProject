package me.fakepumpkin7.pumpkinenchants;

import com.rit.sucy.CustomEnchantment;


public class BaseEnchant extends CustomEnchantment {


    private EnchantmentGroup group;

    public BaseEnchant(String name, String description, EnchantmentGroup group, int max) {
        this(name, description ,group,max, false);
    }

    public BaseEnchant(String name, String description, EnchantmentGroup group, int max,boolean stacks) {
        super(name, description ,group.getAllMaterials(),max, stacks);
        this.group = group;
    }

    public EnchantmentGroup getGroup() {
        return group;
    }
}
