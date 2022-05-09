package io.hikarilan.firestealer.data

import com.mojang.datafixers.util.Pair
import io.hikarilan.firestealer.utils.StdUtils.to
import net.minecraft.data.DataGenerator
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.ValidationContext
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Supplier

class LootGenerator(generator: DataGenerator) : LootTableProvider(generator) {

    private val tables =
        mutableListOf<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>>(
            Supplier { EntityLootTables() as Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> } to LootContextParamSets.ENTITY,
        )

    override fun getTables(): MutableList<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> = tables

    override fun getName(): String = "FireStealer LootTables"

    override fun validate(map: Map<ResourceLocation, LootTable>, validationtracker: ValidationContext) {
        // ignored
    }


}