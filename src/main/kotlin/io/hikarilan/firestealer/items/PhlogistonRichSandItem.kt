package io.hikarilan.firestealer.items

import io.hikarilan.firestealer.FireStealerMod
import io.hikarilan.firestealer.blocks.FireStealerBlocks
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level

class PhlogistonRichSandItem : BlockItem(
    FireStealerBlocks.PHLOGISTON_RICH_SAND,
    Properties().tab(FireStealerMod.creativeModeTab).rarity(Rarity.COMMON)
), IFireReplaceable {

    override fun onFireReplace(level: Level, entity: ItemEntity): ItemStack? {
        if (level.isClientSide) return null
        return ItemStack(Items.GLASS,entity.item.count)
    }

}