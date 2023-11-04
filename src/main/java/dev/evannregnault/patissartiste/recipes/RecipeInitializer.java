package dev.evannregnault.patissartiste.recipes;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class RecipeInitializer {
    public static void InitRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, EnchantmentSmithingRecipe.EnchantmentSmithingRecipeSerializer.ID, EnchantmentSmithingRecipe.EnchantmentSmithingRecipeSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, EnchantmentSmithingRecipe.EnchantmentSmithingRecipeType.ID, EnchantmentSmithingRecipe.EnchantmentSmithingRecipeType.INSTANCE);
    }
}
