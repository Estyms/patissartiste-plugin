package dev.evannregnault.iledesartistes.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EnchantmentLoader {
    public static Enchantment TIMBER = new Timber();
    public static Enchantment FIREPROOF = new Fireproof();

    public static void InitEnchantments() {
        Registry.register(Registries.ENCHANTMENT, new Identifier("iledesartistes", "fireproof"), FIREPROOF);
    }
}
