package me.jishuna.paintbrush;

import java.util.EnumMap;
import java.util.Map;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Tag;

public class ColorUtils {
    private static final Map<DyeColor, Material> WOOL = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, Material> TERRACOTTA = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, Material> GLAZED_TERRACOTTA = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, Material> CONCRETE_POWDER = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, Material> CONCRETE = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, Material> STAINED_GLASS = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, Material> STAINED_GLASS_PANE = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, Material> SHULKER_BOX = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, Material> CARPET = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, Material> CANDLE = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, Material> BANNER = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, Material> WALL_BANNER = new EnumMap<>(DyeColor.class);

    static {
        for (DyeColor color : DyeColor.values()) {
            WOOL.put(color, Material.matchMaterial(color.name() + "_WOOL"));
            TERRACOTTA.put(color, Material.matchMaterial(color.name() + "_TERRACOTTA"));
            GLAZED_TERRACOTTA.put(color, Material.matchMaterial(color.name() + "_GLAZED_TERRACOTTA"));
            CONCRETE_POWDER.put(color, Material.matchMaterial(color.name() + "_CONCRETE_POWDER"));
            CONCRETE.put(color, Material.matchMaterial(color.name() + "_CONCRETE"));
            STAINED_GLASS.put(color, Material.matchMaterial(color.name() + "_STAINED_GLASS"));
            STAINED_GLASS_PANE.put(color, Material.matchMaterial(color.name() + "_STAINED_GLASS_PANE"));
            SHULKER_BOX.put(color, Material.matchMaterial(color.name() + "_SHULKER_BOX"));
            CARPET.put(color, Material.matchMaterial(color.name() + "_CARPET"));
            CANDLE.put(color, Material.matchMaterial(color.name() + "_CANDLE"));
            BANNER.put(color, Material.matchMaterial(color.name() + "_BANNER"));
            WALL_BANNER.put(color, Material.matchMaterial(color.name() + "_WALL_BANNER"));
        }
    }

    public static Material getDyedMaterial(Material material, DyeColor color) {
        if (Tag.WOOL.isTagged(material) && Settings.paintable.getOrDefault("wool", true)) {
            return WOOL.get(color);
        }

        if (Tag.TERRACOTTA.isTagged(material) && Settings.paintable.getOrDefault("terracotta", true)) {
            return TERRACOTTA.get(color);
        }

        if (GLAZED_TERRACOTTA.values().contains(material) && Settings.paintable.getOrDefault("glazed-terracotta", true)) {
            return GLAZED_TERRACOTTA.get(color);
        }

        if (CONCRETE_POWDER.values().contains(material) && Settings.paintable.getOrDefault("concrete-power", true)) {
            return CONCRETE_POWDER.get(color);
        }

        if (CONCRETE.values().contains(material) && Settings.paintable.getOrDefault("concrete", true)) {
            return CONCRETE.get(color);
        }

        if ((material == Material.GLASS || STAINED_GLASS.values().contains(material)) && Settings.paintable.getOrDefault("glass", true)) {
            return STAINED_GLASS.get(color);
        }

        if ((material == Material.GLASS_PANE || STAINED_GLASS_PANE.values().contains(material)) && Settings.paintable.getOrDefault("glass-pane", true)) {
            return STAINED_GLASS_PANE.get(color);
        }

        if ((material == Material.SHULKER_BOX || SHULKER_BOX.values().contains(material)) && Settings.paintable.getOrDefault("shulker-box", true)) {
            return SHULKER_BOX.get(color);
        }

        if (Tag.WOOL_CARPETS.isTagged(material) && Settings.paintable.getOrDefault("carpet", true)) {
            return CARPET.get(color);
        }

        if ((material == Material.CANDLE || CANDLE.values().contains(material)) && Settings.paintable.getOrDefault("candle", true)) {
            return CANDLE.get(color);
        }

        if (BANNER.values().contains(material) && Settings.paintable.getOrDefault("banner", true)) {
            return BANNER.get(color);
        }

        if (WALL_BANNER.values().contains(material) && Settings.paintable.getOrDefault("banner", true)) {
            return WALL_BANNER.get(color);
        }
        return null;
    }
}
