package dev.evannregnault.ida.mixin;


import com.google.gson.JsonElement;
import dev.evannregnault.ida.IDAInitializer;
import dev.evannregnault.ida.recipes.RecipeGenerator;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class IDARecipeManager {
    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("HEAD"))
    public void apply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        map.put(new Identifier("ida","fireproof_book"), RecipeGenerator.FIREPROOF_RECIPE);
        map.put(new Identifier("ida","fireproof_book2"), RecipeGenerator.FIREPROOF_BOOK_RECIPE);
        if (RecipeGenerator.FIREPROOF_RECIPE_1_20 != null) map.put(new Identifier("ida", "fireproof_book_1_20"), RecipeGenerator.FIREPROOF_RECIPE_1_20);
        if (RecipeGenerator.FIREPROOF_BOOK_RECIPE_1_20 != null) map.put(new Identifier("ida", "fireproof_book2_1_20"), RecipeGenerator.FIREPROOF_BOOK_RECIPE_1_20);
    }
}
