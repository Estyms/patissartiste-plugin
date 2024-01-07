package dev.evannregnault.patissartiste.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.evannregnault.patissartiste.api.ServerRecipe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Objects;

public class EnchantmentSmithingRecipe implements SmithingRecipe, ServerRecipe {
        final Ingredient template;
        final Ingredient base;
        final Ingredient addition;
        final Enchantment enchantment;
        final int enchantmentLevel;
        final ItemStack result;

        public EnchantmentSmithingRecipe(Ingredient template, Ingredient base, Ingredient addition, Enchantment enchantment, int enchantmentLevel, ItemStack result) {
            this.template = template;
            this.base = base;
            this.enchantment = enchantment;
            this.addition = addition;
            this.enchantmentLevel = enchantmentLevel;
            this.result = result;
        }

        public Ingredient getTemplate() {
            return template;
        }

        public Ingredient getBase() {
            return base;
        }

        public Ingredient getAddition() {
            return addition;
        }

        public Enchantment getEnchantment() {
            return enchantment;
        }

        @Override
        public boolean testTemplate(ItemStack stack) {
            return this.template.test(stack);
        }

        @Override
        public boolean testBase(ItemStack stack) {
            return this.base.test(stack);
        }

        @Override
        public boolean testAddition(ItemStack stack) {
            return this.addition.test(stack);
        }

        private boolean enchantmentAlreadyOnBook(ItemStack stack) {
            var nbt = EnchantedBookItem.getEnchantmentNbt(stack);
            var enchantmentNbt = new NbtCompound();
            enchantmentNbt.put("id", NbtString.of(Objects.requireNonNull(Registries.ENCHANTMENT.getId(enchantment)).toString()));
            enchantmentNbt.put("lvl", NbtShort.of((short)enchantmentLevel));

            return nbt.stream().anyMatch(nbtElement -> {
                if (nbtElement instanceof NbtCompound) {
                    var a = ((NbtCompound) nbtElement).getString("id").equals(enchantmentNbt.getString("id"));
                    var b = ((NbtCompound) nbtElement).getShort("lvl") == enchantmentNbt.getShort("lvl");
                    return a && b;
                }
                return false;
            });
        }

        @Override
        public boolean matches(Inventory inventory, World world) {
            if (inventory.getStack(1).getEnchantments().stream().anyMatch(e -> e.equals(enchantment))) return false;
            return this.testTemplate(inventory.getStack(0)) && this.testBase(inventory.getStack(1)) && this.testAddition(inventory.getStack(2));
        }

        @Override
        public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
            if (enchantmentAlreadyOnBook(inventory.getStack(1))) return ItemStack.EMPTY;

            ItemStack itemStack = this.result.copy();

            NbtCompound nbtCompound = inventory.getStack(1).getNbt();
            if (nbtCompound != null) {
                itemStack.setNbt(nbtCompound.copy());
            }

            if (itemStack.getItem() == Items.ENCHANTED_BOOK) {
                EnchantedBookItem.addEnchantment(itemStack, new EnchantmentLevelEntry(enchantment, enchantmentLevel));
            } else {
                itemStack.addEnchantment(getEnchantment(), enchantmentLevel);
            }


            return itemStack;
        }

        @Override
        public ItemStack getResult(DynamicRegistryManager registryManager) {
            return this.result;
        }

        @Override
        public RecipeSerializer<EnchantmentSmithingRecipe> getSerializer() {
            var a = EnchantmentSmithingRecipeSerializer.INSTANCE;
            return a;
        }

    public static class EnchantmentSmithingRecipeType implements RecipeType<EnchantmentSmithingRecipe> {
        private EnchantmentSmithingRecipeType() {}
        public static final EnchantmentSmithingRecipeType INSTANCE = new EnchantmentSmithingRecipeType();

        public static final Identifier ID = new Identifier("patissartiste","enchantment_smithing_recipe");

    }

        public static class EnchantmentSmithingRecipeSerializer implements RecipeSerializer<EnchantmentSmithingRecipe> {

            public static final Identifier ID = new Identifier("patissartiste","enchantment_smithing");
            public static final EnchantmentSmithingRecipeSerializer INSTANCE = new EnchantmentSmithingRecipeSerializer();
            public EnchantmentSmithingRecipeSerializer() {
            }
            private static final Codec<EnchantmentSmithingRecipe> CODEC = RecordCodecBuilder.create((instance) -> {
                return instance.group(Ingredient.ALLOW_EMPTY_CODEC.fieldOf("template").forGetter((recipe) -> {
                    return recipe.template;
                }), Ingredient.ALLOW_EMPTY_CODEC.fieldOf("base").forGetter((recipe) -> {
                    return recipe.base;
                }), Ingredient.ALLOW_EMPTY_CODEC.fieldOf("addition").forGetter((recipe) -> {
                    return recipe.addition;
                }), Registries.ENCHANTMENT.getCodec().fieldOf("enchantment").forGetter((recipe) -> {
                    return recipe.enchantment;
                }), Codec.INT.fieldOf("enchantment_level").forGetter((recipe) -> {
                    return recipe.enchantmentLevel;
                }), ItemStack.RECIPE_RESULT_CODEC.fieldOf("result").forGetter((recipe) -> {
                    return recipe.result;
                })).apply(instance, EnchantmentSmithingRecipe::new);
            });

            @Override
            public Codec<EnchantmentSmithingRecipe> codec() {
                return CODEC;
            }

            @Override
            public EnchantmentSmithingRecipe read(PacketByteBuf buf) {
                Ingredient template = Ingredient.fromPacket(buf);
                Ingredient base = Ingredient.fromPacket(buf);
                Ingredient addition = Ingredient.fromPacket(buf);
                Enchantment enchantment = buf.readRegistryValue(Registries.ENCHANTMENT);
                int enchantmentLevel = buf.readInt();
                ItemStack result = buf.readItemStack();
                return new EnchantmentSmithingRecipe(template, base, addition, enchantment, enchantmentLevel, result);
            }

            @Override
            public void write(PacketByteBuf buf, EnchantmentSmithingRecipe recipe) {
                recipe.template.write(buf);
                recipe.base.write(buf);
                recipe.addition.write(buf);
                buf.writeRegistryValue(Registries.ENCHANTMENT, recipe.enchantment);
                buf.writeInt(recipe.enchantmentLevel);
                buf.writeItemStack(recipe.result);
            }
        }
    }
