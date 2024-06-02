//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.service;

public enum PermissionNode {
    ADMIN("admin"),
    LIST("list"),
    BOOK("book"),
    NAMES("names"),
    TABLE("table"),
    ENCHANT("enchant"),
    ENCHANT_VANILLA("enchant.vanilla");

    private static final String PREFIX = "EnchantmentAPI.";
    private final String node;

    private PermissionNode(String subperm) {
        this.node = "EnchantmentAPI." + subperm;
    }

    public String getNode() {
        return this.node;
    }
}
