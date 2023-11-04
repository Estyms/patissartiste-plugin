package dev.evannregnault.patissartiste.enchantments;

import eu.pb4.polymer.core.api.other.PolymerEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;

public class Fireproof extends Enchantment implements PolymerEnchantment {
    public Fireproof() {
        super(Rarity.RARE, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        Item[] validItems = {
                Items.SHULKER_BOX, Items.WHITE_SHULKER_BOX, Items.LIGHT_GRAY_SHULKER_BOX, Items.GRAY_SHULKER_BOX, Items.BLACK_SHULKER_BOX, Items.BROWN_SHULKER_BOX,
                Items.RED_SHULKER_BOX, Items.ORANGE_SHULKER_BOX, Items.YELLOW_SHULKER_BOX, Items.LIME_SHULKER_BOX, Items.GREEN_SHULKER_BOX,
                Items.CYAN_SHULKER_BOX, Items.LIGHT_BLUE_SHULKER_BOX, Items.BLUE_SHULKER_BOX, Items.PURPLE_SHULKER_BOX, Items.MAGENTA_SHULKER_BOX, Items.PINK_SHULKER_BOX,
                Items.ELYTRA,  Items.BOW, Items.CROSSBOW, Items.TRIDENT};
        return Arrays.stream(validItems).anyMatch(x -> x == stack.getItem());
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }
}
