package me.jishuna.paintbrush.recipe;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeData {
    private final Map<Character, RecipeChoice> ingredients;
    private final List<String> shape;

    public RecipeData(Map<Character, RecipeChoice> ingredients, List<String> shape) {
        this.ingredients = ingredients;
        this.shape = shape;
    }

    public Recipe asRecipe(NamespacedKey key, ItemStack result) {
        if (this.shape == null) {
            ShapelessRecipe recipe = new ShapelessRecipe(key, result);
            this.ingredients.values().forEach(recipe::addIngredient);
            return recipe;
        } else {
            ShapedRecipe recipe = new ShapedRecipe(key, result);
            recipe.shape(this.shape.toArray(String[]::new));
            this.ingredients.entrySet().forEach(entry -> recipe.setIngredient(entry.getKey(), entry.getValue()));
            return recipe;
        }
    }

    public Map<Character, RecipeChoice> getIngredients() {
        return Collections.unmodifiableMap(this.ingredients);
    }

    public List<String> getShape() {
        return this.shape == null ? null : Collections.unmodifiableList(this.shape);
    }
}
