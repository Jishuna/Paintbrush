package me.jishuna.paintbrush.recipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;

import me.jishuna.jishlib.config.adapter.TypeAdapter;

public class RecipeAdapter implements TypeAdapter<RecipeData> {

    @Override
    public RecipeData read(ConfigurationSection section, String path) {
        ConfigurationSection recipeSection = section.getConfigurationSection(path);
        if (recipeSection == null) {
            return null;
        }

        ConfigurationSection ingredients = recipeSection.getConfigurationSection("ingredients");
        if (ingredients == null) {
            return null;
        }

        Map<Character, RecipeChoice> ingredientMap = new HashMap<>();
        for (String key : ingredients.getKeys(false)) {
            char recipeKey = key.charAt(0);

            if (ingredients.isList(key)) {
                RecipeChoice choice = new MaterialChoice(ingredients.getStringList(key).stream().map(Material::matchMaterial).filter(Objects::nonNull).toArray(Material[]::new));
                ingredientMap.put(recipeKey, choice);
            } else {
                Material material = Material.matchMaterial(ingredients.getString(key));
                if (material == null)
                    continue;

                ingredientMap.put(recipeKey, new MaterialChoice(material));
            }
        }

        List<String> shape = recipeSection.isList("shape") ? recipeSection.getStringList("shape") : null;
        return new RecipeData(ingredientMap, shape);
    }

    @Override
    public void write(ConfigurationSection section, String path, Object recipe, boolean overwrite) {
        if (!overwrite && section.isSet(path)) {
            return;
        }
        RecipeData data = (RecipeData) recipe;

        for (Entry<Character, RecipeChoice> ingredient : data.getIngredients().entrySet()) {
            if (ingredient.getValue() instanceof MaterialChoice materialChoice) {
                String ingredientPath = new StringBuilder(path).append(".ingredients.").append(ingredient.getKey()).toString();
                List<Material> choices = materialChoice.getChoices();

                if (choices.size() == 1) {
                    section.set(ingredientPath, choices.get(0).getKey().toString());
                } else {
                    section.set(ingredientPath, choices.stream().map(mat -> mat.getKey().toString()).toList());
                }
            }
        }

        if (data.getShape() != null) {
            section.set(path + ".shape", data.getShape());
        }
    }
}
