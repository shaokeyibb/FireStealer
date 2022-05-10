package io.hikarilan.firestealer.data

import com.google.gson.JsonObject
import io.hikarilan.firestealer.FireStealerMod
import io.hikarilan.firestealer.items.FireStealerItems
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.parameters.LootContextParams
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.common.loot.LootModifier
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object LootModifiers {

    val REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, FireStealerMod.MOD_ID)

    private val PHLOGISTON_BOTTLE_DROPS by REGISTRY.registerObject("phlogiston_bottle_drops") {
        PhlogistonBottleDropsLootModifiers.Serializer()
    }

    private val ORE_DROPS by REGISTRY.registerObject("ore_drops") {
        OreDropsLootModifier.Serializer()
    }

    class PhlogistonBottleDropsLootModifiers(private val conditionsIn: Array<LootItemCondition>) :
        LootModifier(conditionsIn) {

        override fun doApply(generatedLoot: List<ItemStack>, context: LootContext): List<ItemStack> {
            return buildList {
                addAll(generatedLoot)
                add(ItemStack(FireStealerItems.PHLOGISTON_BOTTLE))
            }
        }

        class Serializer :
            GlobalLootModifierSerializer<PhlogistonBottleDropsLootModifiers>() {

            override fun read(
                location: ResourceLocation,
                `object`: JsonObject,
                ailootcondition: Array<LootItemCondition>
            ): PhlogistonBottleDropsLootModifiers =
                PhlogistonBottleDropsLootModifiers(ailootcondition)

            override fun write(instance: PhlogistonBottleDropsLootModifiers): JsonObject =
                makeConditions(instance.conditionsIn)

        }
    }

    class OreDropsLootModifier(private val conditionsIn: Array<LootItemCondition>) :
        LootModifier(conditionsIn) {

        override fun doApply(generatedLoot: List<ItemStack>, context: LootContext): List<ItemStack> {
           return if (generatedLoot.all { it.`is`(TagKey.create(Registry.ITEM_REGISTRY, ResourceLocation("forge:raw_ores"))) }){
               generatedLoot
           }else{
               listOf(ItemStack(context.getParam(LootContextParams.BLOCK_STATE).block.asItem()))
           }
        }

        class Serializer :
            GlobalLootModifierSerializer<OreDropsLootModifier>() {

            override fun read(
                location: ResourceLocation,
                `object`: JsonObject,
                ailootcondition: Array<LootItemCondition>
            ): OreDropsLootModifier =
                OreDropsLootModifier(ailootcondition)

            override fun write(instance: OreDropsLootModifier): JsonObject =
                makeConditions(instance.conditionsIn)
        }
    }

}