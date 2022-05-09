package io.hikarilan.firestealer.loot.conditions

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import io.hikarilan.firestealer.loot.FireStealerLoots
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.util.GsonHelper
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.Serializer
import net.minecraft.world.level.storage.loot.parameters.LootContextParams
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType

class HasBlockTag(
    private val tag: TagKey<Block>,
    private val reversed: Boolean = false
) : LootItemCondition {

    override fun test(t: LootContext): Boolean {
        return (t.getParamOrNull(LootContextParams.BLOCK_STATE)?.`is`(tag) ?: false).let { if (reversed) !it else it }
    }

    override fun getType(): LootItemConditionType = FireStealerLoots.HAS_BLOCK_TAG

    class ConditionSerializer : Serializer<HasBlockTag> {

        override fun serialize(
            pJson: JsonObject,
            pValue: HasBlockTag,
            pSerializationContext: JsonSerializationContext
        ) {
            pJson.addProperty("reversed", pValue.reversed)
        }

        override fun deserialize(pJson: JsonObject, pSerializationContext: JsonDeserializationContext): HasBlockTag {
            return HasBlockTag(
                tag = TagKey.create(Registry.BLOCK_REGISTRY, ResourceLocation(GsonHelper.getAsString(pJson,"tag").substring(1))),
                reversed = GsonHelper.getAsBoolean(pJson, "reversed", false)
            )
        }

    }
}