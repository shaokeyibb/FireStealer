package io.hikarilan.firestealer.items

import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

interface IFireReplaceable {

    fun onFireReplace(level: Level, entity: ItemEntity): ItemStack? {
        return null
    }

}