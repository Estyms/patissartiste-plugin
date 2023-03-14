package dev.evannregnault.iledesartistes.mixin;


import dev.evannregnault.iledesartistes.interfaces.IEnchantmentBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.block.Block.dropStack;
import static net.minecraft.block.Block.getDroppedStacks;

@Mixin(Block.class)
public abstract class IDABlock {

    @Inject(method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void dropStacks(BlockState state, World world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack, CallbackInfo ci) {
        List<ItemStack> items = getDroppedStacks(state, (ServerWorld)world, pos, blockEntity, entity, stack);
        Item[] validItems = {
                Items.SHULKER_BOX, Items.WHITE_SHULKER_BOX, Items.LIGHT_GRAY_SHULKER_BOX, Items.GRAY_SHULKER_BOX, Items.BLACK_SHULKER_BOX, Items.BROWN_SHULKER_BOX,
                Items.RED_SHULKER_BOX, Items.ORANGE_SHULKER_BOX, Items.YELLOW_SHULKER_BOX, Items.LIME_SHULKER_BOX, Items.GREEN_SHULKER_BOX,
                Items.CYAN_SHULKER_BOX, Items.LIGHT_BLUE_SHULKER_BOX, Items.BLUE_SHULKER_BOX, Items.PURPLE_SHULKER_BOX, Items.MAGENTA_SHULKER_BOX, Items.PINK_SHULKER_BOX,};
        if (items.size() == 1 && blockEntity instanceof ShulkerBoxBlockEntity && Arrays.stream(validItems).anyMatch(x->x==items.get(0).getItem())) {
            ItemStack stackx = items.get(0);
            EnchantmentHelper.set(((IEnchantmentBlockEntity) blockEntity).getEnchantments(), stackx);
            dropStack(world, pos, stackx);
            state.onStacksDropped((ServerWorld)world, pos, stack, true);
            ci.cancel();
        }
    }

    @Inject(method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;)Ljava/util/List;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void dropStacks(BlockState state, WorldAccess world, BlockPos pos, BlockEntity blockEntity, CallbackInfo ci) {
        List<ItemStack> items = getDroppedStacks(state, (ServerWorld)world, pos, blockEntity);
        Item[] validItems = {
                Items.SHULKER_BOX, Items.WHITE_SHULKER_BOX, Items.LIGHT_GRAY_SHULKER_BOX, Items.GRAY_SHULKER_BOX, Items.BLACK_SHULKER_BOX, Items.BROWN_SHULKER_BOX,
                Items.RED_SHULKER_BOX, Items.ORANGE_SHULKER_BOX, Items.YELLOW_SHULKER_BOX, Items.LIME_SHULKER_BOX, Items.GREEN_SHULKER_BOX,
                Items.CYAN_SHULKER_BOX, Items.LIGHT_BLUE_SHULKER_BOX, Items.BLUE_SHULKER_BOX, Items.PURPLE_SHULKER_BOX, Items.MAGENTA_SHULKER_BOX, Items.PINK_SHULKER_BOX,};
        if (items.size() == 1 && blockEntity instanceof ShulkerBoxBlockEntity && Arrays.stream(validItems).anyMatch(x->x==items.get(0).getItem())) {
            ItemStack stackx = items.get(0);
            EnchantmentHelper.set(((IEnchantmentBlockEntity) blockEntity).getEnchantments(), stackx);
            dropStack((ServerWorld)world, pos, stackx);
            state.onStacksDropped((ServerWorld)world, pos, stackx, true);
            ci.cancel();
        }
    }
}
