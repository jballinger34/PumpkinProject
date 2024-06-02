//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.service;

public interface ConfigNode {
    String getPath();

    VarType getVarType();

    SubType getSubType();

    Object getDefaultValue();

    public static enum SubType {
        PERCENTAGE,
        Y_VALUE,
        HEALTH,
        NATURAL_NUMBER;

        private SubType() {
        }
    }

    public static enum VarType {
        STRING,
        INTEGER,
        DOUBLE,
        BOOLEAN,
        LIST;

        private VarType() {
        }
    }
}
