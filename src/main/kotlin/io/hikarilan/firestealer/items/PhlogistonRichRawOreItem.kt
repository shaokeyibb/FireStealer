package io.hikarilan.firestealer.items

import io.hikarilan.firestealer.FireStealerMod
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraftforge.registries.ForgeRegistries

class PhlogistonRichRawOreItem : Item(
    Properties()
        .tab(FireStealerMod.creativeModeTab)
        .rarity(Rarity.COMMON)
), IFireReplaceable {

    override fun onFireReplace(level: Level, entity: ItemEntity): ItemStack? {
        if (level.isClientSide) return null

        val ore = entity.item.tag.let {
            if (it == null) return null
            if (!it.contains("ore")) return null
            ForgeRegistries.ITEMS.getValue(ResourceLocation(it.getString("ore")))
        } ?: return null

        val recipe = level.recipeManager.getRecipeFor(
            RecipeType.SMELTING,
            SimpleContainer(ItemStack(ore)),
            level
        )

        if (!recipe.isPresent) return null

        return recipe.get().resultItem.copy().apply { count = entity.item.count }
    }

}