package dev.evannregnault.ida.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.evannregnault.ida.IDAInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

public class FireproofRecipes {

    private static JsonObject generateFireproofBookJson() {
        return generateFireproofBookJson(false);
    }

    private static JsonObject generateFireproofBookJson(boolean mergeEnchantments) {
        JsonObject result = new JsonObject();
        result.addProperty("item", "minecraft:enchanted_book");

        JsonObject bookData = new JsonObject();

        if (mergeEnchantments) {
            JsonObject dollarData = new JsonObject();
            dollarData.addProperty("value", "base");
            JsonObject paths = new JsonObject();
            paths.addProperty("/StoredEnchantments\\[\\d+\\]/", "append");
            dollarData.add("paths", paths);
            bookData.add("$", dollarData);
        }

        JsonArray storedEnchantments = new JsonArray();
        JsonObject fireproofEnchantment = new JsonObject();
        fireproofEnchantment.addProperty("id", "ida:fireproof");
        fireproofEnchantment.addProperty("lvl", 1);
        storedEnchantments.add(fireproofEnchantment);
        bookData.add("StoredEnchantments", storedEnchantments);
        result.add("data", bookData);

        return result;
    }

    public static JsonObject FireproofBookRecipe1_20() {

        if (IDAInitializer.CONFIG.FireproofAnvilRecipe()) return null;

        JsonObject fireproofRecipe = new JsonObject();

        JsonObject base = new JsonObject();
        base.addProperty("item", "minecraft:enchanted_book");

        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", "minecraft:netherite_ingot");

        JsonObject template = new JsonObject();
        template.addProperty("item", "minecraft:netherite_upgrade_smithing_template");

        fireproofRecipe.addProperty("type", "nbtcrafting:smithing_transform");
        fireproofRecipe.add("base", base);
        fireproofRecipe.add("addition", ingredient);
        fireproofRecipe.add("template", template);
        fireproofRecipe.add("result", generateFireproofBookJson(true));

        return fireproofRecipe;
    }

    public static JsonObject FireproofRecipe1_20() {

        if (IDAInitializer.CONFIG.FireproofAnvilRecipe()) return null;

        JsonObject fireproofRecipe = new JsonObject();

        JsonObject base = new JsonObject();
        base.addProperty("item", "minecraft:book");

        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", "minecraft:netherite_ingot");

        JsonObject template = new JsonObject();
        template.addProperty("item", "minecraft:netherite_upgrade_smithing_template");

        fireproofRecipe.addProperty("type", "nbtcrafting:smithing_transform");
        fireproofRecipe.add("base", base);
        fireproofRecipe.add("addition", ingredient);
        fireproofRecipe.add("template", template);
        fireproofRecipe.add("result", generateFireproofBookJson());

        return fireproofRecipe;
    }



    public static JsonObject FireproofRecipe() {
        JsonObject fireproofRecipe = new JsonObject();

        JsonObject base = new JsonObject();
        base.addProperty("item", "minecraft:book");

        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", "minecraft:netherite_ingot");

        JsonObject result = generateFireproofBookJson();

        return getJsonObject(fireproofRecipe, base, ingredient, result);
    }

    public static JsonObject FireproofBookRecipe() {
        JsonObject fireproofRecipe = new JsonObject();

        JsonObject base = new JsonObject();
        base.addProperty("item", "minecraft:enchanted_book");

        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", "minecraft:netherite_ingot");

        JsonObject result = generateFireproofBookJson(true);

        return getJsonObject(fireproofRecipe, base, ingredient, result);
    }

    @NotNull
    private static JsonObject getJsonObject(JsonObject fireproofRecipe, JsonObject base, JsonObject ingredient, JsonObject result) {
        if (IDAInitializer.CONFIG.FireproofAnvilRecipe()) {
            fireproofRecipe.addProperty("type", "nbtcrafting:anvil");
            fireproofRecipe.add("base", base);
            fireproofRecipe.add("ingredient", ingredient);
            fireproofRecipe.add("result", result);
        } else {
            fireproofRecipe.addProperty("type", "nbtcrafting:smithing");
            fireproofRecipe.add("base", base);
            fireproofRecipe.add("ingredient", ingredient);
            fireproofRecipe.add("result", result);
        }

        return fireproofRecipe;
    }
}
