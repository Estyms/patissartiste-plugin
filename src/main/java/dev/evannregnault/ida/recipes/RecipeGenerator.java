package dev.evannregnault.ida.recipes;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;

import java.util.function.Consumer;

public class RecipeGenerator  {
    public static JsonObject FIREPROOF_RECIPE = FireproofRecipes.FireproofRecipe();
    public static JsonObject FIREPROOF_BOOK_RECIPE = FireproofRecipes.FireproofBookRecipe();
}
