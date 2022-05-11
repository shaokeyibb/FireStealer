package io.hikarilan.firestealer.crafting

import io.hikarilan.firestealer.FireStealerMod
import io.hikarilan.firestealer.items.FireStealerItems
import net.minecraft.core.Registry
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.SimpleRecipeSerializer
import net.minecraft.world.level.Level
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

class PhlogistonRichRawOreRecipe(pId: ResourceLocation) : CustomRecipe(pId) {

    companion object {

        val REGISTRY: DeferredRegister<RecipeSerializer<*>> =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FireStealerMod.MOD_ID)

        val SERIALIZER by REGISTRY.registerObject("crafting_special_phlogiston_rich_raw_ore") {
            Serializer()
        }

    }

    override fun matches(pContainer: CraftingContainer, pLevel: Level): Boolean {
        return (0 until pContainer.containerSize)
            .map { pContainer.getItem(it) }
            .filter { it.item == FireStealerItems.PHLOGISTON_BOTTLE }
            .toList()
            .let { list ->
                list.size == 1
            } && (0 until pContainer.containerSize)
            .map { pContainer.getItem(it) }
            .filter { it != ItemStack.EMPTY && it.item != FireStealerItems.PHLOGISTON_BOTTLE }
            .filter {
                it.`is`(
                    TagKey.create(
                        Registry.ITEM_REGISTRY,
                        ResourceLocation("firestealer:phlogiston_mixed_ores")
                    )
                )
            }
            .toList()
            .let { list ->
                list.size == 2 && list.all { it.`is`(list.first().item) }
            }
    }

    override fun assemble(pContainer: CraftingContainer): ItemStack {
        return (0 until pContainer.containerSize)
            .map { pContainer.getItem(it) }
            .filter { it != ItemStack.EMPTY }
            .find {
                it.`is`(
                    TagKey.create(
                        Registry.ITEM_REGISTRY,
                        ResourceLocation("firestealer:phlogiston_mixed_ores")
                    )
                )
            }
            ?.let { ore ->
                ItemStack(FireStealerItems.PHLOGISTON_RICH_RAW_ORE).apply {
                    this.tag = CompoundTag().also {
                        it.putString("ore", ForgeRegistries.ITEMS.getKey(ore.item).toString())
                    }
                }
            } ?: ItemStack.EMPTY
    }

    override fun canCraftInDimensions(pWidth: Int, pHeight: Int): Boolean = pWidth * pHeight >= this.ingredients.size


    override fun getSerializer(): RecipeSerializer<*> = SERIALIZER

    override fun isSpecial(): Boolean = false

    class Serializer : SimpleRecipeSerializer<PhlogistonRichRawOreRecipe>({ PhlogistonRichRawOreRecipe(it) })

}