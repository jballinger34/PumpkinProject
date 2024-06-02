package me.fakepumpkin7.pumpkinenchants;

import lombok.Getter;
import org.bukkit.Material;

import java.util.*;

public enum EnchantmentGroup {
    SWORD("Sword", "Swords"),
    BOW("Bow", "Bows"),
    PICKAXE("Pickaxe", "Pickaxes"),
    SPADE("Spade", "Spades"),
    HOE("Hoe", "Hoes"),
    AXE("Axe", "Axes"),
    HELMET("Helmet", "Helmets"),
    CHESTPLATE("Chestplate", "Chestplates"),
    LEGGINGS("Legging", "Leggings"),
    BOOTS("Boots", "Boots"),
    ARMOR("Armor", "Armor", HELMET, CHESTPLATE, LEGGINGS, BOOTS),
    TOOLS("Tool", "Tools", PICKAXE, SPADE, HOE, AXE),
    MELEE_WEAPONS("Melee Weapon", "Melee Weapons", SWORD, AXE),
    WEAPONS("Weapon", "Weapons", SWORD, AXE, BOW),
    WEAPONS_AND_TOOLS("Weapon\nTool", "Weapons\nTools", MELEE_WEAPONS, TOOLS),
    ALL("Equipment", "Equipment", WEAPONS_AND_TOOLS, ARMOR);


    @Getter
    private final String name;
    @Getter
    private final String pluralName;
    @Getter
    List<EnchantmentGroup> children;

    EnchantmentGroup(String name, String pluralName){
        this(name,pluralName,(EnchantmentGroup) null);
    }
    EnchantmentGroup(String name, String pluralName, EnchantmentGroup... children){
        this.name = name;
        this.pluralName = pluralName;

        this.children = new ArrayList<>();

        for (EnchantmentGroup child : children) {
            if (child != null) {
                Set<EnchantmentGroup> subChildren = new HashSet<>();
                getAllChildrenOf(child, subChildren);

                this.children.addAll(subChildren);
            }
        }

        if (this.children.isEmpty()) {
            this.children.add(this);
        }
    }
    private void getAllChildrenOf(EnchantmentGroup superChild, Set<EnchantmentGroup> subChildren) {
        for (EnchantmentGroup childGroup : superChild.getChildren()) {
            if (childGroup == superChild) {
                subChildren.add(childGroup);
            } else {
                getAllChildrenOf(childGroup, subChildren);
            }
        }
    }

    public Material[] getAllMaterials(){
        List<Material> mats = new ArrayList();

        if(children.contains(EnchantmentGroup.SWORD)){
            mats.add(Material.WOOD_SWORD);
            mats.add(Material.STONE_SWORD);
            mats.add(Material.IRON_SWORD);
            mats.add(Material.GOLD_SWORD);
            mats.add(Material.DIAMOND_SWORD);
        }
        if(children.contains(EnchantmentGroup.BOW)){
            mats.add(Material.BOW);
        }
        if(children.contains(EnchantmentGroup.PICKAXE)){
            mats.add(Material.WOOD_PICKAXE);
            mats.add(Material.STONE_PICKAXE);
            mats.add(Material.IRON_PICKAXE);
            mats.add(Material.GOLD_PICKAXE);
            mats.add(Material.DIAMOND_PICKAXE);

        }
        if(children.contains(EnchantmentGroup.SPADE)){
            mats.add(Material.WOOD_SPADE);
            mats.add(Material.STONE_SPADE);
            mats.add(Material.GOLD_SPADE);
            mats.add(Material.IRON_SPADE);
            mats.add(Material.DIAMOND_SPADE);
        }
        if(children.contains(EnchantmentGroup.HOE)){
            mats.add(Material.WOOD_HOE);
            mats.add(Material.STONE_HOE);
            mats.add(Material.GOLD_HOE);
            mats.add(Material.IRON_HOE);
            mats.add(Material.DIAMOND_HOE);

        }
        if(children.contains(EnchantmentGroup.AXE)){
            mats.add(Material.WOOD_AXE);
            mats.add(Material.STONE_AXE);
            mats.add(Material.GOLD_AXE);
            mats.add(Material.IRON_AXE);
            mats.add(Material.DIAMOND_AXE);

        }
        if(children.contains(EnchantmentGroup.HELMET)){
            mats.add(Material.SKULL_ITEM);
            mats.add(Material.LEATHER_HELMET);
            mats.add(Material.CHAINMAIL_HELMET);
            mats.add(Material.GOLD_HELMET);
            mats.add(Material.IRON_HELMET);
            mats.add(Material.DIAMOND_HELMET);
        }
        if(children.contains(EnchantmentGroup.CHESTPLATE)){
            mats.add(Material.LEATHER_CHESTPLATE);
            mats.add(Material.CHAINMAIL_CHESTPLATE);
            mats.add(Material.GOLD_CHESTPLATE);
            mats.add(Material.IRON_CHESTPLATE);
            mats.add(Material.DIAMOND_CHESTPLATE);
        }
        if(children.contains(EnchantmentGroup.LEGGINGS)){
            mats.add(Material.LEATHER_LEGGINGS);
            mats.add(Material.CHAINMAIL_LEGGINGS);
            mats.add(Material.GOLD_LEGGINGS);
            mats.add(Material.IRON_LEGGINGS);
            mats.add(Material.DIAMOND_LEGGINGS);
        }
        if(children.contains(EnchantmentGroup.BOOTS)){
            mats.add(Material.LEATHER_BOOTS);
            mats.add(Material.CHAINMAIL_BOOTS);
            mats.add(Material.GOLD_BOOTS);
            mats.add(Material.IRON_BOOTS);
            mats.add(Material.DIAMOND_BOOTS);
        }

        int size = mats.size();
        Material[] m = new Material[size];
        for(int i = 0; i < size; i++){
            m[i] = mats.get(i);
        }
        return m;
    }
}
