package dev.evannregnault.patissartiste;

import dev.evannregnault.patissartiste.loaders.FallingTreeLoader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dev.evannregnault.patissartiste.enchantments.EnchantmentLoader.InitEnchantments;
import static dev.evannregnault.patissartiste.recipes.RecipeInitializer.InitRecipes;

public class PAInitializer implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Patiss'artiste");

	public static final dev.evannregnault.patissartiste.PAConfig CONFIG = dev.evannregnault.patissartiste.PAConfig.createAndLoad();

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

		InitRecipes();
		InitEnchantments();


		LOGGER.info("Patiss'artiste successfully Loaded");
	}
}
