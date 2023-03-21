package dev.evannregnault.ida;

import dev.evannregnault.ida.loaders.FallingTreeLoader;
import io.wispforest.owo.config.annotation.Config;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dev.evannregnault.ida.enchantments.EnchantmentLoader.InitEnchantments;

public class IDAInitializer implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Ile des Artistes");

	public static final dev.evannregnault.ida.IDAConfig CONFIG = dev.evannregnault.ida.IDAConfig.createAndLoad();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		if (FabricLoader.getInstance().isModLoaded("fallingtree")) {
			FallingTreeLoader.InitFallingTree();
		} else {
			LOGGER.info("Fallingtree mod not found, you night want to install it to enable the timber enchantment");
		}

		InitEnchantments();


		LOGGER.info("IDA successfully Loaded");
	}
}
