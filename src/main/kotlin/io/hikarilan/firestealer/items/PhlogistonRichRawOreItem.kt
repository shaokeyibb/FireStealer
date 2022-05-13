package io.hikarilan.firestealer.items

import io.hikarilan.firestealer.FireStealerMod
import net.minecraft.ChatFormatting
import net.minecraft.core.NonNullList
import net.minecraft.core.Registry
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.*
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

    override fun fillItemCategory(pCategory: CreativeModeTab, pItems: NonNullList<ItemStack>) {
        if (allowdedIn(pCategory)) {
            ForgeRegistries.ITEMS.tags()!!.getTag(
                TagKey.create(
                    Registry.ITEM_REGISTRY,
                    ResourceLocation("firestealer:phlogiston_mixed_ores")
                )
            ).forEach { item ->
                pItems.add(ItemStack(this).apply {
                    this.tag = CompoundTag().also {
                        it.putString("ore", ForgeRegistries.ITEMS.getKey(item).toString())
                    }
                })
            }
        }
    }

    override fun appendHoverText(
        pStack: ItemStack,
        pLevel: Level?,
        pTooltipComponents: MutableList<Component>,
        pIsAdvanced: TooltipFlag
    ) {
        val ore = ForgeRegistries.ITEMS.getValue(ResourceLocation(pStack.tag?.getString("ore") ?: return))?.description
            ?: return
        pTooltipComponents.add(
            TranslatableComponent(
                "item.firestealer.phlogiston_rich_raw_ore.tooltip",
                ore
            ).withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD)
        )
    }

}