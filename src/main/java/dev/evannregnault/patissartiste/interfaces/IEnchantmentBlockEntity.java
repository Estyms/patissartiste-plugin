package dev.evannregnault.patissartiste.interfaces;

import net.minecraft.enchantment.Enchantment;

import java.util.Map;

public interface IEnchantmentBlockEntity {
    void setEnchantments(Map<Enchantment, Integer> enchantments);
    Map<Enchantment, Integer> getEnchantments();
}
