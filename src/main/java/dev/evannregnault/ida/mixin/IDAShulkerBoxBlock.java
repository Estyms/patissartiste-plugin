package dev.evannregnault.ida.mixin;

import dev.evannregnault.ida.interfaces.IEnchantmentBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ShulkerBoxBlock.class)
public class IDAShulkerBoxBlock {
    @Inject(method = "onPlaced", at = @At(value = "HEAD"))
    public void onPlace(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity != null)
            ((IEnchantmentBlockEntity) blockEntity).setEnchantments(EnchantmentHelper.get(itemStack));
    }

    @Inject(method = "onBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci, BlockEntity blockEntity, ShulkerBoxBlockEntity shulkerBoxBlockEntity, ItemStack itemStack, ItemEntity itemEntity) {
        if (((IEnchantmentBlockEntity)shulkerBoxBlockEntity).getEnchantments() != null) {
            EnchantmentHelper.set(((IEnchantmentBlockEntity) shulkerBoxBlockEntity).getEnchantments(), itemStack);
            itemEntity.setStack(itemStack);
        }
    }
}
