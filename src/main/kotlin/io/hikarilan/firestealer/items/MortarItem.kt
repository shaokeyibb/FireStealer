package io.hikarilan.firestealer.items

import io.hikarilan.firestealer.FireStealerMod
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import kotlin.random.Random
import kotlin.random.asJavaRandom

class MortarItem : Item(
    Properties()
        .tab(FireStealerMod.creativeModeTab)
        .stacksTo(1)
        .durability(10)
        .setNoRepair()
        .rarity(Rarity.COMMON)
) {

    override fun getContainerItem(itemStack: ItemStack): ItemStack {
        return itemStack.copy()
            .apply { hurt(1, Random.asJavaRandom(), null) }
            .let { if (it.damageValue >= it.maxDamage) ItemStack.EMPTY else it }
    }

    override fun hasContainerItem(stack: ItemStack): Boolean = true

}