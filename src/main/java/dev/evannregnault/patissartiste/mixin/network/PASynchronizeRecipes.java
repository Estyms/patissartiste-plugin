package dev.evannregnault.patissartiste.mixin.network;


import dev.evannregnault.patissartiste.api.ServerRecipe;
import net.minecraft.network.packet.s2c.play.SynchronizeRecipesS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;

@Mixin(SynchronizeRecipesS2CPacket.class)
public class PASynchronizeRecipes {
    @Final
    @Shadow
    private List<RecipeEntry<?>> recipes;

    @Inject(method = "<init>(Ljava/util/Collection;)V", at = @At("RETURN"))
    public void onCreated(Collection<RecipeEntry<?>> recipes, CallbackInfo ci) {
        this.recipes.removeIf(recipeEntry -> recipeEntry.value() instanceof ServerRecipe);
    }

}
