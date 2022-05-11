package io.hikarilan.firestealer.crafting

import io.hikarilan.firestealer.items.FireStealerItems
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.common.Tags
import net.minecraftforge.common.brewing.BrewingRecipe

class PhlogistonBottleBrewingRecipe : BrewingRecipe(
    Ingredient.of(FireStealerItems.PULVERIZED_COAL_FLASK),
    Ingredient.of(Tags.Items.DUSTS_GLOWSTONE),
    ItemStack(FireStealerItems.PHLOGISTON_BOTTLE)
)