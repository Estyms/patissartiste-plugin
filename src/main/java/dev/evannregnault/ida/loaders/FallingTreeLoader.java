package dev.evannregnault.ida.loaders;

import dev.evannregnault.ida.enchantments.EnchantmentLoader;
import fr.rakambda.fallingtree.common.config.real.EnchantmentConfiguration;
import fr.rakambda.fallingtree.common.wrapper.IEnchantment;
import fr.rakambda.fallingtree.fabric.FallingTree;
import fr.rakambda.fallingtree.fabric.common.FallingTreeCommonsImpl;
import fr.rakambda.fallingtree.fabric.common.wrapper.EnchantmentWrapper;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collection;

public class FallingTreeLoader {
    public static void InitFallingTree() {
        Logger logger = LoggerFactory.getLogger("Ile des Artistes");
        Registry.register(Registries.ENCHANTMENT, new Identifier("ida", "timber"), EnchantmentLoader.TIMBER);

        try {
            // Set custom Enchantment
            Collection<IEnchantment> enchantments;
            Field chopperEnchantments = FallingTreeCommonsImpl.class.getDeclaredField("chopperEnchantments");
            chopperEnchantments.setAccessible(true);
            enchantments = (Collection<IEnchantment>) chopperEnchantments.get(FallingTree.getMod());
            enchantments.clear();
            enchantments.add(new EnchantmentWrapper(EnchantmentLoader.TIMBER));
            chopperEnchantments.set(FallingTree.getMod(), enchantments);

            // Force mode
            Field registerEnchant = EnchantmentConfiguration.class.getDeclaredField("registerEnchant");
            registerEnchant.setAccessible(true);
            registerEnchant.set(FallingTree.getMod().getConfiguration().getEnchantment(), true);
            logger.info("Falling Tree extension : LOADED");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
