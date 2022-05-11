package io.hikarilan.firestealer.items

import io.hikarilan.firestealer.FireStealerMod
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.crafting.RecipeType

class PulverizedCoalItem : Item(Properties().tab(FireStealerMod.creativeModeTab).rarity(Rarity.COMMON)) {

    override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?): Int = 10 * 20 * 16

}