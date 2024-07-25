package me.fakepumpkin7.pumpkinenchants;

import com.rit.sucy.CustomEnchantment;
import lombok.Getter;


public class BaseEnchant extends CustomEnchantment {


    @Getter
    private EnchantmentGroup group;

    public BaseEnchant(String name, String description, int max, EnchantmentGroup group) {
        this(name, description ,group,max, false);
    }

    public BaseEnchant(String name, String description, EnchantmentGroup group, int max,boolean stacks) {
        super(name, description ,group.getAllMaterials(),max, stacks);
        this.group = group;
    }



}
