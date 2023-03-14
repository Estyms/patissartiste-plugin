package dev.evannregnault.iledesartistes.mixin;

import dev.evannregnault.iledesartistes.enchantments.EnchantmentLoader;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class IDAItemEntity {
    @Shadow public abstract ItemStack getStack();

    @Inject(method = "isFireImmune", at = @At("HEAD"), cancellable = true)
    public void isFireImmune(CallbackInfoReturnable<Boolean> cir) {
        if (EnchantmentHelper.getLevel(EnchantmentLoader.FIREPROOF ,this.getStack()) > 0) {
            cir.setReturnValue(true);
        }
    }
}
