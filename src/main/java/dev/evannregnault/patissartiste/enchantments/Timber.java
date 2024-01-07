package dev.evannregnault.patissartiste.enchantments;

import eu.pb4.polymer.core.api.other.PolymerEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class Timber extends Enchantment implements PolymerEnchantment {
    protected static boolean loaded = false;

    @Override
    public boolean canSynchronizeToPolymerClient(ServerPlayerEntity player) {
        return loaded && PolymerEnchantment.super.canSynchronizeToPolymerClient(player);
    }

    @Override
    public boolean canSyncRawToClient(ServerPlayerEntity player) {
        return loaded && PolymerEnchantment.super.canSyncRawToClient(player);
    }

    protected Timber() {
        super(Rarity.RARE, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    public static void setLoaded(boolean loaded) {
        Timber.loaded = loaded;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return loaded && stack.getItem() instanceof AxeItem;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return loaded;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }
}
