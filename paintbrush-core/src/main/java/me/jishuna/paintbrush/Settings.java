package me.jishuna.paintbrush;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.config.annotation.PostLoad;
import me.jishuna.jishlib.items.ItemBuilder;
import me.jishuna.paintbrush.brushdata.PaintbrushData;
import me.jishuna.paintbrush.brushdata.PaintbrushDataType;
import me.jishuna.paintbrush.recipe.RecipeData;

public class Settings {
    private static final NamespacedKey recipeKey = NamespacedKey.fromString("paintbrush:brush_recipe");
    private static ItemStack brush;

    @ConfigEntry("paintbrush.material")
    @Comment("Controls the type of item the paintbrush uses.")
    public static Material brushMaterial = Material.BRUSH;

    @ConfigEntry("paintbrush.model-data")
    @Comment("Controls the custom model data of the paintbrush, allows for changing the texture via resourcepack.")
    public static int brushModelData = 457643;

    @ConfigEntry("paintbrush.recipe")
    @Comment("Controls the recipe for the paintbrush, removing the \"shape\" section will allow for a shapeless recipe.")
    public static RecipeData brushRecipe = createDefaultRecipe();

    @ConfigEntry("dye-per-item")
    @Comment("How much dye (uses) to add to the paintbrush per dye item used on it.")
    public static int dyePerItem = 8;

    @ConfigEntry("max-dye-applied")
    @Comment("The maximum amount of dye (uses) a paintbrush can hold at one time.")
    public static int maxDyeApplied = 256;

    @ConfigEntry("paintable")
    @Comment("Allows enabling and disabling the painting of individual types of blocks.")
    public static Map<String, Boolean> paintable = buildToggleMap();

    @ConfigEntry("allow-cleaning")
    @Comment("Enable or disable cleaning all dye off the paintbrush with a cauldron filled with water.")
    public static boolean allowCleaning = true;

    @ConfigEntry("enable-bstats")
    @Comment("Enable or disable bStats from collecting anonymous data about this plugin.")
    @Comment("Requires a restart for changes to be applied")
    public static boolean bStats = true;

    @ConfigEntry("enable-update-checker")
    @Comment("Enable or disable the update checker.")
    @Comment("Requires a restart for changes to be applied")
    public static boolean updateChecker = true;

    @PostLoad
    private static void postLoad() {
        Bukkit.removeRecipe(recipeKey);
        Bukkit.addRecipe(brushRecipe.asRecipe(recipeKey, getBrush()));
    }

    public static ItemStack getBrush() {
        if (brush == null) {
            brush = ItemBuilder.create(brushMaterial).name(MessageHandler.get("paintbrush.empty-name")).modelData(brushModelData).persistentData(PaintbrushData.DATA_KEY, PaintbrushDataType.INSTANCE, new PaintbrushData(null, 0)).build();
        }

        return brush.clone();
    }

    private static RecipeData createDefaultRecipe() {
        Map<Character, RecipeChoice> ingredients = new HashMap<>();
        ingredients.put('a', new MaterialChoice(Material.WHITE_WOOL));
        ingredients.put('b', new MaterialChoice(Material.COPPER_INGOT));
        ingredients.put('c', new MaterialChoice(Material.STICK));

        List<String> shape = Arrays.asList(" a ", " b ", " c ");
        return new RecipeData(ingredients, shape);
    }

    private static Map<String, Boolean> buildToggleMap() {
        Map<String, Boolean> map = new HashMap<>();

        map.put("wool", true);
        map.put("terracotta", true);
        map.put("glazed-terracotta", true);
        map.put("concrete-powder", true);
        map.put("concrete", true);
        map.put("glass", true);
        map.put("glass-pane", true);
        map.put("shulker-box", true);
        map.put("carpet", true);
        map.put("candle", true);
        map.put("banner", true);

        return map;
    }
}
