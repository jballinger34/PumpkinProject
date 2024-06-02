//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.bukkit.Material;

public class MaterialsParser {
    public MaterialsParser() {
    }

    public static Material[] toMaterial(String[] stringList) {
        List<Material> materials = new ArrayList();
        String[] var2 = stringList;
        int var3 = stringList.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String blockString = var2[var4];
            Pattern whitespace = Pattern.compile("\\s");
            if (whitespace.matcher(blockString).find()) {
                blockString = whitespace.matcher(blockString).replaceAll("");
            }

            Pattern onlyNumbers = Pattern.compile("[^0-9]");
            Material material = Material.matchMaterial(blockString);
            if (material == null) {
                String tempId = onlyNumbers.matcher(blockString).replaceAll("");
                if (!tempId.isEmpty()) {
                    material = Material.getMaterial(tempId);
                }

                if (material == null) {
                    Pattern onlyLetters = Pattern.compile("[^a-zA-Z_]");
                    material = Material.matchMaterial(onlyLetters.matcher(blockString).replaceAll(""));
                }
            }

            if (material != null) {
                materials.add(material);
            }
        }

        return (Material[])materials.toArray(new Material[materials.size()]);
    }

    public static String[] toStringArray(Material[] materials) {
        List<String> items = new ArrayList();
        Material[] var2 = materials;
        int var3 = materials.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Material mat = var2[var4];
            items.add(mat.name());
        }

        return (String[])items.toArray(new String[items.size()]);
    }
}
