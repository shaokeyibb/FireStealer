package io.hikarilan.firestealer.loot

import io.hikarilan.firestealer.FireStealerMod
import io.hikarilan.firestealer.loot.conditions.HasBlockTag
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType

object FireStealerLoots {

    lateinit var HAS_BLOCK_TAG: LootItemConditionType
        private set

    fun init() {
        HAS_BLOCK_TAG = Registry.register(
            Registry.LOOT_CONDITION_TYPE, ResourceLocation("${FireStealerMod.MOD_ID}:has_block_tag"),
            LootItemConditionType(HasBlockTag.ConditionSerializer())
        )
    }

}