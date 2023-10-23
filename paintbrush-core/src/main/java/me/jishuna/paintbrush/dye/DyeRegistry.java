package me.jishuna.paintbrush.dye;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class DyeRegistry {
    private static Map<Material, Dye> byMaterial = new HashMap<>();
    private static Map<String, Dye> byName = new HashMap<>();

    static {
        for (DyeColor color : DyeColor.values()) {
            Material dyeMaterial = getDyeMaterial(color);

            Dye dye = new Dye(dyeMaterial, color);
            byMaterial.put(dyeMaterial, dye);
            byName.put(color.name(), dye);
        }
    }

    public static Dye getDye(Material material) {
        return byMaterial.get(material);
    }

    public static Dye getDye(String name) {
        if (name == null) {
            return null;
        }
        return byName.get(name);
    }

    public static Material getDyeMaterial(DyeColor color) {
        return switch (color) {
        case BLACK -> Material.BLACK_DYE;
        case BLUE -> Material.BLUE_DYE;
        case BROWN -> Material.BROWN_DYE;
        case CYAN -> Material.CYAN_DYE;
        case GRAY -> Material.GRAY_DYE;
        case GREEN -> Material.GREEN_DYE;
        case LIGHT_BLUE -> Material.LIGHT_BLUE_DYE;
        case LIGHT_GRAY -> Material.LIGHT_GRAY_DYE;
        case LIME -> Material.LIME_DYE;
        case MAGENTA -> Material.MAGENTA_DYE;
        case ORANGE -> Material.ORANGE_DYE;
        case PINK -> Material.PINK_DYE;
        case PURPLE -> Material.PURPLE_DYE;
        case RED -> Material.RED_DYE;
        case WHITE -> Material.WHITE_DYE;
        case YELLOW -> Material.YELLOW_DYE;
        default -> null;
        };
    }
}