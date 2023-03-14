package dev.evannregnault.iledesartistes.mixin;

import dev.evannregnault.iledesartistes.interfaces.IEnchantmentBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(BlockEntity.class)
public class IDABlockEntity implements IEnchantmentBlockEntity {
    Map<Enchantment, Integer> enchantments;

    public void setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        if (enchantments != null) {
            return enchantments;
        }
        enchantments = new HashMap<>();
        return enchantments;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    public void writeNbt(NbtCompound nbt, CallbackInfo ci) {
        NbtList enchantmentList = new NbtList();
        Map<Enchantment, Integer> enchantmentIntegerMap = this.getEnchantments();
        for (Enchantment enchantment : enchantmentIntegerMap.keySet()) {
           enchantmentList.add(EnchantmentHelper.createNbt(EnchantmentHelper.getEnchantmentId(enchantment), enchantmentIntegerMap.get(enchantment)));
        }
        nbt.put("Enchantments", enchantmentList);
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    public void readNbt(NbtCompound nbt, CallbackInfo ci) {
        NbtList enchantmentList = (NbtList) nbt.get("Enchantment");
        if(enchantmentList != null) this.setEnchantments(EnchantmentHelper.fromNbt(enchantmentList));
    }
}
