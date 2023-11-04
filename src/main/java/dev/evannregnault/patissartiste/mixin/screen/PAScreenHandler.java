package dev.evannregnault.patissartiste.mixin.screen;

import dev.evannregnault.patissartiste.recipes.EnchantmentSmithingRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SmithingScreenHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmithingScreenHandler.class)
public abstract class PAScreenHandler extends ForgingScreenHandler {

    public PAScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }


    @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
    public void onUpdateResult(CallbackInfo callbackInfo) {
        var match = player.getWorld().getRecipeManager().getFirstMatch(EnchantmentSmithingRecipe.EnchantmentSmithingRecipeType.INSTANCE, input, player.getWorld());

        if (match.isPresent()) {
            ItemStack outputItem = match.get().value().craft(this.input, player.getWorld().getRegistryManager());
            output.setStack(0, outputItem);
            callbackInfo.cancel();
        }
    }
}
